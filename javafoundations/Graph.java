 

//*******************************************************************
//  Graph.java    @author CS230 Staff (TM) @version 2018.11.02
//  Defines a *PARTIAL* interface for an directed graph data structure.
//*******************************************************************
package javafoundations;
import java.util.*;

public interface Graph<T>
{
   /** Returns true if this graph is empty, false otherwise. */
   public boolean isEmpty();

   /** Returns the number of vertices in this graph. */
   public int getNumVertices();

   /** Returns the number of arcs in this graph. */
   public int getNumArcs();

   /** Returns true iff a directed edge exists b/w given vertices */
   public boolean isArc (T vertex1, T vertex2);

   /** Returns true iff an edge exists between two given vertices
   * which means that two corresponding arcs exist in the graph */
   public boolean isEdge (T vertex1, T vertex2);

   /** Adds a vertex to this graph, associating object with vertex.
   * If the vertex already exists, nothing is inserted. */
   public void addVertex (T vertex);

   /** Removes a single vertex with the given value from this graph.
   * If the vertex does not exist, it does not change the graph. */
   public void removeVertex (T vertex);

   /** Inserts an arc between two vertices of this graph,
   * if the vertices exist. Else it does not change the graph. */
   public void addArc (T vertex1, T vertex2);

   /** Removes an arc between two vertices of this graph,
   * if the vertices exist. Else it does not change the graph. */
   public void removeArc (T vertex1, T vertex2);

   /** Inserts an edge between two vertices of this graph,
   * if the vertices exist. Else does not change the graph. */
   public void addEdge (T vertex1, T vertex2);

   /** Removes an edge between two vertices of this graph,
   if the vertices exist, else does not change the graph. */
   public void removeEdge (T vertex1, T vertex2);

   /** Returns a string representation of the graph. */
   public String toString();

   /** Saves the current graph into a .tgf file.
   If it cannot write the file, a message is printed. */
   public void saveTGF(String tgf_file_name);

}
