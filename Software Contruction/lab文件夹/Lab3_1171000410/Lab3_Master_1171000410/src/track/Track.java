package track;

public class Track{

	private final double radius;  //°ë¾¶
	
	// Abstraction function:
    //   represents track in the system
    // Representation invariant:
    //   radius is the track's  radius
    //
    // Safety from rep exposure:
    //   All fields are private and final
	
	/**Constructor*/
	public Track(double r) {
		radius = r;
	}
	
	/**Get double label of the track's radius*/
	public double getRadius() {
		return this.radius;
	}

}
