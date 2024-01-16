import java.util.ArrayList;

/**
 * Creates a Movie object, which will be represented as a vertex in the HollywoodGraph. 
 *
 * @author Beatrix Kim
 * @author Jessica Dai
 * @author Jessica Yang
 * @version December 12, 2023
 */
public class Movie
{
    private String title; // the title of the move
    private ArrayList<Actor> actors; // list of actors in this movie
    private int graphIndex; // the movie's index in a HollywoodGraph
    private ArrayList<Integer> passedTests; // keeps track of which tests are passed

    /**
     * Constructor for objects of class Movie given its title
     * @param t the movie title
     */
    public Movie(String t){
        title = t;
        actors = new ArrayList<Actor>(); 
        passedTests = new ArrayList<Integer>(); 
    }

    /**
     * Getter for the index of the vertex represented by this movie in HollywoodGraph
     * @return graphIndex the index of the movie's vertex in Hollywood Graph
     */
    public int getGraphIndex(){
        return graphIndex;
    }

    /**
     * Setter for index of movie's vertex in HollywoodGraph
     * @param ind the index to set movie's vertex to in HollywoodGraph
     */
    public void setGraphIndex(int ind){
        graphIndex = ind;
    }

    /**
     * Getter for the title of the movie
     * @return title the movie title
     */
    public String getTitle(){
        return title;
    }
    
    /**
     * Finds and returns the number of passed tests for the movie (0s are fails
     * and 1s are passes)
     * @return the number of passed tests
     */
    public int getNumPassedTests(){
        int count = 0;
        for (Integer i : passedTests){
            if (i == 1)
                count++;
        }
        return count;
    }

    /**
     * Getter for the movie's collection of actors
     * @return actors the list of actors in the movie
     */
    public ArrayList getActors(){
        return actors;
    }

    /**
     * Given an actor, adds it to the movie's list of actors
     * @param a the actor to be added to the actor list
     */
    public void addActor(Actor a){
        actors.add(a);
    }
    
    /**
     * Given an Integer 0 (fail) or 1 (pass), adds it to the movie's
     * list of passedTests
     *  @param i the integer representation of a pass or fail to add
     */
    public void addPassedTest(Integer i){
        passedTests.add(i);
    }

    /**
     * toString method 
     * @return String representation of movie
     */
    public String toString(){
        String report = title + " with cast: ";
        for (int i = 0; i < actors.size(); i++){
            // loops through actors list
            report += actors.get(i).getName();
            if(i == actors.size() - 1) 
                report += "."; // for the last actor in the list
            else report += ", "; // otherwise add commas between
        }
        return report;
    }

    /**
     * Testing class
     */
    public static void main (String[] args){
        Movie m = new Movie("Titanic");
        m.setGraphIndex(1);
        m.addActor(new Actor("Leonardo DiCaprio", "Male"));
        m.addActor(new Actor("Kate Winslet", "Female"));
        System.out.println("Created new movie, " + m.getTitle());
        System.out.println("Expected index: 1, index is: " + m.getGraphIndex());
        System.out.println("Actors: " + m.getActors());
        System.out.println(m);
        m.addPassedTest(0);
        m.addPassedTest(1);
        m.addPassedTest(1);
        m.addPassedTest(0);
        System.out.println("getNumpassedTest Expected: 2, actual: " + m.getNumPassedTests());
    }
}
