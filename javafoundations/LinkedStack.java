//********************************************************************
//  LinkedStack.java       Java Foundations
//
//  Represents a linked implementation of a stack.
//********************************************************************
// Sample Solutions of CS230 Assignment 5 
// Author: CS230 Staff

 
package javafoundations;
import javafoundations.exceptions.*;

public class LinkedStack<T> implements Stack<T>
{
  private int count;
  private LinearNode<T> top;
  
  //-----------------------------------------------------------------
  //  Creates an empty stack using the default capacity.
  //-----------------------------------------------------------------
  public LinkedStack()
  {
    count = 0;
    top = null;
  }
  
  //-----------------------------------------------------------------
  //  Removes the element at the top of this stack and returns a
  //  reference to it. Throws an EmptyCollectionException if the
  //  stack contains no elements.
  //-----------------------------------------------------------------
  public T pop() throws EmptyCollectionException
  {
    if (count == 0)
      throw new EmptyCollectionException ("Pop operation failed. "
                                            + "The stack is empty.");
    
    T result = top.getElement();
    top = top.getNext();
    count--;
    
    return result;
  }
  
  //-----------------------------------------------------------------
  //  Returns a string representation of this stack.
  //-----------------------------------------------------------------
  public String toString()
  {
    String result = "<top of stack>\n";
    LinearNode current = top;
    
    while (current != null)
    {
      result += current.getElement() + "\n";
      current = current.getNext();
    }
    
    return result + "<bottom of stack>";
  }
  
  //-----------------------------------------------------------------
  //  The following methods are left as Programming Projects.
  //-----------------------------------------------------------------
  // public void push () { }
  // public T peek () throws EmptyCollectionException { }
  // public boolean isEmpty() { }
  // public int size() { }
  /**
   *  Pushes an element onto the stack.
   */
  public void push (T element)
  {
    LinearNode<T> node = new LinearNode<T>(element);
    node.setNext(top);
    top = node; 
    count++;
  }
  
  /** -----------------------------------------------------------------
    * Takes a peek at the topmost element of the stack
    * without deleting it
    * @return T
    */ 
  public T peek() { // try/catch, no throws EmptyCollectionException{
    try{
      T result = top.getElement();;
    } catch(EmptyCollectionException e){
      System.out.println("Peek operation failed. The stack is empty!");
    }  
    return top.getElement();
  }
  
  
  /**-----------------------------------------------------------------
   *  Checks if stack is empty
   */
  public boolean isEmpty() 
  {
    return count == 0;
  }
  
  /**-----------------------------------------------------------------
   *  Returns the number of eleements in the stack
   */
  public int size() 
  {
    return count;
  }
}
