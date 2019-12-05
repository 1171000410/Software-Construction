package relation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EeIntimacyMap<K> {
  private final List<Edge<K>> edges = new ArrayList<>();
  private final Set<K> keys = new HashSet<>();
  private final Set<K> values = new HashSet<>();

  // Abstraction function:
  // represents all edges in a map as a pair of connected physical objects
  // with a source to target direction that carries weight
  // Representation invariant:
  // edges is a list of distinct weighted Edges made by
  // keys is a set of sources
  // values is a set of targets
  // the size of keys and values are the same
  // Safety from rep exposure:
  // All fields are private and final
  // edges , keys and values are mutable types, so operations use defensive copies
  // and
  // immutable wrappers to avoid sharing the rep's objects to clients

//  @SuppressWarnings("rawtypes")
//  private static EeIntimacyMap instance = new EeIntimacyMap();
//
//  @SuppressWarnings("rawtypes")
//  public static EeIntimacyMap getInstance() {
//	return instance;
//  }
//  
//  EeIntimacyMap s = EeIntimacyMap.getInstance();
  
  private void checkRep() {
    assert keys.size() == values.size();
  }

  /**
   * In order to find if the list has a specified edge, the source and target of
   * the edge must be equal to the parameters passed in.
   * 
   * @param source which is to be compared with
   * @param target which is to be compared with
   * @return index of the edge -1 if the edge is not found
   */
  private int findEdge(K source, K target) {
    for (int i = 0; i < edges.size(); i++) {
      Edge<K> one = edges.get(i);
      if (one.getSource().equals(source) && one.getTarget().equals(target)) {
        return i; // 找到了返回在list的位置
      }
    }
    return -1; // 没找到返回-1
  }

  /**
   * Get the amount of edges.
   * 
   * @return label of edges' size
   */
  public int size() {
    return edges.size();
  }

  /**
   * Create a edge in the map.
   * 
   * @param source label of the edge's source
   * @param target label of the edge's target
   * @param weight label of the edge's weight
   */
  public void put(K source, K target, float weight) { // 构建一条边
    assert weight > 0;
    int index = findEdge(source, target);

    Edge<K> edge = new Edge<K>(source, target, weight);

    if (index < 0) { // 没找到该边
      edges.add(edge);
      keys.add(source);
      values.add(target);
    }
    checkRep();
  }

  /**
   * Remove a edge from this map.
   * 
   * @param source label of the edge's source
   * @param target label of the edge's target
   */
  public void remove(K source, K target) {
    assert source != null;
    assert target != null;
    int index = findEdge(source, target);

    if (index >= 0) {
      edges.remove(index);

      keys.remove(source); // 事实上点没什么用
      values.remove(target);
    }
    checkRep();
  }

  /**
   * Get all the objects connected to a specific object.
   * 
   * @param obj label of the specified object
   * @return list of the adjacent objects
   */
  public ArrayList<K> getAdjacentObjects(K obj) {
    assert obj != null;
    ArrayList<K> adjcentObject = new ArrayList<K>();

    for (Edge<K> edge : edges) {
      if (edge.getSource() == obj) {
        adjcentObject.add(edge.getTarget());
      }
      if (edge.getTarget() == obj) {
        adjcentObject.add(edge.getSource());
      }
    }
    assert adjcentObject != null;
    return adjcentObject;
  }

  /**
   * Obtain the number of objects that reach a certain intimacy with a specific.
   * object
   * 
   * @param obj      label of the specified object
   * @param intimacy label of the edge's intimacy
   * @return
   */
  public int getNumOfClosers(K obj, double intimacy) {
    assert intimacy > 0 && intimacy <= 1; // 亲密度大小
    int counter = 0;
    for (Edge<K> edge : edges) {
      if ((edge.getSource() == obj || edge.getTarget() == obj) && edge.getWeight() >= intimacy) {
        counter++;
      }
    }
    assert counter >= 0; // 数目
    return counter;
  }

  public Set<String> getEdgesString(){
	  Set<String> set = new HashSet<String>();
	  for(int i = 0 ;i < edges.size() ;i++) {
		  String s = edges.get(i).toString();
		  set.add(s);
	  }
	  return set;	  
  }
  
}
