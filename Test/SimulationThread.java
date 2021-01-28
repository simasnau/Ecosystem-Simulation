package Test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Main.Animal;
import Main.Plant;
import Main.Wolf;
/**In this class all the simulation of ecosystem is happening. It also controls the output of the program.
 * 
 */
public class SimulationThread extends Thread {
	
	ArrayList<Animal> animalList=new ArrayList<Animal>();
	ArrayList<Plant> plantList=new ArrayList<Plant>();
	char [][] map=new char[Animal.mapLimit][Animal.mapLimit];
	File failas;
	volatile boolean keepRunning=true;
	
	public SimulationThread(ArrayList<Animal> animalList, ArrayList<Plant> plantList, char[][] map, File failas) {
		this.animalList=animalList;
		this.plantList=plantList;
		this.map=map;
		this.failas=failas;
	}
	
	public void run() {
	
	printEcosystem(plantList,animalList, failas, map);
		
		
	while(!animalList.isEmpty()&&keepRunning) {
		
		for(int i=0;i<plantList.size();i++) {         //augalai plinta/auga
			Plant p=plantList.get(i);
			p.spread(plantList,map);
			p.grow();
		}	

		for(int i=0;i<animalList.size();i++) {       //gyvunai dauginasi
			Animal cur=animalList.get(i);
			for(int j=i+1;j<animalList.size();j++) {
				Animal n=cur.breed(animalList.get(j), map);
				if(n!=null) animalList.add(n);					
			}				
		}		
		
	
		for(int i=0;i<animalList.size();i++) {//gyvunai vaiksto          		
			Animal n=animalList.get(i);
			n.move(map);
											
			if(n.getClass().getName().equals("Main.Wolf")) {	//gyvunai maitinasi/kovoja
				n.eat(animalList,map);
				((Wolf) n).fight(animalList);
			}	
			else if(n.getClass().getName().equals("Main.Sheep")) n.eat(plantList,map);
			
			n.live();		//gyvunai sensta
			if(n.getHealth()<=0||n.getAge()>=Animal.maxAge) {
				map[n.getY()][n.getX()]='-';
				animalList.remove(n);  //mirsta
				i--;
			}
		}				
		printEcosystem(plantList,animalList, failas, map);	
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			System.out.println("Programa buvo pertraukta");
			break;
		}
	}
	
	writeEcosystem(plantList,animalList);
	System.out.println("Programa baige darba");
	
	}
	
	
	private void writeEcosystem(ArrayList<Plant> plantList, ArrayList<Animal> animalList) {
		ObjectOutputStream out = null;
		FileOutputStream fileOut=null;
		
		try {
			fileOut=new FileOutputStream(new File("data.txt"));
			out=new ObjectOutputStream(fileOut);
			out.writeObject(plantList);
			out.writeObject(animalList);
				
			out.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Failas nerastas");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Nepavyko isvesti i faila");
			e.printStackTrace();
		} 
		
	}

	private static void printEcosystem(ArrayList<Plant> plantList, ArrayList<Animal> animalList, File failas, char[][] map) {
		for(int i=0;i<Animal.mapLimit;i++) {
			System.out.print("[");
			for(int j=0;j<Animal.mapLimit;j++) {
				if(map[i][j]!='W'&&map[i][j]!='S'&&map[i][j]!='P') System.out.print("-");
				else System.out.print(map[i][j]+"");	
			}
			System.out.print("]\n");
		}
		System.out.print("\n");
		
		
		DataOutputStream out = null;
		FileOutputStream fileOut=null;
		
		try {
			fileOut=new FileOutputStream(new File("rez.txt"),true);
			out=new DataOutputStream(fileOut);
			for(int i=0;i<plantList.size();i++) out.writeBytes(plantList.get(i).toString()+"\n");		
			for(int i=0;i<animalList.size();i++) out.writeBytes(animalList.get(i).toString()+"\n");		
			
			out.writeBytes("Augalu: "+plantList.size()+", Gyvunu: "+animalList.size()+"\n\n");
			
		} catch (FileNotFoundException e) {
			System.out.println("Failas nerastas");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Ivyko klaida atidarant faila");
			e.printStackTrace();
		} 
	}	
}

