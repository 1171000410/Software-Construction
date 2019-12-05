package relation;

import physicalobject.Friend;

public class Edge<K> {
  private final K source;
  private final K target;
  private final float weight;

  // Abstraction function:
  // represents an edge connecting from source to target with weight
  // Representation invariant:
  // source is a non-null K
  // target is a non-null K
  // K must be immutable
  // weight > 0
  // Safety from rep exposure:
  // All fields are private and final
  // source and target are of type K, required to be immutable
  // int is a primitive type so guaranteed immutable

  private void checkRep() {
    assert weight > 0;
  }

  /** Constructor. */
  public Edge(K source, K target, float weight) {
    this.source = source;
    this.target = target;
    this.weight = weight;
  }

  /** Returns this Edge's source. */
  public K getSource() {
    return source;
  }

  /** Returns this Edge's target. */
  public K getTarget() {
    return target;
  }

  /** Returns this Edge's weight. */
  public float getWeight() {
    checkRep();
    return weight;
  }
  
  @Override
  public String toString() {
	  Friend s = (Friend) source;
	  Friend t = (Friend) target;
	  return s.getName()+","+t.getName()+","+weight;
  }
}

