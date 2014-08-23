package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.Global;
import logic.MainP;


public class CellMouseListener extends MouseAdapter {
	 private int day,
	 			 shift;

	 
	 	public CellMouseListener(int day, int shift){
	 		this.day = day;
	 		this.shift = shift;
	 	}
	   

	    @Override
	    public void mouseClicked(MouseEvent e) {
	    	if(Global.logicdebug)
	    		System.out.println(ScheduleUi.isActivated() + " " +  !ScheduleUi.isProcessing());
	    	if(ScheduleUi.isActivated() && !ScheduleUi.isProcessing()){
	    		if(Global.logicdebug)
	    			System.out.println("entrou aqui com " + day + " " + shift);
	    		new AssignUi(Global.frame,MainP.getNursesInAssign(ScheduleUi.getSelectedSpecialty(),day,shift));
			}
	    	
	    }

	   
}
