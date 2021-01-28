package Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import Main.Animal;
import Main.GetAnimalFactory;
import Main.Plant;
import Main.Sheep;
import Main.Wolf;
/**This is the class which has main() method.
 * 
 *
 *
 */
public class Test {	
	
	@SuppressWarnings("unchecked")
	public static void main(String[]args)  {		
		int health = 12,age = 5;
		double breedRate = 0.7;
		boolean excCaught=false;
		String animalType=null;
		
		ArrayList<Animal> animalList=new ArrayList<Animal>();
		ArrayList<Plant> plantList=new ArrayList<Plant>();
		
		
		File failas=new File("rez.txt");
		if(failas.exists()&&failas.isFile()) failas.delete();
		try {
			failas.createNewFile();
		} catch (IOException e1) {
			System.out.println("Nepavyko sukurti tokio failo.");
			e1.printStackTrace();
		}
		
		System.out.println("Sukurti nauja ekosistema: 0\nNuskaityti is duomenu failo: 1");
		Scanner scan=new Scanner(System.in);
		int komanda=scan.nextInt();
		if(komanda==1) {
			ObjectInputStream in = null;
			FileInputStream fileIn=null;
			
			try {
				fileIn=new FileInputStream(new File("data.txt"));
				in=new ObjectInputStream(fileIn);
				
				plantList=(ArrayList<Plant>) in.readObject();
				animalList=(ArrayList<Animal>) in.readObject();
				
			}catch (ClassNotFoundException e) {
					System.out.println("Ivyko klaida nuskaitant objektus. Nerasta klase");
					e.printStackTrace();	
			} catch (FileNotFoundException e) {
				System.out.println("Failas nerastas");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Ivyko klaida atidarant faila");
				e.printStackTrace();
			} 
			
			
			
		}
		else try {
			for(int i=0;i<6;i++) {
				excCaught=false;
				animalList.add(new Sheep(health,(int)(Math.random()*2), breedRate, age));
			}
			for(int i=0;i<3;i++) {
				excCaught=false;
				animalList.add(new Wolf(health,(int)(Math.random()*2), breedRate, age));
			}
			
		} catch (EcosystemWrongHealthException e) {
			excCaught=true;
			System.out.println(e.wrongHealth+" yra netinkama health lauko reiksme. Health bus nustatytas maksimalus");
			health=Animal.maxHealth;
			animalType=e.animalType;
		}
		catch(EcosystemWrongBreedRateException e) {
			excCaught=true;
			System.out.println(e.wrongBreedRate+" yra netinkama breedRate lauko reiksme. BreedRate bus nustatytas 0");
			breedRate=0;
			animalType=e.animalType;
		} catch (EcosystemException e) {
			excCaught=true;
			System.out.println("Visi parametrai netinkami. Health bus nustatytas maksimalus, BreedRate-0");
			animalType=e.animalType;
		}
		finally {
			if(excCaught) {
				GetAnimalFactory animalFactory=new GetAnimalFactory();
				Animal a=animalFactory.getAnimal(animalType);
				a.setHealth(health);
				a.setbreedRate(breedRate);
				animalList.add(a);
	
			}
		for(int i=0;i<4;i++) plantList.add(new Plant(5,0.15));
		}			
		
		char [][] map=new char[Animal.mapLimit][Animal.mapLimit];
		for(Animal a: animalList) {
			if(a.getClass().getName().contains("Wolf")) map[a.getY()][a.getX()]='W';
			else if(a.getClass().getName().contains("Sheep")) map[a.getY()][a.getX()]='S';
			}
		for(Plant a:plantList) map[a.getY()][a.getX()]='P';	
		
		for(int i=0;i<Animal.mapLimit;i++) {
			for(int j=0;j<Animal.mapLimit;j++) {
				if(map[i][j]!='W'&&map[i][j]!='S'&&map[i][j]!='P') map[i][j]='-';
			}
		}
		
		
		
		SimulationThread simulation=new SimulationThread(animalList, plantList, map, failas);
		simulation.start();
		
		System.out.println("Noredami nutraukti programos darba iveskite raide s.");
		while(!scan.next().toLowerCase().equals("s")) {
			
		}	
		simulation.keepRunning=false;
		try {
			simulation.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		scan.close();
}
	
}
