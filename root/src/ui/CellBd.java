package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bd.Hospital;
import bd.Nurse;

import logic.Global;
import logic.MainP;
import logic.Sgen;

public class CellBd extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -371794368531139589L;
	private JButton badd, bsave, bdelete, bremove, bedit;
	private JLabel lname, lspecialty, ltime_service, lhospital;
	private int index = -1, type = -1;
	private JTextField tfname, tftime_service, tfhospital, tfspecialty;
	private JComboBox cspecialty;
	private PreferencesUi pui;
	private Hospital href;
	private Nurse nref;
	String temp;
	
	
	public CellBd(int type, int index, Hospital href){
		this.index = index;
		this.type = type;
		this.href = href;
		//setOpaque(true);
		//setLocation(0,0);
		//this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		//setLayo);
		//setSize(Global.getW(0.98),100);
		//this.setMaximumSize(new Dimension(Global.getW(0.98),Global.getH(0.20)));
		//this.setLayout(null);
		//this.setPreferredSize(new Dimension(Global.getW(0.98),Global.getH(0.20)));
		this.setLayout(null);
		
		//this.setMinimumSize(new Dimension(Global.getW(0.98),Global.getH(0.20)));
		//this.setMaximumSize(new Dimension(Global.getW(0.98),Global.getH(0.20)));
		
		//this.setSize(new Dimension(Global.getW(0.98),Global.getH(0.20)));
		/**
		 * slbd.setLocation(getW(0.01), getH(0.06));
		slbd.setSize(getW(0.98), getH(0.83));
		 */
		this.setPreferredSize(new Dimension(Global.getW(0.98),Global.getH(0.15)));
		
		if(type == Global.TYPE_ADD){
			init_add();
			
		}else if(type == Global.TYPE_CELL){
			
			this.nref = href.getNurseAtIndex(index-1);
			if(Global.logicdebug)
				System.out.println(Global.frame +  nref.getName()  + nref.getSpec());
			init_nurse(Global.frame, nref.getName(),nref.getSpec().getName());
		}else{
			
			init_hosp(Global.frame, href.getName());
			//this.href = (Hospital)ref;
		}
		
		
		
	}
	

	
/*
	

	public CellBd(int index,Hospital href){
		this.index = index;
		this.href = href;
		this.type = Global.TYPE_CELL;
		this.nref = nurseref;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Global.getW(0.98),Global.getH(0.20)));
		
		init_nurse(Global.frame, nref.getName(),nref.getSpec().getName(),Integer.toString(nref.getTime_service()));
		
		
		//init_nurse(Global.frame, name,idspec,Integer.toString(time_service));
		
	}
	
	public CellBd(int index, Hospital hospref){
		this.index = index;
		this.type = Global.TYPE_HOSP;
		this.href = hospref;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Global.getW(0.98),Global.getH(0.20)));
		
		init_hosp(href.getName());
		
		//init_hosp(name);
		
	}
	*/
	
	public void update_index(int i){
		this.index = i ;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public int getType(){
		return this.type;
	}


	private void init_add(){
		this.setBackground(Color.DARK_GRAY);
		badd = new JButton("add");
		badd.setLayout(null);
		badd.setLocation(0,0);
		badd.setSize(Global.getW(0.98),Global.getH(0.15));
		badd.setVisible(true);
		badd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MainP.add_nurse();
			}
			
		});
		add(badd);
	}
	
	
	private void init_nurse(JFrame frame, String name,String idspec){
		
		pui = new PreferencesUi(frame, Global.TYPE_CELL, href, index);
		
		
		
		this.setBackground(Color.pink);
		lname = new JLabel("name: ");
		lname.setLayout(null);
		lname.setLocation(0,0);
		lname.setSize(Global.getW(0.49),Global.getH(0.05));
		lname.setVisible(true);
		add(lname);
		tfname = new JTextField(name);
		tfname.setLayout(null);
		tfname.setLocation(Global.getW(0.49), 0);
		tfname.setSize(Global.getW(0.49)-Global.scroll_w_size,Global.getH(0.05));
		tfname.setVisible(true);
		tfname.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				update();
			}
			
			private void update(){
				if(!href.nurseExists(tfname.getText()))
				{
					nref.setName(tfname.getText());
					if(Global.logicdebug)
						System.out.println("nome: " + href.getNurseAtIndex(index-1).getName());
					MainP.saveBD();
				}else
				{
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							tfname.setText(nref.getName());
						}
						
					});
					
				}
				
			}
			
		});
		
		add(tfname);
		
		
		bedit = new JButton("Edit Preferences");
		bedit.setLayout(null);
		bedit.setLocation(0, Global.getH(0.05));
		bedit.setSize(Global.getW(0.98)-Global.scroll_w_size, Global.getH(0.05));
		bedit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				pui.setVisible(true);
			}
			
		});
		bedit.setVisible(true);
		add(bedit);
		
		/*lspecialty = new JLabel("Speciality: ");
		lspecialty.setLayout(null);
		lspecialty.setLocation(0,Global.getH(0.05));
		lspecialty.setSize(Global.getW(0.49),Global.getH(0.05));
		lspecialty.setVisible(true);
		add(lspecialty);
		System.out.println(Global.scroll_w_size);
		String[] specs = MainP.getSpecialties();
		cspecialty = new JComboBox(specs);
		
		for(int i = 0 ; i < specs.length;i++){
			System.out.println("spec: " + idspec);
			if(specs[i].equals(idspec))
			{
				cspecialty.setSelectedIndex(i);
				break;
			}
		}
		
		
		cspecialty.setLocation(Global.getW(0.49), Global.getH(0.05));
		cspecialty.setSize(Global.getW(0.49)-Global.scroll_w_size, Global.getH(0.05));
		cspecialty.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainP.setSpecialityNurse(tfname.getText(),(String)cspecialty.getSelectedItem());
			}
			
		});
		cspecialty.setVisible(true);
		add(cspecialty);
		
		ltime_service = new JLabel("Time in service: ");
		ltime_service.setLabelFor(null);
		ltime_service.setLocation(0,Global.getH(0.1));
		ltime_service.setSize(Global.getW(0.49), Global.getH(0.05));
		ltime_service.setVisible(true);
		add(ltime_service);
		
		
		tftime_service = new JTextField(time_service);
		tftime_service.setLayout(null);
		tftime_service.setLocation(Global.getW(0.49),Global.getH(0.1));
		tftime_service.setSize(Global.getW(0.49) - Global.scroll_w_size, Global.getH(0.05));
		tftime_service.setVisible(true);
		add(tftime_service);*/
		
		/*bsave = new JButton("Save");
		bsave.setLayout(null);
		bsave.setLocation(0,Global.getH(0.15));
		bsave.setSize(Global.getW(0.49), Global.getH(0.05));
		bsave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				try{
					//String s = tftime_service.getText().replace(" ", "");
					int i;
					/*if(s == "" || s == null){	
						i = 0;
					}else
					{
						i = Integer.parseInt(tftime_service.getText());
					}*/
					/*
					Vector<Object> content = new Vector<Object>();
					
					content.add(index);
					content.add(tfname.getText());
					content.add((String)cspecialty.getSelectedItem());
					pui.getContent(content);
					if(!MainP.updateNurse(content)){
						
					}else{
						MainP.saveBD();
					}
					
				}catch(NumberFormatException e){
					return;
				}
				
				
			}
			
		});
		
		bsave.setVisible(false);   //////
		add(bsave);*/
		
		bdelete = new JButton("Delete Nurse");
		bdelete.setLayout(null);
		bdelete.setLocation(0, Global.getH(0.1));
		bdelete.setSize(Global.getW(0.98)-Global.scroll_w_size, Global.getH(0.05));
		bdelete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainP.deleteNurse(index-1);
				//Sgen s = new Sgen(MainP.getH());
				//s.print_Schedule(MainP.getH().getSpecialties().get(0));
				//System.out.println(MainP.getH().getName()+"%%");
			}
			
		});
		bdelete.setVisible(true);   /////
		add(bdelete);
		
		
	}
	
	

	private void init_hosp(JFrame frame, String name) {
		// TODO Auto-generated method stub
		
		pui = new PreferencesUi(frame, Global.TYPE_HOSP, href, index);
		lhospital = new JLabel("name of the hospital: ");
		lhospital.setLayout(null);
		lhospital.setLocation(0,0);
		lhospital.setSize(Global.getW(0.49),Global.getH(0.05));
		lhospital.setVisible(true);
		add(lhospital);
		
		
		tfhospital = new JTextField(name);
		tfhospital.setLayout(null);
		tfhospital.setLocation(Global.getW(0.49),0);
		tfhospital.setSize(Global.getW(0.49)-Global.scroll_w_size, Global.getH(0.05));
		tfhospital.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				update();
			}
			
			
			private void update(){
				
				// TODO Auto-generated method stub
				if(tfhospital.getText().length() > 0){
					
					temp  = MainP.setName(tfhospital.getText());
					
					if(!temp.equals(tfhospital.getText()))
					{
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								tfhospital.setText(temp);
							}
						
						});
					
					
						Ui.refresh_bd_view(tfhospital.getText());
					}
					
					
					
			
				}

			}
			
		});
		
		
		tfhospital.setVisible(true);
		add(tfhospital);
		
		
		
		lspecialty = new JLabel("Specialties: ");
		lspecialty.setLayout(null);
		lspecialty.setLocation(0, Global.getH(0.05));
		lspecialty.setSize(Global.getW(0.20),Global.getH(0.05));
		lspecialty.setVisible(true);
		add(lspecialty);
		
		
		
		cspecialty = new JComboBox(MainP.getSpecialties());
		cspecialty.setLocation(Global.getW(0.20),Global.getH(0.05));
		cspecialty.setSize(Global.getW(0.4), Global.getH(0.05));
		cspecialty.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				int i = cspecialty.getSelectedIndex();
				if( i == 0)
				{
					bremove.setEnabled(false);
				}else
				{
					bremove.setEnabled(true);
				}
			}
			
		});
		cspecialty.setVisible(true);
		add(cspecialty);
		
		bedit = new JButton("Edit Preferences");
		bedit.setLayout(null);
		bedit.setLocation(0, Global.getH(0.1));
		bedit.setSize(Global.getW(0.49), Global.getH(0.05));
		bedit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				pui.setVisible(true);
			}
			
		});
		bedit.setVisible(true);
		add(bedit);
		
		
		bremove = new JButton("Remove Specialty");
		bremove.setLayout(null);
		bremove.setLocation(Global.getW(0.6),Global.getH(0.05));
		bremove.setSize(Global.getW(0.1), Global.getH(0.05));
		bremove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String i = (String)cspecialty.getSelectedItem();
				if(MainP.removeSpecialty(i))
				{
					//System.out.println(cspecialty.getItemCount() + " " + i);
					cspecialty.removeItemAt(cspecialty.getSelectedIndex());
					update_specialties();
					bremove.setEnabled(false);
					cspecialty.setSelectedIndex(0);
					Ui.update_specialties();
					
				}
			}
			
		});
		bremove.setEnabled(false);
		bremove.setVisible(true);
		add(bremove);
		
		
		badd = new JButton("Add Specialty: ");
		badd.setLayout(null);
		badd.setLocation(Global.getW(0.7),Global.getH(0.05));
		badd.setSize(Global.getW(0.1), Global.getH(0.05));
		badd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				tfspecialty.setText(tfspecialty.getText().replace(" " , ""));
				if(tfspecialty.getText().length() > 0)
				{
					MainP.add_specialty(tfspecialty.getText());
					tfspecialty.setText("");
					update_specialties();
					Ui.update_specialties();
				}
				
			}

			
			
		});
		badd.setVisible(true);
		add(badd);
		
		
		
		tfspecialty = new JTextField();
		tfspecialty.setLayout(null);
		tfspecialty.setLocation(Global.getW(0.8), Global.getH(0.05));
		tfspecialty.setSize(Global.getW(0.19)-Global.scroll_w_size, Global.getH(0.05));
		tfspecialty.setVisible(true);
		add(tfspecialty); 
		
		
		/*bsave = new JButton("Save hospital");
		bsave.setLayout(null);
		bsave.setLocation(0, Global.getH(0.15));
		bsave.setSize(Global.getW(0.49), Global.getH(0.05));
		bsave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/*if(tfhospital.getText().length() > 0){
					if(MainP.setName(tfhospital.getText())){
						Ui.refresh_bd_view(tfhospital.getText());
					}
					
					//System.out.println("feito");
				}*//*
			}
			
		});
		bsave.setVisible(false);  /////
		add(bsave);*/
		
		bdelete = new JButton("Delete hospital");
		bdelete.setLayout(null);
		bdelete.setLocation(Global.getW(0.49), Global.getH(0.1));
		bdelete.setSize(Global.getW(0.49)-Global.scroll_w_size, Global.getH(0.05));
		bdelete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainP.delete();
				Ui.deleteH();
				Ui.refresh_bd_view(null);
				//Ui.update_listbds("");
			}
			
		});
		bdelete.setVisible(true);  
		add(bdelete);
	
		
	}
	
	public void update_specialties() {
		// TODO Auto-generated method stub
		
		pui.update_specialties();
		
		if(type == Global.TYPE_HOSP){
			String[] spec = MainP.getSpecialties();
			String specn = (String) cspecialty.getSelectedItem();
			
			cspecialty.removeAllItems();
			
			for( int i = 0 ; i < spec.length; i++){
				cspecialty.addItem(spec[i]);
			}
			
			for(int i = 0 ; i < cspecialty.getItemCount() ; i++){
				if(((String)cspecialty.getItemAt(i)).equals(specn)){
					cspecialty.setSelectedIndex(i);
					break;
				}
			}
			
			cspecialty.validate();
		}
		
	}

}
