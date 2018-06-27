package com.malecmateusz.burger_knight;
import com.malecmateusz.burger_knight.gui.Main;

import javax.swing.SwingUtilities;

public class App {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new Main().start();
			}
			
		});
	}
}
