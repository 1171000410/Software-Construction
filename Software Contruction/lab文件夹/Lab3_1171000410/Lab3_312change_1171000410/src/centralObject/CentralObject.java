package centralObject;

public class CentralObject {
	private final String name;  //Ãû³Æ
	
	 // Abstraction function:
    //   represents the central object
    // Representation invariant:
    //   name is the central object's name
    //	name is immutable type
    // Safety from rep exposure:
    //   All fields are private and final
	
	/**Constructor*/
	public CentralObject(String name){
		this.name = name;
	}
	
	/**Get the name of central object*/
	public String getName() {
		return this.name;
	}
	
	
}
