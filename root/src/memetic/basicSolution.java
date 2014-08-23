package memetic;

import java.util.Random;
import java.util.Vector;

import logic.Global;
import logic.MainProcessor;

import tabusearch.MySolution;


import bd.Nurse;
import bd.Schedule;
import bd.Specialty;

public class basicSolution extends Thread{

	private MySolution msol;
	private MainProcessor mref;
	
	public basicSolution(MainProcessor mref, Specialty spec){
		this.mref = mref;
		this.msol= new MySolution(spec);
		this.start();
	}

	
	
	public void run(){
		Random rand = new Random();
		Vector<Nurse> temp3 = msol.spec.getNurses();
		Schedule temp4 = msol.spec.getSchedule();
		
		for (int j = 0; j < 7; j++) 
		{
			for(int q = 0 ; q < 3; q++)
			{
				
				if(Global.logicdebug)
					System.out.println(msol.spec.getAssign(j,q) + "|" + temp3.size());
				
				while(!(temp4.isShiftFull(j, q, msol.spec.getDaysAssign())))
				{
					int insertNurse= rand.nextInt(msol.spec.getNurses().size());
					//System.out.println("Size is "+h.getSpecialties().get(i).getNurses().size()+" Nurse is "+insertNurse);
					if(!(msol.spec.getSchedule().isAssigned(j, q, msol.spec.getNurses().get(insertNurse))))
					{
						if(Global.logicdebug)
							System.out.println("Sim");
						msol.spec.getSchedule().AssignNurse(j, q, msol.spec.getNurses().get(insertNurse));
					}
					else
						{
							if(Global.logicdebug)
								System.out.println("No");
						}
				}
			}
		}
		
		int min=Integer.MAX_VALUE;
		int pos=0;
		for (int k = 0; k < Global.solutions.length; k++) {
			if(Global.solutions[k].getObjectiveValue()==null)
			{
				pos=k;
				min=-1;
			}
			else if(Global.solutions[k].getObjectiveValue()[0]<=min)
				{
					pos=k;
					min=(int)Global.solutions[k].getObjectiveValue()[0];
				}
		}
		if(msol.value>=min)
		{
			Global.solutions[pos]= new MySolution(msol);
		}
		
		mref.notify(true, null);
		
	}
	
	

}
