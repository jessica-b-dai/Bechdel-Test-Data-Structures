import javafoundations.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * HollywoodGraph creates an undirected graph with vertices that represent
 * movies and actors. The edges in the graph represents a relationship in which
 * an actor played a role in a movie. 
 *
 * @author Beatrix Kim
 * @author Jessica Dai
 * @author Jessica Yang
 * @version December 11, 2023
 */
public class HollywoodGraph
{
    private ArrayList<Movie> movies; // keeps track of movies in data file
    private ArrayList<Actor> actors; // keeps track of actors in data file
    private AdjListGraph<String> graph; // the actual graph with vertices and edges 

    /**
     * Constructor for objects of class HollywoodGraph
     * @param fileName name of file to read data from
     * @param testsFiles the name of the file containing test result data
     */
    public HollywoodGraph(String fileName, String testsFile){
        movies = new ArrayList<Movie>(); // empty movie collection
        actors = new ArrayList<Actor>(); // empty actor collection
        readData(fileName); // fills movies and actors lists
        readTests(testsFile); // fills in passedTests variable for each movie
        this.writeTGF("outputFile.txt");
        // creates a graph using data from newly created tgf file
        graph = AdjListGraph.AdjListGraphFromFile("outputFile.txt");
    }

    /**
     * Populates the movies and actors lists based off data contained in the input file. Files
     * must contain data in the order: "MOVIE","ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER"
     * @param fileName file with data to be read into a HollywoodGraph
     */
    public void readData(String fileName){
        try {
            Scanner fileScan = new Scanner (new File(fileName));
            fileScan.nextLine(); 
            while (fileScan.hasNextLine()) {
                // scanner scans by each line
                String line = fileScan.nextLine(); // ignores the first line of the file which contains no information
                line = line.replaceAll("\"", ""); // ignore quotation marks
                Scanner lineScan = new Scanner(line).useDelimiter(","); // new scanner to traverse each line by commas 
                while(lineScan.hasNext()){
                    String movie = lineScan.next(); // first element in the line
                    String actor = lineScan.next(); // second element in the line
                    lineScan.next();
                    lineScan.next();
                    lineScan.next();
                    String gender = lineScan.next(); // sixth and last element in the line
                    Movie m = new Movie(movie); // creates a default new movie from data
                    Actor a = new Actor(actor, gender); // creates a default new actor from data

                    if(hasMovie(movie) != -1){ 
                        // if the collection of movies already has the movie
                        m = movies.get(hasMovie(movie)); // pre-existing movie assigned to newly created movie
                    } else movies.add(m);

                    if(hasActor(actor) != -1){
                        // if the collection of actors already has the actor
                        a = actors.get(hasActor(actor)); // pre-existing actor assigned to newly created actor
                    } else actors.add(a);

                    m.addActor(a); // adds actor to the movie's list of actors
                    a.addMovie(m); // adds movie to the actor's list of movies
                }
                lineScan.close(); // close scanner
            }
            fileScan.close(); // close scanner
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * **Assumes all movies are already added to HollywoodGraph** 
     * (Which is true, since we call it in the constructor after readData() is called.)
     * Instantiates the passedLists variable for each movie in HollywoodGraph with result data contained 
     * in the input file. Files must contain data in the order: 
     * movie,bechdel,peirce,landau,feldman,villareal,hagen,ko,villarobos,waithe,koeze_dottle,uphold,white,rees-davies
     * @param fileName file with test result data to be read into HollywoodGraph's movies
     */
    public void readTests(String fileName){
        try {
            Scanner fileScan = new Scanner (new File(fileName));
            fileScan.nextLine(); 
            while (fileScan.hasNextLine()) {
                // scanner scans by each line
                String line = fileScan.nextLine(); // ignores the first line of the file which contains no information
                Scanner lineScan = new Scanner(line).useDelimiter(","); // new scanner to traverse each line by commas 
                String movie = lineScan.next(); // first element in the line is always a movie title
                while(lineScan.hasNext()){
                    if (hasMovie(movie) > -1)
                        movies.get(hasMovie(movie)).addPassedTest(lineScan.nextInt()); // if movie exists, add result
                }
                lineScan.close(); // close scanner
            }
            fileScan.close(); // close scanner
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Using the file that the Hollywood Graph is constructed with, writes the data into a 
     * tgf file, with vertices being represented by movies and actors, and edges if an actor 
     * plays in a movie and vice versa
     * @param outFileName file that the tgf format of the graph is written to
     */
    public void writeTGF(String outFileName){
        try{
            PrintWriter writer = new PrintWriter (new File(outFileName));
            int count = 1; // keeps track of vertex number
            // loop through movies to add vertices in the tgf
            for (Movie m : movies) {
                String input = count + " " + m.getTitle(); // format: "vertex title" (1 Movie, 2 Movie, etc)
                m.setGraphIndex(count); // sets the movie's index in the graph
                count++;
                writer.println(input); // add string to file
            }
            // loop through actors to add vertices in the tgf
            for (Actor a : actors) {
                String input = count + " " + a.getName();
                a.setGraphIndex(count);// sets its graph index
                count++;
                writer.println(input);
            }
            writer.println("#");
            // loop through movies and add edges with its actors
            for (int i = 0; i < movies.size(); i++) {
                Movie temp = movies.get(i);
                ArrayList<Actor> actorsT = temp.getActors(); // actors in each movie
                for (Actor a: actorsT) {
                    int aIndex = a.getGraphIndex();
                    int mIndex = temp.getGraphIndex();

                    String edge1 = aIndex + " " + mIndex; // add edge
                    String edge2 = mIndex + " " + aIndex; // add edge both ways because its undirected

                    writer.println(edge1); // add edges to tgf file
                    writer.println(edge2);
                }
            }
            writer.close(); // close scanner
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Finds whether an actor already exists in a HollywoodGraph's actor list or not. Returns
     * -1 if the actor is not in the list and returns the actor's index in the actor list if they
     * do exist.
     * @param aName name of actor to check for in the list of actors
     * @return the index of the actor in the actor list
     */
    public int hasActor(String aName){
        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i).getName().equals(aName))
                return i;
        }
        return -1;
    }

    /**
     * Finds whether a movie already exists in a HollywoodGraph's movies list or not. Returns
     * -1 if the movie is not in the list and returns the movie's index in the movie list if it
     * does exist.
     * @param mTitle title of movie to check for in the movie list
     * @return the index of the movie in the movie list
     */
    public int hasMovie(String mTitle){
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getTitle().equals(mTitle)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Task 2.0
     * Given a movie title, prints out a list of actors in the movie.
     * Calls on the toString method for Movie objects
     * @param mT the title of the movie
     */
    public void listActorsInMovie(String mT){
        int idx = hasMovie(mT);
        if (idx >= 0)
            System.out.println(movies.get(idx));
        else System.out.println("Movie does not exist in the data file.");
    }

    /**
     * Task 2.1
     * Given an actor's name, prints out a list of movies the actor has been
     * in. Calls on the toString method for Actor objects
     * @param aN the name of the actor
     */
    public void listMoviesWithActor(String aN){
        int idx = hasActor(aN);
        if (idx >= 0)
            System.out.println(actors.get(idx));
        else System.out.println("Actor does not exist in the data file.");
    }

    /**
     * Task 2.2
     * Given two actors a1 and a2 (here represented by their String names), performs
     * a BFS traversal of the graph to determine the number of movies that separate 
     * the actors, noninclusive of the movie in which the actors actually costar with 
     * another. 
     * @param a1 the name of actor 1
     * @param a2 the name of actor 2
     * @return deg the degree of movie separation between two actors
     */
    public int findActorSeparation(String a1, String a2){
        int deg = -1; // default degree of separation - actors are not connected in any way
        boolean prev = false; // whether the preceding element in the graph is a movie 
        if (hasActor(a1) == -1 && hasActor(a2) == -1)
            return deg;  
        // contains the vertices of the graph in breadth-first order, using the index
        // of the first actor in the graph as the starting point for BFS traversal
        ArrayIterator<String> orderGraph = graph.iteratorBFS(graph.getObjIdx(a1));
        while (orderGraph.hasNext()){
            String element = orderGraph.next(); // vertex element in graph which is either a movie or actor
            if (hasMovie(element) != -1){
                // if the element is a movie
                if (prev == false) // and the previous element was not a movie
                    deg++; // add a degree of separation
                prev = true;
            } else {
                // otherwise if the element is an actor
                prev = false;
                if (hasActor(element) == hasActor(a2))
                    return deg; // return degree if the current vertex is the second actor
            }
        } // loop ends when there are no more vertices left
        return -1;
    }

    /**
     * Task 2.3
     * The BJJ test has two parts: 
     * First - Out of thirteen Bechdel-like tests, what is the minimum number of tests which should be passed in
     * order for a movie to be considered diverse?
     * Second - What is the minimum percentage of women that a movie's cast should have?
     * A movie that satisfies both parts successfully passes the BJJ test.
     * @return passed the ArrayList of movies which passes the BJJ test
     */
    public ArrayList<String> passBJJTest(){
        Scanner scan = new Scanner(System.in);
        System.out.println("\nWe are creating a new Bechdel-like test. Out of thirteen Bechdel-like tests, \nwhat is the minimum number which should be passed for a film to be considered diverse? (1-13):");
        int input = scan.nextInt(); // user's input
        while (input > 13 || input < 1){
            System.out.println("Invalid value entered. Please enter a number 1-13:");
            input = scan.nextInt();
        }
        System.out.println("\nWe're not done yet!! (\"⊙﹏⊙) We are also looking to see the minimum percent of women in a movie cast should be.");
        System.out.println("Please enter a value (0-100):");
        double percent = scan.nextDouble(); // user's input
        scan.close(); // close scanner

        ArrayList<String> passed1 = new ArrayList<String>(); // keeps track of which movies pass results test
        ArrayList<String> passed2 = new ArrayList<String>(); // keeps track of which movies pass gender percent test
        ArrayList<String> passed3 = new ArrayList<String>(); // keeps track of which movies pass both tests

        for (Movie m : movies){
            // tests each movie in HollywoodGraph to see if it passes each part(s) of our test
            if (m.getNumPassedTests() >= input)
                passed1.add(m.getTitle());
            if (testPercentOfWomen(m, percent/100))
                passed2.add(m.getTitle());
            if (m.getNumPassedTests() >= input && testPercentOfWomen(m, percent/100))
                passed3.add(m.getTitle());
        }
        System.out.println("\nThere are " + passed1.size() + " out of " + movies.size() + " movies that pass " + input + " of the tests:");
        System.out.println(passed1);
        System.out.println("\nThere are " + passed2.size() + " out of " + movies.size() + " movies with a cast of at least " + (int)(percent) + "% women:");
        System.out.println(passed2);
        System.out.println("\nThere are " + passed3.size() + " out of " + movies.size() + " movies total that pass the full BJJ test:");
        System.out.println(passed3);
        return passed3;
    }

    /**
     * Helper method for our BJJ test. Given a movie and a double, checks whether 
     * the percentage of female actors in the movie is greater than or equal to the 
     * double value
     * @param m the movie to be checked
     * @param d the value to check against
     * @return whether the movie's female cast percentage is at least the value (true) or not (false)
     */
    private boolean testPercentOfWomen(Movie m, double d){
        double count = 0;
        ArrayList<Actor> actorsInMov = m.getActors(); 
        // loops through actors in the given movie
        for (Actor a : actorsInMov){
            if (a.getGender().equals("Female"))
                count++; // gets count of female actors in movie
        }
        double percent = (count/actorsInMov.size());
        return ((percent - d) > 0.00001);
    }

    /**
     * Returns a String representation of the graph, which only lists which movies actors
     * have played in and which actors each movie contains
     * @return String representation of HollywoodGraph
     */
    public String toString(){
        String s = "ACTORS: \n";
        for (Actor a : actors){
            s += a + "\n";
        }
        s += "\nMOVIES:\n";
        for (Movie m : movies){
            s += m + "\n";
        }
        return s;
    }

    /**
     * Main method - used for testing
     */
    public static void main (String[] args){
        /*
        System.out.println("Testing I/O exception error:");
        HollywoodGraph empty = new HollywoodGraph("", "");
        HollywoodGraph g1 = new HollywoodGraph("small_castGender.txt", "small_allTests.txt");
        HollywoodGraph g = new HollywoodGraph("nextBechdel_castGender.txt", "nextBechdel_allTests.txt");
        g.listActorsInMovie("The Jungle Book");
        g.listMoviesWithActor("Jennifer Lawrence");
        System.out.println("\nThe degree of separation between M.Fox and T.Perry is: " + g.findActorSeparation("Megan Fox", "Tyler Perry"));
        System.out.println("The degree of separation between Stella and Takis is: " + g1.findActorSeparation("Stella", "Takis"));
        System.out.println("The degree of separation between N.Arapoglou and T.Perry is: " + g.findActorSeparation("Nick Arapoglou", "Tyler Perry"));
        System.out.println("The degree of separation between S.Lang and M.Vairo is: " + g.findActorSeparation("Stephen Lang", "Mat Vairo"));
        System.out.println("The degree of separation between \"\" and \"\" is: " + g.findActorSeparation("", ""));
        System.out.println("The degree of separation between A.Ly and R.Blat is: " + g.findActorSeparation("Aaron Ly", "Roman Blat"));

        g.passBJJTest();

         */
        String s = "Jessica and mengmeng and pupu";
        System.out.println(s);
        System.out.println(s.substring(0,6));
        System.out.println(Math.log10(10));

        Integer[] array = {1,2,3,4};
        System.out.println(binarySearch(array, 0, array.length-1, 2));

    }

    public static Comparable linearSearch (Comparable[] data, Comparable target){
        Comparable result = null;
        int index = 0;
        while(result == null && index < data.length) {
            if(data[index].compareTo(target) == 0)
                result = data[index];
            index++;
        }
        return result;
    }

    public static <T extends Comparable<T>> boolean binarySearch(T[] data, int min, int max, T target)
    {
        boolean found = false;
        int midpoint = (min + max) / 2; // determine the midpoint
        if (data[midpoint].compareTo(target) == 0)
            found = true;
        else if (data[midpoint].compareTo(target) > 0)
        {
            if (min <= midpoint - 1)
                found = binarySearch(data, min, midpoint - 1, target);

        }
        else if (midpoint + 1 <= max)
            found = binarySearch(data, midpoint + 1, max, target);
        return found;
    }

}
