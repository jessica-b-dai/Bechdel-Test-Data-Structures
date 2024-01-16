import java.util.ArrayList;

/**
 * Creates an Actor, which will be represented as a vertex in the HollywoodGraph.
 *
 * @author Beatrix Kim
 * @author Jessica Dai
 * @author Jessica Yang
 * @version December 12, 2023
 */
public class Actor
{
    private String gender; // gender of actor
    private String name; // name of actor
    private ArrayList<Movie> movies; // list of movies that actor is in
    private int graphIndex; // the actor's index in a HollywoodGraph

    /**
     * Constructor for objects of class Actor
     * @param n the actor name
     * @param g the actor gender
     */
    public Actor(String n, String g){
        name = n;
        gender = g;
        movies = new ArrayList<Movie>();
    }
    
    /**
     * Getter for the index of the vertex represented by the actor in HollywoodGraph
     * @return graphIndex the index of the actor's vertex in Hollywood Graph
     */
    public int getGraphIndex(){
        return graphIndex;
    }
    
    /**
     * Setter for index of actor's vertex in HollywoodGraph
     * @param ind the index to set actor's vertex to in HollywoodGraph
     */
    public void setGraphIndex(int ind){
        graphIndex = ind;
    }
    
    /**
     * Getter for the name of the actor
     * @return name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Getter for the gender of actor
     * @return gender
     */
    public String getGender(){
        return gender;
    }
    
    /**
     * Getter for the actor's collection of movies
     * @return movies the list of movies the actor has been in
     */
    public ArrayList getMovies(){
        return movies;
    }
    
    /**
     * Given a movie, adds it to the actors's list of movies
     * @param m the movie to be added the movie list
     */
    public void addMovie(Movie m){
        movies.add(m);
    }
    
    /**
     * Creates string representation of Actor including the name, gender, and 
     * list of movies they've acted in
     * @return String representation of Actor
     */
    public String toString() {
        String s = name + " (" + gender + ") has played in: ";
        for (int i = 0; i < movies.size(); i++) {
            if (i != movies.size() - 1) 
                s += movies.get(i).getTitle() + ", "; // add commas between movies
            else s += movies.get(i).getTitle() + ". "; // add period for last movie
        }
        return s;
    }

    /**
     * Testing class
     */
    public static void main (String[] args){
        Actor a = new Actor("Josh Hutcherson", "Male");
        a.setGraphIndex(1);
        a.addMovie(new Movie("The Hunger Games"));
        a.addMovie(new Movie("The Hunger Games: Catching Fire"));
        a.addMovie(new Movie("The Hunger Games: Mockingjay Pt.1"));
        a.addMovie(new Movie("The Hunger Games: Mockingjay Pt.2"));
        System.out.println("Created new actor: " + a.getName() + ", " + a.getGender());
        System.out.println("Expected index: 1, index is: " + a.getGraphIndex());
        System.out.println("Movies: " + a.getMovies());
        System.out.println(a);
    }
}
