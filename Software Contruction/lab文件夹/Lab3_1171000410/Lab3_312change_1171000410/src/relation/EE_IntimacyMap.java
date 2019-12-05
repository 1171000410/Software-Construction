package relation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EE_IntimacyMap<K> {
	private final List<Edge<K>> edges = new ArrayList<>();
	private final Set<K> keys = new HashSet<>();
	private final Set<K> values = new HashSet<>();

	// Abstraction function:
    //   represents all edges in a map as a pair of connected physical objects
    //   with a source to target direction that carries weight 
    // Representation invariant:
    //   edges is a list of distinct weighted Edges made by 
    //   keys is a set of sources
    //	 values is a set of targets
    // Safety from rep exposure:
    //   All fields are private and final
	//	 edges , keys and values are mutable types, so operations use defensive copies and
    //   immutable wrappers to avoid sharing the rep's objects to clients
	
	/**
	 * In order to find if the list has a specified edge, 
	 * the source and target of the edge must be equal to 
	 * the parameters passed in.
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
	 * Get the amount of edges
	 * @return label of edges' size
	 */
	public int size() {
		return edges.size();
	}
	
	/**
	 * Create a edge in the map
	 * @param source label of the edge's source
	 * @param target label of the edge's target
	 * @param weight label of the edge's weight
	 */
	public void put(K source, K target, float weight) { // 构建一条边
		int index = findEdge(source, target);

		Edge<K> edge = new Edge<K>(source, target, weight);

		if (index < 0) { // 没找到该边
			edges.add(edge);
			keys.add(source);
			values.add(target);
		}
	}

	/**
	 * Remove a edge from this map
	 * @param source label of the edge's source
	 * @param target label of the edge's target
	 */
	public void remove(K source, K target) {
		int index = findEdge(source, target);

		if (index >= 0) {
			edges.remove(index);

			keys.remove(source); // 事实上点没什么用
			values.remove(target);
		}
	}

	/**
	 * Get all the objects connected to a specific object
	 * @param obj label of the specified object
	 * @return list of the adjacent objects
	 */
	public ArrayList<K> getAdjacentObjects(K obj) {
		ArrayList<K> adjcentObject = new ArrayList<K>();

		for (Edge<K> edge : edges) {
			if (edge.getSource() == obj) {
				adjcentObject.add(edge.getTarget());
			}
			if (edge.getTarget() == obj) {
				adjcentObject.add(edge.getSource());
			}
		}

		return adjcentObject;
	}
	
	/**
	 * Obtain the number of objects that reach a certain intimacy 
	 * with a specific object
	 * @param obj label of the specified object
	 * @param intimacy label of the edge's intimacy
	 * @return
	 */
	public int getNumOfClosers(K obj , double intimacy) {
		int counter = 0;
		for (Edge<K> edge : edges) {
			if ((edge.getSource() == obj||edge.getTarget() == obj) &&edge.getWeight() >= intimacy) {
				counter++;
			}
		}
		return counter;
	}
	
}

class Edge<K> {
	private final K source;
	private final K target;
	private final float weight;

	// Abstraction function:
    //   represents an edge connecting from source to target with weight
    // Representation invariant:
    //   source is a non-null K
    //   target is a non-null K
    //   K must be immutable
    //   weight > 0
    // Safety from rep exposure:
    //   All fields are private and final
    //   source and target are of type K, required to be immutable
    //   int is a primitive type so guaranteed immutable
	
	/**Constructor*/
	public Edge(K source, K target, float weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	/** Returns this Edge's source*/   
	public K getSource() {
		return source;
	}

	/**Returns this Edge's target*/
	public K getTarget() {
		return target;
	}

	/**Returns this Edge's weight*/
	public float getWeight() {
		return weight;
	}
}
