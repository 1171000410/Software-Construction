package relation;

import centralobject.CentralUser;
import physicalobject.Friend;

public class LeEdge<K, V> {
  private final K source;
  private final V target;
  private final float weight;

  // Abstraction function:
  //   represents an edge connecting from source to target with weight
  // Representation invariant:
  //   source is a non-null K
  //   target is a non-null V
  //   K ,V must be immutable
  //   weight > 0
  // Safety from rep exposure:
  //   All fields are private and final
  //   source is type of K and target is type of V, required to be immutable
  //   int is a primitive type so guaranteed immutable

  private void checkRep() {
    assert weight > 0;
  }

  /**Constructor.*/
  public LeEdge(K source, V target, float weight) {
    this.source = source;
    this.target = target;
    this.weight = weight;
  }

  /** Returns this Edge's source.*/
  public K getSource() {
    return source;
  }

  /**Returns this Edge's target.*/
  public V getTarget() {
    return target;
  }

  /**Returns this Edge's weight.*/
  public float getWeight() {
    checkRep();
    return weight;
  }
  
  /**to string*/
  @Override
  public String toString() {
	  CentralUser s = (CentralUser) source;
	  Friend t = (Friend) target;
	  return s.getName()+","+t.getName()+","+weight;
	  
  }
}
