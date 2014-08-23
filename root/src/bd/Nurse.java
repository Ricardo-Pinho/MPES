package bd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import logic.Global;

public class Nurse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6468546601814613728L;
	private Specialty spec;
	private String name;
	/*private int time_service, 
				maxAssigns, 
				minConsecutiveDays, 
				maxConsecutiveDays,
				minConsecutiveFreeDays,
				maxConsecutiveFreeDays,
				maxAssignsPerDay,
				breakday;*/
	
	private boolean assignCompleteWeek;
	private ArrayList<Constraint> constraints;
	private Constraint[] cbd;
	private Semaphore s;
	
	
	
	//construir estrutura para alocamento de horario
	
	
	
	
	
	
	///////////////////////////////////////////////////////
	
	
	public Nurse(String name, Specialty spec)
	{
		this.spec = spec;
		this.setName(name);
		s = new Semaphore(200, true);
		//this.setTime_service(time_service);
	}

	public Nurse() {
		// TODO Auto-generated constructor stub
		this.spec = new Specialty("");
		this.setName("");
		s = new Semaphore(200, true);
		//this.setTime_service(0);
		constraints = new ArrayList<Constraint>();
		/*this.maxAssigns = 0;
		this.maxAssignsPerDay = 0;
		this.maxConsecutiveDays = 0;
		this.maxConsecutiveFreeDays = 0;
		this.breakday = 0;*/
	}
	

	public void save() {
		// TODO Auto-generated method stub
		cbd = constraints.toArray(new Constraint[constraints.size()]);
	
	}
	
	public void load(){
		constraints = new ArrayList(Arrays.asList(cbd));
		for(int i = 0 ; i < constraints.size(); i++){
			if(Global.bddebug)
				System.out.println("type: " + constraints.get(i).getType() + " " + constraints.get(i).getValue());
		}
		
	}
	
	

	public Specialty getSpec() {
		return spec;
	}

	public void setSpec(Specialty spec) {
		this.spec = spec;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public void update(String name, String spec, int time_service) {
		// TODO Auto-generated method stub
		System.out.println("actualizado ");
		this.name = name;
		this.spec.setName(spec);
		this.time_service = time_service;
		
	}*/

	

	/*public int getMaxAssigns() {
		return maxAssigns;
	}

	public void setMaxAssigns(int maxAssigns) {
		this.maxAssigns = maxAssigns;
	}

	public int getMinConsecutiveDays() {
		return minConsecutiveDays;
	}

	public void setMinConsecutiveDays(int minConsecutiveDays) {
		this.minConsecutiveDays = minConsecutiveDays;
	}

	public int getMaxConsecutiveDays() {
		return maxConsecutiveDays;
	}

	public void setMaxConsecutiveDays(int maxConsecutiveDays) {
		this.maxConsecutiveDays = maxConsecutiveDays;
	}

	public int getMinConsecutiveFreeDays() {
		return minConsecutiveFreeDays;
	}

	public void setMinConsecutiveFreeDays(int minConsecutiveFreeDays) {
		this.minConsecutiveFreeDays = minConsecutiveFreeDays;
	}

	public int getMaxConsecutiveFreeDays() {
		return maxConsecutiveFreeDays;
	}

	public void setMaxConsecutiveFreeDays(int maxConsecutiveFreeDays) {
		this.maxConsecutiveFreeDays = maxConsecutiveFreeDays;
	}

	public int getMaxAssignsPerDay() {
		return maxAssignsPerDay;
	}

	public void setMaxAssignsPerDay(int maxAssignsPerDay) {
		this.maxAssignsPerDay = maxAssignsPerDay;
	}*/

	/*public boolean isAssignCompleteWeek() {
		return assignCompleteWeek;
	}

	public void setAssignCompleteWeek(boolean assignCompleteWeek) {
		this.assignCompleteWeek = assignCompleteWeek;
	}

	public int getTime_service() {
		for(int i = 0 ; i < constraints.size(); i++){
			if(constraints.)
		}
		return time_service;
	}*/

	/*public void setTime_service(int time_service) {
		this.time_service = time_service;
	}

	public int getBreakday() {
		return breakday;
	}

	public void setBreakday(int breakday) {
		this.breakday = breakday;
	}*/
	
	

	/*public boolean isAssigned(int day, int sift) {
		// TODO Auto-generated method stub
		return false;
	}*/
	
	
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

public ArrayList<Constraint> getConstraints() {
	return constraints;
}

public void setConstraints(ArrayList<Constraint> cbd) {
	constraints.clear();
	for(int i = 0; i < cbd.size(); i++){
		constraints.add(cbd.get(i));
	}
}
	
	
	
	
}
