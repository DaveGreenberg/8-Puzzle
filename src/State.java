/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
/**
 * A class to represent the board of the 8-puzzle
 * @author David Greenberg
 */
public class State 
{
    //Matrix to store the board of the 8-puzzle
    private Tile[][] puzzle;
    
    //String to store the file name
    private String inputFile;
    
    //String to store Scanner line
    //private static String str;
    
    /**
     * Constructor to accept a file
     * @param file 
     */
    /*public State(String file)
    {
        this.inputFile = file;
    } */
    
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
     */
    public void move (String dir)
    {
       int temp;  //Used to swap Tile values
       int row = 0;
       int col = 0;
       for (int i = 0; i < puzzle.length; i++)
       {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                if (puzzle[i][j].getValue() == 0)
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
                    temp = puzzle[row][col].getValue();
                    puzzle[row][col].setValue(puzzle[row][col - 1].getValue());
                    puzzle[row][col - 1].setValue(temp);
                }    break;
            case "right":
                if ((col + 1) == 3)
                    throw new IndexOutOfBoundsException("Right is out of bounds!"); //Switched with Down
                else
                {
                    temp = puzzle[row][col].getValue();
                    puzzle[row][col].setValue(puzzle[row][col + 1].getValue());
                    puzzle[row][col + 1].setValue(temp);
                }    break;
            case "up":
                if ((row - 1) == -1)
                    throw new IndexOutOfBoundsException("Up is out of bounds!");
                else
                {
                    temp = puzzle[row][col].getValue();
                    puzzle[row][col].setValue(puzzle[row - 1][col].getValue());
                    puzzle[row - 1][col].setValue(temp);
                }    break;
            case "down":
                if ((row + 1) == 3)
                    throw new IndexOutOfBoundsException("Down is out of bounds!");
                else
                {
                    temp = puzzle[row][col].getValue();
                    puzzle[row][col].setValue(puzzle[row + 1][col].getValue());
                    puzzle[row + 1][col].setValue(temp);
                }    break;
            default:
                System.out.println("Invalid Direction, no move made!");
                break;
        }
    }
    
    /**
     * Method to make a series of random moves
     * @param n number of moves to make
     */
    public void randomizeState (int n)
    {
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
        else if (command[0].equals("quit"))
            return;
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
