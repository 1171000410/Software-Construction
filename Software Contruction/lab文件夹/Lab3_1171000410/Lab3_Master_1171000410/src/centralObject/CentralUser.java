package centralObject;

public class CentralUser extends CentralObject{

	private final int centralAge;
	private final String centralGender;
	
	// Abstraction function:
    //   represents the central user
    // Representation invariant:
    //  centralAge is the central object's age
	//  centralGender is the central object's gender
    //	centralAge and centralGender are immutable types
    // Safety from rep exposure:
    //   All fields are private and final
	
	/**Constructor*/
	public CentralUser(String name ,int age , String gender) {
		super(name);
		this.centralAge = age;
		this.centralGender = gender;
	}

	@Override
	/**Get the name of super class*/
	public String getName() {
		return super.getName();
	}
	
	/**Get the name of central user*/
	public int getAge() {
		return centralAge;
	}
	
	/**Get the gender of central user*/
	public String getGender() {
		return centralGender;
	}
	
}
