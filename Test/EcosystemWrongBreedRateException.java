package Test;
/**This exception is thrown when wrong breedRate is passed when creating a new Animal object.
 *
 */
public class EcosystemWrongBreedRateException extends EcosystemException{

	private static final long serialVersionUID = 3L;
	
	public double wrongBreedRate;
	
	public EcosystemWrongBreedRateException(String animalType,double breedRate) {
		super(animalType);
		wrongBreedRate=breedRate;
		
		
	}
	
}
