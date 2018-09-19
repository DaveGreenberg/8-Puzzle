
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
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
    
    //Store if h1 or h2
    private String heuristic;
    
    //Store the sequence of moves
    ArrayList<String> moveList = new ArrayList<String>();
 
    //PriorityQueue to store Nodes for searches
    PriorityQueue <Node> stateTree = new PriorityQueue();  //Priority Queue to handle A* search
    
    public AStarSearch(State s, String heuristic)
    {
       //Package the state that called A* into a node
       this.start = new Node(s, 0, heuristic.equals("h1") ? s.getMisplacedTiles() : s.getDistance(), "", null);
       this.heuristic = heuristic;
    }
    
    /**
     * Method to get the successors of the current state
     * @param n Node to get successors of
     */
    public void getSuccessors(Node n)
    {
       Node newNode = null;
       //Print the reference in memory or test for inequality
       //Move the blank tile up
        System.out.println("1: " + n.getState().printState());
       try
       {
           State moved = n.getState().move("up");
           if (this.heuristic.equals ("h1"))
               newNode = new Node (moved, n.getG() + 1, moved.getMisplacedTiles(),"up", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (moved, n.getG() + 1, moved.getDistance(),"up", n);
           stateTree.add (newNode);
           System.out.println("Up Successor: " + newNode.getState().printState());
       }
       catch (Exception e)
       {
           ;
       }
       System.out.println("2: " + n.getState().printState());
       //Move the blank tile down
       try
       {
           State moved = n.getState().move("down");
           if (this.heuristic.equals ("h1"))
               newNode = new Node (moved, n.getG() + 1, moved.getMisplacedTiles(),"down", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (moved, n.getG() + 1, moved.getDistance(),"down", n);
           stateTree.add (newNode);
           System.out.println("Down Successor: " + newNode.getState().printState());
       }
       catch (Exception e)
       {
           ;
       }
       System.out.println("3: " + n.getState().printState());
       //Move the blank tile left
       try
       {
           State moved = n.getState().move("left");
           if (this.heuristic.equals ("h1"))
               newNode = new Node (moved, n.getG() + 1, moved.getMisplacedTiles(),"left", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (moved, n.getG() + 1, moved.getDistance(),"left", n);
           stateTree.add (newNode);
           System.out.println("Left Successor: " + newNode.getState().printState());
       }
       catch (Exception e)
       {
           ;
       }
       System.out.println("4: " + n.getState().printState());
       //Move the blank tile right
       try
       {
           State moved = n.getState().move("right");
           if (this.heuristic.equals ("h1"))
               newNode = new Node (moved, n.getG() + 1, moved.getMisplacedTiles(),"right", n);
           else if (this.heuristic.equals ("h2"))
               newNode = new Node (moved, n.getG() + 1, moved.getDistance(),"right", n);
           stateTree.add (newNode);
           System.out.println("Right Successor: " + newNode.getState().printState());
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
     * @return 
     */
    public String solveAStar()
    {
       stateTree.clear(); //Clear the PQ before use
       stateTree.add(start);
       if (!(start.getState().isGoalState()))
       {
           //Start node is not goal state
           Node firstNode = stateTree.poll();
           System.out.println("State of Node being pulled off PQ: " + firstNode.getState().printState());
           while (!(firstNode.getState().isGoalState())) 
           {
               if (!(firstNode.getExpanded()))
               {
                  this.getSuccessors (firstNode);
                  firstNode.setExpanded (true);
               }
               firstNode = stateTree.poll();
           }
           String sequence = this.getSequence(firstNode);
           System.out.println("Number of moves: " + firstNode.getG() + " Sequence of moves: "+ sequence);
           return "Number of moves: " + firstNode.getG() + " Sequence of moves: "+ sequence;
       }
       else
       {
           System.out.println("Puzzle already solved!");
           return "Puzzle already solved";
       }
    } 
}
