
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
    //Store the starting node
    private Node start;
    
    //Store the number of moves needed to reach the goal state
    private int numMovesNeeded;
    
    //Store if h1 or h2
    private String heuristic;
    
    //Store the sequence of moves
    ArrayList<String> moveList = new ArrayList<String>();
 
    //PriorityQueue to store Nodes for searches
    PriorityQueue <Node> stateTree = new PriorityQueue();  //Priority Queue to handle A* search
    
    public AStarSearch(State s, String heuristic)
    {
       this.start = new Node(s, 0, 0, "", null);
       this.heuristic = heuristic;
    }
    
    /**
     * Method to get the successors of the current state
     * @return 
     */
    public void getSuccessors(Node n)  //Should pass in a Node as input
    {
       //ArrayList<State> stateList = new ArrayList<State>();
       Node newNode = null;
       try
       {
          //stateList.add(n.getState().move("up")); 
           if (this.heuristic.equals ("h1"))
               newNode = new Node (n.getState().move("up"), n.getNumMoves() + 1, n.getState().move("up").getMisplacedTiles(),"up", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (n.getState().move("up"), n.getNumMoves() + 1, n.getState().move("up").getDistance(),"up", n);
           stateTree.add (newNode);
           //Fix the rest of the try statements in this method
       }
       catch (Exception e)
       {
           ;
       }
       try
       {
          //stateList.add(n.getState().move("down"));
           if (this.heuristic.equals ("h1"))
               newNode = new Node (n.getState().move("down"), n.getNumMoves() + 1, n.getState().move("down").getMisplacedTiles(),"down", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (n.getState().move("down"), n.getNumMoves() + 1, n.getState().move("down").getDistance(),"down", n);
           stateTree.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       try
       {
          //stateList.add(n.getState().move("left"));
           if (this.heuristic.equals ("h1"))
               newNode = new Node (n.getState().move("left"), n.getNumMoves() + 1, n.getState().move("left").getMisplacedTiles(),"left", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (n.getState().move("left"), n.getNumMoves() + 1, n.getState().move("left").getDistance(),"left", n);
           stateTree.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       try
       {
          //stateList.add(n.getState().move("right"));
           if (this.heuristic.equals ("h1"))
               newNode = new Node (n.getState().move("right"), n.getNumMoves() + 1, n.getState().move("right").getMisplacedTiles(),"right", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (n.getState().move("right"), n.getNumMoves() + 1, n.getState().move("right").getDistance(),"right", n);
           stateTree.add (newNode);
       }
       catch (Exception e)
       {
           ;
       }
       /*for (int i = 0; i < stateList.size(); i++)
       {
          Node other = null;
          if (heuristic.equals("h1"))
            other = new Node (stateList.get(i), 1, 1, stateList.get(i).getMisplacedTiles());
          else if (heuristic.equals("h2"))
            other = new Node (stateList.get(i), 1, 1, stateList.get(i).getDistance());
          stateTree.add(other);
          //Is the # of moves to this point the g, and the h is either h1 or h2?
          //Create a new Node, increment the Node's # moves to this point, adjust estimated distance?
       } */
       this.numMovesNeeded++;
    }
    
    /**
     * Method to solve the 8-puzzle using A* search
     * @return 
     */
    public String solveAStar()
    {
       stateTree.add(start);
       if (!(start.getState().isGoalState()))
       {
           //Start node is not goal state
           Node firstNode;
           while (!(firstNode = stateTree.remove()).getState().isGoalState())  //When to return the solution, is the goal node stored?
           {
               this.getSuccessors (firstNode);
               firstNode.setExpanded (true);
           }
           //Maybe make a method that appends all the moves to a Stringbuilder as it works its way up the node tree
           //Append the g function from the goalNode to that to show the # moves?
           return "List numMoves and sequence of moves";  //Is the numMoves the g value of the goal node? How to pull sequence of moves?
       }
       else
           return "Puzzle already solved";
    } 
}
