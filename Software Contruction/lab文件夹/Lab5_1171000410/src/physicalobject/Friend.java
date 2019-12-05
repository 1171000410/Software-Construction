package physicalobject;

public class Friend extends PhysicalObject {
  private final int friendAge;
  private final String friendGender;

  // Abstraction function:
  //  represents orbital object in the SocialNetWork
  // Representation invariant:
  //    friendAge is the friend's age
  //  friendGender is the friend's gender
  //    friendAge > 0 , friendGender is not null;
  // Safety from rep exposure:
  //   All fields are private and final

  private void checkRep() {
    assert friendAge > 0;
    assert friendGender != null;
  }

  /**Constructor.*/
  public Friend(String name, int age, String gender) {
    super(name);
    this.friendAge = age;
    this.friendGender = gender;
  }

  /**
   * Get a friend's age.
   * @return int label of age
   */
  public int getFriendAge() {
    checkRep();
    return friendAge;
  }

  /**
   * Get a friend's gender.
   * @return string label of age
   */
  public String getFriendGender() {
    checkRep();
    return friendGender;
  }

  /**
   * Override hashCode method.
   * to record a friend's specific attributes
   * @return int label of hash address
   */
  @Override
  public int hashCode() {
    int h = super.getName().hashCode();
    return h;
//    h = 31 * h + friendGender.hashCode();
//    h = 31 * h + friendAge;
  }
}
