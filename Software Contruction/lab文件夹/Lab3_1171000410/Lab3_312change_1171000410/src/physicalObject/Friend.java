package physicalObject;

public class Friend extends PhysicalObject{
	private final int friendAge;
	private final String friendGender;
	
	// Abstraction function:
	//  represents orbital object in the SocialNetWork
	// Representation invariant:
	//	friendAge is the friend's age
	//  friendGender is the friend's gender
    // Safety from rep exposure:
    //   All fields are private and final	
	
	/**Constructor*/
	public Friend(String name ,int age , String gender) {
		super(name);
		this.friendAge = age;
		this.friendGender = gender;
	}
	
	/**
	 * Get a friend's age
	 * @return int label of age
	 */
	public int getFriendAge() {
		return friendAge;
	}
	
	/**
	 * Get a friend's gender
	 * @return string label of age
	 */
	public String getFriendGender() {
		return friendGender;
	}
	
	/**
	 * Override hashCode method
	 * to record a friend's specific attributes
	 * @return int label of hash address
	 */
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + friendGender.hashCode();
		h = 31 * h + super.getName().hashCode();
		h = 31 * h + friendAge;
		return h;
	}
}
