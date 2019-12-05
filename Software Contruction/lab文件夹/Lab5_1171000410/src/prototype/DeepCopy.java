package prototype;

import track.Track;

public class DeepCopy implements Cloneable {
  Track track = new Track(1);

  @Override
  public Object clone() throws CloneNotSupportedException {
	DeepCopy t = (DeepCopy) super.clone();
	t.track = new Track(1);
	return t;
  }

}
