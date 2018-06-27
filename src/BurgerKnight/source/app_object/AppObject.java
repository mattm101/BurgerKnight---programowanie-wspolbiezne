package com.malecmateusz.burger_knight.app_object;

import com.malecmateusz.burger_knight.animation.Animate;
import com.malecmateusz.burger_knight.app_object.doors.Doors;

public abstract class AppObject {
	protected volatile int x, y, moveX, moveY, startX, startY;
	protected boolean doorsState;
	protected Doors doors;
	protected Animate animate;
	
	
	public AppObject(int x, int y, boolean doorsState, Doors doors, String sprite){
		this.animate = new Animate(sprite);
		this.x = x;
		this.y = y;
		this.moveX = x;
		this.moveY = y;
		this.startX = x;
		this.startY = y;
		this.doorsState = doorsState;
		this.doors = doors;
		
	}
	
	protected boolean move(){
		if(y > moveY){
			y--;
			animate.moveUp(x, y);
			
			return false;
		}
		else if(x > moveX){
			x--;
			animate.moveLeft(x, y);
			return false;
		}
		else if(x < moveX){
			x++;
			animate.moveRight(x, y);
			return false;
		}
		else if(y < moveY){
			y++;
			animate.moveDown(x, y);
			
			return false;
		}
		
		animate.dontMove(x, y);
		return true;
		
	}
}
