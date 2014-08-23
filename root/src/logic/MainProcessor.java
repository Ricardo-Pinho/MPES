package logic;

import memetic.basicSolution;
import genetic.GenAlg;
import tabusearch.MySolution;
import tabusearch.TSearch;
import ui.Ui;
import bd.Hospital;
import bd.Results;
import bd.Schedule;

public class MainProcessor extends Thread {
	
	private Hospital href;
	private Sgen scheduleGenerator;
	private Object lock;
	private GenAlg ga;
	private TSearch ts;
	private boolean running = false;
	private long start_time;
	public MainProcessor(Hospital href){
		this.href = href;
		lock = new Object();
	}
	
	
	public void run(){
		this.running = true;
		start_time = System.currentTimeMillis();
		scheduleGenerator = new Sgen(this, href);
		Ui.updateScheduleStatus("Generating schedule", 100/4);
		scheduleGenerator.start();
		
		lock();
		
		if(!running){
			return;
		}
		
		int indexAlgm = Ui.getOptimizationAlgorithm(), itu = 0;
		
		for(int i=1; i < href.getSpecialties().size() ;i++)
		{
			
			if(indexAlgm == 0){
				Ui.updateScheduleStatus("Optimizing with Tabu Search for specialty " + href.getSpecialties().get(i).getName(), 25 + ((i*75)/href.getSpecialties().size()));
				ts = new TSearch(this,href.getSpecialties().get(i));
				lock();
				href.setSpecialty(i,ts.getSolution());
			}else if(indexAlgm == 1){
				ts = new TSearch(this,href.getSpecialties().get(i));
				Ui.updateScheduleStatus("Optimizing with Switch for specialty " + href.getSpecialties().get(i).getName() + ": Tabu Search for generating solution",  25 + ((itu*75)/(2*href.getSpecialties().size())));
				lock();
				href.setSpecialty(i,ts.getSolution());
				itu++;
				ga = new GenAlg(this);
				Ui.updateScheduleStatus("Optimizing with Switch  Algorithm " + href.getSpecialties().get(i).getName() + ": Genetic Algorithm processing",  25 + ((itu*75)/(2*href.getSpecialties().size())));
				lock();
				href.setSpecialty(i, ga.getSolution());
				itu++;
			}else if(indexAlgm == 2){
			
		        for (int j = 0; j < Global.solutions.length; j++) {
					Global.solutions[j] = new MySolution();
				}
				MySolution initSolution = new MySolution(href.getSpecialties().get(i));
				Global.solutions[0] = initSolution;
				
				for (int j = 0; j < Global.baseSolIterations; j++) 		
				{	
					new basicSolution(this,href.getSpecialties().get(i));
					//basicSol.run();
					lock();
					
				}
				ga = new GenAlg(this);
				Ui.updateScheduleStatus("Optimizing with Memetic Algorithm " + href.getSpecialties().get(i).getName(), 25 + ((i*75)/href.getSpecialties().size()));
				lock();
				href.setSpecialty(i, ga.getSolution());
				System.out.println("teste");
			}
		}
		long time = System.currentTimeMillis() - start_time;
		
		new Results(href.getFileName(),indexAlgm, time,href.getSpecialties());
		Ui.updateScheduleStatus("Complete", 100);
		signalSucess();
	}
	
	
	public void notify(boolean status, String error){
		if(!status){
			Ui.stopProcessing(false, error);
		}
		
		unlock();
		
		
	}
	
	
	public void forceStop(String error, boolean master){
		if(running){
			running = false;
			if(!master){
				Ui.stopProcessing(false, error);
			}
			if(scheduleGenerator != null){
				scheduleGenerator.forceStop(null, true);
			}
		}
		
		
	}
	
	
	public void signalSucess(){
		MainP.saveBD();
		Ui.stopProcessing(true, null);
	}
	
	
	private void lock(){
		synchronized(lock){
			try {
				lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void unlock(){
		synchronized(lock){
			lock.notify();
		}
	}
	
	
	
	

}
