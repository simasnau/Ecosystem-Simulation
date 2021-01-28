package Main;

import java.io.Serializable;
/**This class provides various Animal properties like health and gender.
 *
 */
public class AnimalProperties implements Cloneable, Serializable{
	
	private static final long serialVersionUID = 1L;
	int health; //between 0 and maxHealth
	 int gender; //0=male 1=female
	 
	 public Object clone() throws CloneNotSupportedException {
			AnimalProperties cloned=(AnimalProperties)super.clone();
			
			return cloned;
		}
	 
	 
}