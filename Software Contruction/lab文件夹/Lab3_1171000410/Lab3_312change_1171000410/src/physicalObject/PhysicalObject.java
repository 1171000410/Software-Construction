package physicalObject;

public abstract class PhysicalObject {
	private final String name;

	// Abstraction function:
	//  represents physical object in the system
	// Representation invariant:
	//  name is the physical object's name
	// Safety from rep exposure:
	//   All fields are private and final	
	
	/**Constructor*/
	public PhysicalObject(String name) {
		this.name = name;
	}
	
	/**Get the physical object's name*/
	public String getName() {
		return name;
	}
	
}
