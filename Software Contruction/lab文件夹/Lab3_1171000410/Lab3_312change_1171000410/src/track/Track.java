package track;

public class Track{

	private final double a;  //∞Î≥§÷·
	private final double b;  //∞Î∂Ã÷·
	private final double c;  //Ωπµ„
	
	// Abstraction function:
    //   represents track in the system
    // Representation invariant:
    //   radius is the track's  radius
    //
    // Safety from rep exposure:
    //   All fields are private and final
	
	/**Constructor*/
	public Track(double a,double b ,double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	/**Get double label of the track's a*/
	public double getA() {
		return this.a;
	}
	
	/**Get double label of the track's b*/
	public double getB() {
		return this.b;
	}
	
	/**Get double label of the track's c*/
	public double getC() {
		return this.c;
	}
	

}
