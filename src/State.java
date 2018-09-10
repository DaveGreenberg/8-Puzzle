/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.StringBuilder;
import java.lang.Exception;

/**
 *
 * @author Dave
 */
public class State 
{
    //Fields
    private Tile[][] puzzle;
    
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
              if (i == 0) //First row of puzzle
              {
                  if (r1.charAt(j) == 'b')
                     state[i][j] = new Tile (0);
                  else
                     state[i][j] = new Tile (Integer.parseInt(String.valueOf(r1.charAt(j))));
              }
              else if (i == 1) //Second row of puzzle
              {
                  if (r2.charAt(j) == 'b')
                     state[i][j] = new Tile (0);
                  else
                     state[i][j] = new Tile (Integer.parseInt(String.valueOf(r2.charAt(j))));
              }
              else if (i == 2) //Third row of puzzle
              {
                  if (r3.charAt(j) == 'b')
                     state[i][j] = new Tile (0);
                  else
                     state[i][j] = new Tile (Integer.parseInt(String.valueOf(r3.charAt(j))));
              }
              else
                  System.out.println("Something went wrong");
            }
        }
        puzzle = state;
    }
    
    /**
     * 
     * @return puzzle state as String
     */
    public String printState()
    {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                if (((count % 3) == 0) && count > 0 && count < 8)
                    builder.append(' ');
                builder.append(puzzle[i][j].getValue());
                count++;
            }
        }
        return builder.toString();
    }
    
    /**
     * Method to swap the blank tile up, down, left or right
     * @param dir Direction 
     */
    public void move (String dir)
    {
       int temp;
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
       
       if (dir.equals ("left"))
       {
           if ((col - 1) == -1)
               throw new IndexOutOfBoundsException("Left is out of bounds!");  //Switched with Up
           else
           {
               temp = puzzle[row][col].getValue();
               puzzle[row][col].setValue(puzzle[row][col - 1].getValue());
               puzzle[row][col - 1].setValue(temp);
           }
          
       }
       else if (dir.equals ("right"))
       {
           if ((col + 1) == 3)
               throw new IndexOutOfBoundsException("Right is out of bounds!"); //Switched with Down
           else
           {
               temp = puzzle[row][col].getValue();
               puzzle[row][col].setValue(puzzle[row][col + 1].getValue());
               puzzle[row][col + 1].setValue(temp);
           }
       }
       else if (dir.equals ("up"))
       {
           if ((row - 1) == -1)
               throw new IndexOutOfBoundsException("Up is out of bounds!");
           else
           {
               temp = puzzle[row][col].getValue();
               puzzle[row][col].setValue(puzzle[row - 1][col].getValue());
               puzzle[row - 1][col].setValue(temp);
           } 
       }
       else if (dir.equals ("down"))
       {
           if ((row + 1) == 3)
               throw new IndexOutOfBoundsException("Down is out of bounds!");
           else
           {
               temp = puzzle[row][col].getValue();
               puzzle[row][col].setValue(puzzle[row + 1][col].getValue());
               puzzle[row + 1][col].setValue(temp);
           }
       }
    }
    
    public void randomizeState (int n)
    {
        double r = 0.0;
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
     * Main Method
     * @param args 
     */
    public static void main (String[] args)
    {
        
    }
}
