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
   private State state;
   
   //Store the number of moves to get to this Node
   private int numMoves;
   
   //Store the parent node
   private Node parent;
   
   //Store the action applied to the parent node to get to current node
   private String action;
   
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
    * @param state State for this node
    * @param g Number of moves to this state
    * @param h Estimated cost (Misplaced tile for h1, Manhattan for h2)
    * @param action move that parent used to get to this state
    * @param parent Node's parent
    */
   public Node(State state, int g, int h, String action, Node parent)
   {
      this.state = state;
      this.h = h;
      this.g = g;
      this.action = action;
      this.parent = parent;
      this.f = h + g;
      this.expanded = false;
   }

   public State getState() 
   {
       return state;
   }

   public void setState(State state) 
   {
       this.state = state;
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
   
   public String getAction() 
   {
       return this.action;
   }
   
   public Node getParent() 
   {
       return this.parent;
   }

   public void setF(int f) 
   {
       this.f = f;
   }
   
   public void setExpanded(boolean answer) 
   {
       this.expanded = answer;
   }
   
   public boolean getExpanded() 
   {
       return this.expanded;
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
