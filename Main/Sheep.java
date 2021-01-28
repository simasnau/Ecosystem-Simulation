//super.setZoledis(true)  visuose konst
package Main;
import java.util.ArrayList;

import Test.EcosystemException;


/**This is the Sheep class which inherits from Animal.
 * 
 *
 */
public class Sheep extends Animal {
	
	//private static final long serialVersionUID = 1L;
	private int kailioStoris;
	
	
	public Sheep() {
		super();
		kailioStoris=1;
		
	}
	
	public Sheep(int health, int gender, double breedRate, int age) throws EcosystemException {
		super(health, gender, breedRate, age);
		kailioStoris=1;		
	}
	
	public int getKailioStoris() {
		return kailioStoris;		
	}
	public void setKailioStoris(int a) {
		kailioStoris=a;
	}
	
	public void makeSound() {
		System.out.println("MEEEE");
	}
	
	public String toString() {
		return super.toString()+" Kailio Storis: "+kailioStoris;
		
		
	}
		
	public void eat(ArrayList<?> eatList, char[][] map) {
		for(int i=0;i<eatList.size();i++) {
			Plant cur=(Plant) eatList.get(i);
			if(distanceToPlant(cur)<=1) {
				this.setHealth(this.getHealth()+cur.getNutrition());
				map[cur.getY()][cur.getX()]='-';
				eatList.remove(i);
				break;
			} 
			
		}
		
	}
	
	public void live() {
		super.live();
		this.kailioStoris++;
		
	}

			
	
}
