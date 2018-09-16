
import java.util.ArrayList;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A class to handle solving the 8-puzzle with A* search
 * @author Dave
 */
public class AStarSearch 
{
    private Node start;
    
    //Store the number of moves needed to reach the goal state
    private int numMovesNeeded;
    
    //
    private String heuristic;
    
    //Store the sequence of moves
    ArrayList<String> moveList = new ArrayList<String>();
 
    //PriorityQueue to store Nodes for searches
    PriorityQueue <Node> stateTree = new PriorityQueue();  //Priority Queue to handle A* search, Need to make a Node class
    
    public AStarSearch(int g, int h, State s, String heuristic)
    {
        
    }
    
    /**
     * Method to get the successors of the current state
     * @return 
     */
    public State getSuccessors(State s)
    {
       ArrayList<Tile[][]> list = new ArrayList<Tile[][]>();   //Need to package the successors into nodes, adding 1 to their cost function
       ArrayList<State> stateList = new ArrayList<State>();
       try
       {
          list.add(s.move("up"));
       }
       catch (Exception e)
       {
           ;
       }
       try
       {
          list.add(s.move("down"));
       }
       catch (Exception e)
       {
           ;
       }
       try
       {
          list.add(s.move("left"));
       }
       catch (Exception e)
       {
           ;
       }
       try
       {
          list.add(s.move("right"));
       }
       catch (Exception e)
       {
           ;
       }
       for (int i = 0; i < list.size(); i++)
       {
          State temp = new State();
          Node n;
          temp.setPuzzle(list.get(i));
          if (heuristic.equals("h1"))
            n = new Node (temp, 1, 1, temp.getMisplacedTiles(temp));
          else if (heuristic.equals("h2"))
            n = new Node (temp, 1, 1, temp.getDistance(temp));
          //Is the # of moves to this point the g, and the h is either h1 or h2?
          //Create a new Node, increment the Node's # moves to this point, adjust estimated distance?
       }
       this.numMovesNeeded++;
       return s;
    }
    
    /**
     * Method to solve the 8-puzzle using A* search
     * @param heuristic h1 or h2
     * @return 
     */
    public String solveAStar(String heuristic)
    {
       if (heuristic.equals("h1"))  //# of misplaced tiles
       {
          return "h1"; 
       }
       else if (heuristic.equals("h2"))
       {
          return "h2";
       }
       else
           return "Invalid Heuristic!";
    } 
}
