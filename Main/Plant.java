package Main;

import java.io.Serializable;
import java.util.ArrayList;
/**This class represents all Plant objects.
 *
 */
public class Plant implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private double spreadRate;  //between 0 and 1
	private int x;
	private int y;
	private final int mapLimit=Animal.mapLimit;
	private int nutrition;
	private final static int maxNutrition=10;
	
	public Plant() {
		this(maxNutrition,1);	
	}
	
	public Plant(int nutrition, double spreadRate){
	    this.setNutrition(nutrition);
	    this.setSpreadRate(spreadRate);
	    
	    this.setX((int)(Math.random()*mapLimit));
	    this.setY((int)(Math.random()*mapLimit));

	}
	public void grow() {
		this.setNutrition(getNutrition()+ 1);
	}
	
	
	public int getNutrition() {
		return nutrition;
	}
	public void setNutrition(int nutrition) {
		if(nutrition>maxNutrition||nutrition<0) this.nutrition=maxNutrition;
		else this.nutrition = nutrition;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		if(y<mapLimit&&y>=0) this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		if(x<mapLimit&&x>=0) this.x = x;
	}
	public double getSpreadRate() {
		return spreadRate;
	}
	public void setSpreadRate(double spreadRate) {
		if(spreadRate>1||spreadRate<0) this.spreadRate=0;
		else this.spreadRate = spreadRate;
	}
	
	
	public String toString() {
		return this.getClass().getName()+": Nutrition: "+getNutrition()+",  SpreadRate: "+getSpreadRate()+" Koordinates: X: "+this.getX()+" Y: "+this.getY();
	}
	
	public void spread(ArrayList<Plant> plantList, char[][] map) {
		if(Math.random()<spreadRate&&nutrition>1) {
			int x=this.getX(),y=this.getY();
			
			while(true) {
			int moveDirection=(int)(Math.random()*4); //0-up, 1-right, 2-down, 3-left
			if(moveDirection==0&&this.getY()!=0) y=(this.getY()-1);
			else if(moveDirection==1&&(this.getX()+1)!=mapLimit) x=(this.getX()+1);
			else if(moveDirection==2&&this.getY()!=mapLimit-1) y=(this.getY()+1);
			else if(moveDirection==3&&this.getX()!=0) x=(this.getX()-1);
			else continue;
			
			if(map[y][x]=='-') {
				Plant n=new Plant(1,this.getSpreadRate());
				n.setX(x);
				n.setY(y);
				plantList.add(n);
				map[y][x]='P';
			}			
			break;
			}
		}		
	}
}
