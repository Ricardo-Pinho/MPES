package bd;

import java.awt.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import logic.Global;

public class Hospital implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7414952052908754420L;
	private String name, oldname;
	private static ArrayList<Nurse> nurses;
	private static ArrayList<Specialty> specialties;
	private Nurse[] nbd;
	private Specialty[] sbd;
	//private int timeBetweenAssign;
	//private boolean nightbeforebreakday;
	
	
	public Hospital()
	{
		nurses = new ArrayList<Nurse>();
		specialties = new ArrayList<Specialty>();
		specialties.add(new Specialty(""));
		name = "";
		oldname = "";
		//this.timeBetweenAssign = 0;
		//this.nightbeforebreakday = false;
		
	}
	
	
	public void save(){
		
		for(int i = 0 ; i < nurses.size(); i++){
				nurses.get(i).save();
		}
		nbd = nurses.toArray(new Nurse[nurses.size()]);
		for(int i = 0 ; i < specialties.size(); i++){
			specialties.get(i).save();
		}
		sbd = specialties.toArray(new Specialty[specialties.size()]);
		
	}
	
	public void load(){
		nurses = new ArrayList(Arrays.asList(nbd));
		for(int i = 0 ; i < nurses.size(); i++){
			nurses.get(i).load();
		}
		
		specialties = new ArrayList(Arrays.asList(sbd));
		for(int i = 0 ; i < specialties.size(); i++){
			specialties.get(i).load();
			specialties.get(i).updateNurses(nurses);
		}
		
		
	}




	public boolean addNurse(Nurse n)
	{
		for(int i = 0; i < nurses.size();i++)
		{
			if(nurses.get(i).getName().equals(n.getName()))
			{
				return false;
			}
		}
		
		nurses.add(n);
		return true;
	}
	
	
	public void removeNurse(Nurse n)
	{
		for(int i = 0; i < nurses.size();i++)
		{
			if(nurses.get(i).getName().equals(n.getName()))
			{
				nurses.remove(i);
				return;
			}
		}
	}
	
	
	public void addSpecialty(Specialty spec){
		for(int i = 0 ; i < specialties.size(); i++){
			if(specialties.get(i).getName().equals(spec.getName())){
				return;
			}
		}
		if(Global.bddebug)
			System.out.println("especialidade adicionada");
		specialties.add(spec);
	}
	
	
	public boolean removeSpecialty(String name){
		for(int i = 0 ; i < specialties.size();i++){
			if(specialties.get(i).getName().equals(name)){
				specialties.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(oldname == null){
			oldname = name;
		}
		this.name = name;
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getOldname() {
		return oldname;
	}


	public void setOldname(String oldname) {
		this.oldname = oldname;
	}


	public ArrayList<Specialty> getSpecialties() {
		// TODO Auto-generated method stub
		return specialties;
	}




	public boolean removeNurse(int i) {
		// TODO Auto-generated method stub
		if(i < nurses.size() )
		{
			Nurse temp = nurses.get(i);
			for(int j = 0 ; j < specialties.size(); j++){
				specialties.get(j).removeNurse(temp);
			}
			nurses.remove(i);
			return true;
		}
		return false;
	}




	public int getNursesCount() {
		// TODO Auto-generated method stub
		return nurses.size();
	}
	
	
	public Nurse getNurseAtIndex(int i){
		Nurse n = null;
		
		if(nurses.size() > i){
			n = nurses.get(i);
		}
		
		return n;
	}
	
	/*public void update_nurse(int index, String name, String spec, int time_service){
		nurses.get(index).update(name, spec, time_service);
	}*/




	public boolean nurseExists(String name) {
		// TODO Auto-generated method stub
		
		for( int i = 0 ; i < nurses.size(); i++){
			if(nurses.get(i).getName().equals(name)){
				return true;
			}
		}
		return false;
	}


	public void setSpecialityNurse(String name, String specialty) {
		// TODO Auto-generated method stub
		//System.out.println("is this ever called?");
		if(specialty == null){
			specialty = "";
		}
		Specialty spec = null;
		int index = 0;
		for (int i = 0; i < specialties.size(); i++) {
			if(specialties.get(i).getName().equals(specialty))
				{
					spec = specialties.get(i);
					index=i;
					break;
				}
		}
		for(int i = 0 ; i < nurses.size(); i++){
			if(Global.bddebug)
				System.out.println(nurses.get(i).getName() + ":" + name);
			if(nurses.get(i).getName().equals(name)){
				nurses.get(i).setSpec(spec);
				if(spec.equals("")){
					specialties.get(index).removeNurse(nurses.get(i));
				}else{
					specialties.get(index).addNurses(nurses.get(i));
					if(Global.bddebug)
						System.out.println("adicionou especialidade");
				}
				
			}
		}
	}


	/*public int getTimeBetweenAssign() {
		return timeBetweenAssign;
	}


	public void setTimeBetweenAssign(int timeBetweenAssign) {
		this.timeBetweenAssign = timeBetweenAssign;
	}


	public boolean isNightbeforebreakday() {
		return nightbeforebreakday;
	}


	public void setNightbeforebreakday(boolean nightbeforebreakday) {
		this.nightbeforebreakday = nightbeforebreakday;
	}*/
	
	
	public Vector<Vector<Integer>> getAssignsPerSpeciality(String name){
		Vector<Vector<Integer>> result = null;
		
		
		for(int i = 0 ; i < specialties.size();i++){
			if(specialties.get(i).getName().equals(name))
			{
				result = specialties.get(i).getAssigns();
				break;
			}
		}
		return result;
	}
	
	
	public int getSpecialtyForAssign(String name, int day, int sift){
		for(int i = 0 ; i < specialties.size();i++){
			if(specialties.get(i).getName().equals(name)){
				return specialties.get(i).getAssign(day, sift);
			}
		}
		return -1;
	}
	
	
	public void setSpecialtyForAssign(String name, int day, int sift, int value){
		for(int i = 0 ; i < specialties.size();i++){
			if(specialties.get(i).getName().equals(name)){
				specialties.get(i).setAssign(day, sift, value);
				return;
			}
		}
	}


	public int getSpecConstraint(String specialty, int type) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < specialties.size(); i++){
			if(specialties.get(i).getName().equals(specialty)){
				return specialties.get(i).getConstraint(type);
			}
		}
		return -1;
	}
	
	
	public int setSpecConstraint(String specialty, int type, int value){
		for(int i = 0 ; i < specialties.size(); i++){
			if(specialties.get(i).getName().equals(specialty)){
				return specialties.get(i).setConstraint(type, value);
			}
		}
		return -1;
	}


	public ArrayList<Nurse> getNursesInAssign(String specialty, int day, int shift) {
		for(int i = 0 ; i < specialties.size(); i++){
			if(specialties.get(i).getName().equals(specialty)){
				return specialties.get(i).getAssignedNurses(day, shift);
			}
		}
		
		return null;
	}


	public void setSpecialty(int index, Specialty specialty) {
		getSpecialties().set(index, specialty);
		
	}


	
	
	
	
	
	
	
	

}
