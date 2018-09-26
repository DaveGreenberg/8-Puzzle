
/**
 * Class to represent a Tile
 * @author David Greenberg
 */
public class Tile 
{
    //Value of the tile
    private int value;
    
    /**
     * Constructor
     * @param val Value to store in the Tile
     */
    public Tile (int val)
    {
        this.value = val;
    }
    
    /**
     * Get the value
     * @return value
     */
    public int getValue()
    {
        return this.value;
    }
    
    /**
     * Set the value
     * @param val 
     */
    public void setValue(int val)
    {
        this.value = val;
    }
}
