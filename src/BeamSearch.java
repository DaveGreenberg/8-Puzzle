
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dave
 */
public class BeamSearch 
{
    //Fields
    //Store the starting node
    private BeamNode start;
    
    //Store k-value
    private int k;
    
    //Store the sequence of moves
    ArrayList<String> moveList = new ArrayList<String>();
 
    //Main PQ to help with beam search
    PriorityQueue <BeamNode> mainPQ = new PriorityQueue();
    
    //PQ to order the k-best successors
    PriorityQueue <BeamNode> successorPQ = new PriorityQueue();
    
    /**
     * Constructor
     * @param s
     * @param k 
     */ 
    public BeamSearch(State s, int k)
    {
       this.start = new BeamNode(s, 0, s.getDistance(), "", null);
       this.k = k;
    }
    
    /**
     * Method to retrieve the k value
     * @return int k
     */
    public int getK()
    {
        return this.k;
    }
    
    /**
     * Method to get the successors of the current state
     * @param n Node to get successors of
     */
    public void getSuccessors(Node n)
    {
       BeamNode newNode = null;
       //System.out.println("1: " + n.getState().printState());
       try
       {
           State moved = n.getState().move("up");
           newNode = new BeamNode (moved, n.getG() + 1, moved.getDistance(),"up", n);
           //Question for Jacob: Should I make the g-value always 0, since we are only evaluating off the heuristic?
           successorPQ.add (newNode);
           //System.out.println("Up Successor: " + newNode.getState().printState());
       }
       catch (Exception e)
       {
           ;
       }
       //System.out.println("2: " + n.getState().printState());
       //Move the blank tile down
       try
       {
           State moved = n.getState().move("down");
           newNode = new BeamNode (moved, n.getG() + 1, moved.getDistance(),"down", n);
           successorPQ.add (newNode);
           //System.out.println("Down Successor: " + newNode.getState().printState());
       }
       catch (Exception e)
       {
           ;
       }
       //System.out.println("3: " + n.getState().printState());
       //Move the blank tile left
       try
       {
           State moved = n.getState().move("left");
           newNode = new BeamNode (moved, n.getG() + 1, moved.getDistance(),"left", n);
           successorPQ.add (newNode);
           //System.out.println("Left Successor: " + newNode.getState().printState());
       }
       catch (Exception e)
       {
           ;
       }
       //System.out.println("4: " + n.getState().printState());
       //Move the blank tile right
       try
       {
           State moved = n.getState().move("right");
           newNode = new BeamNode (moved, n.getG() + 1, moved.getDistance(),"right", n);
           successorPQ.add (newNode);
           //System.out.println("Right Successor: " + newNode.getState().printState());
       }
       catch (Exception e)
       {
           ;
       }
       //Of the generated successors, all of them are wrong
       //Successors are sharing States to do the operation on (i.e. Up affects the proper Node, but then Down affects the Node after Up's change
    }
    
    /**
     * Method to return the sequence of moves from start --> goal in order
     * @param n
     * @return 
     */
    public String getSequence(Node n)
    {
          Stack<String> stack = new Stack<>();
          StringBuilder builder = new StringBuilder();
          
          //Push all moves onto Stack to get them in order
          while (n != null)
          {
              stack.push(n.getAction());
              n = n.getParent();
          }
          //Pop the moves off so they are in order
          while (! (stack.empty()))
          {
              builder.append(stack.pop());
              builder.append(' ');
          }
          return builder.toString();
    }
    
    /**
     * Method to solve the 8-puzzle using A* search
     * @param k Nodes to consider
     * @return 
     */
    public String solveBeam(int k)
    {
       //max Nodes is:
       //If a search looks at n-nodes, abadnon the search, indicate an error
       //Use a PQ
       //Get all successors and put on a separate PQ if haven't been expanded, then move the k best to the actual PQ
       //Clear the successor PQ
       //Use h2 to compare them
       mainPQ.clear(); //Clear the PQ before use
       mainPQ.add(start);
       boolean done = false;
       BeamNode goalNode = null;
       if (!(start.getState().isGoalState()))
       {
           //Start node is not goal state
           //System.out.println("State of Node being pulled off PQ: " + firstNode.getState().printState());
           outerLoop:
           while (!done) 
           {
               int counter = 0;
               while ((! mainPQ.isEmpty()) && (counter <= k))
               {
                    BeamNode firstNode = mainPQ.poll();
                    if ((firstNode.getState().isGoalState()))
                    {
                        done = true;
                        goalNode = firstNode;
                        break outerLoop;
                    }
                    
                    if (!(firstNode.getExpanded()))
                    {
                       this.getSuccessors (firstNode);
                       firstNode.setExpanded (true);
                    }
                    counter++;
               }
               mainPQ = successorPQ;
               successorPQ = new PriorityQueue();
           }
           String sequence = this.getSequence(goalNode);
           System.out.println("Beam Search Done! Number of moves: " + goalNode.getG() + ".\tSequence of moves: "+ sequence + '.');
           return "Number of moves: " + goalNode.getG() + " Sequence of moves: "+ sequence;
       }
       else
       {
           System.out.println("Puzzle already solved!");
           return "Puzzle already solved";
       }
    } 
}
