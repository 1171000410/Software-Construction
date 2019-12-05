package centralobject;

public class CentralObject {
  private final String name; //Ãû³Æ

  // Abstraction function:
  //   represents the central object
  // Representation invariant:
  //   name is the central object's name
  //    name is immutable type
  // Safety from rep exposure:
  //   All fields are private and final

  private void checkRep() {
    assert name != null;
  }

  /**Constructor.*/
  public CentralObject(String name) {
    this.name = name;
  }

  /**Get the name of central object.*/
  public String getName() {
    checkRep();
    return this.name;
  }

}
