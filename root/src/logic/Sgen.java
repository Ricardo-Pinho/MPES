package logic;

import genetic.GenAlg;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import tabusearch.TSearch;
import ui.Ui;


import bd.Hospital;
import bd.Nurse;
import bd.Schedule;
import bd.Specialty;

public class Sgen {
	
	/*
	 * Garante que todos as posicoes sao preenchidas, a mesma enfermeira nao fica atribuida duas vezes ao mesmo turno e so as enfermeiras daquela especialidade sao atribuidas
	 */
	
	private Hospital href;
	private Vector<SgenWorker> workers;
	private boolean processing = false;
	private MainProcessor mref;
	private Semaphore s1;
	private int completedWorkers = 0;
	
	public Sgen(MainProcessor mref, Hospital href)
	{
		this.mref = mref;
		this.href = href;
		for (int i = 0; i < href.getSpecialties().size(); i++) 
		{
			href.getSpecialties().get(i).setSchedule(new Schedule());
			//System.out.println("Constraints of "+href.getSpecialties().get(i).getName()+": "+href.getSpecialties().get(i).getTotalConstraintNum());
		}
		workers = new Vector<SgenWorker>();
		s1 = new Semaphore(10, true);
		init();
	}
	
	
	
	private void init(){
		SgenWorker temp;
		for(int i = 0 ; i < 7; i++){
			temp = new SgenWorker(this, i, href);
			workers.add(temp);
		}
	}
	
	
	/*public void run(){
		ArrayList<Specialty> temp1 = href.getSpecialties();
		int size1 = temp1.size();
		Specialty temp2;
		Vector<Nurse> temp3;
		Schedule temp4;
		Random rand = new Random();
		for(int i=0; i < size1 ;i++)
		{
			//System.out.println("Number of Nurses: "+h.getSpecialties().get(i).getNurses().size());
			//while(!(h.getSpecialties().get(i).getSchedule().isScheduleFull(h.getSpecialties().get(i).getDaysAssign())))
			//{
				//h.getSpecialties().get(i).getSchedule().isScheduleFull(h.getSpecialties().get(i).getDaysAssign());
			
			temp2 = temp1.get(i);
			temp3 = href.getSpecialties().get(i).getNurses();
			temp4 = temp1.get(i).getSchedule();
			for(int j = 0 ; j < 7; j++)
			{
				//System.out.println("Day "+j);
				for(int q = 0 ; q < 3; q++)
				{
					System.out.println(temp2.getAssign(j,q) + " " + temp3.size());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(temp2.getAssign(j,q) > temp3.size())
					{
						System.out.println("Not enough Nurses");
						return;
					}
					
					while(!(temp4.isShiftFull(j, q, href.getSpecialties().get(i).getDaysAssign())))
					{
						
						int insertNurse= rand.nextInt(href.getSpecialties().get(i).getNurses().size());
						//System.out.println("Size is "+h.getSpecialties().get(i).getNurses().size()+" Nurse is "+insertNurse);
						if(!(href.getSpecialties().get(i).getSchedule().isAssigned(j, q, href.getSpecialties().get(i).getNurses().get(insertNurse))))
						{
							System.out.println("Sim");
							href.getSpecialties().get(i).getSchedule().AssignNurse(j, q, href.getSpecialties().get(i).getNurses().get(insertNurse));
						}
						else
							System.out.println("No");
					}
				}
			}
			//}
		}
		
	}*/
	
	
	public void forceStop(String error, boolean master){
		if(processing){
			processing = false;
			for(int i = 0 ; i < workers.size(); i++){
				workers.get(i).forceStop();
			}
			
			if(!master){
				mref.notify(false, error);
			}
			
		}
		
		
	}
	
	
	public void notifySuccess(){
		try {
			s1.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		completedWorkers++;
		//System.out.println(completedWorkers);
		if(completedWorkers == 7){
			mref.notify(true, null);
			
		}
		s1.release();
		
	}
	
	
	
	/*public Void print_Schedule(Specialty spec){
		System.out.println("Specialty:"+spec.getName());
		System.out.println("////////////////////////////////////////////////////////");
		
		for(int j = 0 ; j < 7; j++)
		{
			System.out.println("Dia "+j+":");
			for(int q = 0 ; q < 3; q++)
			{
				System.out.println("\tTurno "+q+":");
				Vector<Nurse> ntemp=spec.getSchedule().getAssigment(j,q);
				if(ntemp.size()==0)
					System.out.println("\t\tNinguem");
				for (int i = 0; i < ntemp.size(); i++) {
					System.out.println("\t\t"+ntemp.get(i).getName());
				}
				
			}
		}
		return null;
	}*/



	public void start() {
		// TODO Auto-generated method stub
		processing = true;
		for(int i = 0 ; i < workers.size(); i++){
			workers.get(i).start();
		}
		
	}



	
}
