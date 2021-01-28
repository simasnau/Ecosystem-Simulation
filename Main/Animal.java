package Main;

import java.io.Serializable;
import java.util.ArrayList;

import Test.EcosystemException;
import Test.EcosystemWrongBreedRateException;
import Test.EcosystemWrongHealthException;

interface Alive{
	void live();
	
}
interface Breedable extends Alive{
	boolean isBreedable(Animal a);
	Animal breed(Animal n, char[][] map);
	
}

/**This is an abstract Animal class. All animals must inherit fields from this class.
 *
 */

 public abstract class Animal implements Breedable, Cloneable, Serializable {
	 
	private static final long serialVersionUID = 1L;
	public final static int maxHealth=20;
	public final static int maxAge=40;
	final static int breedAge=3;
	public final static int mapLimit=10;
	
	
	AnimalProperties properties=new AnimalProperties();
	private double breedRate;  //between 0 and 1
	private int x;
	private int y;
	private int age;
	private boolean reproduced;

	
	public Animal() {
		this.setHealth(maxHealth);
		this.setGender((int)(Math.random()*2));
		this.setbreedRate(1);
		this.setAge(5);
		this.setX((int)(Math.random()*mapLimit));
		this.setY((int)(Math.random()*mapLimit));
		
	}
	
	public Animal(int health, int gender, double breedRate, int age) throws EcosystemException{
		if((health<=0||health>maxHealth)&&(breedRate>1||breedRate<0)) throw new EcosystemException(this.getClass().getName());
		
		if(health<=0||health>maxHealth) throw new EcosystemWrongHealthException(this.getClass().getName(),health);
		else this.setHealth(health);
		    
		if(breedRate>1||breedRate<0) throw new EcosystemWrongBreedRateException(this.getClass().getName(),breedRate);
		else this.setbreedRate(breedRate);
		
		this.setAge(age);
	    this.setGender(gender);
	    
	    this.setX((int)(Math.random()*mapLimit));
	    this.setY((int)(Math.random()*mapLimit));
	   	    
	}
	
	
	public boolean hasReproduced() {
		return reproduced;
	}
	
	public void setReproduced(boolean reproduced) {
		this.reproduced=reproduced;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if(age>=0&&age<=maxAge)this.age = age;
		else this.age=0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(y<mapLimit&&y>=0) this.y = y;
		else this.y=-1;
	}

	public void setX(int x) {
		if(x<mapLimit&&x>=0) this.x = x;
		else this.x=-1;
	}

	public void setbreedRate(double breedRate) {
		if(breedRate>=0&&breedRate<=1) this.breedRate=breedRate;
		else {
			this.breedRate=0;
		}
	}

	public double getBreedRate() {
		return this.breedRate;	
	}
	public int getGender() {
		return this.properties.gender;
	}

	public void setGender(int gender) {
		if(gender==0||gender==1)this.properties.gender = gender;
		else {
			this.properties.gender=0;
			System.out.println("Wrong gender number");
		}
	}

	public int getHealth() {
		return this.properties.health;
	}

	public void setHealth(int health) {
		if(health>=0&&health<=maxHealth)this.properties.health = health;
		else this.properties.health=maxHealth;
	}
	public void live() {
		this.setReproduced(false); //reset reproduction counter
		this.setAge(this.getAge()+1); //age++
		this.setHealth(this.getHealth()-1); //health--	
	}
	
	public String toString() {
		return this.getClass().getName()+": Health: "+getHealth()+", Gender: "+getGender()+", Age: "+getAge()+",  BreedRate: "+getBreedRate()+" Koordinates: X: "+this.getX()+" Y: "+this.getY();
	}
	public final double distanceToAnimal(Animal a) {
		int x=this.getX()-a.getX();
		int y=this.getY()-a.getY();
		return Math.sqrt(x*x+y*y);				
	}
	public final double distanceToPlant(Plant a) {
		int x=this.getX()-a.getX();
		int y=this.getY()-a.getY();
		return Math.sqrt(x*x+y*y);	
	}			
	
	public final void move(char[][] map) {
		int k=0;
		boolean a=false,b=false,c=false,d=false; //patikrinimas ar visas puses isbande;
		char simb='-';
		if(this.getClass().getName().contains("Sheep")) simb='S';
		else if(this.getClass().getName().contains("Wolf")) simb='W';
		
		while(k<50) {
		try {
		int moveDirection=(int)(Math.random()*4); //0-up, 1-right, 2-down, 3-left		
		if(moveDirection==0&&this.getY()!=0&&map[this.getY()-1][this.getX()]=='-') {
			map[this.getY()][this.getX()]='-';
			this.setY(getY()-1);
			map[this.getY()][this.getX()]=simb;
			a=true;
		}
		else if(moveDirection==1&&this.getX()+1!=mapLimit-1&&map[this.getY()][this.getX()+1]=='-') {
			map[this.getY()][this.getX()]='-';
			this.setX(getX()+1);
			map[this.getY()][this.getX()]=simb;
			b=true;
		}
		else if(moveDirection==2&&this.getY()!=mapLimit-1&&map[this.getY()+1][this.getX()]=='-') {
			map[this.getY()][this.getX()]='-';
			this.setY(getY()+1);
			map[this.getY()][this.getX()]=simb;
			c=true;
		}
		else if(moveDirection==3&&this.getX()!=0&&map[this.getY()][this.getX()-1]=='-') {
			map[this.getY()][this.getX()]='-';
			this.setX(getX()-1);
			map[this.getY()][this.getX()]=simb;
			d=true;
		}
		else {
			k++;
			if(a==true&&b==true&&c==true&&d==true) break;
			continue;
		}
		}catch(ArrayIndexOutOfBoundsException e) {
			k++;
			continue;
		}
		break;
		}
	}
	
	
	public final void move(int x, int y) {
		 this.setX(getX()+x);
		 this.setY(getY()+y);		
	}
	
	public abstract void makeSound();
	public abstract void eat(ArrayList<?> eatList, char[][] map);
	
	
	public final Animal breed(Animal a, char[][] map) {
		if(isBreedable(a)) {
			GetAnimalFactory animalFactory=new GetAnimalFactory();
			Animal b=animalFactory.getAnimal(this.getClass().getName());				
			try {	
				
			if(map[this.getY()-1][this.getX()]=='-') {
				b.setX(this.getX());
				b.setY(this.getY()-1);
			}
			else if(map[this.getY()][this.getX()+1]=='-') {
				b.setX(this.getX()+1);
				b.setY(this.getY());
			}
			else if(map[this.getY()+1][this.getX()]=='-') {
				b.setX(this.getX());
				b.setY(this.getY()+1);				
			}
			else if(map[this.getY()][this.getX()-1]=='-') {
				b.setX(this.getX()-1);
				b.setY(this.getY());
			}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Gyvuno neimanoma padeti");	
				return null;
			}
			
			if(b.getClass().getName().contains("Sheep")) map[b.getY()][b.getX()]='S';
			else if(b.getClass().getName().contains("Wolf")) map[b.getY()][b.getX()]='W';
			
			this.child(b);
			return b;
		}
		else return null;
	}
	
	
	public final boolean isBreedable(Animal a) {
		if(Math.random()<=this.getBreedRate()) {				
				if(this.getClass().equals(a.getClass())&&getGender()!=a.getGender()&&this.hasReproduced()==false&&a.hasReproduced()==false&&getAge()>=breedAge&&a.getAge()>=breedAge&&distanceToAnimal(a)<=1) {
					if(this.getGender()==1) this.setReproduced(true);
					else a.setReproduced(true);
					return true;
				}
			}
		return false;
	}
	
	public void child(Animal n) {
		n.setbreedRate(this.getBreedRate());  //paveldimas
		n.setHealth(this.getHealth()+2);
		n.setAge(0);
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		Animal cloned=(Animal)super.clone();
		this.properties=(AnimalProperties) this.properties.clone();
		return cloned;
	}
	
}

 
