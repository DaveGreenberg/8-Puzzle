/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A class to package the States into the Priority Queue
 * @author Dave
 */
public class Node implements Comparable<Node>
{
   //Store the state in this node
   private State board;
   
   //Store the number of moves to get to this Node
   private int numMoves;
   
   //Store the g(n) value of the node; actual cost to reach node n from start (# moves to this point)
   private int g;
   
   //Store the h(n) value of the node; estimated cost to reach goal from node n (Manhattan, misplaced tiles)
   private int h;
   
   //Store the f(n) value of the node
   private int f;
   
   //Boolean to store whether this node has been expanded or not
   private boolean expanded;
   
   /**
    * Constructor
    * @param board
    * @param numMoves
    * @param g
    * @param h 
    */
   public Node(State board, int numMoves, int g, int h)
   {
      this.board = board;
      this.numMoves = numMoves;
      this.h = h;
      this.g = g;
      this.f = h + g;
      this.expanded = false;
   }

   public State getBoard() 
   {
       return board;
   }

   public void setBoard(State board) 
   {
       this.board = board;
   }

   public int getNumMoves() 
   {
       return numMoves;
   }

   public void setNumMoves(int numMoves) 
   {
       this.numMoves = numMoves;
   }

   public int getH() 
   {
       return h;
   }

   public void setH(int h) 
   {
       this.h = h;
   }

   public int getG() 
   {
       return g;
   }

   public void setG(int g) 
   {
       this.g = g;
   }

   public int getF() 
   {
       return f;
   }

   public void setF(int f) 
   {
       this.f = f;
   }
   
   
   /**
    * Want to compare Nodes based on total F value
    * @param other
    * @return 
    */
   @Override
   public int compareTo(Node other)
   {
       if (this.getF() < other.getF())
           return -1;
       else if (this.getF() > other.getF())
           return 1;
       else
           return 0;
   }
}
