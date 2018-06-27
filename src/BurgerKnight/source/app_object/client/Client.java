package com.malecmateusz.burger_knight.app_object.client;
import com.malecmateusz.burger_knight.animation.AnimateCloud;
import com.malecmateusz.burger_knight.gui.ClientsBar;
import com.malecmateusz.burger_knight.app_object.AppObject;
import com.malecmateusz.burger_knight.app_object.doors.Doors;
import com.malecmateusz.burger_knight.app_object.table.Table;
import com.malecmateusz.burger_knight.renderer.Manager;
import com.malecmateusz.burger_knight.renderer.Renderable;

import java.awt.Graphics;
import java.util.Random;

public class Client extends AppObject implements Runnable, Renderable {
	
	
	
	private ClientState state = ClientState.ENTER1;
	private int orderState = 0;
	private boolean running = true;
	private Table table;
	private Manager manager;
	ClientsBar bar;
	protected AnimateCloud cloud = new AnimateCloud("umatiego/sprites/ok3.png");
	
	private Object clientLock = new Object();
	
	public Client(int x, int y, Manager manager, String sprite, boolean doors, Doors d, ClientsBar bar){
		super(x,y, doors, d, sprite);
		this.x = x;
		this.y = y;
		Random rand = new Random();
		moveX = rand.nextInt(200) + 500;
		moveY = rand.nextInt(100) + 550;
		this.manager = manager;
		this.bar = bar;
		
	}
	
	public void render(Graphics g) {
		animate.playAnimation(g);
		cloud.playAnimation(g);
	}

	public void run() {
		
		bar.increase();
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		manager.addRender(this);
		while(running){
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				update();
				Thread.yield();
				delta = 0.0;
			}
			
		}
		
		manager.removeRender(this);	
		bar.decrease();
	}
	
	private void update(){
		synchronized(clientLock){
			switch(state){
			case ENTER1:
				if(move()) state = ClientState.SEARCHFORTABLE2;
			break;
			
			case SEARCHFORTABLE2:
				
				table = Table.clientGetTable();
				if(table != null) state = ClientState.FINDDOORS3;
				break;
		
			case FINDDOORS3:
				
				if(table.doorsState){
					moveX = doors.x;
					moveY = doors.y + doors.posGet(doorsState);
					state = ClientState.GOTODOORS4;
				}
				
				else state = ClientState.GOTOTABLE7;
				
			break;
			
			case GOTODOORS4:
				
				if(move()){
					state = ClientState.WAITATDOORS5;
				}
			break;
			
			case WAITATDOORS5:
				if(doors.canIGo(this)){
					moveY = doors.getPass(doorsState);
					state = ClientState.PASSIN6;
				}
				
			break;
			
			case PASSIN6:
				if(move()){
					doors.ivePassed();
					doorsState = true;
					state = ClientState.GOTOTABLE7;
				}
			break;
			case GOTOTABLE7:
				moveX = table.x + (table.tableWidth / 2);
				moveY = table.y + (table.tableHeight);
				if(move()){
					state = ClientState.ATTABLE8;
					animate.moveUp(x, y);
					animate.dontMove(x, y);
					table.clientSit(this);
				
				}
			
			break;
		
			case ATTABLE8:
				if(orderState != 0){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
					
					if(orderState == 1)
						cloud.setVisible(x, y, "Poprosz�\nChop Suey.");
					else if(orderState == 2){
						cloud.setVisible(x, y, "Nie to\nzamawia�em.");
						animate.setHUD(1);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					animate.disableHUD();
				
				try {
					
					clientLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			break;
			
			
			
			case GETOUT9:
				if(table != null){
					
				table.clientReturnTable();
				table = null;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				cloud.setVisible(x, y, "Musz� do\ntoalety.");
				animate.setHUD(2);
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				animate.disableHUD();
				}
				else if(doorsState){
					state = ClientState.PASSOUT10;
				}
				else{
					moveX = 584;
					moveY = 750;
					if(move()) running = false;
					
				}
			break;
			
			case PASSOUT10:
				moveX = doors.x;
				moveY = doors.y + doors.posGet(doorsState);
				state = ClientState.MOVETODOORS11;
			break;
			
			case MOVETODOORS11:
				if(move()){
					state = ClientState.WAITTOPASS12;
				}
			break;
			
			case WAITTOPASS12:
				if(doors.canIGo(this)){
					moveY = doors.getPass(doorsState);
					state = ClientState.FINALPASS13;
				}
			break;
			
			case FINALPASS13:
				if(move()){
					doors.ivePassed();
					doorsState = false;
					state = ClientState.GETOUT9;
				}
			break;
			}
		}
	}
	
	public void garsonReady(){
		
		synchronized(clientLock){
			orderState++;
			if(orderState == 3)
				state = ClientState.GETOUT9;
			clientLock.notify();
		}
	}

}
