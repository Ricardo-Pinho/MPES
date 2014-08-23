package bd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import sun.org.mozilla.javascript.internal.ast.ForInLoop;
import tabusearch.MySolution;

import bd.Nurse;

public class Specialty implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6874558605325900330L;
	private String name;
	private Vector<Vector<Integer>> daysAssign;
	private Schedule schedule;
	private Vector<Nurse> nurses;
	private ArrayList<Constraint> constraints;
	private Constraint[] cbd;
	private Semaphore s;
	
	public Specialty(String name){
		this.setName(name) ;
		constraints = new ArrayList<Constraint>();
		initDaysAssigns();
		setSchedule(new Schedule());
		setNurses(new Vector<Nurse>());
		s = new Semaphore(200, true);
	}
	
	/*public Specialty(Specialty spec){
		this.setName(spec.name) ;
		this.constraints = spec.constraints;
		this.constraints=spec.constraints;
		this.daysAssign=spec.daysAssign;
		setSchedule(spec.schedule);
		setNurses(spec.nurses);
		s = spec.s;
	}*/
	
	

	public void save() {
		// TODO Auto-generated method stub
		
		cbd = constraints.toArray(new Constraint[constraints.size()]);
		schedule.save();
		
	}
	
	public void load(){
		constraints = new ArrayList(Arrays.asList(cbd));
		schedule.load();
		
	}

	

	public Specialty() {
		// TODO Auto-generated constructor stub
	}

	public Object clone() {
		try
		{
			return super.clone();
		}
		catch(Exception e){ return null; }
		}

	
	private void initDaysAssigns(){
		
		
		setDaysAssign(new Vector<Vector<Integer>>());
		
		for(int i = 0 ; i < 7; i++){
			Vector<Integer> t1 = new Vector<Integer>();
			for(int j = 0 ; j < 3; j++){
				t1.add(0);
			}
			getDaysAssign().add(t1);
		}
	}
	
	
	public int getTotalConstraintNum()
	{
		int total = constraints.size();
		for (int i = 0; i < nurses.size(); i++) {
			total=total+nurses.get(i).getConstraints().size();
		}
		return total;
	}
	
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAssign(int day, int sift){
		int result = -1;
		
		if( day >= 0 && day <= 6 && sift >= 0 && sift <= 2){
			result = getDaysAssign().get(day).get(sift);
		}
			
		return result;
	}
	
	
	public void setAssign(int day, int sift, int value){
		if( day >= 0 && day <= 6 && sift >= 0 && sift <= 2){
			Vector<Integer> t1 = getDaysAssign().get(day);
			t1.set(sift, value);
			//System.out.println("foi chamado com day " + day + " e sift " + sift + " e valor " + value);
			getDaysAssign().set(day, t1);
		}
	}


	public Vector<Vector<Integer>> getAssigns() {
		// TODO Auto-generated method stub
		return getDaysAssign();
	}


	public Schedule getSchedule() {
		return schedule;
	}


	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}


	public Vector<Vector<Integer>> getDaysAssign() {
		return daysAssign;
	}


	public void setDaysAssign(Vector<Vector<Integer>> daysAssign) {
		this.daysAssign = daysAssign;
	}


	public Vector<Nurse> getNurses() {
		return nurses;
	}


	public void setNurses(Vector<Nurse> nurses) {
		this.nurses = nurses;
	}
	
	public void addNurses(Nurse nurse) {
		this.nurses.add(nurse);
	}


	public void removeNurse(Nurse nurse) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < nurses.size(); i++){
			if(nurses.get(i).getName().equals(nurse.getName())){
				nurses.remove(i);
				return;
			}
		}
	}
	




	public double getTotalConstraintValue()
	{
		double score=0;
		for (int i = 0; i < getConstraints().size(); i++) {
					score=score+getConstraintValue(getConstraints().get(i));
		}
		for (int i = 0; i < getNurses().size(); i++) {
			for (int k = 0; k < getNurses().get(i).getConstraints().size(); k++) {
					score=score+getConstraintValue(getNurses().get(i).getConstraints().get(k));
			}
		}
		
		return score;
	}
	
	public int getConstraint(int type){
		for(int i = 0 ; i < constraints.size();i++){
			if(constraints.get(i).getType() == type){
				return constraints.get(i).getValue();
			}
		}
		
		return 0;
	}
	
	
	public int setConstraint(int type, int value){
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0 ; i < constraints.size();i++){
			if(constraints.get(i).getType() == type){
				if(value > 0){
					constraints.get(i).setValue(value);
					s.release();
					return value;
				}else{
					constraints.remove(i);
					s.release();
					return 0;
				}
			}
		}
		
		if(value > 0){
			constraints.add(new Constraint(type,value));
			s.release();
			return value;
		}else{
			s.release();
			return 0;
		}
	}





	public ArrayList<Nurse> getAssignedNurses(int day, int shift) {
		// TODO Auto-generated method stub
		return schedule.getAssigment(day, shift);
	}





	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}





	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}





	public boolean isConstraintSatisfied(Constraint i) {
		switch(i.getType())
		{
			case 0: {
						
					return true; //0 - Max Assigns (Specialty)
			}
			case 1: {
					for (int j = 0; j < 7; j++) {
						ArrayList<Nurse> nightnurses = getSchedule().getAssigment(j, 2);
						for (int k = 0; k < nightnurses.size(); k++) {
							if(j==6)
							{
								if(getSchedule().isAssigned(0,0,nightnurses.get(k)) ||
									getSchedule().isAssigned(0,1,nightnurses.get(k)) ||
									getSchedule().isAssigned(0,2,nightnurses.get(k)))
									return false;
							}
							else{
								if(getSchedule().isAssigned(j+1,0,nightnurses.get(k)) ||
										getSchedule().isAssigned(j+1,1,nightnurses.get(k)) ||
										getSchedule().isAssigned(j+1,2,nightnurses.get(k)))
										return false;
							}
						}
					}
					return true; //All day break start with assigned shift at night
			}
			default: return false; 
		}
	}
	
	public boolean isConstraintSatisfied(Constraint i, Nurse nurse) {
		switch(i.getType())
		{
				case 1: {
					for (int j = 0; j < 7; j++) {
						if(getSchedule().isAssigned(j,2,nurse))
						{
							if(j==6)
							{}
							else{
								if(getSchedule().isAssigned(j+1,0,nurse) ||
										getSchedule().isAssigned(j+1,1,nurse) ||
										getSchedule().isAssigned(j+1,2,nurse))
										return false;
							}
						}
					}
					return true; //All day break start with assigned shift at night
			}
		
			case 2: {
					
					return true; //Time service (Nurse)
			}
			case 3: {
					int assigns= 0;
					for (int j = 0; j < 7; j++) {
						for (int j2 = 0; j2 < 3; j2++) {
							if(getSchedule().isAssigned(j,j2,nurse))
								assigns++;
						}
					}
					if(assigns>i.getValue())
						return false;
					else
						return true; //Max Assigns (Nurse)
			}
			case 4: {
					int mindays=0;
					boolean assignedday=false;
					for (int j = 0; j < 7; j++) {

						for (int j2 = 0; j2 < 3; j2++) {
							if(getSchedule().isAssigned(j,j2,nurse))
								assignedday=true;
						}
						if(assignedday==true)
							{
								mindays++;
								if(mindays>=i.getValue())
									return true;
							}
						else
						{
							mindays=0;
						}
					}
					return false; //Min Consecutive Days (Nurse) - ta a funcionar
			}
			case 5: {
				int maxdays=0;
				boolean assignedday=false;
				for (int j = 0; j < 7; j++) {
					assignedday=false;
					for (int j2 = 0; j2 < 3; j2++) {
						if(getSchedule().isAssigned(j,j2,nurse))
							assignedday=true;
					}
					if(assignedday==true)
						{
							maxdays++;
							if(maxdays>i.getValue())
								return false;
						}
					else
					{
						maxdays=0;
					}
				}
					return true; //Max Consecutive Days (Nurse) - ta a funcionar
			}
			case 6: {
				int assignsday=0;
				for (int j = 0; j < 7; j++) {
					assignsday=0;
					for (int j2 = 0; j2 < 3; j2++) {
						if(getSchedule().isAssigned(j,j2,nurse))
							assignsday++;
						if(assignsday>i.getValue())
							return false;
					}
				}
					return true; //Max Assigns per Day (Nurse)
			}
			case 7: {
				int leavedays=0;
				boolean assignedday=false;
				for (int j = 0; j < 7; j++) {

					for (int j2 = 0; j2 < 3; j2++) {
						if(getSchedule().isAssigned(j,j2,nurse))
							assignedday=true;
					}
					if(assignedday==false)
						{
							leavedays++;
							if(leavedays>i.getValue())
								return false;
						}
				}
				//if(leavedays==i.getValue())
					return true; //Number of Break Days (Nurse)
				//else
					//return false;
			}
			default: return false; 
		}
	}


	public double evaluateSchedule() {
		double score=0;
		//System.out.println("Spec Constraints - "+solution.spec.getconstraints().length);
		for (int i = 0; i < getConstraints().size(); i++) {
			if(isConstraintSatisfied(getConstraints().get(i)))
				{
					//solution.constraints+="Constraint type "+solution.spec.getconstraints()[i].getType()+" for Specialty\n";
					score=score+getConstraintValue(getConstraints().get(i));
				}
		}
		for (int i = 0; i < getNurses().size(); i++) {
			//System.out.println("Nurse Constraints - "+solution.spec.getNurses().get(i).getconstraints().length);
			for (int k = 0; k < getNurses().get(i).getConstraints().size(); k++) {
				if(isConstraintSatisfied(getNurses().get(i).getConstraints().get(k),getNurses().get(i)))
					{
						//solution.constraints+="Constraint type "+solution.spec.getNurses().get(i).getconstraints()[k].getType()+" for Nurse "+solution.spec.getNurses().get(i).getName()+"\n";
						score=score+getConstraintValue(getNurses().get(i).getConstraints().get(k));
					}
			}
		}
		
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


	public void switchShifts(Nurse nurse1, Nurse nurse2, int[] shift1,
			int[] shift2) {
		getSchedule().getAssigment(shift1[0], shift1[1]).remove(nurse1);
		getSchedule().getAssigment(shift1[0], shift1[1]).add(nurse2);
		getSchedule().getAssigment(shift2[0], shift2[1]).remove(nurse2);
		getSchedule().getAssigment(shift2[0], shift2[1]).add(nurse1);
		
	}





	public int getNumberofAssigns() {
		int total=0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 3; j++) {
				total=total+daysAssign.get(i).get(j);
			}
		}
		return total;
	}





	public void switchNurses(Nurse nurse1, Nurse nurse2, int[] shift1) {
		getSchedule().getAssigment(shift1[0], shift1[1]).remove(nurse1);
		getSchedule().getAssigment(shift1[0], shift1[1]).add(nurse2);	
	}

	public void updateNurses(ArrayList<Nurse> nurses2) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < nurses.size(); i++){
			for(int j = 0 ; j < nurses2.size(); j++){
				if(nurses.get(i).getName().equals(nurses2.get(j).getName())){
					nurses.set(i, nurses2.get(j));
					break;
				}
			}
		}
	}



}
