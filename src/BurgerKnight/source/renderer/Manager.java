package com.malecmateusz.burger_knight.renderer;
import java.awt.Graphics;
import java.util.LinkedList;

public class Manager {
	LinkedList<Renderable> renders = new LinkedList<Renderable>();
	
	public void render(Graphics g){
		for(int i=0;i<renders.size();i++){
			Renderable renderObject = renders.get(i);
			
			renderObject.render(g);
		}
	}
	
	public void addRender(Renderable object){
		this.renders.add(object);
	}
	
	public void removeRender(Renderable object){
		this.renders.remove(object);
	}
}
