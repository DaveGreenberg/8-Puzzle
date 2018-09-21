/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
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
