
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Class to run Beam Search
 * @author David Greenberg
 */
public class BeamSearch 
{
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
     * @param s State to start search from
     * @param k # nodes to consider at each 'level'
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
       
       //Move the blank tile up
       try
       {
           State moved = n.getState().move("up");
           newNode = new BeamNode (moved, n.getG() + 1, moved.getDistance(),"up", n);
           successorPQ.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       
       //Move the blank tile down
       try
       {
           State moved = n.getState().move("down");
           newNode = new BeamNode (moved, n.getG() + 1, moved.getDistance(),"down", n);
           successorPQ.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       
       //Move the blank tile left
       try
       {
           State moved = n.getState().move("left");
           newNode = new BeamNode (moved, n.getG() + 1, moved.getDistance(),"left", n);
           successorPQ.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       
       //Move the blank tile right
       try
       {
           State moved = n.getState().move("right");
           newNode = new BeamNode (moved, n.getG() + 1, moved.getDistance(),"right", n);
           successorPQ.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
    }
    
    /**
     * Method to return the sequence of moves from start --> goal in order
     * @param n Node to get the sequence of moves for
     * @return the sequence of moves
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
     * Method to solve the 8-puzzle using Beam search
     * @param k Nodes to consider
     * @return # moves & sequence of moves from start --> goal
     */
    public String solveBeam(int k)
    {
       mainPQ.clear();
       mainPQ.add(start);
       boolean done = false;
       BeamNode goalNode = null;
       int numLevels = 0;
       if (!(start.getState().isGoalState()))
       {
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
               numLevels++;
               if (numLevels > 250) //Arbitrary number, can increase if wanted
               {
                   System.out.println("Infinite Loop Detected!  Beam Search couldn't complete.");
                   return "Infinite Loop Detected!  Beam Search couldn't complete.";
               }
               successorPQ = new PriorityQueue();  //Need to reference a new spot in memory.  Very important!
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
