/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.*;
import java.util.Scanner;
//import java.util.PriorityQueue;
//import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

/**
 * A class to represent the state of the 8-puzzle
 * @author David Greenberg
 */
public class State 
{
    //Matrix to store the board of the 8-puzzle
    private Tile[][] puzzle;
    
    //String to store the file name
    private String inputFile;
    
    //Matrix to store the goal board
    final Tile[][] goalState;
    
    
    //String to store Scanner line
    //private static String str;
    
    public State()
    {
        this.goalState = new Tile[3][3];
        goalState[0][0] = new Tile(0);
        goalState[0][1] = new Tile(1);
        goalState[0][2] = new Tile(2);
        goalState[1][0] = new Tile(3);
        goalState[1][1] = new Tile(4);
        goalState[1][2] = new Tile(5);
        goalState[2][0] = new Tile(6);
        goalState[2][1] = new Tile(7);
        goalState[2][2] = new Tile(8);
    }
    
    /**
     * Constructor to accept a file
     * @param file 
     */
    /*public State(String file)
    {
        this.inputFile = file;
    } */
    
    /**
     * Method to return the puzzle state/board
     */
    public Tile[][] getPuzzle()
    {
        return this.puzzle;
    }
    
    /**
     * Method to set the puzzle state with a tile matrix
     */
    public void setPuzzle(Tile[][] state)
    {
        this.puzzle = state;
    }
    
    /**
     * Set the puzzle state. The goal state  is "b12 345 678"
     * @param str 
     */
    public void setState (String str)
    {
        Tile[][] state = new Tile[3][3];
        String r1 = str.substring(0, 3);
        String r2 = str.substring(4, 7);
        String r3 = str.substring(8);
        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[i].length; j++)
            {
                switch (i) 
                {
                //First row of puzzle
                    case 0:
                        if (r1.charAt(j) == 'b')
                            state[i][j] = new Tile (0);
                        else
                            state[i][j] = new Tile (Integer.parseInt(String.valueOf(r1.charAt(j))));
                        break;
                //Second row of puzzle
                    case 1:
                        if (r2.charAt(j) == 'b')
                            state[i][j] = new Tile (0);
                        else
                            state[i][j] = new Tile (Integer.parseInt(String.valueOf(r2.charAt(j))));
                        break;
                //Third row of puzzle
                    case 2:
                        if (r3.charAt(j) == 'b')
                            state[i][j] = new Tile (0);
                        else
                            state[i][j] = new Tile (Integer.parseInt(String.valueOf(r3.charAt(j))));
                        break;
                    default:
                        System.out.println("Something went wrong");
                        break;
                }
            }
        }
        puzzle = state;
    }
    
    /**
     * Method to print the state of the puzzle
     * @return puzzle state as String
     */
    public String printState()
    {
        StringBuilder builder = new StringBuilder();
        if (puzzle == null)
            return "State not set!";
        int count = 0;
        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                if (((count % 3) == 0) && count > 0 && count < 8)  //Add spaces every 3 to make it look pretty
                    builder.append(' ');
                if (puzzle[i][j].getValue() == 0)                  //Change the 0-blank space representation to a b
                    builder.append('b');
                else
                 builder.append(puzzle[i][j].getValue());
                count++;
            }
        }
        System.out.println(builder.toString());
        return builder.toString();
    }
    
    /**
     * Method to swap the blank tile up, down, left or right
     * @param dir Direction
     * @return Tile[][] New State
     */
    public State move (String dir)
    {
       //int temp;  //Used to swap Tile values
       Tile temp;
       int row = 0;
       int col = 0;
       Tile[][] result = new Tile[3][3];
       for (int i = 0; i < puzzle.length; i++)
       {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                result[i][j] = puzzle[i][j];
            }
       }
       for (int i = 0; i < result.length; i++)
       {
            for (int j = 0; j < result[i].length; j++)
            {
                if (result[i][j].getValue() == 0)
                {
                    row = i;
                    col = j;
                }
            }
       }
       
        switch (dir) 
        {
            case "left":
                if ((col - 1) == -1)
                    throw new IndexOutOfBoundsException("Left is out of bounds!");  //Switched with Up
                else
                {
                    /*temp = result[row][col].getValue();
                    result[row][col].setValue(result[row][col - 1].getValue());
                    result[row][col - 1].setValue(temp); */
                    temp = result[row][col];
                    result[row][col] = result[row][col - 1];
                    result[row][col - 1] = temp;
                }    break;
            case "right":
                if ((col + 1) == 3)
                    throw new IndexOutOfBoundsException("Right is out of bounds!"); //Switched with Down
                else
                {
                    /*temp = result[row][col].getValue();
                    result[row][col].setValue(result[row][col + 1].getValue());
                    result[row][col + 1].setValue(temp); */
                    temp = result[row][col];
                    result[row][col] = result[row][col + 1];
                    result[row][col + 1] = temp;
                }    break;
            case "up":
                if ((row - 1) == -1)
                    throw new IndexOutOfBoundsException("Up is out of bounds!");
                else
                {
                    /*temp = result[row][col].getValue();
                    result[row][col].setValue(result[row - 1][col].getValue());
                    result[row - 1][col].setValue(temp); */
                    temp = result[row][col];
                    result[row][col] = result[row - 1][col];
                    result[row - 1][col] = temp;
                }    break;
            case "down":
                if ((row + 1) == 3)
                    throw new IndexOutOfBoundsException("Down is out of bounds!");
                else
                {
                    /*temp = result[row][col].getValue();
                    result[row][col].setValue(result[row + 1][col].getValue());
                    result[row + 1][col].setValue(temp); */
                    temp = result[row][col];
                    result[row][col] = result[row + 1][col];
                    result[row + 1][col] = temp;
                }    break;
            default:
                System.out.println("Invalid Direction, no move made!");
                break;
        }
        //puzzle = result;  //In practice I think we want to keep the state as-is
        State newState = new State();
        newState.setPuzzle(result);
        return newState;  //return result
    }
    
    /**
     * Method to make a series of random moves
     * @param n number of moves to make
     */
    public void randomizeState (int n)
    {
        /*
        * Check where the 0 tile is and store the coordinate
        * Check what moves are in bounds
        * Append legal moves to an array list
        ** Use if statements from move method
        * Use Math.random() * list.length() to pick an index of the list
        */
        double r; //Store the random #
        for (int i = 0; i < n; i++)
        {
          r = Math.random();   //Need to deal with error catching
          if (r <= 0.25)
          {
              try
              {
                  //statements that may cause an exception
                  this.move("up");
              }
              catch (Exception e)
              {
                 //error handling code
                 //What to do if can't move up?
                 i -= 1;
                 break;
              }
              //this.move("up");
          }
          else if (r > 0.25 && r <= 0.5)
          {
              try
              {
                  this.move("down");
              }
              catch (Exception e)
              {
                  i -= 1;
                  break;
              }
              
              //this.move("down");
          }
          else if (r > 0.5 && r <= 0.75)
          {
              try
              {
                  this.move("left");
              }
              catch (Exception e)
              {
                  i -= 1;
                  break;
              }
              //this.move("left");
          }
          else //if (r > 0.75 && r <= 1.0)
          {
              try
              {
                  this.move("right");
              }
              catch (Exception e)
              {
                  i -= 1;
                  break;
              }
              //this.move("right");
          }
        }
    }
    
    /**
    * Read commands from text file
    * @param file file to read from
    * @throws IOException
    */
    public void readFile(String file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file)); 
        String str; 
        while ((str = reader.readLine()) != null) 
            this.acceptCommands(str);
    }
    
    /**
     * Method to handle commands from the Scanner and input file
     * @param command 
     */
    public void acceptCommands(String inputString)
    {
        String[] command = inputString.split("\\s+");  //Split the input line using whitespace
        if (command[0].equals("setState"))
        {
            this.setState(command[1] + " " + command[2] + " " + command[3]);
        }
        else if (command[0].equals("randomizeState"))
        {
            this.randomizeState(Integer.parseInt(command[1])); //Second String in array should be # of random moves
        }
        else if (command[0].equals("printState"))
        {
            this.printState();
        }
        else if (command[0].equals("move"))
        {
            this.move(command[1]);
        }
        else if (command[0].equals("solve"))
        {
            if (command[1].equals("A-star"))
            {
                AStarSearch a = new AStarSearch(this, command[2]);
                a.solveAStar();
                System.out.println("Calling A*");
            }
            else if (command[1].equals("beam"))
            {
                //Create Beamsearch object
                //Call beam search method
            }
        }
        else if (command[0].equals("quit"))
            return;
    }
    
    /**
     * Method to check if the current state is the goal state
     * @return 
     */
    public boolean isGoalState()
    {
        int count = 0;
        for (int i = 0; i < this.getPuzzle().length; i++)
        {
            for (int j = 0; j < this.getPuzzle()[i].length; j++)
            {
                if (this.getPuzzle()[i][j].getValue() != count)
                    return false;
                count++;
            }
        }
        return true;
    }
    
    /**
     * Method to return the number of misplaced tiles (h1)
     * @param s
     * @return 
     */
    public int getMisplacedTiles()
    {
       Tile[][] board = this.getPuzzle();
       int count = 0;
       for (int i = 0; i < board.length; i++)
       {
         for (int j = 0; j < board[i].length; j++)
         {
             if (! (board[i][j].getValue() == this.goalState[i][j].getValue()))
             {
                 count++;
             }
         }
       }
       return count;
    }
    
    /**
     * Method to compute and return the total Manhattan distance for a given state/board (h2)
     * @param s
     * @return 
     */
    public int getDistance() 
    {
       int totalDistance = 0;
       Tile[][] board = this.getPuzzle();
       for (int i = 0; i < board.length; i++)
       {
          for (int j = 0; j < board[i].length; j++) 
          { 
              int value = board[i][j].getValue();
              if (value != 0)  //0 represents the blank tile, don't process it
              {
                  int goalRow = (value - 1) / board.length; // Row of tile in Goal State
                  int goalCol = (value) % board.length;     // Column of tile in Goal State FIXME: somehow getting wrong columns and rows
                  int distRow = i - goalRow;                // Distance to the goal row
                  int distCol = j - goalCol;                // Distance to the goal Column
                  totalDistance += Math.abs(distRow) + Math.abs(distCol);   //Distance could be negative, confirm it's positive 
              } 
          }
       }
       return totalDistance;
    }
    
    /**
     * Method to solve the puzzle using local beam search with k states
     * @param k 
     */
    public void solveBeam(int k)
    {
        //max Nodes is:
        //If a search looks at n-nodes, abadnon the search, indicate an error
    }
    
    /**
     * Main Method, takes a text-file name as input
     * @param args 
     */
    public static void main (String[] args)
    {
      State s = new State(); //Modify to accept 1 argument for the text file
      Scanner scan = new Scanner (System.in);
      String cmd = "";
      while (!(cmd.equals("quit")))
      {
          cmd = scan.nextLine();
         //str = scan.nextLine();
         s.acceptCommands(cmd);
      }
    }
}
