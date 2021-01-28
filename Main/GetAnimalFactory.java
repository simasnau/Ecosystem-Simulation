package Main;
/**This class provides the functionality for creating new Animals using method factory design pattern.
 * 
 */
public class GetAnimalFactory {

	public Animal getAnimal(String animalType) {
		
		if(animalType.contains("Sheep")) return new Sheep();
		else if(animalType.contains("Wolf")) return new Wolf();
		else return null;	
		
	}	
}
