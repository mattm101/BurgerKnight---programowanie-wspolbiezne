package com.malecmateusz.burger_knight.app_object.garson;
import com.malecmateusz.burger_knight.animation.AnimateCloud;
import com.malecmateusz.burger_knight.app_object.AppObject;
import com.malecmateusz.burger_knight.app_object.doors.Doors;
import com.malecmateusz.burger_knight.app_object.table.Table;
import com.malecmateusz.burger_knight.app_object.client.Client;
import com.malecmateusz.burger_knight.renderer.Manager;
import com.malecmateusz.burger_knight.renderer.Renderable;

import java.awt.Graphics;

public class Garson extends AppObject implements Runnable, Renderable {

	private boolean running = true;
	private volatile Table table;
	private Client client = null;
	private Manager manager;
	Object garsonLock = new Object();
	protected AnimateCloud cloud = new AnimateCloud("umatiego/sprites/ok3.png");
	
	private GarsonState state = GarsonState.GOTODEST;
	private int orderState = 0;
	
	public Garson(int x, int y, Table table, Manager manager, String sprite, boolean doors, Doors d){
		super(x,y, doors,d, sprite);
		this.manager = manager;
		this.table = table;
		
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
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
	}
	
	public void update(){
		
		synchronized(garsonLock){
		switch(state){
		case CHECKTABLE:
			
			client = table.garsonClientSitCheck();
			if(client != null) state = GarsonState.GOTODOORS;
			if(orderState == 1) animate.setHUD(2);
			if(orderState == 2) animate.setHUD(3);
		break;
		
		case GOTODOORS:
			if(table.doorsState){
				moveX = doors.x;
				moveY = doors.y + doors.posGet(doorsState);
				state = GarsonState.DOORSQUEUE;
			}
			
			else state = GarsonState.MOVETOTABLE;
		break;
		
		case DOORSQUEUE:
			if(move()){
				state = GarsonState.CANIPASS;
				
			}
		break;
		
		case CANIPASS:
			if(doors.canIGo(this)){
				
				moveY = doors.getPass(doorsState);
				state = GarsonState.PASS;
			}
			
		break;
		
		case PASS:
			if(move()){
				doors.ivePassed();
				doorsState = true;
				state = GarsonState.MOVETOTABLE;
			}
		break;
		
		case MOVETOTABLE:
			
			
			moveX = table.x + (table.tableWidth / 2);
			moveY = table.y - 48;
			
			if(move()) state = GarsonState.ATTABLE;
			
		break;
		
		case ATTABLE:
			animate.moveDown(x, y);
			animate.dontMove(x, y);
			client.garsonReady();
			if(orderState == 0){
				cloud.setVisible(x, y, "Co dla ciebie\nkowboju?");
				orderState = 1;
			}
			else if(orderState == 1){
				cloud.setVisible(x, y, "Tw�j burger.\nSmacznego!");
				orderState = 2;
				animate.disableHUD();
			}
			else{
				cloud.setVisible(x, y, "Dzi�ki.\nZapraszamy\nponownie!");
				orderState = 3;
				client = null;
				
			}
			
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			state = GarsonState.GOTODEST;
		break;
		
		case GOTODEST:
			if(orderState == 3){
				orderState = 0;
				animate.setHUD(4);
			}
			
			else if(orderState == 1){
				animate.setHUD(1);
			}
		
			if(doorsState){
				state = GarsonState.POSATDOORS;
			}
			else{
				moveX = startX;
				moveY = startY;
				if(move()){
					state = GarsonState.CHECKTABLE;
					animate.disableHUD();
				}
			}
		break;
	
		
		case POSATDOORS:
			moveX = doors.x;
			moveY = doors.y + doors.posGet(doorsState);
			state = GarsonState.DOORSOUTQUEUE;
		break;
		
		case DOORSOUTQUEUE:
			if(move()){
				state = GarsonState.CANIGOOUT;
			}
		break;
		
		case CANIGOOUT:
			if(doors.canIGo(this)){
				
				moveY = doors.getPass(doorsState);
				state = GarsonState.PASSOUT;
			}
		break;
		
		case PASSOUT:
			if(move()){
				doors.ivePassed();
				doorsState = false;
				state = GarsonState.GOTODEST;
			}
		break;
		}
		}
		
	}

	public void render(Graphics g) {
		animate.playBig(g, 32, 48);
		cloud.playAnimation(g);
	}

}
