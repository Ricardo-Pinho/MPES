package tabusearch;

import org.coinor.opents.*;

import bd.Constraint;
import logic.Global;


public class MyObjectiveFunction implements ObjectiveFunction
{
   
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 320745987442373297L;

	public MyObjectiveFunction() 
    {   
    }   // end constructor

    
    public double[] evaluate(Solution solution, Move move )
    {
        
        // If move is null, calculate distance from scratch
        if( move == null )
        {
           //System.out.println("Begin");
           // System.out.println(solution);
           //System.out.println("resultado: "+evaluateSchedule((MySolution)solution));
        	return  new double[]{ evaluateSchedule((MySolution)solution)};
        }   // end if: move == null

        // Else calculate incrementally
        else
        {
          //System.out.println("Before");
          //System.out.println(solution);
          double vl = evaluateSchedule((MySolution)solution);
          //System.out.println("resultado: "+vl);
          move.operateOn(solution);
          MySwapMove mv = (MySwapMove) move;
          double[] val = {evaluateSchedule((MySolution)solution)};
          //System.out.println("After");
          //System.out.println(solution);
          //System.out.println("resultado: "+val[0]);
          
          mv.undoOperation(solution);
          //System.out.println("resultado (depois de undo): "+val[0]);
          //System.out.println("hmm");
        	return val;
        }   
    }   // end evaluate


	public double evaluateSchedule(MySolution solution) {
		double score=0;
		//System.out.println("Spec Constraints - "+solution.spec.getCbd().length);
		for (int i = 0; i < solution.spec.getConstraints().size(); i++) {
			if(solution.spec.isConstraintSatisfied(solution.spec.getConstraints().get(i)))
				{
					//solution.constraints+="Constraint type "+solution.spec.getCbd()[i].getType()+" for Specialty\n";
					score=score+getConstraintValue(solution.spec.getConstraints().get(i));
				}
		}
		for (int i = 0; i < solution.spec.getNurses().size(); i++) {
			//System.out.println("Nurse Constraints - "+solution.spec.getNurses().get(i).getCbd().length);
			for (int k = 0; k < solution.spec.getNurses().get(i).getConstraints().size(); k++) {
				if(solution.spec.isConstraintSatisfied(solution.spec.getNurses().get(i).getConstraints().get(k),solution.spec.getNurses().get(i)))
					{
						//solution.constraints+="Constraint type "+solution.spec.getNurses().get(i).getCbd()[k].getType()+" for Nurse "+solution.spec.getNurses().get(i).getName()+"\n";
						score=score+getConstraintValue(solution.spec.getNurses().get(i).getConstraints().get(k));
					}
			}
		}
		//System.out.println("teste");
		solution.value=score;
		//checkIfInsert(solution);
		return score;	
	}
	
	private double getConstraintValue(Constraint cont)
	{
		switch(cont.getType())
		{
			case 0: return 0; //0 - Max Assigns (Specialty)
			case 1: return 3; //All day break start with assigned shift at night
			case 2: return 0; //Time service (Nurse)
			case 3: return 10; //Max Assigns (Nurse)
			case 4: return 1; //Min Consecutive Days (Nurse)
			case 5: return 5; //Max Consecutive Days (Nurse)
			case 6: return 5; //Max Assigns per Day (Nurse)
			case 7: return 5; //Number of Break Days (Nurse)
			default: return 0; 
		}
	}
    
}   // end class MyObjectiveFunction