package flyweight;

import java.util.HashMap;

import track.Track;

public class ElectronFactory {
  private HashMap<Track, ElectronFlyweight> eletronMap;

  public ElectronFactory() {
	eletronMap = new HashMap<>();
  }

  public ElectronFlyweight getFlyweight(Track t) {
	if (eletronMap.containsKey(t)) {
	  return eletronMap.get(t);
	} else {
	  ElectronFlyweight flyweight = new ElectronFlyweight(t);
	  eletronMap.put(t, flyweight);
	  return flyweight;
	}
  }
}
