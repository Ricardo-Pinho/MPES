package ui;

import java.awt.Dialog;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logic.Global;

import bd.Nurse;

public class AssignUi extends JDialog {
	private JTextArea area;
	private JScrollPane scrollp;
	
	public AssignUi(JFrame frame,ArrayList<Nurse> nurses){
		super(frame,"", Dialog.ModalityType.DOCUMENT_MODAL);
		init(nurses);
	}

	private void init(ArrayList<Nurse> nurses) {
		// TODO Auto-generated method stub
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(Global.getW(0.4), Global.getH(0.8));
		this.setLayout(null);
		this.setVisible(false);
		this.setResizable(false);
		
		
		area = new JTextArea();
		scrollp = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollp.setLocation(0, 0);
		scrollp.setSize(Global.getW(0.4), Global.getH(0.8));
		area.setEditable(false);
		
		for(int i = 0 ; i < nurses.size(); i++){
			area.append(nurses.get(i).getName() + "\n");
		}
		
		add(scrollp);
		
		setVisible(true);
	}
	
	

}
