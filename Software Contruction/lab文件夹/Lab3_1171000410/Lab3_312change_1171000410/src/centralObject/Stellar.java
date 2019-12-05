package centralObject;

public class Stellar extends CentralObject{
	private final double stellarRadius;  //∫„–«∞Îæ∂
	private final double stellarQuality; //∫„–«÷ ¡ø
	
	// Abstraction function:
    //   represents the stellar in the center
    // Representation invariant:
    //   stellarRadius is the Stellar's radius
	//	 stellarQuality is the Stellar's quality
    //	stellarRadius and stellarQuality are immutable types
    // Safety from rep exposure:
    //   All fields are private and final
	
	/**Constructor*/
	public Stellar(String name , double radius , double quality) {
		super(name);
		this.stellarRadius = radius;
		this.stellarQuality = quality;
	}
	
	/**Get radius of the stellar*/
	public double getStellarRadius() {
		return stellarRadius;
	}

	/**Get quality of the stellar*/
	public double getStellarQuality() {
		return stellarQuality;
	}
}
