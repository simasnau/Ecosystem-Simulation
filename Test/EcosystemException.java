package Test;
/**This is exception is thrown if all the arguments when creating a new Animal object are wrong. 
 *
 */
public class EcosystemException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public String animalType;
	public EcosystemException(String a) {
		super();
		animalType=a;
	}
}
