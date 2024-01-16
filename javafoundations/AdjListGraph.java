package javafoundations;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

/********************************************************************
 * AdjListGraph.java 
 * @author Beatrix Kim
 * @author Jessica Dai
 * @author Jessica Yang
 * @author CS230Staff 
 * @consulted CS230 slides on BFS traversal
 * @version December 12, 2023
 * 
 * Implementation of the Graph.java interface using Lists of
 * Adjacent nodes - USED FOR CS230-S23 FINAL PROJECT. 
 * 
 * NOTE: Our group has made a few changes to the original 
 * AdjListsGraph.java class. We changed one line in 
 * AdjListGraphFromFile (trim the token and scan next line...see 
 * comment in method). We also added two methods, iteratorBFS() and 
 * getObjIdx(). We consulted the class slides for help with BFS. 
 * 
 * KNOWN FEATURES/BUGS:
 * It handles unweighted graphs only, but it can be extended.
 * It does not handle operations involving non-existing vertices
 ********************************************************************/

public class AdjListGraph<T> implements Graph<T>{
    private final int NOT_FOUND = -1;
    private Vector<LinkedList<T>> arcs;   // adjacency matrices of arcs
    private Vector<T> vertices;   // values of vertices

    /******************************************************************
     * Constructor. Creates an empty graph.
     ******************************************************************/
    public AdjListGraph() {
        this.arcs = new Vector<LinkedList<T>>();
        this.vertices = new Vector<T>();
    }

    /*****************************************************************
     * Creates and returns a new graph using the data found in the input file.
     * If the file does not exist, a message is printed.
     *****************************************************************/
    public static AdjListGraph<String> AdjListGraphFromFile(String tgf_file_name) {
        AdjListGraph<String> g = new AdjListGraph<String>();
        try{ // to read from the tgf file
            Scanner scanner = new Scanner(new File(tgf_file_name));
            //read vertices
            while (!scanner.next().equals("#")){
                String token = "";
                token = scanner.nextLine().trim(); /** OUR GROUP CHANGED THIS LINE **/
                    /** original was: token = scanner.next(); **/
                g.addVertex(token);
            }
            //read arcs
            while (scanner.hasNext()){
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                g.addArc(from, to);
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println(" ***(T)ERROR*** The file was not found: " + ex);
        }
        return g;
    }

    /**** OUR GROUP HAS ADDED THE FOLLOWING METHODS FOR OUR PROJECT: ****/
    
    /**
     * Given a starting index, uses ArrayIterator to perform a breadth-first 
     * search traversal and returns the iterator. Assumes that startIndex is a 
     * valid index in the graph——which it always will be when called in our
     * HollywoodGraph method, findActorSeparation().
     * @param startIndex the index in the graph to start BFS from
     * @return iter the iterator containing vertices in breadth-first order
     */
    public ArrayIterator<T> iteratorBFS(int startIndex){
        int currentVertex; // the current vertex being visited
        // keeps track of where traversal currently is
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>(); 
        ArrayIterator<T> iter = new ArrayIterator<T>(); // keep track of result
        // keeps track of which vertices have already been visited
        boolean[] visited = new boolean[getNumVertices()]; 
        
        for (int vertexIndex = 0; vertexIndex < getNumVertices(); vertexIndex++){
            visited[vertexIndex] = false; // initialization, sets all vertices as unvisited 
        }
        traversalQueue.enqueue(startIndex); // add starting index to traversalQueue 
        visited[startIndex] = true; // mark as visited
        
        // loops through visited vertices
        while (!traversalQueue.isEmpty()){
            currentVertex = traversalQueue.dequeue(); // dequeue first element in queue
            iter.add(vertices.get(currentVertex)); // add it to the iterator
            for (int vertexIndex = 0; vertexIndex < getNumVertices(); vertexIndex++){
                // for each vertex adjacent to currentVertex... 
                T v1 = vertices.get(vertexIndex);
                T v2 = vertices.get(currentVertex);
                if (isArc(v1, v2) && !visited[vertexIndex]){
                    // ...which is marked as unvisited... 
                    traversalQueue.enqueue(vertexIndex); // add into the queue
                    visited[vertexIndex] = true; // and mark as visited
                }
                // repeat the loop until there are no more new vertices
            }
        }
        return iter;
    }
    
    /**
     * Given an object, finds and returns its index within the Vector collection
     * of vertices
     * @param object
     * @return the index of the given object
     */
    public int getObjIdx(T object){
        return vertices.indexOf(object);
    }
    
    /**** END OF OUR NEWLY ADDED METHODS. THE FOLLOWING WERE GIVEN TO US: ****/

    /******************************************************************
     * Returns true if the graph is empty and false otherwise.
     ******************************************************************/
    public boolean isEmpty() {
        return vertices.size() == 0;
    }

    /******************************************************************
     * Returns the number of vertices in the graph.
     ******************************************************************/
    public int getNumVertices() {
        return vertices.size();
    }

    /******************************************************************
     * Returns the number of arcs in the graph by counting them.
     ******************************************************************/
    public int getNumArcs() {
        int totalArcs = 0;
        for (int i = 0; i < vertices.size(); i++) //for each vertex
        //add the number of its connections
            totalArcs = totalArcs + arcs.get(i).size();

        return totalArcs;
    }

    /******************************************************************
     * Returns true iff a directed edge exists from v1 to v2.
     ******************************************************************/
    public boolean isArc (T vertex1, T vertex2){
        try {
            int index = vertices.indexOf(vertex1);
            LinkedList<T> l = arcs.get(index);
            return (l.indexOf(vertex2) != -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(vertex1 + " vertex does not belong in the graph");
            return false;
        }
    }

    /******************************************************************
    Returns true iff an edge exists between two given vertices
    which means that two corresponding arcs exist in the graph
     ******************************************************************/
    public boolean isEdge (T vertex1, T vertex2) {
        return (isArc(vertex1, vertex2) && isArc(vertex2, vertex1));
    }

    //  /******************************************************************
    //    Adds a vertex to the graph, expanding the capacity of the graph
    //    if necessary.  If the vertex already exists, it does not add it.
    //    ******************************************************************/
    public void addVertex (T vertex) {
        if (vertices.indexOf(vertex) == NOT_FOUND) { //the vertex is not already there
            // add it to the vertices vector
            vertices.add(vertex);

            //indicate that the new vertex has no arcs to other vertices yet
            arcs.add(new LinkedList<T>());
        }
    }

    /******************************************************************
     * Removes a single vertex with the given value from the graph.
     * Uses equals() for testing equality
     ******************************************************************/
    public void removeVertex (T vertex) {
        int index = vertices.indexOf(vertex);
        this.removeVertex(index);
    }

    /******************************************************************
    Helper. Removes a vertex at the given index from the graph.
    Note that this may affect the index values of other vertices.
     ******************************************************************/
    private void removeVertex (int index) {
        T vertex = vertices.get(index);
        vertices.remove(index); //remove vertex from vertices vector
        arcs.remove(index); //remove its list of adjacent vertices vector
        //remove it from the other lists, wherever it was found
        for (int i = 0; i < arcs.size(); i++) {
            for (T otherVertex : arcs.get(i)) {
                if (otherVertex.equals(vertex))
                    arcs.get(i).remove(vertex);
            }
        }
    }

    /******************************************************************
     * Inserts an edge between two vertices of the graph.
     * If one or both vertices do not exist, ignores the addition.
     ******************************************************************/
    public void addEdge (T vertex1, T vertex2) {
        // getIndex will return NOT_FOUND if a vertex does not exist,
        // and the addArc() will not insert it
        //System.out.println("addEdge between " + vertex1 + " and " + vertex2);
        this.addArc (vertex1, vertex2);
        addArc (vertex2, vertex1);
    }

    /******************************************************************
     * Inserts an arc from v1 to v2.
     * If the vertices exist, else does not change the graph.
     ******************************************************************/
    public void addArc (T source, T destination){
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        //if source and destination exist, add the arc. do nothing otherwise
        if ((sourceIndex != -1) && (destinationIndex != -1)){
            LinkedList<T> l = arcs.get(sourceIndex);
            l.add(destination);
        }
    }

    /******************************************************************
    Helper. Inserts an edge between two vertices of the graph.
     ******************************************************************/
    protected void addArc (int index1, int index2) {
        //if (indexIsValid(index1) && indexIsValid(index2))
        //vertices.get(index1).add(v2);
        LinkedList<T> l = arcs.get(index1-1);
        T v = vertices.elementAt(index2-1);
        l.add(v);
    }

    /******************************************************************
     * Removes an edge between two vertices of the graph.
     * If one or both vertices do not exist, ignores the removal.
     ******************************************************************/
    public void removeEdge (T vertex1, T vertex2) {
        removeArc (vertex1, vertex2);
        removeArc (vertex2, vertex1);
    }

    /******************************************************************
     * Removes an arc from vertex v1 to vertex v2,
     * if the vertices exist, else does not change the graph.
     ******************************************************************/
    public void removeArc (T vertex1, T vertex2) {
        int index1 = vertices.indexOf(vertex1);
        int index2 = vertices.indexOf(vertex2);
        removeArc (index1, index2);
    }

    /******************************************************************
     * Helper. Removes an arc from index v1 to index v2.
     ******************************************************************/
    private void removeArc (int index1, int index2) {
        //if (indexIsValid(index1) && indexIsValid(index2))
        T to = vertices.get(index2);
        LinkedList<T> connections = arcs.get(index1);
        connections.remove(to);
    }

    /******************************************************************
    Returns a string representation of the graph.
     ******************************************************************/
    public String toString() {
        if (vertices.size() == 0) return "Graph is empty";

        String result = "Vertices: \n";
        result = result + vertices;

        result = result + "\n\nEdges: \n";
        for (int i=0; i< vertices.size(); i++)
            result = result + "from " + vertices.get(i) + ": "  + arcs.get(i) + "\n";

        return result;
    }

    /******************************************************************
     * Saves the current graph into a .tgf file.
     * If it cannot save the file, a message is printed.
     *****************************************************************/
    public void saveTGF(String fName) {
        try {
            PrintWriter writer = new PrintWriter(new File(fName));

            //write vertices by iterating through vector "vertices"
            for (int i = 0; i < vertices.size(); i++) {
                writer.print((i+1) + " " + vertices.get(i));
                writer.println("");
            }
            writer.print("#"); // Prepare to print the edges
            writer.println("");

            //write arcs by iterating through arcs vector
            for (int i = 0; i < arcs.size(); i++){ //for each linked list in arcs
                for (T vertex :arcs.get(i)) {
                    int index2 = vertices.indexOf(vertex);
                    writer.print((i+1) + " " + (index2+1));
                    writer.println("");
                }
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("***ERROR***" +  fName + " could not be written: " + ex);
        }
    }

    // DO NOT CHANGE ANY OF THE CODE ABOVE.
    
}
