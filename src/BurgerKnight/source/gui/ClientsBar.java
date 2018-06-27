package com.malecmateusz.burger_knight.gui;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ClientsBar extends JProgressBar{
	private int clients;
	private int allClients;
	private ClientsBar bar;
	private Object clientsLock = new Object();
	public ClientsBar(int min, int max){
		super(min, max);
		clients = 0;
		allClients = 0;
		bar = this;
		setString("Klienci 0 / 16");
		setStringPainted(true);
	}
	
	public void increaseQueued(){
		synchronized(clientsLock){
			allClients++;
			updateStatus();
		}
	}
	
	public void increase(){
		synchronized(clientsLock){
			clients++;
			updateStatus();
		}
	}
	
	public void decrease(){
		synchronized(clientsLock){
			clients--;
			allClients--;
			updateStatus();
		}
	}
	
	private void updateStatus(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				bar.setValue(clients);
				if(allClients > 16)
					bar.setString("Klienci " + clients + " / " + "16 oczekuj�cy " + (allClients - 16));
				else bar.setString("Klienci " + clients + " / " + "16");
			}
			
		});
	}
}
