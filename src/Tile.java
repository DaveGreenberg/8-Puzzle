/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class to represent a Tile
 * @author Dave
 */
public class Tile 
{
    //Fields
    private int value;
    
    /**
     * Constructor
     */
    public Tile (int val)
    {
        this.value = val;
    }
    
    /**
     * 
     * @return value
     */
    public int getValue()
    {
        return this.value;
    }
    
    /**
     * 
     * @param val 
     */
    public void setValue(int val)
    {
        this.value = val;
    }
}
