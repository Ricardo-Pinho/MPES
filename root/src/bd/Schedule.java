package bd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import logic.Global;
import bd.Nurse;

public class Schedule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1960889519422383059L;
	private ArrayList<ArrayList<ArrayList<Nurse>>> assignments;
	Nurse[][][] abd;
	//private Vector<Vector<Integer>> req_assignments;
	//private Vector<String> specialties;
	//cada especialidade vai ter um horario
	
	
	public Schedule(/*Vector<String> specialties*/){
		//this.specialties = specialties;
		init();
	}
	
	
	
	
	public void save(){
		abd = new Nurse[7][3][];
		for(int i = 0 ; i < 7 ; i++){
			for(int j = 0 ; j < 3; j++){
				abd[i][j] = assignments.get(i).get(j).toArray(new Nurse[assignments.get(i).get(j).size()]);
			}
		}
	}
	
	public void load(){
		assignments = new ArrayList<ArrayList<ArrayList<Nurse>>>();
		
		ArrayList<ArrayList<Nurse>> temp;
		for(int i = 0 ; i < 7 ; i++){
			temp = new ArrayList<ArrayList<Nurse>>();
			for(int j = 0 ; j < 3; j++){
				temp.add(new ArrayList<Nurse>(Arrays.asList(abd[i][j])));
			}
			assignments.add(temp);
		}
		
	}
	
	
	private void init(){
		assignments = new ArrayList<ArrayList<ArrayList<Nurse>>>();
		
		
		for(int i = 0; i < 7; i++){ //por cada dia
			assignments.add(new ArrayList<ArrayList<Nurse>>());
			for(int j = 0; j < 3; j++){ //por cada turno
				//System.out.println(j);
				assignments.get(i).add(new ArrayList<Nurse>());
			}
		}
	}
	
	
	
	public boolean isAssigned(/*int specialty,*/ int day, int shift, Nurse nurse){
		if(validate(/*specialty,*/day,shift)){
			//Vector<Nurse> vec = assignments.get(specialty);
			/*for(int i = 0 ; i < vec.size();i++){
				if(vec.get(i).getName().equals(nurse)){
					return vec.get(i).isAssigned(day,shift);
				}
				
			}*/
			
			ArrayList<Nurse> temp1 = assignments.get(day).get(shift);
			int size1 = temp1.size();
			//System.out.println("Checking "+nurse.getName()+" for day "+day+" shift "+shift);
			
			for(int i = 0; i < size1 ; i++) {
				if(temp1.get(i).getName().equals(nurse.getName()))
					return true;
			}
			
			
			return false;
		}
		
		
		
		
		return false;
	}
	
	
	public ArrayList<Nurse> getAssigment(/*int specialty,*/ int day, int shift){
		if(validate(/*specialty,*/day,shift))
		{
			//System.out.println(1);
			return assignments.get(day).get(shift);
		}
		
		return null;
	}
	
	public void setAssigment(int day, int shift,ArrayList<Nurse> nurses){
		if(validate(/*specialty,*/day,shift))
		{
			//System.out.println(1);
			assignments.get(day).get(shift).clear();
			assignments.get(day).get(shift).addAll(nurses);
		}
		
	}
	
	
	public void removeAssignment(/*int specialty,*/ int day, int shift){
		if(validate(/*specialty,*/day,shift))
		{
			assignments.get(day).get(shift).clear();
		}
	}
	
	public void AssignNurse(/*int specialty,*/ int day, int shift, Nurse nurse){
		if(validate(/*specialty,*/day,shift))
		{
				assignments.get(day).get(shift).add(nurse);
		}
	}
	
	private boolean validate(/*int specialty,*/ int day, int shift){
		return !(day > Global.WEEK_SUNDAY || day < Global.WEEK_MONDAY || shift > Global.SHIFT_NIGHT || Global.SHIFT_MORNING > shift /*|| specialty < 0 || specialty >= specialties.size()*/);
	}
	
	
	
	public boolean isScheduleFull(Vector<Vector<Integer>> req_assignments){
			for(int j = 0 ; j < 7; j++)
			{
				if(Global.bddebug)
					System.out.println("Day "+j);
				for(int q = 0 ; q < 3; q++)
				{
					if(Global.bddebug)
						System.out.println("\tShift "+q+": "+req_assignments.get(j).get(q).toString());
					/*if(req_assignments.get(j).get(q)!=assignments.get(j).get(q).size()){
						return false;
					}*/
				}
		}
		
		
		return true;
	}
	
	
	public boolean isShiftFull(int day, int shift, Vector<Vector<Integer>> req_assignments){
		//System.out.println("Is "+assignments.get(day).get(shift).size()+" and needs "+req_assignments.get(day).get(shift));
		
		if(assignments.get(day).get(shift).size()==req_assignments.get(day).get(shift))
			return true;
		else 
			return false;
	}
	
	
	public boolean isAllNursesAssigned(Vector<Nurse> nurses){
			for(int j = 0 ; j < 7; j++)
			{
				for(int q = 0 ; q < 3; q++)
				{
					/*if(!assignments.get(j).get(q).equals("")){
						String temp = assignments.get(j).get(q);
						for(int w = 0 ; w < nurses.size(); w++){
							if(nurses.get(w).equals(temp)){
								nurses.remove(w);
								if(nurses.size() == 0){
									return true;
								}
							}*/
					for (int i = 0; i < nurses.size(); i++) {
						if(assignments.get(j).get(q).contains(nurses.get(i)))
							{
								nurses.remove(i);
								i--;
							}
					}
				}
			}
				if(nurses.size()>0)
					return false;
				else
					return true;
		}


	public int getShiftNo(int j, int q) {
		return assignments.get(j).get(q).size();
	}




	public void removeNurse(int i, int j, int nurse) {
		assignments.get(i).get(j).remove(nurse);
		
	}


		
}
