package centralobject;

public class Stellar extends CentralObject {
  private final double stellarRadius; //ºãÐÇ°ë¾¶
  private final double stellarQuality; //ºãÐÇÖÊÁ¿

  // Abstraction function:
  //   represents the stellar in the center
  // Representation invariant:
  //   stellarRadius is the Stellar's radius
  //    stellarQuality is the Stellar's quality
  //    stellarRadius and stellarQuality are immutable types
  // Safety from rep exposure:
  //   All fields are private and final

  private void checkRep() {
    assert stellarRadius > 0;
    assert stellarQuality > 0;
  }

  /**Constructor.*/
  public Stellar(String name, double radius, double quality) {
    super(name);
    this.stellarRadius = radius;
    this.stellarQuality = quality;
  }

  /**Get radius of the stellar.*/
  public double getStellarRadius() {
    checkRep();
    return stellarRadius;
  }

  /**Get quality of the stellar.*/
  public double getStellarQuality() {
    checkRep();
    return stellarQuality;
  }
}
