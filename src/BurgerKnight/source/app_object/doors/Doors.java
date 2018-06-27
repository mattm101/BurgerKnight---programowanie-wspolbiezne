package com.malecmateusz.burger_knight.app_object.doors;

import com.malecmateusz.burger_knight.app_object.AppObject;

public class Doors extends AppObject {
	public static int pos = 0;
	volatile boolean occupied = false;
	Object queueLock = new Object();
	
	public Doors(int x, int y, boolean doors) {
		super(x,y, doors, null, "");
		
	}
	
	public int posGet(boolean doors){
		synchronized(queueLock){
			pos++;
			if(doors) return (-10 * (pos + 1)) - 58;
			else return (10 * (pos + 1)) + 10;
			}
	}
	
	public boolean canIGo(AppObject object){
		synchronized(queueLock){
		if(occupied == false){
			occupied = true;
			return true;
		}
		else{
			try {
				queueLock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		}
	}
	
	public int getPass(boolean doors){
		if(doors) return y + 10;
		else return y - 58;
	}
	
	public void ivePassed(){
		synchronized(queueLock){
			pos--;
			occupied = false;
			queueLock.notify();
		}
	}

}
