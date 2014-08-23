package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bd.Hospital;
import bd.Nurse;
import bd.Specialty;

import logic.Global;
import logic.MainP;

public class PreferencesUi extends JDialog {
	
	
	private JPanel mainp;
	private JScrollPane scrollp;
	private GridLayout scrollLayout;
	private JLabel ltimeBetweenAssign, 
				   ltime_service,
				   lmaxAssings,
				   lminConsecutiveDays,
				   lmaxConsecutiveDays,
				   lminConsecutiveFreeDays,
				   lmaxConsecutiveFreeDays,
				   lmaxAssignsPerDay,
				   lspecialty,
				   lbreakDay,
				   lnightBeforeBreakDay;
	
	
	private Vector<JLabel> assignsL;
	
	private Vector<JTextField> assignsT;
	
	private JTextField tftime_service,
					   tftimeBetweenAssign,
					   tfmaxAssigns,
					   tfminConsecutiveDays,
					   tfmaxConsecutiveDays,
					   tfminConsecutiveFreeDays,
					   tfmaxConsecutiveFreeDays,
					   tfmaxAssignsPerDay,
					   tfbreakDay;
	
	
	private JComboBox<String> cspecialty;
	
	private JCheckBox cbnightBeforeBreakDay;
	
	private Nurse nref;
	private Hospital href;
	private int index,
				type,
				tempday,
				tempsift;
	private Container lmaxAssigns;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1026107271989812080L;

	public PreferencesUi(JFrame frame, int type, Hospital href,int index){
		super(frame,"", Dialog.ModalityType.DOCUMENT_MODAL);
		this.href = href;
		this.index = index;
		this.type = type;
		init();
		
		
	}
	
	
	private void init()
	{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(Global.getW(0.4), Global.getH(0.8));
		this.setLayout(null);
		this.setVisible(false);
		this.setResizable(false);
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {}

			@Override
			public void windowClosed(WindowEvent arg0) {}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				//gravar dados.
				//System.out.println("fechou janela");
				MainP.saveBD();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {}

			@Override
			public void windowDeiconified(WindowEvent arg0) {}

			@Override
			public void windowIconified(WindowEvent arg0) {}

			@Override
			public void windowOpened(WindowEvent arg0) {}
			
		});
		
		
		mainp = new JPanel();
		
		scrollLayout = new GridLayout();
		
		
		
		
		if(type == Global.TYPE_CELL)     ///////////////////////////////////////////////////////////////////////////////////nurse type////////////////////////////////////////////////////////////////////////////
		{
			scrollLayout.setRows(9);  // por analizar 
			
			
			
			nref = href.getNurseAtIndex(index-1);
			lspecialty = new JLabel("Speciality: ");
			lspecialty.setLayout(null);
			lspecialty.setLocation(0,0);
			lspecialty.setSize(Global.getW(0.2),Global.getH(0.05));
			lspecialty.setVisible(true);
			add(lspecialty);
			//System.out.println(Global.scroll_w_size);
			String[] specs = MainP.getSpecialties();
			cspecialty = new JComboBox(specs);
			String spec = nref.getSpec().getName();
			for(int i = 0 ; i < specs.length;i++){
				if(Global.logicdebug)
					System.out.println("spec:" + spec + "|" + specs[i]);
				if(specs[i].equals(spec))
				{
					cspecialty.setSelectedIndex(i);
					break;
				}
			}
			
			
			cspecialty.setLocation(Global.getW(0.2), Global.getH(0));
			cspecialty.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			cspecialty.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Specialty spec = new Specialty((String)cspecialty.getSelectedItem());
					nref.setSpec(spec);
					href.setSpecialityNurse(nref.getName(),(String)cspecialty.getSelectedItem());
					
					//MainP.setSpecialityNurse(nref.getName(),(String)cspecialty.getSelectedItem());
				}
				
			});
			cspecialty.setVisible(true);
			add(cspecialty);
			
			ltime_service = new JLabel("time service:");
			ltime_service.setLayout(null);
			ltime_service.setLocation(0, Global.getH(0.05));
			ltime_service.setSize(Global.getW(0.2), Global.getH(0.05));
			ltime_service.setVisible(true);
			
			add(ltime_service);
			
			tftime_service = new JTextField();
			tftime_service.setText(Integer.toString(nref.getConstraint(Global.CONSTRAINT_TIME_SERVICE)));
			tftime_service.setLayout(null);
			tftime_service.setLocation(Global.getW(0.2), Global.getH(0.05));
			tftime_service.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tftime_service.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tftime_service.getText();
						
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(nref.getConstraint(Global.CONSTRAINT_TIME_SERVICE));
								tftime_service.setText(i);
								
							}
						});
						return;
					}
					
					
					
					nref.setConstraint(Global.CONSTRAINT_TIME_SERVICE, Integer.parseInt(tftime_service.getText()));
					
					tftime_service.validate();

					
					
					
				}
				
			});
			tftime_service.setVisible(true);
			add(tftime_service);
			
			
			lmaxAssigns = new JLabel("Max Assigns:");
			lmaxAssigns.setLayout(null);
			lmaxAssigns.setLocation(0, Global.getH(0.1));
			lmaxAssigns.setSize(Global.getW(0.2), Global.getH(0.05));
			lmaxAssigns.setVisible(true);
			
			add(lmaxAssigns);
			
			tfmaxAssigns = new JTextField();
			tfmaxAssigns.setText(Integer.toString(nref.getConstraint(Global.CONSTRAINT_MAX_ASSIGNS)));
			tfmaxAssigns.setLayout(null);
			tfmaxAssigns.setLocation(Global.getW(0.2), Global.getH(0.1));
			tfmaxAssigns.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tfmaxAssigns.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tfmaxAssigns.getText();
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(nref.getConstraint(Global.CONSTRAINT_MAX_ASSIGNS));
								tfmaxAssigns.setText(i);
								
							}
						});
						return;
					}
					
						
					nref.setConstraint(Global.CONSTRAINT_MAX_ASSIGNS, Integer.parseInt(tfmaxAssigns.getText()));
					tfmaxAssigns.validate();
					
					
				}
				
			});
			tfmaxAssigns.setVisible(true);
			add(tfmaxAssigns);
			

			lminConsecutiveDays = new JLabel("Min Consecutive days:");
			lminConsecutiveDays.setLayout(null);
			lminConsecutiveDays.setLocation(0, Global.getH(0.15));
			lminConsecutiveDays.setSize(Global.getW(0.2), Global.getH(0.05));
			lminConsecutiveDays.setVisible(true);
			
			add(lminConsecutiveDays);
			
			tfminConsecutiveDays = new JTextField();
			tfminConsecutiveDays.setText(Integer.toString(nref.getConstraint(Global.CONSTRAINT_MIN_CONSECUTIVE_DAYS)));
			tfminConsecutiveDays.setLayout(null);
			tfminConsecutiveDays.setLocation(Global.getW(0.2), Global.getH(0.15));
			tfminConsecutiveDays.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tfminConsecutiveDays.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tfminConsecutiveDays.getText();
						
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(nref.getConstraint(Global.CONSTRAINT_MIN_CONSECUTIVE_DAYS));
								tfminConsecutiveDays.setText(i);
								
							}
						});
						return;
					}

					nref.setConstraint(Global.CONSTRAINT_MIN_CONSECUTIVE_DAYS, Integer.parseInt(tfminConsecutiveDays.getText()));
					tfminConsecutiveDays.validate();
					
					
					
				}
				
			});
			
			tfminConsecutiveDays.setVisible(true);
			add(tfminConsecutiveDays);
			

			lmaxConsecutiveDays = new JLabel("Max Consecutive days:");
			lmaxConsecutiveDays.setLayout(null);
			lmaxConsecutiveDays.setLocation(0, Global.getH(0.2));
			lmaxConsecutiveDays.setSize(Global.getW(0.2), Global.getH(0.05));
			lmaxConsecutiveDays.setVisible(true);
			
			add(lmaxConsecutiveDays);
			
			tfmaxConsecutiveDays = new JTextField();
			tfmaxConsecutiveDays.setText(Integer.toString(nref.getConstraint(Global.CONSTRAINT_MAX_CONSECUTIVE_DAYS)));
			tfmaxConsecutiveDays.setLayout(null);
			tfmaxConsecutiveDays.setLocation(Global.getW(0.2), Global.getH(0.2));
			tfmaxConsecutiveDays.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tfmaxConsecutiveDays.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tfmaxConsecutiveDays.getText();
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(nref.getConstraint(Global.CONSTRAINT_MAX_CONSECUTIVE_DAYS));
								tfmaxConsecutiveDays.setText(i);
								
							}
						});
						return;
					}

					
					nref.setConstraint(Global.CONSTRAINT_MAX_CONSECUTIVE_DAYS, Integer.parseInt(tfmaxConsecutiveDays.getText()));
					tfmaxConsecutiveDays.validate();
					
					
				}
				
			});
			
			tfmaxConsecutiveDays.setVisible(true);
			add(tfmaxConsecutiveDays);
			
		/*	lminConsecutiveFreeDays = new JLabel("Min Consecutive Free days:");
			lminConsecutiveFreeDays.setLayout(null);
			lminConsecutiveFreeDays.setLocation(0, Global.getH(0.25));
			lminConsecutiveFreeDays.setSize(Global.getW(0.2), Global.getH(0.05));
			lminConsecutiveFreeDays.setVisible(true);
			
			add(lminConsecutiveFreeDays);
			
			/*tfminConsecutiveFreeDays = new JTextField();
			tfminConsecutiveFreeDays.setText(Integer.toString(nref.getMinConsecutiveFreeDays()));
			tfminConsecutiveFreeDays.setLayout(null);
			tfminConsecutiveFreeDays.setLocation(Global.getW(0.2), Global.getH(0.25));
			tfminConsecutiveFreeDays.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tfminConsecutiveFreeDays.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tfminConsecutiveFreeDays.getText();
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(nref.getMinConsecutiveFreeDays());
								tfminConsecutiveFreeDays.setText(i);
								
							}
						});
						return;
					}

					nref.setMinConsecutiveFreeDays(i);
					
					
				}
				
			});
			
			tfminConsecutiveFreeDays.setVisible(true);
			add(tfminConsecutiveFreeDays);
			
			
			lmaxConsecutiveFreeDays = new JLabel("Max Consecutive Free days:");
			lmaxConsecutiveFreeDays.setLayout(null);
			lmaxConsecutiveFreeDays.setLocation(0, Global.getH(0.3));
			lmaxConsecutiveFreeDays.setSize(Global.getW(0.2), Global.getH(0.05));
			lmaxConsecutiveFreeDays.setVisible(true);
			
			add(lmaxConsecutiveFreeDays);
			
			tfmaxConsecutiveFreeDays = new JTextField();
			tfmaxConsecutiveFreeDays.setText(Integer.toString(nref.getMaxConsecutiveFreeDays()));
			tfmaxConsecutiveFreeDays.setLayout(null);
			tfmaxConsecutiveFreeDays.setLocation(Global.getW(0.2), Global.getH(0.3));
			tfmaxConsecutiveFreeDays.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tfmaxConsecutiveFreeDays.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tfmaxConsecutiveFreeDays.getText();
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(nref.getMaxConsecutiveFreeDays());
								tfmaxConsecutiveFreeDays.setText(i);
								
							}
						});
						return;
					}

					nref.setMaxConsecutiveFreeDays(i);
					
					
				}
				
			});
			
			tfmaxConsecutiveFreeDays.setVisible(true);
			add(tfmaxConsecutiveFreeDays);*/
			
			lmaxAssignsPerDay = new JLabel("Max Assigns per day:");
			lmaxAssignsPerDay.setLayout(null);
			lmaxAssignsPerDay.setLocation(0, Global.getH(0.25));
			lmaxAssignsPerDay.setSize(Global.getW(0.2), Global.getH(0.05));
			lmaxAssignsPerDay.setVisible(true);
			
			add(lmaxAssignsPerDay);
			
			tfmaxAssignsPerDay = new JTextField();
			tfmaxAssignsPerDay.setText(Integer.toString(nref.getConstraint(Global.CONSTRAINT_MAX_ASSIGNS_DAY)));
			tfmaxAssignsPerDay.setLayout(null);
			tfmaxAssignsPerDay.setLocation(Global.getW(0.2), Global.getH(0.25));
			tfmaxAssignsPerDay.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tfmaxAssignsPerDay.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tfmaxAssignsPerDay.getText();
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(nref.getConstraint(Global.CONSTRAINT_MAX_ASSIGNS_DAY));
								tfmaxAssignsPerDay.setText(i);
								
							}
						});
						return;
					}

				
							
					nref.setConstraint(Global.CONSTRAINT_MAX_ASSIGNS_DAY,Integer.parseInt(tfmaxAssignsPerDay.getText()));
					tfmaxAssignsPerDay.validate();
				
					//nref.setConstraint(Global.CONSTRAINT_MAX_ASSIGNS_DAY, i);
					
				}
				
			});
			
			tfmaxAssignsPerDay.setVisible(true);
			add(tfmaxAssignsPerDay);
			
			lbreakDay = new JLabel("Number of Break days:");
			lbreakDay.setLayout(null);
			lbreakDay.setLocation(0, Global.getH(0.30));
			lbreakDay.setSize(Global.getW(0.2), Global.getH(0.05));
			lbreakDay.setVisible(true);
			
			add(lbreakDay);
			
			tfbreakDay = new JTextField();
			tfbreakDay.setText(Integer.toString(nref.getConstraint(Global.CONSTRAINT_BREAK_DAYS)));
			tfbreakDay.setLayout(null);
			tfbreakDay.setLocation(Global.getW(0.2), Global.getH(0.30));
			tfbreakDay.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tfbreakDay.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tfbreakDay.getText();
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(nref.getConstraint(Global.CONSTRAINT_BREAK_DAYS));
								tfbreakDay.setText(i);
								
							}
						});
						return;
					}

					nref.setConstraint(Global.CONSTRAINT_BREAK_DAYS, Integer.parseInt(tfbreakDay.getText()));
					tfbreakDay.validate();
					
					
					
				}
				
			});
			
			tfbreakDay.setVisible(true);
			add(tfbreakDay);
			
			lnightBeforeBreakDay = new JLabel("All day break start with assigned shift at night:");
			lnightBeforeBreakDay.setLayout(null);
			lnightBeforeBreakDay.setLocation(0,Global.getH(0.35));
			lnightBeforeBreakDay.setSize(Global.getW(0.2), Global.getH(0.05));
			lnightBeforeBreakDay.setVisible(true);
			
			add(lnightBeforeBreakDay);
			
			cbnightBeforeBreakDay = new JCheckBox();
			cbnightBeforeBreakDay.setLayout(null);
			cbnightBeforeBreakDay.setLocation(Global.getW(0.2), Global.getH(0.35));
			cbnightBeforeBreakDay.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			cbnightBeforeBreakDay.setSelected(nref.getConstraint(Global.CONSTRAINT_DAY_BREAK_AFTER_SHIFT_NIGHT) == 1 ? true : false);
			cbnightBeforeBreakDay.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					nref.setConstraint(Global.CONSTRAINT_DAY_BREAK_AFTER_SHIFT_NIGHT, cbnightBeforeBreakDay.isSelected() ? 1 : 0);
					//href.setNightbeforebreakday(cbnightBeforeBreakDay.isSelected());
					
				}
				
			});
			
			cbnightBeforeBreakDay.setVisible(true);
			add(cbnightBeforeBreakDay);
			
		}else    //////////////////////////////////////////////////////////////////////////////hospital type //////////////////////////////////////////////////////////////////////////////
		{
			

			
			scrollLayout.setRows(2);  // por analizar 
			
			String[] specs = MainP.getSpecialties();
			cspecialty = new JComboBox(specs);
			
			cspecialty.setLocation(Global.getW(0), Global.getH(0));
			cspecialty.setSize(Global.getW(0.4)-Global.scroll_w_size, Global.getH(0.05));
			cspecialty.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//Specialty spec = new Specialty((String)cspecialty.getSelectedItem());
					if((String)cspecialty.getSelectedItem() == null){
						return;
					}
					Vector<Vector<Integer>> spec = href.getAssignsPerSpeciality((String)cspecialty.getSelectedItem());
					
					if(((String)cspecialty.getSelectedItem()).equals("")){
						spec = null;
						tftimeBetweenAssign.setEnabled(false);
					}else{
						tftimeBetweenAssign.setEnabled(true);
						tftimeBetweenAssign.setText(Integer.toString(href.getSpecConstraint((String)cspecialty.getSelectedItem(),Global.CONSTRAINT_SPECIALTY_MAX_ASSIGNS)));
					}
					//System.out.println("spec : " + (String)cspecialty.getSelectedItem());
					int ind = 0 ;
					for(int i = 0 ; i < 7;i++) {
						for(int j = 0 ;  j < 3 ; j++ )
						{
							ind = 3 * i + j;
							if(spec != null){
								assignsT.get(ind).setEnabled(true);
								assignsT.get(ind).setText(Integer.toString(spec.get(i).get(j)));
							}else{
								assignsT.get(ind).setEnabled(false);
								assignsT.get(ind).setText("");
							}
						}
						
						
					}
					
					
					//nref.setSpec(spec);
					// obter a especialidade requerida e guardar todas as alterações efectuadas na especialidade seleccionada 
					//MainP.setSpecialityNurse(nref.getName(),(String)cspecialty.getSelectedItem());
				}
				
			});
			cspecialty.setVisible(true);
			add(cspecialty);
			
			ltimeBetweenAssign = new JLabel("Max Assigns:");
			ltimeBetweenAssign.setLayout(null);
			ltimeBetweenAssign.setLocation(0, Global.getH(0.05));
			ltimeBetweenAssign.setSize(Global.getW(0.2), Global.getH(0.05));
			ltimeBetweenAssign.setVisible(true);
			
			add(ltimeBetweenAssign);
			
			tftimeBetweenAssign = new JTextField();
			tftimeBetweenAssign.setText(Integer.toString(href.getSpecConstraint((String)cspecialty.getSelectedItem(),Global.CONSTRAINT_SPECIALTY_MAX_ASSIGNS)));
			tftimeBetweenAssign.setLayout(null);
			tftimeBetweenAssign.setLocation(Global.getW(0.2), Global.getH(0.05));
			tftimeBetweenAssign.setEnabled(false);
			tftimeBetweenAssign.setSize(Global.getW(0.2)-Global.scroll_w_size, Global.getH(0.05));
			tftimeBetweenAssign.getDocument().addDocumentListener(new DocumentListener(){

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

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update();
				}
				
				private void update(){
					int i = 0 ;
					
					try{
						
						String s = tftimeBetweenAssign.getText();
						if(s.equals(""))
						{
							return;
						}
						
						i = Integer.parseInt(s);
						
					}catch(NumberFormatException e){
						
						SwingUtilities.invokeLater(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String i = Integer.toString(href.getSpecConstraint((String)cspecialty.getSelectedItem(),Global.CONSTRAINT_SPECIALTY_MAX_ASSIGNS));
								tftimeBetweenAssign.setText(i);
								
							}
						});
						return;
					}

					
					href.setSpecConstraint((String)cspecialty.getSelectedItem(), Global.CONSTRAINT_SPECIALTY_MAX_ASSIGNS, Integer.parseInt(tftimeBetweenAssign.getText()));
					tftimeBetweenAssign.validate();
					
					
					//href.setTimeBetweenAssign(i);
					
					
				}
				
			});
			
			tftimeBetweenAssign.setVisible(true);
			add(tftimeBetweenAssign);
			
			
			
			
			
			
			
			
			
			
			
			
			
			JTextField t1;
			
			assignsT = new Vector<JTextField>();
			for(int i = 0 ; i < 7; i++){
				for (int j = 0 ; j < 3; j++){
					t1 = new JTextField();
					t1.setLayout(null);
					t1.setSize((Global.getW(0.4)-Global.scroll_w_size)/8, Global.getH(0.05));
					t1.setText("0");
					t1.setLocation(((Global.getW(0.4)-Global.scroll_w_size)/8)*(i+1),Global.getH(0.15 + 0.05*j));
					t1.getDocument().putProperty("owner", Integer.toString(i) + Integer.toString(j));
					t1.setEnabled(false);
					assignsT.add(t1);
					assignsT.get(assignsT.size()-1).getDocument().addDocumentListener(new DocumentListener(){

						@Override
						public void insertUpdate(DocumentEvent e) {
							// TODO Auto-generated method stub
							update(e);
						}

						@Override
						public void removeUpdate(DocumentEvent e) {
							// TODO Auto-generated method stub
							update(e);
						}

						@Override
						public void changedUpdate(DocumentEvent e) {
							// TODO Auto-generated method stub
							update(e);
						}
						
						private void update(DocumentEvent doc){
							int i = 0 ;
							try{
								String index = (String)doc.getDocument().getProperty("owner");
								tempday = Integer.parseInt(Character.toString(index.charAt(0)));
								tempsift = Integer.parseInt(Character.toString(index.charAt(1)));
								int ind = 3*tempday + tempsift;
								String s = assignsT.get(ind).getText();
								if(s.equals(""))
								{
									return;
								}
								
								i = Integer.parseInt(s);
								
							}catch(NumberFormatException e){
								
								SwingUtilities.invokeLater(new Runnable(){

									@Override
									public void run() {
										// TODO Auto-generated method stub
										
										String i = Integer.toString(href.getSpecialtyForAssign((String)cspecialty.getSelectedItem(), tempday, tempsift));
										assignsT.get(3*tempday + tempsift).setText(i);
										//String i = Integer.toString(href.getTimeBetweenAssign());
										//tftimeBetweenAssign.setText(i);
										
									}
								});
								return;
							}
							href.setSpecialtyForAssign((String)cspecialty.getSelectedItem(), tempday, tempsift, i);
							//href.setTimeBetweenAssign(i);
							
							
						}
						
					});
				}
			}
			
			for(int i = 0 ; i < assignsT.size(); i++){
				add(assignsT.get(i));
			}
			
			
			
			assignsL = new Vector<JLabel>();
			JLabel l1;
			for(int i = 0 ; i < 7; i++){
				switch (i)
				{
					case 0: l1 = new JLabel("Mon"); break;
					case 1: l1 = new JLabel("Tue"); break;
					case 2: l1 = new JLabel("Wed"); break;
					case 3: l1 = new JLabel("Thu"); break;
					case 4: l1 = new JLabel("Fri"); break;
					case 5: l1 = new JLabel("Sat"); break;
					case 6: l1 = new JLabel("Sun"); break;
					default: l1 = new JLabel("Error");
				}
				l1.setLayout(null);
				l1.setSize((Global.getW(0.4)-Global.scroll_w_size)/8, Global.getH(0.05));
				l1.setLocation(((Global.getW(0.4)-Global.scroll_w_size)/8)*(i+1), Global.getH(0.10));
				assignsL.add(l1);
			}
			
			
			
			String[] stemp = { "Day", "Morning", "Late", "Night" };
			
			
			for(int i = 0 ; i < stemp.length; i++){
				l1 = new JLabel(stemp[i]);
				l1.setLayout(null);
				l1.setSize((Global.getW(0.4)-Global.scroll_w_size)/8, Global.getH(0.05));
				l1.setLocation(0, Global.getH(0.10 + i*0.05));
				assignsL.add(l1);
			}

			for(int i = 0 ; i < assignsL.size();i++){
				add(assignsL.get(i));
			}
			
		}
		
		
		//ltemp.setColumns(2);
		
		scrollLayout.setVgap(1);
		mainp.setLayout(scrollLayout);
	
		mainp.setVisible(true);
		
		mainp.setBackground(Color.yellow);
		
		
		
		//lbdp.add(new CellBd());
		//lbdp.add(new CellBd());
		//layoutbdp.setRows(2);
		//lbdp.add(new CellBd());
		//lbdp.add(new CellBd());
		scrollp = new JScrollPane(mainp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollp.getVerticalScrollBar().setUnitIncrement(16);
		scrollp.setLocation(Global.getW(0), Global.getH(0));
		scrollp.setSize(Global.getW(0.4), Global.getH(0.8));
		scrollp.setBackground(Color.pink);
		scrollp.setVisible(true);
		scrollp.validate();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.add(scrollp);
		
	}


	public void getContent(Vector<Object> content) {
		// TODO Auto-generated method stub
		
	}


	public void update_specialties() {
		// TODO Auto-generated method stub
		
		String[] spec = MainP.getSpecialties();
		String specn = (String) cspecialty.getSelectedItem();
		
		cspecialty.removeAllItems();
		
		for( int i = 0 ; i < spec.length; i++){
			cspecialty.addItem(spec[i]);
		}
		
		if(nref != null){
			for(int i = 0 ; i < cspecialty.getItemCount() ; i++){
				if(((String)cspecialty.getItemAt(i)).equals(specn)){
					cspecialty.setSelectedIndex(i);
					break;
				}
			}
		}else
		{
			cspecialty.setSelectedIndex(0);
		}
		
		
		
		cspecialty.validate();
		
		
	}



}
