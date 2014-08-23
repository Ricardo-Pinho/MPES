package genetic;

import bd.Specialty;
import tabusearch.MySolution;
import logic.Global;
import logic.MainProcessor;

public class GenAlg extends Thread {

	private Specialty result;
	private MainProcessor mref;
	private boolean running = true;
	
	public GenAlg(MainProcessor mref){
		this.mref = mref;
		this.start();
	}
	
	public void run() {
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        if(Global.gendebug)
        	System.out.println(getFittestSol().spec.getTotalConstraintValue());
        while (generationCount < Global.geniterations && getFittest()!=getFittestSol().spec.getTotalConstraintValue()) {
        	if(!running){
        		return;
        	}
            generationCount++;
            if(Global.gendebug)
            	System.out.println("Generation: " + generationCount + " Fittest: " + getFittest());
            Algorithm.evolvePopulation();
        }
        if(Global.gendebug)
        	{
        		System.out.println("Solution found!");
        		System.out.println("Generation: " + generationCount);
        		System.out.println("Value:");
        	}
        MySolution best = getFittestSol();
        if(!running){
    		return;
    	}
        double diff=best.getObjectiveValue()[0]-best.spec.getTotalConstraintValue();
        if(Global.gendebug)
        {
        	System.out.println("Value:"+best.getObjectiveValue()[0]);
        	System.out.println("Diference from Optimal Solution: "+diff);
        }
        result = best.spec;
        
        if(running){
        	running = false;
        	mref.notify(true, null);
        }
    }
	
	
	public void forceStop(){
		running = false;
	}
	
	
	public Specialty getSolution(){
		return result;
	}

	private static double getFittest() {
        int pos=0;
        int max=0;
        for (int i = 0; i < Global.solutions.length; i++) {
        	if(max<Global.solutions[i].getObjectiveValue()[0])
        	{
        		pos=i;
        		max=(int) Global.solutions[i].getObjectiveValue()[0];
        	}
		}
        return Global.solutions[pos].getObjectiveValue()[0];
	}
	
	private static MySolution getFittestSol() {
        int pos=0;
        int max=0;
        for (int i = 0; i < Global.solutions.length; i++) {
        	if(max<Global.solutions[i].getObjectiveValue()[0])
        	{
        		pos=i;
        		max=(int) Global.solutions[i].getObjectiveValue()[0];
        	}
		}
        return Global.solutions[pos];
	}

	
}
