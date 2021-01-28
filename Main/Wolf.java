//super.setMesedis(true);
//this.fight(animalList);

package Main;
import java.util.ArrayList;

import Test.EcosystemException;
/**This is the Wolf class which inherits from Animal.

 *
 */

public class Wolf extends Animal{
	//private static final long serialVersionUID = 1L;
	private String status;
	
	
	public Wolf() {
		super();
		double rand=(Math.random());
		if(rand<0.15) setStatus("Alfa");
		else if(rand>=0.15&&rand<0.5) setStatus("Beta");
		else if(rand>=0.5&&rand<0.75) setStatus("Delta");
		else if(rand>=0.75&&rand<=1) setStatus("Omega");
	}
	
	public Wolf(int health, int gender, double breedRate, int age) throws EcosystemException {
		super(health, gender, breedRate, age);
		double rand=(Math.random());
		if(rand<0.15) setStatus("Alfa");
		else if(rand>=0.15&&rand<0.5) setStatus("Beta");
		else if(rand>=0.5&&rand<0.75) setStatus("Delta");
		else if(rand>=0.75&&rand<=1) setStatus("Omega");
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void makeSound() {
		System.out.println("AUUUUUU");
	}
	public String toString() {
		return super.toString()+" Statusas: "+getStatus();
	}
	
	public void fight(ArrayList<Animal> animalList) {
		for(int i=0;i<animalList.size();i++) {
			Animal curAnimal=animalList.get(i);
			if(this.getClass().equals(curAnimal.getClass())&&this.getHealth()>curAnimal.getHealth()&&distanceToAnimal(curAnimal)<=1&&this.getAge()>=3&&curAnimal.getAge()>=3){
				if(this.getStatus().charAt(0)>((Wolf)curAnimal).getStatus().charAt(0)) {
					String temp=this.getStatus();
					this.setStatus((((Wolf)curAnimal)).getStatus());
					(((Wolf)curAnimal)).setStatus(temp);
				}
				
			}
		}
		
	}
		
	
	public void eat(ArrayList<?> eatList, char[][]map) {
		for(int i=0;i<eatList.size();i++) {
			Animal curAnimal=(Animal) eatList.get(i);
			if(this.getClass().equals(curAnimal.getClass())==false&&distanceToAnimal(curAnimal)<=1) {
				this.setHealth(this.getHealth()+curAnimal.getHealth());
				map[curAnimal.getY()][curAnimal.getX()]='-';
				eatList.remove(i);
				break;
			} 
		}
	}
	
}
