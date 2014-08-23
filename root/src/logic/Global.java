package logic;


import javax.swing.JFrame;

import tabusearch.MySolution;

public class Global {
	
	public final static int TYPE_ADD = 0,
							TYPE_CELL = 1,
							TYPE_HOSP = 2;
	
	
	public static int screen_W, screen_H;

	public static boolean bddebug=false;
	public static boolean gendebug=false;
	public static boolean logicdebug=false;
	public static boolean tabusearchdebug=false;
	public static boolean uidebug=false;
	
	public static void setDebugMode(boolean set)
	{
		if(set==true)
		{
			bddebug=true;
			gendebug=true;
			logicdebug=true;
			tabusearchdebug=true;
			uidebug=true;
		}
		else
		{
			bddebug=false;
			gendebug=false;
			logicdebug=false;
			tabusearchdebug=false;
			uidebug=false;
		}
	}
	
	public static int scroll_w_size = -1;
	
	public final static String[] specialities = { "Spec1",
									              "Spec2" };
	
	public static int popsize = 10;//define a vizinhan�a
	
	public static int tsiterations = 100;//nr de itera�oes do tabu search
	
	public static int geniterations = 10;//nr de itera��es do alg. genetico
	
	public static int MemNumber = 4;//nr de membros selecionados para cross over no alg. genetico
	
	public static int baseSolIterations =  100;
	
	public static MySolution[] solutions = new MySolution[popsize];
	
    /* GA parameters */
    public static double uniformRate = 0.5;
    public static double mutationRate = 0.015;
    public static boolean elitism = true;
	
	
	public final static int ID_ALGORITHM_TABU_SEARCH = 0,
							ID_ALGORITHM_SWITCH = 1,
							ID_ALGORITHM_MEMETIC = 2;


	public static JFrame frame;
	
	
	
	
	public  final static int WEEK_MONDAY = 0,
							 WEEK_TUESDAY = 1,
							 WEEK_WEDNESDAY = 2,
							 WEEK_THURSDAY = 3,
							 WEEK_FRIDAY = 4,
							 WEEK_SATURDAY = 5,
							 WEEK_SUNDAY = 6;
	
	
	public final static int SHIFT_MORNING = 0,
							SHIFT_DAY = 1,
							SHIFT_NIGHT = 2;
	
	
	/**
	 * Soft Constraints
	 * Type index:
	 * 0 - Max Assigns (Specialty)
	 * 1 - All day break start with assigned shift at night;
	 * 2 - Time service (Nurse)
	 * 3 - Max Assigns (Nurse)
	 * 4 - Min Consecutive Days (Nurse)
	 * 5 - Max Consecutive Days (Nurse)
	 * - - Min FREE Consecutive Days (Nurse)
	 * - - Max FREE Consecutive Days (Nurse)  
	 * 6 - Max Assigns per Day (Nurse)
	 * 7 - Number of Break Days (Nurse)
	 */
	public final static int CONSTRAINT_SPECIALTY_MAX_ASSIGNS = 0,
							CONSTRAINT_DAY_BREAK_AFTER_SHIFT_NIGHT = 1,
							CONSTRAINT_TIME_SERVICE = 2,
							CONSTRAINT_MAX_ASSIGNS = 3,
							CONSTRAINT_MIN_CONSECUTIVE_DAYS = 4,
							CONSTRAINT_MAX_CONSECUTIVE_DAYS = 5,
							CONSTRAINT_MAX_ASSIGNS_DAY = 6,
							CONSTRAINT_BREAK_DAYS = 7;
	
	
	public static int getW(double percent){
		return (int)(Global.screen_W*percent);
	}
	
	public static int getH(double percent){
		return (int)(Global.screen_H*percent);
	}

}
