/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep. 没有设置真实存在的边，只有点之间的对应关系
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<>();

	// Abstraction function:
	// 将有向加权图描述为多个顶点
	// 点之间的映射关系为边
	// 边有权值weight
	//
	// Representation invariant:
	// 每个顶点只能存在一个实例
	// 因此顶点个数vertices()的大小相等
	//
	// Safety from rep exposure:
	// 变量尽可能定义为private和final
	// 防御式编程
	//
	// TODO constructor

	// TODO checkRep

	public ConcreteVerticesGraph() {
	}

	public void checkRep() {
		assert vertices().size() == vertices.size();
	}

	@Override
	public boolean add(L vertex) {
		if (vertices().contains(vertex)) {
			return false;
		} else {
			Vertex<L> v = new Vertex<>(vertex);
			vertices.add(v);
			return true;
		}
	}

	/**
	 * My method findVertex , 找到对应str的vertex所在的位置
	 * 
	 * @param str string of a vertex
	 * @return index of the vertex in vertices
	 */
	private int findVertex(L str) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getString().equals(str)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int set(L source, L target, int weight) {
		assert weight >= 0;
		assert source != target;
		Vertex<L> vertexOfSource, vertexOfTarget;
		int index;

		if (vertices().contains(source)) {
			index = findVertex(source);
			vertexOfSource = vertices.get(index);
		} else { // 如果string对应的点不在list中，创建新的点并且添加进去
			vertexOfSource = new Vertex<>(source);
			vertices.add(vertexOfSource);
		}

		if (vertices().contains(target)) {
			index = findVertex(target);
			vertexOfTarget = vertices.get(index);
		} else {
			vertexOfTarget = new Vertex<>(target);
			vertices.add(vertexOfTarget);
		}

		final int a1 = vertexOfSource.setTarget(target, weight); 
		final int a2 = vertexOfTarget.setSource(source, weight);
		assert a1 == a2;
		checkRep();
		return a1; // 返回pre_weight
	}

	@Override
	public boolean remove(L vertex) {
		assert vertex != null;
		if (!vertices().contains(vertex)) {
			return false;
		}
		for(Vertex<L> one :vertices) {   //删除点的所有sources和targets
			if(one.getOneSources().containsKey(vertex)) {
				one.getOneSources().remove(vertex);
			}
			if(one.getOneTargets().containsKey(vertex)) {
				one.getOneTargets().remove(vertex);
			}
		}
//		for(Vertex one : vertices) {  //删除没有连接边的点
//			if(one.getOneSources().keySet().isEmpty() 
//					&& one.getOneTargets().keySet().isEmpty()) {
//				vertices().remove(one.getString());   //从set中删除
//				vertices.remove(one);  //从list中删除
//			}
//		}
		final int index = findVertex(vertex);
		vertices.remove(index);  
		return true;
	}

	@Override
	public Set<L> vertices() {
		Set<L> s = new HashSet<L>();
		for (Vertex<L> one : vertices) {
			s.add(one.getString());
		}
		return s;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		assert target != null;
		int index = findVertex(target);
		if (index < 0) {
			return Collections.emptyMap();
		} else {
			return vertices.get(index).getOneSources();
		}
	}

	@Override
	public Map<L, Integer> targets(L source) {
		assert source != null;
		int index = findVertex(source);
		if (index < 0) {
			return Collections.emptyMap();
		} else {
			return vertices.get(index).getOneTargets();
		}
	}

	// TODO toString()
	@Override
	public String toString() {
		if (vertices.isEmpty()) {
			return "This is An Empty Graph";
		}
		String stringGraph = "";
		for (int i = 0; i < vertices.size(); i++) {
			if(vertices.get(i).toString() != "") {
			stringGraph = stringGraph.concat(vertices.get(i).toString()+"\n");
			}
		}
		return stringGraph;
	}

}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */

class Vertex<L> {

	// TODO fields
	private L str;
	private Map<L, Integer> oneSources = new HashMap<L, Integer>();
	private Map<L, Integer> oneTargets = new HashMap<L, Integer>();
	
	// Abstraction function:
	// 顶点类刻画图的关键要素
	// 使用HashMap存取映射关系
	// key为该点的source或target , value为该点的weight
	//
	// Representation invariant:
	// 每个顶点的source或target不能是自身
	// HashMap中的values必须不小于0
	//
	// Safety from rep exposure:
	// 所有fields是private final
	// String是imutable类型
	// 防御性编程

	// TODO constructor

	// TODO checkRep

	// TODO methods

	// TODO toString()

	/*构造函数*/
	public Vertex(L str) {
		this.str = str;
	}

	/* 检查不变量，保证该点不会是自己的source或者target，weight不小于0*/
	public void checkRep() {
		assert !oneSources.keySet().contains(this.str);
		assert !oneTargets.keySet().contains(this.str);
		for(Integer v1 : oneSources.values()) {
			assert v1 >=0;
		}
		for(Integer v2 : oneSources.values()) {
			assert v2 >=0;
		}
		
		
	}

	public L getString() {
		return this.str;
	}

	public Map<L, Integer> getOneSources() {
		return this.oneSources;
	}

	public Map<L, Integer> getOneTargets() {
		return this.oneTargets;
	}

	/**
	 * 设置到该点的source，处理同ConcreteVerticesGraph中set方法
	 * weight = 0 , source存在 ：移除
	 * weight > 0 , source存在 ：更新 
	 * weight > 0 , source不存在 ：添加
	 * 
	 * @param source ,weight of an edge
	 * @return pre_weight of pre_edge
	 */
	public int setSource(L source, int weight) {
		assert source != null;
		assert source != this.str; // 不重合
		assert weight >= 0;

		int pre_weight = 0;

		if (weight == 0 && this.oneSources.keySet().contains(source)) { // remove
			pre_weight = oneSources.remove(source);
		}
		if (weight > 0 && this.oneSources.keySet().contains(source)) { // update
			pre_weight = oneSources.put(source, weight);
		}
		if (weight > 0 && !this.oneSources.keySet().contains(source)) { // add
			oneSources.put(source, weight);
			pre_weight = 0;
		}
		checkRep();
		return pre_weight;
	}

	/**
	 * 设置到该点的target，处理同ConcreteVerticesGraph中set方法
	 * weight = 0 , target存在 ：移除
	 * weight > 0 , target存在 ：更新 
	 * weight > 0 , target不存在 ：添加
	 * 
	 * @param target ,weight of an edge
	 * @return pre_weight of pre_edge
	 */
	public int setTarget(L target, int weight) {
		assert target != null;
		assert target != this.str; // 不重合
		assert weight >= 0;

		int pre_weight = 0;

		if (weight == 0 && this.oneTargets.keySet().contains(target)) { // remove
			pre_weight = oneTargets.remove(target);
		}
		if (weight > 0 && this.oneTargets.keySet().contains(target)) { // update
			pre_weight = oneTargets.put(target, weight);
		}
		if (weight > 0 && !this.oneTargets.keySet().contains(target)) { // add
			oneTargets.put(target, weight);
			pre_weight = 0;
		}
		checkRep();
		return pre_weight;
	}

	@Override
	public String toString() {
		String str = "";
		for (L s : this.oneTargets.keySet()) {
			str = str.concat(this.getString() + "->" + s + ":" + this.oneTargets.get(s));
		}
		return str;
	}
}
