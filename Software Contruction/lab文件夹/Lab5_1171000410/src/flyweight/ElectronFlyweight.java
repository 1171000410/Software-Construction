package flyweight;

import track.Track;

public class ElectronFlyweight implements Flyweight {
  Track track;

  public ElectronFlyweight(Track t) {
	this.track = t;
  }
   
}
