package com.malecmateusz.burger_knight.gui;
import com.malecmateusz.burger_knight.utils.Randomizer;
import com.malecmateusz.burger_knight.animation.Animate;
import com.malecmateusz.burger_knight.app_object.client.Client;
import com.malecmateusz.burger_knight.app_object.doors.Doors;
import com.malecmateusz.burger_knight.app_object.garson.Garson;
import com.malecmateusz.burger_knight.app_object.table.Table;
import com.malecmateusz.burger_knight.renderer.Manager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JPanel{
	
	class Mouse implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			addClient();
		}
		public void mouseEntered(MouseEvent e) {
			
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
			
		}
		public void mouseReleased(MouseEvent e) {
		}
	}
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH =  1200, HEIGHT = 750;
	private ExecutorService exec;
	private Manager manager;
	private Doors doors;
	private Timer mainTimer = new Timer();
	private ClientsBar bar;
	
	private Image back = new ImageIcon("umatiego/sprites/back.png").getImage();
	
	public Main(){
		bar = new ClientsBar(0, 16);
		new Window(WIDTH, HEIGHT, "Burger Knight - Tw�j ostatni posi�ek!", bar, this);
		manager = new Manager();
		Animate.timerStart();
		this.doors = new Doors(700, 300, false);
		Table table1 = new Table(100,450, 60, 48, false, doors);
		Table table2 = new Table(200,450, 60, 48, false, doors);
		Table table3 = new Table(900,150, 60, 48, true, doors);
		Table table4 = new Table(1000,150, 60, 48, true, doors);
		
		exec = Executors.newFixedThreadPool(20);
		
		Garson g1 = new Garson(100, 350, table1, manager, "umatiego/sprites/jasmine.png", false, doors);
		Garson g2 = new Garson(200, 350, table2, manager,"umatiego/sprites/jasmine.png", false, doors);
		Garson g3 = new Garson(300, 350, table3, manager,"umatiego/sprites/jasmine.png", false, doors);
		Garson g4 = new Garson(400, 350, table4, manager,"umatiego/sprites/jasmine.png", false, doors);
	
		manager.addRender(g1);
		manager.addRender(g2);
		manager.addRender(g3);
		manager.addRender(g4);
		
		this.addMouseListener(new Mouse());
		
		exec.execute(g1);
		exec.execute(g2);
		exec.execute(g3);
		exec.execute(g4);
	}
	
	
	public void addClient(){
		bar.increaseQueued();
		Client client = new Client(600, 750, manager, Randomizer.randomizeClient(), false, doors, bar);
		exec.execute(client);
	}
	
	public synchronized void start(){
		
		mainTimer.scheduleAtFixedRate(new TimerTask(){
			public void run() {
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						repaint();
					}
					
				});
			}
			
		}, 15, 15);
		
	}
	
	public void paintComponent(Graphics g){
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(back, 0, 0, null);
		manager.render(g);
		
	}

}
