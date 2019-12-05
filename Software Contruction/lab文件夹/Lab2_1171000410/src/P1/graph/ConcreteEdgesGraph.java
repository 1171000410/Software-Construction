/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Collection;
//import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//import java.util.stream.Collectors;

//import javax.swing.text.StyledEditorKit.BoldAction;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

	private final Set<L> vertices = new HashSet<>();
	private final List<Edge<L>> edges = new ArrayList<>();

	// Abstraction function:
	// 通过对graph中边和点的抽象
	// 边的类中包含了source,target,weight
	// 带方向的边
	// 构成有向图
	//
	// Representation invariant:
	// 不变量在于隐含的数学关系
	// n个点，最多构成n*(n-1)/2条有向边
	//
	// Safety from rep exposure:
	// 在可操作的情况下，所有的变量都定义为private , final
	// 顶点和边是mutable类型, 因此多处使用：防御式拷贝
	// 使用Collections.unmodifiableSet等方法

	// TODO constructor

	// TODO checkRep


	public ConcreteEdgesGraph() {
	}

	/*checkRep 最大边数n*(n-1)/2 ，逆推出最少顶点*/
	private void checkRep() {
//    	int minVertices = 0;
//    	if(edges.size() == 0) {
//    		minVertices = 0;
//    	}else {
//    		minVertices = (int)(Math.ceil(Math.sqrt(2*edges.size() + 0.25) + 0.5));
//    	}
//    	assert vertices.size() >= minVertices;

		int maxEdges = 0;
		if (vertices.size() == 0) {
			maxEdges = 0;
		} else {
			maxEdges = vertices.size() * (vertices.size() - 1) / 2;
		}
		assert edges.size() <= maxEdges;

	}

	@Override
	public boolean add(L vertex) {
		return vertices.add(vertex);
	}

	/**
	 * My method findEdge ，为了找到list是否存在一条指定的边 该边的source和target必须与传入的参数相等
	 * 
	 * @param source which is to be compared with
	 * @param target which is to be compared with
	 * @return index of the edge -1 if the edge is not found
	 */
	private int findEdge(L source, L target) {
		for (int i = 0; i < edges.size(); i++) {
			Edge<L> one = edges.get(i);
			if (one.getSource().equals(source) && one.getTarget().equals(target)) {
				return i; // 找到了返回在list的位置
			}
		}
		return -1; // 没找到返回-1
	}

	@Override
	public int set(L source, L target, int weight) {
		assert weight >= 0;
		int index = findEdge(source, target);
		int pre_weight = 0;
		Edge<L> edge = new Edge<L>(source, target, weight);
		Edge<L> pre_edge;

		if (weight > 0) {
			if (index < 0) { // 没找到该边
				add(source);
				add(target);
				edges.add(edge);
			} else {
				pre_edge = edges.set(index, edge); // 更换元素,并返回以前的元素
				pre_weight = pre_edge.getWeight();
			}
		} else if (weight == 0 && index >= 0) { // 删除
			pre_edge = edges.remove(index);
			pre_weight = pre_edge.getWeight();
		}
		checkRep();
		return pre_weight;

	}

	@Override
	public boolean remove(L vertex) {
		if (!vertices().contains(vertex)) {
			return false;
		}
		for (int i = 0; i < edges.size(); i++) { // 删除与该点相关的边
			if (edges.get(i).getSource().equals(vertex) || edges.get(i).getTarget().equals(vertex)) {
				edges.remove(edges.get(i));
			}
		}
		vertices.remove(vertex); // 删除该点
		checkRep();
		return true;
	}

	@Override
	public Set<L> vertices() {
		return Collections.unmodifiableSet(vertices); // immutable type
	}

	@Override
	public Map<L, Integer> sources(L target) {
		assert target != null;
		Map<L, Integer> map = new HashMap<L, Integer>();
		for (Edge<L> edge : edges) {
			if (edge.getTarget().equals(target)) {
				map.put(edge.getSource(), edge.getWeight());
			}
		}
		return map;
//		return edges.stream()
//				.filter(edge -> edge.getTarget().equals(target))
//				.collect(Collectors.toMap(Edge::getSource, Edge::getWeight));
	}

	@Override
	public Map<L, Integer> targets(L source) {
		assert source != null;
		Map<L, Integer> map = new HashMap<L, Integer>();
		for (Edge<L> edge : edges) {
			if (edge.getSource().equals(source)) {
				map.put(edge.getTarget(), edge.getWeight());
			}
		}
		return map;
//		return edges.stream()
//		.filter(edge -> edge.getSource().equals(source))
//		.collect(Collectors.toMap(Edge::getTarget, Edge::getWeight));
	}

	// TODO toString()

	@Override
	public String toString() {
		if (edges.isEmpty()) {
			return "This is An Empty Graph";
		}
		String stringGraph = "";
		for (int i = 0; i < edges.size(); i++) {
			stringGraph = stringGraph.concat(edges.get(i).toString() + "\n");
		}
		return stringGraph;
	}

}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge <L>{

	// TODO fields
	private L source;
	private L target;
	private int weight;

	// Abstraction function:
	// 设置了边有向边必备的参数
	// source , target , 和权值weight
	//
	// Representation invariant:
	// source和target不能是null
	// weight >= 0
	//
	// Safety from rep exposure:
	// 尽量使用private和final来定义内部属性
	// 使用immutable数据类型
	//

	// TODO constructor
	public Edge(L source, L target, int weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
		checkRep();
	}

	// TODO checkRep
	public void checkRep() {
		assert source != null;
		assert target != null;
		assert weight >= 0;
	}
	// TODO methods

	// TODO toString()

	public L getSource() {
		return source;
	}

	public L getTarget() {
		return target;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() { // 覆盖超类的toString方法
		String s = this.getSource() + "->" + this.getTarget() + ":" + this.getWeight();
		return s;
	}

	/**
	 * 判断两条边是否相等 先判断是否是同一对象(在此方法不需要) 
	 * 之后再判断一个对象是否是另一个对象的实例 
	 * 如果是再判断source , target ,
	 * weight等是否相等
	 * 
	 * @param that edge to be compared
	 * @return true if they are the same edge ,else false
	 */
	@Override
	public boolean equals(Object that) { // 覆盖超类的equals方法
		if (that instanceof Edge) {
			if (getSource().equals(((Edge<?>) that).getSource()) && getTarget().equals(((Edge<?>) that).getTarget())
					&& getWeight() == (((Edge<?>) that).getWeight())) {
				return true;
			}
		}
		checkRep();
		return false;
	}

	/**
	 * 复写hashCode方法。
	 * 
	 * @return int hash address
	 */
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + getSource().hashCode();
		h = 31 * h + getTarget().hashCode();
		h = 31 * h + getWeight();
		return h;
	}

}
