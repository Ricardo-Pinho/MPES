package logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import bd.Hospital;
import bd.Nurse;
import bd.Schedule;
import bd.Specialty;

public class SgenWorker extends Thread {
	private Sgen sref;
	private int day;
	private Hospital href;
	private boolean running = false;
	
	public SgenWorker(Sgen sref, int day, Hospital href){
		this.href = href;
		this.sref = sref;
		this.day = day;
		
	}
	
	
	public void run(){
		this.running = true;
		ArrayList<Specialty> temp1 = href.getSpecialties();
		int size1 = temp1.size();
		Specialty temp2;
		Vector<Nurse> temp3;
		Schedule temp4;
		Random rand = new Random();
		for(int i=1; i < size1 ;i++)
		{
			//System.out.println("Number of Nurses: "+h.getSpecialties().get(i).getNurses().size());
			//while(!(h.getSpecialties().get(i).getSchedule().isScheduleFull(h.getSpecialties().get(i).getDaysAssign())))
			//{
				//h.getSpecialties().get(i).getSchedule().isScheduleFull(h.getSpecialties().get(i).getDaysAssign());
			temp2 = temp1.get(i);
			temp3 = href.getSpecialties().get(i).getNurses();
			temp4 = temp1.get(i).getSchedule();
			
			//System.out.println("Day "+j);
			for(int q = 0 ; q < 3; q++)
			{
				
				if(Global.logicdebug)
					System.out.println(temp2.getAssign(day,q) + "|" + temp3.size());
				if(temp2.getAssign(day,q) > temp3.size())
				{
					//System.out.println("Not enough Nurses");
					sref.forceStop("Not enough Nurses", false);
					return;
				}
				
				while(!(temp4.isShiftFull(day, q, href.getSpecialties().get(i).getDaysAssign())))
				{
					if(!running){
						return;
					}
					int insertNurse= rand.nextInt(href.getSpecialties().get(i).getNurses().size());
					//System.out.println("Size is "+h.getSpecialties().get(i).getNurses().size()+" Nurse is "+insertNurse);
					if(!(href.getSpecialties().get(i).getSchedule().isAssigned(day, q, href.getSpecialties().get(i).getNurses().get(insertNurse))))
					{
						if(Global.logicdebug)
							System.out.println("Sim");
						href.getSpecialties().get(i).getSchedule().AssignNurse(day, q, href.getSpecialties().get(i).getNurses().get(insertNurse));
					}
					else
						{
							if(Global.logicdebug)
								System.out.println("No");
						}
				}
			}
			
			//}
		}
		
		sref.notifySuccess();
		
	}
	
	public Void print_Schedule(Specialty spec){
		if(Global.logicdebug)
			System.out.println("Specialty:"+spec.getName());
		if(Global.logicdebug)
			System.out.println("////////////////////////////////////////////////////////");
		
		for(int j = 0 ; j < 7; j++)
		{
			if(Global.logicdebug)
				System.out.println("Dia "+j+":");
			for(int q = 0 ; q < 3; q++)
			{
				if(Global.logicdebug)
					System.out.println("\tTurno "+q+":");
				ArrayList<Nurse> ntemp=spec.getSchedule().getAssigment(j,q);
				if(ntemp.size()==0)
					if(Global.logicdebug)
						System.out.println("\t\tNinguem");
				for (int i = 0; i < ntemp.size(); i++) {
					if(Global.logicdebug)
						System.out.println("\t\t"+ntemp.get(i).getName());
				}
				
			}
		}
		return null;
	}


	public void forceStop() {
		// TODO Auto-generated method stub
		running = false;
	}

}
