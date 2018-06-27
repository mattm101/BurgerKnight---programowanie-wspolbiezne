package com.malecmateusz.burger_knight.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame{

	public Window(int width, int height, String title, ClientsBar bar, Main main){
		super(title);
		
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		add(main, BorderLayout.CENTER);
		add(bar, BorderLayout.NORTH);
		setVisible(true);
		
		
	}
}
