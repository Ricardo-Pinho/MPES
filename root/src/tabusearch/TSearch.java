package tabusearch;


import logic.Global;
import logic.MainProcessor;

import org.coinor.opents.*;

import bd.Specialty;

public class TSearch extends Thread implements TabuSearchListener 
{
	/**
	 * 
	 */
	private boolean running = true;
	private MainProcessor mref;
	public Specialty result;
	public TSearch(MainProcessor mref, Specialty spec){
		this.result = spec;
		this.mref = mref;
		
		this.start();
	}
	
	
	public void run(){
		
		
		ObjectiveFunction objFunc = new MyObjectiveFunction();
        Solution initialSolution  = new MySolution( result );
        MoveManager   moveManager = new MyMoveManager();
        TabuList         tabuList = new SimpleTabuList( 7 ); // In OpenTS package
        
        for (int i = 0; i < Global.solutions.length; i++) {
        	if(!running){
            	return;
            }
			Global.solutions[i] = new MySolution();
		}
        if(moveManager.getAllMoves(initialSolution).length==0)
        	{
	        	for (int i = 0; i < Global.solutions.length; i++) {
		        	if(!running){
		            	return;
		            }
						Global.solutions[i] = new MySolution((MySolution)initialSolution);
	        	}
	            if(running){
	            	running = false;
	                mref.notify(true, null);
	            }
	            return;
        	}
        
        // Create Tabu Search object
        TabuSearch tabuSearch = new SingleThreadedTabuSearch(
                initialSolution,
                moveManager,
                objFunc,
                tabuList,
                new BestEverAspirationCriteria(), // In OpenTS package
                true ); // maximizing = yes/no; false means minimizing
        
        // Start solving
        tabuSearch.addTabuSearchListener( this );
        tabuSearch.setIterationsToGo( Global.tsiterations ); // corre o tabu search iterations vezes
        tabuSearch.startSolving();
        
        // Show solution
        //MySolution best = (MySolution)tabuSearch.getBestSolution();
        //best.value=((MyObjectiveFunction) objFunc).evaluateSchedule(best);
        int pos=0;
        int max=0;
        for (int i = 0; i < Global.solutions.length; i++) {
        	if(!running){
            	return;
            }
        	if(max<Global.solutions[i].getObjectiveValue()[0])
        	{
        		pos=i;
        		max=(int) Global.solutions[i].getObjectiveValue()[0];
        	}
		}
        //System.out.println( "Best Solution:\n" + Global.solutions[pos] );
        MySolution best = new MySolution( Global.solutions[pos]);
        if(Global.logicdebug)
        	System.out.println( "Best Solution:\n" + best );
        //System.out.println("Others were:");
        
        for (int i = 0; i < Global.solutions.length; i++) {
        	if(!running){
            	return;
            }
        	if(Global.logicdebug)
        		System.out.println("\t"+Global.solutions[i]);
		}
        //spec=best.spec;
        result = best.spec;
        
        
        if(running){
        	running = false;
            mref.notify(true, null);
        }
        
        //return best.spec;
        //System.out.println("teste");
	}
	
	
	
	public void forceStop(){
		running = false;
	}
	
	public Specialty getSolution(){
		return result;
	}
   

	@Override
	public void improvingMoveMade(TabuSearchEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newBestSolutionFound(TabuSearchEvent arg0) {
		//System.out.println("Better Solution:");
		//System.out.println(arg0.getTabuSearch().getCurrentSolution());
		//System.out.println("Value is "+arg0.getTabuSearch().getCurrentSolution().getObjectiveValue());
	}

	@Override
	public void newCurrentSolutionFound(TabuSearchEvent arg0) {
		checkIfInsert((MySolution)arg0.getTabuSearch().getCurrentSolution());
		if(Global.logicdebug)
			System.out.println("Current Solution:");
		if(Global.logicdebug)
			System.out.println(arg0.getTabuSearch().getCurrentSolution());
		//System.out.println("Value is "+arg0.getTabuSearch().getCurrentSolution());
	}

	@Override
	public void noChangeInValueMoveMade(TabuSearchEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tabuSearchStarted(TabuSearchEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tabuSearchStopped(TabuSearchEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unimprovingMoveMade(TabuSearchEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void checkIfInsert(MySolution sol)
	{
		int min=Integer.MAX_VALUE;
		int pos=0;
		for (int i = 0; i < Global.solutions.length; i++) {
			if(Global.solutions[i].getObjectiveValue()==null)
			{
				pos=i;
				min=-1;
			}
			else if(Global.solutions[i].getObjectiveValue()[0]<=min)
				{
					pos=i;
					min=(int)Global.solutions[i].getObjectiveValue()[0];
				}
		}
		if(sol.getObjectiveValue()[0]>min)
		{
			Global.solutions[pos]= new MySolution(sol);
		}
	}

}   // end class Main