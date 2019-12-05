package physicalobject;

public abstract class PhysicalObject {
  private final String name;

  // Abstraction function:
  //  represents physical object in the system
  // Representation invariant:
  //  name is the physical object's name
  //  name is not null
  // Safety from rep exposure:
  //   All fields are private and final

  private void checkRep() {
    assert name != null;
  }

  /**Constructor.*/
  public PhysicalObject(String name) {
    this.name = name;
  }

  /**Get the physical object's name.*/
  public String getName() {
    checkRep();
    return name;
  }

}
