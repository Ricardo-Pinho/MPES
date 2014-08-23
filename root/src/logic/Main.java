package logic;

import tabusearch.MySolution;
import ui.Ui;

public class Main {
	private static Ui ui;
	private static FileManager fmanager;
	private static MainP mp;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("teste 1");
		Global.setDebugMode(true);
		mp = new MainP();
		ui = new Ui();
	}

}
