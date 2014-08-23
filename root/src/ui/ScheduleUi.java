package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import bd.Hospital;
import bd.Nurse;
import bd.Schedule;
import bd.Specialty;

import logic.Global;
import logic.MainP;

public class ScheduleUi {
	private Vector<Vector<JLabel>> schedule;
	private static JComboBox<String> specialties;
	private JPanel panelReference;
	private JLabel lspecialty;
	private String href;
	private static boolean activated = false;
	private static boolean processing = false;
	
	public ScheduleUi(JPanel reference){
		this.panelReference = reference;
		schedule = new Vector<Vector<JLabel>>();
		init();
	}
	
	
	
	private void init(){
		
		
		
		Vector<JLabel> temp1;
		JLabel temp2;
		
		for(int j = 0 ; j < 7; j++){
			temp1 = new Vector<JLabel>();
			for(int i = 0 ; i < 3; i++){
				temp2 = new JLabel();
				temp2.setLayout(null);
				temp2.setText("-");
				temp2.setHorizontalAlignment(SwingConstants.CENTER);
				temp2.setVerticalAlignment(SwingConstants.CENTER);
				temp2.setBorder(BorderFactory.createLineBorder(Color.black));
				temp2.setBackground(Color.white);
				temp2.setOpaque(true);
				temp2.setLocation((int)((getW(1)/8)*(j+1)),(int) ((getH(1)/5)*(i+2)));
				temp2.setSize((int)(getW(1)/8),(int)(getH(1)/5));
				temp2.addMouseListener(new CellMouseListener(j, i));
				temp1.add(temp2);
			}
			schedule.add(temp1);
		}
		
		
		for(int j = 0 ; j < 7; j++){
			for(int i = 0 ; i < 3; i++){
				
				panelReference.add(schedule.get(j).get(i));
			}
		}
		
		String[] t = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		
		for(int i = 0 ; i < 7; i++){
			temp2 = new JLabel();
			temp2.setLayout(null);
			temp2.setBackground(Color.white);
			temp2.setOpaque(true);
			temp2.setHorizontalAlignment(SwingConstants.CENTER);
			temp2.setVerticalAlignment(SwingConstants.CENTER);
			temp2.setBorder(BorderFactory.createLineBorder(Color.black));
			temp2.setLocation((int)((getW(1)/8)*(i+1)),(int) (getH(1)/5));
			temp2.setSize((int)(getW(1)/8),(int)(getH(1)/5));
			temp2.setText(t[i]);
			panelReference.add(temp2);
		}
		
		
		String[] t1 = { "Morning", "Late", "Night"};
		
		
		for(int i = 0 ; i < 3; i++) {
			temp2 = new JLabel();
			temp2.setLayout(null);
			temp2.setBackground(Color.white);
			temp2.setOpaque(true);
			temp2.setHorizontalAlignment(SwingConstants.CENTER);
			temp2.setVerticalAlignment(SwingConstants.CENTER);
			temp2.setBorder(BorderFactory.createLineBorder(Color.black));
			temp2.setLocation(0, (int)((getH(1)/5)*(i+2)));
			temp2.setSize((int)(getW(1)/8),(int)(getH(1)/5));
			temp2.setText(t1[i]);
			panelReference.add(temp2);
		}
		
		
		
		temp2 = new JLabel();
		temp2.setLayout(null);
		temp2.setBackground(Color.black);
		temp2.setOpaque(true);
		temp2.setBorder(BorderFactory.createLineBorder(Color.black));
		temp2.setLocation(0, (int)(getH(1)/5));
		temp2.setSize((int)(getW(1)/8),(int)(getH(1)/5));
		panelReference.add(temp2);
		
		
		specialties = new JComboBox<String>();
		specialties.setLocation(0,0);
		specialties.setSize((int)getW(1),(int)((getH(1)/5)/2));
		specialties.setEnabled(false);
		specialties.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String temp = (String)specialties.getSelectedItem();
				if(temp != ""){
					lspecialty.setText(temp);
					activated = true;
					ArrayList<Specialty> temp1 = MainP.getHospital(href).getSpecialties();
					if(Global.logicdebug)
						System.out.println("name: " + href);
					for(int i = 0 ; i < MainP.getHospital(href).getSpecialties().size(); i++){
						Schedule s = MainP.getHospital(href).getSpecialties().get(i).getSchedule();
						for(int j = 0 ; j < 7; j++){
							for(int q = 0 ; q < 3 ; q++){
								if(s.getAssigment(j, q).size() != 0){
									if(Global.logicdebug)
										System.out.println("assign : " + s.getAssigment(j, q).size() );
								}
							}
						}
					}
					for(int i = 0 ; i < temp1.size(); i++){
						if(temp1.get(i).getName().equals(temp)){
							loadSchedule(temp1.get(i));
							break;
						}
					}
					
				}else
				{
					reset();
				}
				
				
			}
			

			
			
		});
		specialties.setVisible(true);
		panelReference.add(specialties);
		
		lspecialty = new JLabel();
		lspecialty.setLayout(null);
		lspecialty.setText("There is no specialty selected");
		lspecialty.setLocation(0,(int) (getH(1)/5/2));
		lspecialty.setSize((int)getW(1),(int) (getH(1)/5/2));
		lspecialty.setOpaque(true);
		lspecialty.setBackground(Color.white);
		lspecialty.setBorder(BorderFactory.createLineBorder(Color.black));
		lspecialty.setHorizontalAlignment(SwingConstants.CENTER);
		lspecialty.setVerticalAlignment(SwingConstants.CENTER);
		
		
		panelReference.add(lspecialty);
		
	}
	
	
	//Global.getW(0.98), Global.getH(0.80)
	
	private double getW(double p){
		return (int)((Global.getW(p)*Global.getW(0.98))/Global.getW(p));
	}
	
	private double getH(double p){
		return (int)((Global.getH(p)*Global.getH(0.80))/Global.getH(p));
	}
	
	
	private void loadSchedule(Specialty spec){
		Schedule temp = spec.getSchedule();
		ArrayList<Nurse> temp1;
		for(int i = 0 ; i < 7 ; i++){
			for(int j = 0 ; j < 3; j++){
				temp1 = temp.getAssigment(i, j);
				
				if(temp1 != null){
					schedule.get(i).get(j).setText(Integer.toString(temp1.size()));
				}else{
					schedule.get(i).get(j).setText("-");
				}
				
			}
		}
	}



	public void reset() {
		// TODO Auto-generated method stub
		
		String temp = (String)specialties.getSelectedItem();
		if( temp != "" && processing){
			ArrayList<Specialty> temp1 = MainP.getH().getSpecialties();
			for(int i = 0 ; i < temp1.size(); i++){
				if(temp1.get(i).getName().equals(temp)){
					loadSchedule(temp1.get(i));
					break;
				}
			}
			
		}else{
			for(int j = 0 ; j < 7; j++){
				for(int i = 0 ; i < 3; i++){
					schedule.get(j).get(i).setText("-");
				}
			}
			
			lspecialty.setText("There is no specialty selected");
			specialties.removeAllItems();
			specialties.setEnabled(false);
			specialties.validate();
			activated = false;	
		}

		
	}



	public void setBd(String temp) {
		// TODO Auto-generated method stub
		//href = null;
		//href = temp;
		href = temp;
		reset();
		ArrayList<Specialty> temp1 = MainP.getHospital(href).getSpecialties();
		specialties.addItem("");
		for(int i = 0 ; i < temp1.size(); i++){
			specialties.addItem(temp1.get(i).getName());
		}
		specialties.validate();
		specialties.setEnabled(true);
		
			
	}



	/*public Hospital getH() {
		// TODO Auto-generated method stub
		return href;
	}*/



	public void start() {
		// TODO Auto-generated method stub
		//href.clearShedules();
		specialties.setEnabled(false);
		processing = true;
	}



	public void stop() {
		// TODO Auto-generated method stub
		reset();
		specialties.setEnabled(true);
		processing = false;
	}
	
	public static boolean isActivated(){
		return activated;
	}
	
	public static boolean isProcessing(){
		return processing;
	}
	
	public static String getSelectedSpecialty(){
		return (String)specialties.getSelectedItem();
	}



	public String getHospitalName() {
		// TODO Auto-generated method stub
		return href;
	}
}
