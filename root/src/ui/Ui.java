package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import tabusearch.MySolution;

import bd.Hospital;

import logic.Global;
import logic.MainP;

public class Ui {
	private JFrame frame;
	private static JButton bbd;
	private JButton bschedule;
	private static JButton boptions;
	private JButton brefresh;
	private JButton bcreateh;
	private static JButton bcreates;
	private JPanel mainp, menup, bdp, optionsp, schedulep, scheduleTablep;
	private static JPanel lbdp; 
	private JLabel lbds, loptionChoiceAlgorithm, loptionPopSize, loptionTsIterations, loptionGenIterations, loptionMemNumber, loptionBaseSol, loptionUniformR, loptionMutationR, loptionElitism;
	private static JLabel lschedule;
	private static JComboBox<String> cbdchoose, cbdschoose, cboptionsChooseAlgorithm;
	private JList lbd;
	private ImageIcon irefresh;
	private static JScrollPane slbd;
	private DefaultListModel mlbd;
	private static GridLayout layoutbdp;
	private static boolean bbdchoose = false;
	private static ScheduleUi scheduleUi;
	private static JProgressBar pbSchedule;
	private static JTextField tfoptionPopSize, tfoptionTsIterations, tfoptionGenIterations, tfoptionMemNumber, tfoptionBaseSol, tfoptionUniformR, tfoptionMutationR;
	private static JCheckBox cboptionElitism;
	
	
	public Ui(){
		
		 try {
			UIManager.setLookAndFeel(
					"javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Global.screen_W = (int)(screen.getWidth()*0.7);
		Global.screen_H = (int)(screen.getHeight()*0.7);
		init();
		Global.scroll_w_size = 2* (int) slbd.getVerticalScrollBar().getSize().getWidth();
	}
	
	
	private void init(){
		load_images();
		frame = new JFrame("Nusering Rostering");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(Global.screen_W,Global.screen_H));
		frame.setLayout(null);
		frame.setResizable(false);
		Global.frame = frame;
		mainp = new JPanel();
		mainp.setLayout(null);
		mainp.setSize(Global.screen_W,Global.screen_H);
		mainp.setLocation(0,0);
		mainp.setBackground(Color.white);
		mainp.setVisible(true);
		init_menus();
		init_panels();
		frame.add(mainp);
		frame.setVisible(true);
		
		
	}
	
	
	
	private void init_menus(){
		
		menup = new JPanel();
		menup.setLayout(null);
		menup.setSize(Global.screen_W,Global.getH(0.05));
		menup.setLocation(0,0);
		menup.setBackground(Color.pink);
		menup.setVisible(true);
		
		
		
		bbd = new JButton("Database");
		bbd.setLayout(null);
		bbd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				bschedule.setEnabled(true);
				boptions.setEnabled(true);
				schedulep.setVisible(false);
				optionsp.setVisible(false);
				bbd.setEnabled(false);
				bdp.setVisible(true);
				
			}
			
		});
		bbd.setLocation(0,0);
		bbd.setSize(Global.screen_W/3,Global.getH(0.05));
		bbd.setEnabled(true);
		bbd.setVisible(true);
		menup.add(bbd);
		
		bschedule = new JButton("Schedule");
		bschedule.setLayout(null);
		bschedule.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				reset_schedule_view();
				boptions.setEnabled(true);
				bbd.setEnabled(true);
				bdp.setVisible(false);
				
				optionsp.setVisible(false);
				schedulep.setVisible(true);
				bschedule.setEnabled(false);
				
			}
			
		});
		bschedule.setLocation((Global.screen_W/3),0);
		bschedule.setSize((Global.screen_W/3),Global.getH(0.05));
		bschedule.setEnabled(true);
		bschedule.setVisible(true);
		menup.add(bschedule);
		
		
		boptions = new JButton("Options");
		boptions.setLayout(null);
		boptions.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				bschedule.setEnabled(true);
				bbd.setEnabled(true);
				schedulep.setVisible(false);
				bdp.setVisible(false);
				
				boptions.setEnabled(false);
				optionsp.setVisible(true);
				
				
			}
			
		});
		boptions.setLocation((Global.screen_W/3)*2,0);
		boptions.setSize((Global.screen_W/3),Global.getH(0.05));
		boptions.setEnabled(true);
		boptions.setVisible(true);
		menup.add(boptions);
		
		mainp.add(menup);
		
	}
	
	
	private void init_panels(){
		
		optionsp = new JPanel();
		optionsp.setLayout(null);
		optionsp.setLocation(0,Global.getH(0.05));
		optionsp.setSize(Global.screen_W,Global.getH(0.95));
		optionsp.setBackground(Color.white);
		optionsp.setVisible(false);
		init_options_view();
		mainp.add(optionsp);
		
		bdp = new JPanel();
		bdp.setLayout(null);
		bdp.setLocation(0,Global.getH(0.05));
		bdp.setSize(Global.screen_W,Global.getH(0.95));
		bdp.setBackground(Color.white);
		bdp.setVisible(false);
		init_bd_view();
		mainp.add(bdp);
		
		schedulep = new JPanel();
		schedulep.setLayout(null);
		schedulep.setLocation(0,Global.getH(0.05));
		schedulep.setSize(Global.screen_W,Global.getH(0.95));
		schedulep.setBackground(Color.white);
		schedulep.setVisible(false);
		init_schedule_view();
		mainp.add(schedulep);
		
		
		
	}
	
	
	private void init_bd_view()
	{
		lbds = new JLabel("choose the desired database: ");
		lbds.setLayout(null);
		lbds.setLocation(Global.getW(0.02),0);
		lbds.setSize(Global.getW(0.2), Global.getH(0.05));
		lbds.setVisible(true);
		bdp.add(lbds);
		
		
		cbdchoose = new JComboBox<String>(MainP.getBds());
		cbdchoose.setLocation(Global.getW(0.22),0);
		cbdchoose.setSize(Global.getW(0.6),Global.getH(0.05));
		cbdchoose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!bbdchoose){
					if(Global.logicdebug)
						System.out.println("////////////////////////////////////////////////////////");
					if(MainP.changeBD((String)cbdchoose.getSelectedItem()))
					{
						load_bd((String)cbdchoose.getSelectedItem());
					}else{
						refresh_bd_view(null);
					}
				}
				
				
			}

			
			
		});
		cbdchoose.setVisible(true);
		bdp.add(cbdchoose);
		
		
		
		brefresh = new JButton("refresh");
		brefresh.setLayout(null);
		brefresh.setLocation(Global.getW(0.82),0);
		brefresh.setSize(Global.getW(0.05),Global.getH(0.05));
		brefresh.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				/**
				 * fazer refresh da ista mantendo a escolha actual.
				 */
				if(Global.logicdebug)
					System.out.println("chamou 1");
				refresh_bd_view(null);
		
			}
			
		});
		brefresh.setIcon(irefresh);
		brefresh.setVisible(true);
		bdp.add(brefresh);
		
		
		bcreateh = new JButton("create new Hospital");
		bcreateh.setLayout(null);
		bcreateh.setLocation(Global.getW(0.87),0);
		bcreateh.setSize(Global.getW(0.13),Global.getH(0.05));
		bcreateh.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(MainP.start_new_hospital())
				{
					if(Global.logicdebug)
						System.out.println("criado com sucesso");
				}else
				{
					if(Global.logicdebug)
						System.out.println("erro");
				}
			}
			
		});
		bcreateh.setVisible(true);
		bdp.add(bcreateh);
		/*String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		JComboBox petList = new JComboBox(petStrings);
		petList.setSelectedIndex(4);
		petList.addActionListener(this);
		*/
		
		
		lbdp = new JPanel();
		layoutbdp = new GridLayout();
		
		//ltemp.setColumns(0);
		layoutbdp.setVgap(1);
		lbdp.setLayout(layoutbdp);
	
		lbdp.setVisible(true);
		
		lbdp.setBackground(Color.yellow);
		
		//lbdp.add(new CellBd());
		//lbdp.add(new CellBd());
		//layoutbdp.setRows(2);
		//lbdp.add(new CellBd());
		//lbdp.add(new CellBd());
		slbd = new JScrollPane(lbdp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		slbd.getVerticalScrollBar().setUnitIncrement(16);
		slbd.setLocation(Global.getW(0.01), Global.getH(0.06));
		slbd.setSize(Global.getW(0.98), Global.getH(0.83));
		slbd.setBackground(Color.pink);
		slbd.setVisible(true);
		slbd.validate();
		
		
		
		bdp.add(slbd);

		//create_new_bd();
	}
	
	
	private void init_schedule_view()
	{
		lbds = new JLabel("choose the desired database: ");
		lbds.setLayout(null);
		lbds.setLocation(Global.getW(0.02),0);
		lbds.setSize(Global.getW(0.2), Global.getH(0.05));
		lbds.setVisible(true);
		schedulep.add(lbds);
		
		
		cbdschoose = new JComboBox<String>(MainP.getBds());
		cbdschoose.setLocation(Global.getW(0.22),0);
		cbdschoose.setSize(Global.getW(0.6),Global.getH(0.05));
		cbdschoose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(MainP.changeBD((String)cbdschoose.getSelectedItem())){
					Hospital temp = MainP.getHospital((String)cbdschoose.getSelectedItem());
					if(temp == null){
						reset_schedule_view();
						bcreates.setEnabled(false);
					}else
					{
						lschedule.setText("Database " + temp.getName() + " selected");
						scheduleUi.setBd(temp.getName());
						bcreates.setEnabled(true);
						
					}
				}
					
			
			
				
			}

			
			
		});
		cbdschoose.setVisible(true);
		schedulep.add(cbdschoose);
		
		
		
		
		
		bcreates = new JButton("Generate");
		bcreates.setLayout(null);
		bcreates.setLocation(Global.getW(0.82),0);
		bcreates.setSize(Global.getW(0.18),Global.getH(0.05));
		bcreates.setEnabled(false);
		bcreates.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bcreates.setEnabled(false);
				
				if(bcreates.getText().equals("Generate")){
					cbdschoose.setEnabled(false);
					boptions.setEnabled(false);
					bbd.setEnabled(false);
					bcreates.setText("Stop");
					lschedule.setText("initiating process");
					lschedule.setSize(Global.getW(0.98), Global.getH(0.025));
					lschedule.validate();
					pbSchedule.setVisible(true);
					scheduleUi.start();
					MainP.processSchedule();
				}else{
					stopProcessing(false, "Generation stopped");
				}
				
				bcreates.setEnabled(true);
				/*if(MainP.start_new_hospital())
				{
					System.out.println("criado com sucesso");
				}else
				{
					System.out.println("erro");
				}*/
			}
			
		});
		bcreates.setVisible(true);
		
		pbSchedule = new JProgressBar();
		pbSchedule.setLayout(null);
		pbSchedule.setLocation(Global.getW(0.01), Global.getH(0.075));
		pbSchedule.setSize(Global.getW(0.98), Global.getH(0.025));
		pbSchedule.setOpaque(true);
		pbSchedule.setVisible(false);
		pbSchedule.setValue(0);
		pbSchedule.setStringPainted(true);
		schedulep.add(pbSchedule);
		

		lschedule = new JLabel();
		lschedule.setLayout(null);
		lschedule.setText("Please select a database for generation");
		lschedule.setHorizontalAlignment(SwingConstants.CENTER);
		lschedule.setVerticalAlignment(SwingConstants.CENTER);
		lschedule.setBackground(Color.black);
		lschedule.setForeground(Color.white);
		lschedule.setOpaque(true);
		lschedule.setLocation(Global.getW(0.01),Global.getH(0.05));
		lschedule.setSize(Global.getW(0.98),Global.getH(0.05));
		schedulep.add(lschedule);
		
		
		scheduleTablep = new JPanel();
		scheduleTablep.setLayout(null);
		scheduleTablep.setBackground(Color.green);
		scheduleTablep.setLocation(Global.getW(0.01), Global.getH(0.1));
		scheduleTablep.setSize(Global.getW(0.98), Global.getH(0.80));
		schedulep.add(scheduleTablep);
		
		
		
		scheduleUi = new ScheduleUi(scheduleTablep);

		schedulep.add(bcreates);
	}
	
	
	private void init_options_view(){
		
		
		loptionChoiceAlgorithm = new JLabel();
		loptionChoiceAlgorithm.setLayout(null);
		loptionChoiceAlgorithm.setText("Optimization Algorithm:");
		loptionChoiceAlgorithm.setLocation(Global.getW(0.01), Global.getH(0.05));
		loptionChoiceAlgorithm.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionChoiceAlgorithm);
		
		
		String[] temp = { "Tabu Search","Switch", "Memetic Algoritm" };
		cboptionsChooseAlgorithm = new JComboBox<String>(temp);
		cboptionsChooseAlgorithm.setLocation(Global.getW(0.21),Global.getH(0.05));
		cboptionsChooseAlgorithm.setSize(Global.getW(0.78), Global.getH(0.05));
		cboptionsChooseAlgorithm.setSelectedIndex(0);
		cboptionsChooseAlgorithm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cboptionsChooseAlgorithm.getSelectedIndex() == 0){
					loptionGenIterations.setVisible(false);
					tfoptionGenIterations.setVisible(false);
					loptionMemNumber.setVisible(false);
					tfoptionMemNumber.setVisible(false);
					loptionBaseSol.setVisible(false);
					tfoptionBaseSol.setVisible(false);
					loptionUniformR.setVisible(false);
					tfoptionUniformR.setVisible(false);
					loptionMutationR.setVisible(false);
					tfoptionMutationR.setVisible(false);
					loptionElitism.setVisible(false);
					cboptionElitism.setVisible(false);
				}else{
					loptionGenIterations.setVisible(true);
					tfoptionGenIterations.setVisible(true);
					loptionMemNumber.setVisible(true);
					tfoptionMemNumber.setVisible(true);
					loptionBaseSol.setVisible(true);
					tfoptionBaseSol.setVisible(true);
					loptionUniformR.setVisible(true);
					tfoptionUniformR.setVisible(true);
					loptionMutationR.setVisible(true);
					tfoptionMutationR.setVisible(true);
					loptionElitism.setVisible(true);
					cboptionElitism.setVisible(true);
				}
			}
			
		});
		optionsp.add(cboptionsChooseAlgorithm);
		
		
		loptionPopSize = new JLabel();
		loptionPopSize.setLayout(null);
		loptionPopSize.setText("PopSize:");
		loptionPopSize.setLocation(Global.getW(0.01), Global.getH(0.12));
		loptionPopSize.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionPopSize);
		
		
		
		tfoptionPopSize = new JTextField(Integer.toString(Global.popsize));
		tfoptionPopSize.setLayout(null);
		tfoptionPopSize.setLocation(Global.getW(0.21), Global.getH(0.12));
		tfoptionPopSize.setSize(Global.getW(0.78), Global.getH(0.05));
		tfoptionPopSize.getDocument().addDocumentListener(new DocumentListener(){

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
					
					String s = tfoptionPopSize.getText();
					
					if(s.equals(""))
					{
						return;
					}
					
					i = Integer.parseInt(s);
					
				}catch(NumberFormatException e){
					
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							tfoptionPopSize.setText("10");
							
						}
					});
					return;
				}
				
				
				
				
				Global.popsize = i;
				
				
				
			}
			
		});
		
		
		optionsp.add(tfoptionPopSize);
		
		
		loptionTsIterations = new JLabel();
		loptionTsIterations.setLayout(null);
		loptionTsIterations.setText("Tabu Search Iterations:");
		loptionTsIterations.setLocation(Global.getW(0.01), Global.getH(0.19));
		loptionTsIterations.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionTsIterations);
		
		
		
		tfoptionTsIterations = new JTextField(Integer.toString(Global.tsiterations));
		tfoptionTsIterations.setLayout(null);
		tfoptionTsIterations.setLocation(Global.getW(0.21), Global.getH(0.19));
		tfoptionTsIterations.setSize(Global.getW(0.78), Global.getH(0.05));
		tfoptionTsIterations.getDocument().addDocumentListener(new DocumentListener(){

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
					
					String s = tfoptionTsIterations.getText();
					
					if(s.equals(""))
					{
						return;
					}
					
					i = Integer.parseInt(s);
					
				}catch(NumberFormatException e){
					
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							tfoptionTsIterations.setText("100");
							
						}
					});
					return;
				}
				
				
				
				
				Global.tsiterations = i;
				
				
				
			}
			
		});
		
		
		optionsp.add(tfoptionTsIterations);
		
		
		loptionGenIterations = new JLabel();
		loptionGenIterations.setLayout(null);
		loptionGenIterations.setVisible(false);
		loptionGenIterations.setText("Genetic Algorithm Iterations:");
		loptionGenIterations.setLocation(Global.getW(0.01), Global.getH(0.26));
		loptionGenIterations.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionGenIterations);
		
		
		
		tfoptionGenIterations = new JTextField(Integer.toString(Global.geniterations));
		tfoptionGenIterations.setLayout(null);
		tfoptionGenIterations.setVisible(false);
		tfoptionGenIterations.setLocation(Global.getW(0.21), Global.getH(0.26));
		tfoptionGenIterations.setSize(Global.getW(0.78), Global.getH(0.05));
		tfoptionGenIterations.getDocument().addDocumentListener(new DocumentListener(){

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
					
					String s = tfoptionGenIterations.getText();
					
					if(s.equals(""))
					{
						return;
					}
					
					i = Integer.parseInt(s);
					
				}catch(NumberFormatException e){
					
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							tfoptionGenIterations.setText("10");
							
						}
					});
					return;
				}
				
				
				
				
				Global.geniterations = i;
				
				
				
			}
			
		});
		
		
		optionsp.add(tfoptionGenIterations);
		
		
		loptionMemNumber = new JLabel();
		loptionMemNumber.setLayout(null);
		loptionMemNumber.setVisible(false);
		loptionMemNumber.setText("Number of members for cross-over:");
		loptionMemNumber.setLocation(Global.getW(0.01), Global.getH(0.33));
		loptionMemNumber.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionMemNumber);
		
		
		
		tfoptionMemNumber = new JTextField(Integer.toString(Global.MemNumber));
		tfoptionMemNumber.setLayout(null);
		tfoptionMemNumber.setVisible(false);
		tfoptionMemNumber.setLocation(Global.getW(0.21), Global.getH(0.33));
		tfoptionMemNumber.setSize(Global.getW(0.78), Global.getH(0.05));
		tfoptionMemNumber.getDocument().addDocumentListener(new DocumentListener(){

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
					
					String s = tfoptionMemNumber.getText();
					
					if(s.equals(""))
					{
						return;
					}
					
					i = Integer.parseInt(s);
					
				}catch(NumberFormatException e){
					
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							tfoptionMemNumber.setText("4");
							
						}
					});
					return;
				}
				
				
				
				
				Global.MemNumber = i;
				
				
				
			}
			
		});
		
		
		optionsp.add(tfoptionMemNumber);
		
		
		//loptionBaseSol, loptionUniformR, loptionMutationR, loptionElitism
		
		loptionBaseSol = new JLabel();
		loptionBaseSol.setLayout(null);
		loptionBaseSol.setVisible(false);
		loptionBaseSol.setText("Number of iterations BaseSolution:");
		loptionBaseSol.setLocation(Global.getW(0.01), Global.getH(0.40));
		loptionBaseSol.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionBaseSol);
		
		
		
		tfoptionBaseSol = new JTextField(Integer.toString(Global.baseSolIterations));
		tfoptionBaseSol.setLayout(null);
		tfoptionBaseSol.setVisible(false);
		tfoptionBaseSol.setLocation(Global.getW(0.21), Global.getH(0.40));
		tfoptionBaseSol.setSize(Global.getW(0.78), Global.getH(0.05));
		tfoptionBaseSol.getDocument().addDocumentListener(new DocumentListener(){

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
					
					String s = tfoptionBaseSol.getText();
					
					if(s.equals(""))
					{
						return;
					}
					
					i = Integer.parseInt(s);
					
				}catch(NumberFormatException e){
					
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							tfoptionBaseSol.setText("100");
							
						}
					});
					return;
				}
				
				
				
				
				Global.baseSolIterations = i;
				
				
				
			}
			
		});
		
		
		optionsp.add(tfoptionBaseSol);
		
		loptionUniformR = new JLabel();
		loptionUniformR.setLayout(null);
		loptionUniformR.setVisible(false);
		loptionUniformR.setText("Uniform Rate:");
		loptionUniformR.setLocation(Global.getW(0.01), Global.getH(0.47));
		loptionUniformR.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionUniformR);
		
		
		
		tfoptionUniformR = new JTextField(Double.toString(Global.uniformRate));
		tfoptionUniformR.setLayout(null);
		tfoptionUniformR.setVisible(false);
		tfoptionUniformR.setLocation(Global.getW(0.21), Global.getH(0.47));
		tfoptionUniformR.setSize(Global.getW(0.78), Global.getH(0.05));
		tfoptionUniformR.getDocument().addDocumentListener(new DocumentListener(){

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
				double i = 0 ;
				
				try{
					
					String s = tfoptionUniformR.getText();
					
					if(s.equals(""))
					{
						return;
					}
					
					i = Double.parseDouble(s);
					
				}catch(NumberFormatException e){
					
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							tfoptionUniformR.setText("0.5");
							
						}
					});
					return;
				}
				
				
				
				
				Global.uniformRate = i;
				
				
				
			}
			
		});
		
		
		optionsp.add(tfoptionUniformR);
		
		loptionMutationR = new JLabel();
		loptionMutationR.setLayout(null);
		loptionMutationR.setVisible(false);
		loptionMutationR.setText("Mutation Rate:");
		loptionMutationR.setLocation(Global.getW(0.01), Global.getH(0.54));
		loptionMutationR.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionMutationR);
		
		
		
		tfoptionMutationR = new JTextField(Double.toString(Global.mutationRate));
		tfoptionMutationR.setLayout(null);
		tfoptionMutationR.setVisible(false);
		tfoptionMutationR.setLocation(Global.getW(0.21), Global.getH(0.54));
		tfoptionMutationR.setSize(Global.getW(0.78), Global.getH(0.05));
		tfoptionMutationR.getDocument().addDocumentListener(new DocumentListener(){

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
				double i = 0 ;
				
				try{
					
					String s = tfoptionMutationR.getText();
					
					if(s.equals(""))
					{
						return;
					}
					
					i = Double.parseDouble(s);
					
				}catch(NumberFormatException e){
					
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							tfoptionMutationR.setText("0.015");
							
						}
					});
					return;
				}
				
				
				
				
				Global.mutationRate = i;
				
				
				
			}
			
		});
		
		
		optionsp.add(tfoptionMutationR);
		
		loptionElitism = new JLabel();
		loptionElitism.setLayout(null);
		loptionElitism.setVisible(false);
		loptionElitism.setText("Elitism:");
		loptionElitism.setLocation(Global.getW(0.01), Global.getH(0.61));
		loptionElitism.setSize(Global.getW(0.20),Global.getH(0.05));
		
		
		optionsp.add(loptionElitism);
		
		
		
		cboptionElitism = new JCheckBox();
		cboptionElitism.setSelected(Global.elitism);
		cboptionElitism.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Global.elitism = cboptionElitism.isSelected();
			}
			
		});
		cboptionElitism.setLocation(Global.getW(0.21), Global.getH(0.61));
		cboptionElitism.setSize(Global.getW(0.78), Global.getH(0.05));
		cboptionElitism.setVisible(false);
		
		optionsp.add(cboptionElitism);
		
		
		
		
		
	}
	
	
	
	private void load_images()
	{
		Graphics g = null;
		BufferedImage buf = null;
		Image img = null;
		irefresh = new ImageIcon("images/refresh.png");
		img = irefresh.getImage();
		buf = new BufferedImage(Global.getW(0.05), Global.getH(0.05), BufferedImage.TYPE_INT_ARGB);
		g = buf.getGraphics();
		if(Global.logicdebug)
			System.out.println(Global.getW(0.05) + " " + Global.getH(0.05) + " " + ((Global.getW(0.05)/2)-(Global.getH(0.05)/2)));
		g.drawImage(img, ((Global.getW(0.05)/2)-(Global.getH(0.05)/3)), 0, Global.getH(0.05), Global.getH(0.05), null);
		irefresh = new ImageIcon(buf);
		if(Global.logicdebug)
			System.out.println(irefresh.getImage().getWidth(null) + " "  + irefresh.getImage().getHeight(null));
		
	}
	
	
	public static void create_new_bd(CellBd c){
		
		Component[] comp = lbdp.getComponents();

		for( int  i = 0, ic = 0 ; i < comp.length ; i++, ic++){
			if(comp[i] instanceof CellBd)
			{
				lbdp.remove(ic);
				ic--;
			}
		}
		if(Global.logicdebug)
			System.out.println("adicionado vista de hospital");
		layoutbdp.setRows(MainP.getCount());
		lbdp.add(c);
		slbd.validate();
		
	}


	public static void add_nurse(CellBd c) {
		// TODO Auto-generated method stub
		Component[] comp = lbdp.getComponents();
		CellBd add = null;
		for( int  i = 0; i < comp.length ; i++){
			if(comp[i] instanceof CellBd)
			{
				if(((CellBd)comp[i]).getType() == Global.TYPE_ADD)
				{
					add = (CellBd)comp[i];
					lbdp.remove(i);
					break;
				}
				
			}
		}
		layoutbdp.setRows(MainP.getCount());
		lbdp.add(c);
		lbdp.add(add);
		slbd.validate();
	}


	public static void addCell(CellBd c) {
		// TODO Auto-generated method stub
		layoutbdp.setRows(MainP.getCount());
		lbdp.add(c);
		slbd.validate();
	}


	public static void update_specialties() {
		// TODO Auto-generated method stub
		CellBd c = null;
		for(int i = 0 ; i < lbdp.getComponentCount(); i++){
			if(lbdp.getComponent(i) instanceof CellBd){
				c = (CellBd)lbdp.getComponent(i);
				
				if(c.getType() == Global.TYPE_CELL){
					c.update_specialties();
				}
			}
		}
	}


	public static void deleteH() {
		// TODO Auto-generated method stub
		Component[] comp = lbdp.getComponents();

		for( int  i = 0, ic = 0 ; i < comp.length ; i++, ic++){
			if(comp[i] instanceof CellBd)
			{
				lbdp.remove(ic);
				ic--;
			}
		}
		
		slbd.validate();
	}
	
	
	/*public static void update_listbds(String name){
		cbdchoose.removeAllItems();
		
		String[] s = MainP.getBds();
		int founded = -1;
		for( int i = 0 ; i < s.length; i++){
			cbdchoose.addItem(s[i]);
			System.out.println("adicionou : " + s[i] + "|");
			if(s[i].equals(name)){
				founded = i;
			}
		}
		
		if(founded != -1){
			cbdchoose.setSelectedIndex(founded);
		}
		
		

		cbdchoose.validate();
	}*/
	
	
	private static void load_bd(String selectedItem) {
		// TODO Auto-generated method stub
		Component[] comp = lbdp.getComponents();
		
		
		for( int  i = 0, ic = 0 ; i < comp.length ; i++, ic++){
			if(comp[i] instanceof CellBd)
			{
				lbdp.remove(ic);
				ic--;
			}
		}
		slbd.validate();
		if(selectedItem != ""){
			Vector<CellBd> cells = MainP.getCells();
			layoutbdp.setRows(cells.size());
			for( int i = 0 ; i < cells.size();i++){
				lbdp.add(cells.get(i));
				//slbd.validate();
			}

			slbd.validate();
		}
		
		
		
	}
	
	
	public static void refresh_bd(){
		load_bd((String) cbdchoose.getSelectedItem());
	}
	
	
	public static void refresh_bd_view(String name){
		bbdchoose = true;
		String schoose;
		
		
		if(name == null){
			schoose = (String) cbdchoose.getSelectedItem();
		}else
		{
			schoose = name;
		}
		
		cbdchoose.removeAllItems();
		if(Global.logicdebug)
			System.out.println("chamou isto");
		String[] stemp = MainP.getBds();
		int founded = -1;
		for(int i = 0 ; i < stemp.length; i++){
			cbdchoose.addItem(stemp[i]);
			if(Global.logicdebug)
				System.out.println("adicionado: " + stemp[i]);
			if(stemp[i].equals(schoose))
			{
				
				founded = i;
			}
		}
		cbdchoose.validate();
		
		if(founded != -1){
			if(Global.logicdebug)
				System.out.println("vai seleccionar " + schoose + " no indice " + founded);
			cbdchoose.setSelectedIndex(founded);
			if(MainP.changeBD(schoose))
			{
				load_bd(schoose);
			}else{
				//refresh_bd_view();
			}
		}else{
		
		}
		
		cbdchoose.validate();
		bbdchoose = false;
	}
	
	public void reset_schedule_view(){
		
		if(!MainP.isScheduleRunning()){
			lschedule.setText("Please select a database for generation");
			lschedule.setSize(Global.getW(0.98), Global.getH(0.05));
			pbSchedule.setVisible(false);
			lschedule.validate();
			bcreates.setEnabled(false);
			cbdschoose.removeAllItems();
			
			
			String[] temp = MainP.getBds();
			for(int i = 0 ; i < temp.length; i++){
				cbdschoose.addItem(temp[i]);
			}
			
			cbdschoose.validate();
			
			cbdschoose.setSelectedIndex(0);
			
			cbdschoose.validate();
			
			scheduleUi.reset();
		}
		
		
	}
	
	
	public static void updateScheduleStatus(String message, int p){
		if(message != null){
			lschedule.setText(message);
		}
		pbSchedule.setValue(p);
	}
	
	
	public static void stopProcessing(boolean success, String error){
		if(Global.logicdebug)
			System.out.println("entrou aqui");
		MainP.stopProcessSchedule();
		boptions.setEnabled(true);
		bbd.setEnabled(true);
		cbdschoose.setEnabled(true);
		scheduleUi.stop();
		if(success){
			lschedule.setText("Schedule generated with success !");
		}else{
			lschedule.setText(error);
		}
		bcreates.setText("Generate");
		
		pbSchedule.setValue(0);
		lschedule.setSize(Global.getW(0.98), Global.getH(0.05));
		pbSchedule.setVisible(false);
	}
	
	
	
	public static int getOptimizationAlgorithm(){
		return cboptionsChooseAlgorithm.getSelectedIndex();
	}
	
	
	
	


}
