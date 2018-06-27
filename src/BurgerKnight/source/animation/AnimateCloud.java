package com.malecmateusz.burger_knight.animation;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class AnimateCloud {
	public static Timer timer = new Timer();
	private Image cloud;
	private boolean visible = false;
	private int x, y;
	private String text;
	
	Object cloudLock = new Object();
	
	public AnimateCloud(String path){
		this.cloud = new ImageIcon(path).getImage();
	}
	
	public void setVisible(int x, int y, String text){
		synchronized(cloudLock){
			visible = true;
			this.x = x;
			this.y = y;
			this.text = text;
		}
		timer.schedule(new TimerTask(){
			public void run(){
				hide();
			}
		}, 1000);
	}
	
	private void hide(){
		synchronized(cloudLock){
			visible = false;
		}
	}
	
	public void playAnimation(Graphics g){
		synchronized(cloudLock){
			if(visible){
				g.drawImage(cloud, x - 25, y - 85, null);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 11));
				int i = 0;
				for(String line : text.split("\n")){
					g.drawString(line, x - 15, y - 65 + g.getFontMetrics().getHeight() * i);
					i++;
				}
				
			}
		}
	}
}
