package bd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import tabusearch.MySolution;

import logic.Global;

public class Results {
	
	private long run_time;
	private Vector<Double> nurse_scores, specialty_scores;
	private Vector<String> nurse_names, specialty_names, names;
	private ArrayList<Specialty> specialties;
	private String name;
	private int type;
	private MySolution solution;
	public Results(String name,int type,  long run_time,ArrayList<Specialty> specialties)
	{
		this.name = name;
		this.specialties = specialties;
		this.type = type;
		this.run_time = run_time; 
		nurse_scores = new Vector<Double>();
		this.printResults();
	}
	
	
	public void addSpecialtyScore(String name, double score){
		specialty_names.add(name);
		specialty_scores.add(score);
	}
	
	
	/**
	 * imprime resultado para um ficheiro local
	 */
	public void printResults(){
		File file = new File("results/");
		if(!file.exists() || !file.isDirectory())
		{
			file.mkdir();
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date d = new Date();
		file = new File("results/","result_"  + name + "_" + dateFormat.format(d) + ".txt");
		if(Global.bddebug)
			System.out.println("path: " + file.getAbsolutePath());
		try {
			file.createNewFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter p = null;
		try {
			p = new PrintWriter(file);
			String temp1 = "Switch";
			if(type == Global.ID_ALGORITHM_TABU_SEARCH){
				temp1 = "Tabu Search";
			}else if(type == Global.ID_ALGORITHM_MEMETIC){
				temp1 = "Memetic ALgorithm";
			}
			NumberFormat formatter = new DecimalFormat("#0.00000");
			
			p.println("###RESULTS FOR " + temp1 + " for database " + name);
			p.println("##################");
			p.println("###TIME OF EXECUTION : " + formatter.format(run_time / 1000d));
			p.println("##################");
			for(int i = 1 ; i < specialties.size(); i++){
				solution = new MySolution(specialties.get(i));
				solution.setObjectiveValue();
				p.println(solution.toString());
			}
			p.print("######Generation occured in " + dateFormat.format(d) + "######");
			p.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void addNurseScore(String name, double score)
	{
		nurse_scores.add(score);
		nurse_names.add(name);
	}


	public double getRun_time() {
		return run_time;
	}


	public void setRun_time(long run_time) {
		this.run_time = run_time;
	}


	
	
	
}
