package relation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeIntimacyMap<K, V> {
  private final List<LeEdge<K, V>> edges = new ArrayList<>();
  private final Set<K> keys = new HashSet<>();
  private final Set<V> values = new HashSet<>();

  // Abstraction function:
  //   represents all edges in a map as a pair of central object and physical objects
  //   with a source to target direction that carries weight 
  // Representation invariant:
  //   edges is a list of distinct weighted Edges made by 
  //   keys is a set of sources
  //    values is a set of targets
  //    the size of edges and values are the same
  // Safety from rep exposure:
  //   All fields are private and final
  //     edges , keys and values are mutable types, so operations use defensive copies and
  //   immutable wrappers to avoid sharing the rep's objects to clients

  private void checkRep() {
    assert edges.size() == values.size();
  }

  /**
   * Get the amount of edges.
   * @return label of edges' size
   */
  public int size() {
    return edges.size();
  }

  /**
   * In order to find if the list has a specified edge, 
   * the source and target of the edge must be equal to 
   * the parameters passed in.
   * @param source which is to be compared with
   * @param target which is to be compared with
   * @return index of the edge -1 if the edge is not found
   */
  private int findEdge(K source, V target) {
    for (int i = 0; i < edges.size(); i++) {
      LeEdge<K, V> one = edges.get(i);
      if (one.getSource().equals(source) && one.getTarget().equals(target)) {
        return i; // 找到了返回在list的位置
      }
    }
    return -1; // 没找到返回-1
  }

  /**
   * Create a edge in the map.
   * @param source label of the edge's source
   * @param target label of the edge's target
   * @param weight label of the edge's weight
   */
  public void put(K source, V target, float weight) { // 构建一条边
    assert weight > 0;
    int index = findEdge(source, target);

    LeEdge<K, V> edge = new LeEdge<K, V>(source, target, weight);

    if (index < 0) { // 没找到该边
      edges.add(edge);
      keys.add(source);
      values.add(target);
    }
    checkRep();
  }

  /**
   * Remove a edge from this map.
   * @param source label of the edge's source
   * @param target label of the edge's target
   */
  public void remove(K source, V target) {
    int index = findEdge(source, target);

    if (index >= 0) {
      edges.get(0).getWeight();
      edges.remove(index);
      values.remove(target);
    }
    checkRep();
  }

  /**
   * Get all the objects connected with the central object.
   * @return
   */
  public Set<V> getValues() {
    return Collections.unmodifiableSet(values);
  }

  public Set<String> getValuesAndWeight(){
	  Set<String> set = new HashSet<String>();
	  for(int i = 0 ;i < edges.size() ;i++) {
		  String s = edges.get(i).toString();
		  set.add(s);
	  }
	  return set;
  }
  
}