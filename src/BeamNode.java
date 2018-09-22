
/**
 * Class to store the information for a beam search node
 * @author Dave
 */
public class BeamNode extends Node
{
    public BeamNode(State state, int g, int h, String action, Node parent)
    {
       super(state, g, h, action, parent);         
    }
    
    /**
    * Want to compare Nodes based on total H value
    * @param other
    * @return 
    */
   public int compareTo(BeamNode other)
   {
       if (this.getH() < other.getH())
           return -1;
       else if (this.getH() > other.getH())
           return 1;
       else
           return 0;
   }
}
