package com.malecmateusz.burger_knight.app_object.table;
import com.malecmateusz.burger_knight.app_object.AppObject;
import com.malecmateusz.burger_knight.app_object.client.Client;
import com.malecmateusz.burger_knight.app_object.doors.Doors;

import java.util.LinkedList;

public class Table extends AppObject {
	public static LinkedList<Table> freeTables = new LinkedList<Table>();
	private static Object freeTablesLock = new Object();
	
	public final int tableWidth, tableHeight;
	
	public Client client = null;
	
	private Object thisTableLock = new Object();
	
	public Table(int x, int y, int width, int height, boolean doors, Doors d){
		super(x,y, doors, d, "");
		this.tableWidth = width;
		this.tableHeight = height;
		
		
		synchronized(freeTablesLock){
			Table.addFreeTable(this);
		}
	}
	
	public static void addFreeTable(Table table){
		Table.freeTables.add(table);
	}
	
	public static void removeFreeTable(Table table){
		Table.freeTables.remove(table);
	}
	
	
	public static Table clientGetTable(){
		Table tmpTable = null;
		boolean waiting = false;
		synchronized(freeTablesLock){
			do{
			
				try{
					waiting = false;
					tmpTable = freeTables.getFirst();
				} catch(Exception e){
					try {
						waiting = true;
						freeTablesLock.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			
			}while(waiting == true);
			
			removeFreeTable(tmpTable);
			
			return tmpTable;
		}
	}
	
	public void clientReturnTable(){
		
		synchronized(thisTableLock){
			this.client = null;
		}
		
		synchronized(freeTablesLock){
			addFreeTable(this);
			freeTablesLock.notify();
		}
	}
	
	public void clientSit(Client client){
		synchronized(thisTableLock){
			this.client = client;
			thisTableLock.notify();
		}
	}
	
	public Client garsonClientSitCheck(){
		synchronized(thisTableLock){
			while(this.client == null){
				try {
					thisTableLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return this.client;
		}
	}
}
