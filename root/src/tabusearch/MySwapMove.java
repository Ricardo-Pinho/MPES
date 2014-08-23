package tabusearch;

import org.coinor.opents.*;

import bd.Nurse;

public class MySwapMove implements Move 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 932527393764846650L;
	public Nurse nurse1;
    public Nurse nurse2;
    public int[] shift1 = new int[2];
    public int[] shift2 = new int[2];
    
    
    public MySwapMove( Nurse nurse1, Nurse nurse2, int[] snurse1, int[] snurse2 )
    {   
        this.nurse1 = nurse1;
        this.nurse2 = nurse2;
        this.shift1 = snurse1;
        this.shift2 = snurse2;
    }   // end constructor
    
    public MySwapMove( Nurse nurse1, Nurse nurse2, int[] snurse1)
    {   
        this.nurse1 = nurse1;
        this.nurse2 = nurse2;
        this.shift1 = snurse1;
        this.shift2[0] = -1;
    }   // end constructor
    
    
    public void operateOn( Solution soln)
    {
    	if(shift2[0]==-1)
    		((MySolution)soln).spec.switchNurses(nurse1, nurse2, shift1);
    	else
    		((MySolution)soln).spec.switchShifts(nurse1, nurse2, shift1, shift2);
        
    }   // end operateOn
    
    
    public void undoOperation( Solution soln)
    {
    	if(shift2[0]==-1)
    		((MySolution)soln).spec.switchNurses(nurse2, nurse1, shift1);
    	else
    		((MySolution)soln).spec.switchShifts(nurse2, nurse1, shift1, shift2);
        
    }  
    
    /** Identify a move for SimpleTabuList */
    public int hashCode()
    {   
        int hash = shift1[0]*10+shift1[1];
    	return hash;
    }   // end hashCode
    
}   // end class MySwapMove
