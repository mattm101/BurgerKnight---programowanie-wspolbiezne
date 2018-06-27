package com.malecmateusz.burger_knight.animation;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Timer;
import java.util.TimerTask;

public class Animate {
	public static Timer timer = new Timer();
	public static Object frameLock = new Object();
	
	static int frame;
	
	private Image spriteSheet;
	private static Image ClientHUD = new ImageIcon("umatiego/sprites/hud.png").getImage();
	private static Image GarsonHUD = new ImageIcon("umatiego/sprites/hud3.png").getImage();
	
	public Object thisFrameLock = new Object();
	int thisFrame = 0;
	int row;
	boolean move = false;
	int hudPos = 0;
	
	int x = 600, y = 750;
	
	public Animate(String path){
		this.spriteSheet = new ImageIcon(path).getImage();
		row = 0;
	}
	
	private static void updateFrame(){
		synchronized(frameLock){
			frame++;
			frame %= 4;
		}
	}
	
	public static void timerStart(){
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				updateFrame();
			}
		}, 200, 200);
		
	}
	
	public void setCord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void dontMove(int x , int y){
		synchronized(thisFrameLock){
			move = false;
			setCord(x,y);
		}
	}
	
	public void setHUD(int val){
		synchronized(thisFrameLock){
			hudPos = val;
		}
	}
	public void disableHUD(){
		synchronized(thisFrameLock){
			hudPos = 0;
		}
	}
	
	public void moveDown(int x, int y){
		synchronized(thisFrameLock){
			row = 0;
			move = true;
			setCord(x,y);
		}
	}
	public void moveUp(int x, int y){
		synchronized(thisFrameLock){
			row = 3;
			move = true;
			setCord(x,y);
		}
	}
	
	public void moveLeft(int x, int y){
		synchronized(thisFrameLock){
			row = 1;
			move = true;
			setCord(x,y);
		}
	}
	
	public void moveRight(int x, int y){
		synchronized(thisFrameLock){
			row = 2;
			move = true;
			setCord(x,y);
		}
	}
	
	public void playBig(Graphics g, int width, int height){
		synchronized(thisFrameLock){
			if(move == true){
				synchronized(frameLock){
					g.drawImage(spriteSheet, x, y, x + width, y + height, frame * width, row * height, frame * width + width, row * height + height, null);
					
				}
			}
			else{
				g.drawImage(spriteSheet, x, y, x + width, y + height, thisFrame * width, row * height, thisFrame * width + width, row * height + height, null);
				}
			g.drawImage(GarsonHUD, x + 8, y - 25, x + 28, y - 5, hudPos*20, 0, hudPos*20 + 20, 20, null);
		}
	}
	
	public void playAnimation(Graphics g){
		synchronized(thisFrameLock){
			if(move == true){
				synchronized(frameLock){
					g.drawImage(spriteSheet, x, y, x + 32, y + 48, frame * 32, row * 48, frame * 32 + 32, row * 48 + 48, null);
					
				}
			}
			else{
				g.drawImage(spriteSheet, x, y, x + 32, y + 48, thisFrame * 32, row * 48, thisFrame * 32 + 32, row * 48 + 48, null);
			}
			g.drawImage(ClientHUD, x + 8, y - 25, x + 28, y - 5, hudPos*20, 0, hudPos*20 + 20, 20, null);
		}
	}
}
