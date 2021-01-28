package Test;
/**This exception is thrown when wrong health argument is passed when creating a new Animal object
 *
 */
public class EcosystemWrongHealthException extends EcosystemException{

	private static final long serialVersionUID = 2L;
	
	public int wrongHealth;
	
	public EcosystemWrongHealthException(String animalType,int health) {
		super(animalType);
		wrongHealth=health;
		
		
		
	}

}
