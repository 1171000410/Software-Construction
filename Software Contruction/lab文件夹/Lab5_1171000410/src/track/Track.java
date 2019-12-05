package track;

public class Track {

  private final double radius; // °ë¾¶

  // Abstraction function:
  // represents track in the system
  // Representation invariant:
  // radius is the track's radius
  // radius > 0
  // Safety from rep exposure:
  // All fields are private and final

  private void checkRep() {
    assert radius > 0;
  }

  /** Constructor. */
  public Track(double r) {
    radius = r;
  }

  /** Get double label of the track's radius. */
  public double getRadius() {
    checkRep();
    return this.radius;
  }

}
