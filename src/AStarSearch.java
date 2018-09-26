
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * A class to handle solving the 8-puzzle with A* search
 * @author David Greenberg
 */
public class AStarSearch 
{
    //Store the starting node
    private Node start;
    
    //Store if h1 or h2
    private String heuristic;
    
    //Store the sequence of moves
    ArrayList<String> moveList = new ArrayList<String>();
 
    //PriorityQueue to store Nodes for searches
    PriorityQueue <Node> stateTree = new PriorityQueue();
    
    /**
     * Constructor
     * @param s State of starting node
     * @param heuristic h1 or h2
     */
    public AStarSearch(State s, String heuristic)
    {
       //Package the state that called A* into a node
       this.start = new Node(s, 0, heuristic.equals("h1") ? s.getMisplacedTiles() : s.getDistance(), "", null);
       this.heuristic = heuristic;
    }
    
    /**
     * Method to get the successors of the current state
     * @param n Node to get successors for
     */
    public void getSuccessors(Node n)
    {
       Node newNode = null;
       //Move the blank tile up
       try
       {
           State moved = n.getState().move("up");
           if (this.heuristic.equals ("h1"))
               newNode = new Node (moved, n.getG() + 1, moved.getMisplacedTiles(),"up", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (moved, n.getG() + 1, moved.getDistance(),"up", n);
           stateTree.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       //Move the blank tile down
       try
       {
           State moved = n.getState().move("down");
           if (this.heuristic.equals ("h1"))
               newNode = new Node (moved, n.getG() + 1, moved.getMisplacedTiles(),"down", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (moved, n.getG() + 1, moved.getDistance(),"down", n);
           stateTree.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       //Move the blank tile left
       try
       {
           State moved = n.getState().move("left");
           if (this.heuristic.equals ("h1"))
               newNode = new Node (moved, n.getG() + 1, moved.getMisplacedTiles(),"left", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (moved, n.getG() + 1, moved.getDistance(),"left", n);
           stateTree.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       //Move the blank tile right
       try
       {
           State moved = n.getState().move("right");
           if (this.heuristic.equals ("h1"))
               newNode = new Node (moved, n.getG() + 1, moved.getMisplacedTiles(),"right", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (moved, n.getG() + 1, moved.getDistance(),"right", n);
           stateTree.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
    }
    
    /**
     * Method to return the sequence of moves from start --> goal in order
     * @param n Node to get the sequence from (goal Node)
     * @return String sequence of moves
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
     * @param maxNodes maximum nodes to consider in the search
     * @return String # moves & sequence of moves to get from start Node --> Goal Node
     */
    public String solveAStar(int maxNodes)
    {
       stateTree.clear(); //Clear the PQ before use
       stateTree.add(start);
       int counter = 0;
       if (!(start.getState().isGoalState()))
       {
           Node firstNode = stateTree.poll();
           while (!(firstNode.getState().isGoalState())) 
           {
               if (!(firstNode.getExpanded()))
               {
                  this.getSuccessors (firstNode);
                  firstNode.setExpanded (true);
               }
               firstNode = stateTree.poll();
               counter++;
               if (counter > maxNodes)
               {
                   System.out.println("Max Node Limit Reached!  A* couldn't complete!");
                   return "Max Node Limit Reached!  A* couldn't complete!";
               }
           }
           String sequence = this.getSequence(firstNode);
           System.out.println("A* Search Done! Number of moves: " + firstNode.getG() + ".\tSequence of moves: "+ sequence + '.');
           return "A* Search Done! Number of moves: " + firstNode.getG() + ".\tSequence of moves: "+ sequence + '.';
       }
       else
       {
           System.out.println("Puzzle already solved!");
           return "Puzzle already solved!";
       }
    } 
}
