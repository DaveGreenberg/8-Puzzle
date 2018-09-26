import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.*;

/**
 * A class to represent the state of the 8-puzzle
 * @author David Greenberg
 */
public class State 
{
    //Matrix to store the board of the 8-puzzle
    private Tile[][] puzzle;
    
    //Matrix to store the goal board
    final Tile[][] goalState;
    
    //Used to store what the current state is
    public State currentState;
    
    //Store max nodes (for A* Search)
    public static int maxNodes = Integer.MAX_VALUE;
    
    //Random object for consistent random # generation
    Random rnd = new Random();
    
    /**
     * Constructor to set up the goal State and the random number seed
     */
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
        
        rnd.setSeed(1234567890);
    }
    
    /**
     * Method to return the puzzle state/board
     * @return Tile[][] puzzle
     */
    public Tile[][] getPuzzle()
    {
        return this.puzzle;
    }
    
    /**
     * Method to set the puzzle state with a tile matrix
     * @param state puzzle state to set
     */
    public void setPuzzle(Tile[][] state)
    {
        this.puzzle = state;
    }
    
    /**
     * Set the puzzle state. The goal state  is "b12 345 678"
     * @param str State 
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
     * @return State new State after move
     */
    public State move (String dir)
    {
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
                    temp = result[row][col];
                    result[row][col] = result[row][col - 1];
                    result[row][col - 1] = temp;
                }    break;
            case "right":
                if ((col + 1) == 3)
                    throw new IndexOutOfBoundsException("Right is out of bounds!"); //Switched with Down
                else
                {
                    temp = result[row][col];
                    result[row][col] = result[row][col + 1];
                    result[row][col + 1] = temp;
                }    break;
            case "up":
                if ((row - 1) == -1)
                    throw new IndexOutOfBoundsException("Up is out of bounds!");
                else
                {
                    temp = result[row][col];
                    result[row][col] = result[row - 1][col];
                    result[row - 1][col] = temp;
                }    break;
            case "down":
                if ((row + 1) == 3)
                    throw new IndexOutOfBoundsException("Down is out of bounds!");
                else
                {
                    temp = result[row][col];
                    result[row][col] = result[row + 1][col];
                    result[row + 1][col] = temp;
                }    break;
            default:
                System.out.println("Invalid Direction, no move made!");
                break;
        }
        State newState = new State();
        newState.setPuzzle(result);
        return newState;
    }
    
    /**
     * Method to make a series of random moves
     * @param n number of moves to make
     */
    public void randomizeState (int n)
    {
        ArrayList<String> legalMoves = new ArrayList<>();
        for (int moves = 0; moves < n; moves++)
        {
            int row = 0;
            int col = 0;
            for (int i = 0; i < this.getPuzzle().length; i++)
            {
                for (int j = 0; j < this.getPuzzle()[i].length; j++)
                {
                    if (this.getPuzzle()[i][j].getValue() == 0)
                    {
                        row = i;
                        col = j;
                    }
                }
            }
            
            legalMoves.clear();
            //Now, Gather the possible moves

            //left is possible
            if (! ((col - 1) == -1))
            {
               legalMoves.add("left");
            }
            //right is possible
            if (! ((col + 1) == 3))
            {
               legalMoves.add("right");  
            }
            //up is possible
            if (! ((row - 1) == -1))
            {
               legalMoves.add("up"); 
            }
            //down is possible
            if (! ((row + 1) == 3))
            {
               legalMoves.add("down");
            }
            
            //Now, figure out which of the legal moves to do randomly
            int r = (int) (rnd.nextInt (legalMoves.size()));
            puzzle = this.move (legalMoves.get(r)).getPuzzle();
        }
    }
    
    /**
     * Method to handle commands from the Scanner and input file
     * @param inputString command 
     */
    public void acceptCommands(String inputString)
    {
        System.out.println(inputString);
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
        else if (command[0].equals ("maxNodes"))
        {
            maxNodes = Integer.parseInt(command[1]);
        }
        else if (command[0].equals("solve"))
        {
            if (command[1].equals("A-star"))
            {
                AStarSearch a = new AStarSearch(this, command[2]);
                System.out.println("Calling A*...");
                a.solveAStar(maxNodes);
            }
            else if (command[1].equals("beam"))
            {
                BeamSearch b = new BeamSearch(this, Integer.parseInt(command[2]));
                System.out.println("Calling Beam...");
                b.solveBeam(Integer.parseInt(command[2]));
            }
        }
        else if (command[0].equals ("experimentA"))
        {
            this.experimentA(command[1]);
        }
        else if (command[0].equals ("experimentC"))
        {
            this.experimentC(Integer.parseInt(command[1]));
        }
        else if (command[0].equals("quit"))
            return;
    }
    
    /**
     * Method to check if the current state is the goal state
     * @return boolean if this puzzle is the goal state 
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
     * @return int number of tiles out of place
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
     * @return Manhattan distance of the board
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
     * Method to conduct experiment A; Return the fraction of solvable puzzles
     * @param heuristic h1 or h2
     * @return 
     */
    public double experimentA(String heuristic)
    {
        double h1NumSolved = 0;
        double h2NumSolved = 0;
        AStarSearch a;
        for (int i = 1; i <= 1000; i++)
        {
            maxNodes = (100);
            this.randomizeState(1);
            if (heuristic.equals("h1"))
            {
                //System.out.println("trying H1");
                a = new AStarSearch(this, "h1");
                String str = a.solveAStar(maxNodes);
                //System.out.println(value);
                if (! str.startsWith("Max"))
                    h1NumSolved++;
            }
            else if (heuristic.equals("h2"))
            {
                a = new AStarSearch(this, "h2");
                String str = a.solveAStar(maxNodes);
                if (! str.startsWith("Max"))
                    h2NumSolved++;
            }
            else
                return h1NumSolved; //heuristic invalid
        }
        System.out.println("h1 Num Solved out of 1000: " + h1NumSolved);
        System.out.println("h2 Num Solved out of 1000: " + h2NumSolved);
        return h1NumSolved;

    }
    
    /**
     * Method to conduct experiment C; return length of Beam and compare to A* length
     * @param k
     * @return 
     */
    public String experimentC(int k)
    {
        int beamSuccesses = 0;
        int aStarH1Successes = 0;
        int aStarH2Successes = 0;
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        StringBuilder builder3 = new StringBuilder();
        BeamSearch b;
        AStarSearch a1;
        AStarSearch a2;
        for (int i = 0; i <= 25; i++)
        {
             this.randomizeState(i);
             b = new BeamSearch (this, k);
             a1 = new AStarSearch (this, "h1");
             a2 = new AStarSearch (this, "h2");
             String beamResult = b.solveBeam(k);
             if (beamResult.startsWith("Number"))
                 beamSuccesses++;
             String h1Result = a1.solveAStar(k);
             if (h1Result.startsWith("A*"))
                 aStarH1Successes++;
             String h2Result = a2.solveAStar(k);
             if (h2Result.startsWith("A*"))
                 aStarH2Successes++;
             builder1.append (beamResult);
             builder2.append (h1Result);
             builder3.append (h2Result);
        }
        System.out.println("Beam Ave. Seq. Length: " + (builder1.toString().length() / 10) + " A* h1 Ave. Seq. Length: " + (builder2.toString().length() / 10) +
                 " A* h2 Ave. Seq. Length: " + (builder3.toString().length() / 10));
        System.out.println("Beam #Successes: " + beamSuccesses + " H1 #Successes: " + aStarH1Successes + " H2 #Successes: " + aStarH2Successes);
        return "Beam Ave. Seq. Length: " + (builder1.toString().length() / 10) + "A* h1 Ave. Seq. Length: " + (builder2.toString().length() / 10)
                + "A* h2 Ave. Seq. Length: " + (builder3.toString().length() / 10);
    }
    
    /**
     * Main Method, takes a text-file name as input
     * @param args input file 
     */
    public static void main (String[] args)
    {
      State s = new State();
      /*Scanner scan = new Scanner (System.in);
      String cmd = "";
      while (!(cmd.equals("quit")))
      {
          cmd = scan.nextLine();
         //str = scan.nextLine();
         s.acceptCommands(cmd);
      } */
      
      try
      {
        Scanner reader = new Scanner(new File(args[0]));
        while (reader.hasNext())
        {
          String str = reader.nextLine();
          s.acceptCommands(str);
        }
        reader.close();
      }
      catch (Exception e)
      {
        System.out.println("No input file found!"); 
      }
    }
}
