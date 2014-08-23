package logic;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JProgressBar;

import tabusearch.MySolution;
import ui.CellBd;
import ui.Ui;

import bd.Hospital;
import bd.Nurse;
import bd.Schedule;
import bd.Specialty;

import logic.Sgen;

public class MainP {
	
	private static Hospital h = null;
	private static FileManager fm;
	private static Vector<CellBd> cells;
	private static String oldname;
	private static boolean processingSchedule = false;
	private static MainProcessor processor;
	
	
	public MainP(){
		fm = new FileManager();
		cells = new Vector<CellBd>();
		Global.solutions = new MySolution[Global.popsize];
	}
	
	
	
	
	public static boolean start_new_hospital(){
		if(getH() != null)
		{
			fm.saveBD(getH());
			setH(null);
		}
		
		setH(new Hospital());
		oldname = "";
		
		/*if(!fm.saveBD(h))
		{
			return false;
		}*/
		
		
		cells = new Vector<CellBd>();
		CellBd c = new CellBd(Global.TYPE_HOSP,0, getH());
		cells.add(c);
		Ui.create_new_bd(c);
		return true;
		
		
		
	}
	
	
	/*public static Void generate_schedule(){
		
		Sgen get_shedule  = new Sgen(getH());
		return null;
		
	}*/
	
	
	
	
	public static boolean add_nurse(){
		
		Nurse n = new Nurse();
		if(getH().addNurse(n))
		{
			CellBd c = new CellBd(Global.TYPE_CELL,cells.size()-1, getH());
			cells.add(cells.size()-1, c);
			Ui.add_nurse(c);
			update_indexs();
			return true;
		}
		return false;
		
		
	}
	
	
	private static void update_indexs(){
		for( int i = 0 ; i < cells.size(); i++)
		{
			cells.elementAt(i).update_index(i);
		}
	}
	
	public static boolean saveBD(){
		/*System.out.println();
		System.out.println("count: " + h.getNursesCount());
		System.out.println("a salvar: " + h.getFileName());
		for(int i = 0 ; i < h.getSpecialties().size(); i++){
			
			for(int j = 0 ; j < 7; j++){
				for(int q = 0 ; q < 3 ; q++){
					if(h.getSpecialties().get(i).getSchedule().getAssigment(j, q).size() != 0){
						System.out.println("assign : " + h.getSpecialties().get(i).getSchedule().getAssigment(j, q).size() );
					}
				}
			}
		}*/
		getH().save();
		
		if(fm.saveBD(getH()))
		{
			if(!getH().getName().equals(oldname))
			{
				fm.deleteBD(oldname);
			}
			
			return true;
		}
		
		return false;
		
	}




	public static int getCount() {
		// TODO Auto-generated method stub
		return cells.size();
	}




	public static void deleteNurse(int index) {
		// TODO Auto-generated method stub
		if(Global.logicdebug)
			System.out.println(index);
		if(getH().removeNurse(index))
		{
			
			//cells.get(index).get
			cells.remove(index+1);
			update_indexs();
			saveBD();
			Ui.refresh_bd();
		}else
		{
			if(Global.logicdebug)
				System.out.println("falhou remoçao");
		}
		
		/**
		 * por implementar 
		 */
	}




	public static void add_specialty(String text) {
		// TODO Auto-generated method stub
		Specialty s = new Specialty(text);
		getH().addSpecialty(s);
	}




	public static String[] getSpecialties() {
		// TODO Auto-generated method stub
		ArrayList<Specialty> s = getH().getSpecialties();
		String[] spec = new String[s.size()];
		//spec[0] = "";
		for(int i = 0; i < s.size(); i++){
			spec[i] = s.get(i).getName();
		}
		
		return spec;
	}


   

	public static boolean removeSpecialty(String name) {
		// TODO Auto-generated method stub
		return getH().removeSpecialty(name);
	}




	public static String setName(String text) {
		// TODO Auto-generated method stub
		getH().setName(text);
		
		if(saveBD()){
			if(Global.logicdebug)
				System.out.println("type: " + cells.get(0).getType());
			if(cells.size() == 1){
				
				CellBd c = new CellBd(Global.TYPE_ADD, 1, null);
				cells.add(c);
				if(Global.logicdebug)
					System.out.println("adicionado");
				Ui.addCell(c);
			}
			
			
			oldname = getH().getName();
			return oldname;
		}
		
		if(cells.size() > 1)
		{
			getH().setName(oldname);
		}

		return oldname;
	}




	public static void delete() {
		// TODO Auto-generated method stub
		fm.deleteBD(getH());
		setH(null);
		
	}




	public static String[] getBds() {
		// TODO Auto-generated method stub
		return fm.getBds();
	}




	public static Vector<CellBd> getCells() {
		// TODO Auto-generated method stub
		return cells;
	}




	public static boolean changeBD(String name) {
		// TODO Auto-generated method stub
		if(Global.logicdebug)
			System.out.println("carregar");
		if(name == ""){
			setH(null);
			return true;
		}
		Hospital htemp = fm.getBD(name);
		
		CellBd c;
		if(htemp != null){
			setH(htemp) ;
			oldname = getH().getName();
			getH().load();
			cells = new Vector<CellBd>();
			c = new CellBd(Global.TYPE_HOSP,0, getH());
			cells.add(c);
			Nurse n = null;
			for( int i = 0 ; i < getH().getNursesCount(); i++){
				n= getH().getNurseAtIndex(i);
				cells.add(new CellBd(Global.TYPE_CELL,i+1,getH()));
			}
			
			cells.add(new CellBd(Global.TYPE_ADD,cells.size(), null));
			if(Global.logicdebug)
				System.out.println("loaded");
			//c.setName(name)
			//cells.add(;
			
			return true;
		}
		if(Global.logicdebug)
			System.out.println("error");
		return false;
	}



	/**
	 * n�o sei se ser� necess�rio apos ser tudo passado como referencia. 
	 * @param content
	 * @return
	 */
	public static boolean updateNurse(Vector<Object> content) {
		// TODO Auto-generated method stub
		/*if(!h.nurseExists(name)){
			h.update_nurse(index-1, name, spec, time_service);
			return true;
		}*/
		
		
		/// por fazer
		
		return false;
		
	}




	public static void setSpecialityNurse(String name, String spec) {
		// TODO Auto-generated method stub
		getH().setSpecialityNurse(name,spec);
		saveBD();
	}




	public static Hospital getH() {
		return h;
	}




	public static void setH(Hospital h) {
		MainP.h = h;
		
	}




	public static Hospital getHospital(String name) {
		// TODO Auto-generated method stub
		Hospital temp = fm.getBD(name);
		if(temp != null){
			temp.load();
		}
		return  temp;
	}




	public static boolean isScheduleRunning() {
		// TODO Auto-generated method stub
		return processingSchedule;
	}




	public static void processSchedule() {
		// TODO Auto-generated method stub
		//MainP.h = MainP.getHospital(h);
		processingSchedule = true;
		processor = new MainProcessor(h);
		processor.start();
		// executa todos algoritmos necessários para execução e actualizar estado do processo no status bar
	}




	public static void stopProcessSchedule() {
		// TODO Auto-generated method stub
		// pára o processo lógico de geracao de schedule
		processingSchedule = false;
		processor.forceStop(null, true);
	}




	public static ArrayList<Nurse> getNursesInAssign(String specialty, int day, int shift) {
		return h.getNursesInAssign(specialty,day,shift);

	}
	
	
	
	
	

}
