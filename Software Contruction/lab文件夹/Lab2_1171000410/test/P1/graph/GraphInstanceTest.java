/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;

//import javax.swing.text.StyledEditorKit.BoldAction;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
	
	// 使用抽象类
	// 针对两个测试类
	// 此test与具体实现无关
	//	
	// Testing strategy
	//
	// 测试Graph.add()方法：
	// 如果已经存在该顶点，修改graph，顶点数加1
	// 否则图不变
	// 通过顶点个数的变化来测试
	//
	// 测试Graph.remove()方法：
	// 如果不存在该点，图不变
	// 否则删除该点
	// 相邻的边删除
	// 通过顶点个数变化来测试
	//
	// 测试Graph.set()方法：
	// 分开测试weight的情况 : 0 , > 0
	// weight为0检查边是否被删除
	// weight > 0 , 检查边是否被添加或更新
	// 观察source , target 的映射关系变化
	//
	// 测试Graph.vertices()方法：
	// graph为空
	// graph添加顶点后
	// 观察顶点的数量
	//	
	// 测试Graph.sources()方法
	// graph为空
	// set点和边
	// 观察source数量  , 对应关系是否正确
	//	
	// 测试Graph.targets()方法
	// graph为空
	// set点和边 
	// 观察targets数量  , 对应关系是否正确
	//	

	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		// TODO you may use, change, or remove this test
		assertEquals("Expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	// TODO other tests for instance methods of Graph

	@Test
	public void testAdd() {
		Graph<String> graph = emptyInstance();

		final String vertex1 = "v1";
		final String vertex2 = "v2";

		final int sum0 = graph.vertices().size();
		final boolean judge1 = graph.add(vertex1);
		assertTrue("Expected one addition of vertex", judge1);
		final int sum1 = graph.vertices().size();
		assertEquals("Expected an increase in quantity", sum0 + 1, sum1);

		final boolean judge2 = graph.add(vertex2);
		assertTrue("Expected one addition of vertex", judge2);
		final int sum2 = graph.vertices().size();
		assertEquals("Expected an increase in quantity", sum1 + 1, sum2);

		/* vertex已经在set中 */
		final boolean judge3 = graph.add(vertex1);
		assertFalse("Expected a repetitive addition of vertex", judge3);
		final int sum3 = graph.vertices().size();
		assertEquals("Expected no change of quantity", sum2, sum3);

	}
	
	@Test
	public void testSet() {
		Graph<String> graph = emptyInstance();

		final String vertex1 = "v1";
		final String vertex2 = "v2";
		final String vertex3 = "v3";
		final String vertex4 = "v4";
		
		final String vertex5 = "v5";
		final int weight0 = 0;
		final int weight1 = 1;
		final int weight2 = 2;
		
		final int pre_weight = graph.set(vertex1, vertex4, weight1);
		final boolean b = weight1 >= 0;
		assertTrue("Expected a positive weight",b);
		assertEquals("Expected a zero weight",0 ,weight0 );
		assertEquals("Expected no previous weight", 0, pre_weight);
		/* weight > 0 , 不存在边则添加 */
		graph.set(vertex2, vertex4, weight1);
		graph.set(vertex3, vertex4, weight1);
		graph.set(vertex4, vertex5, weight1);
		assertTrue("Expected an addition of edge", graph.sources(vertex4).containsKey(vertex2));
		assertTrue("Expected an addition of edge", graph.sources(vertex4).containsKey(vertex3));
		assertFalse("Excepted not a source",graph.sources(vertex4).containsKey(vertex5));
		
		/* weight > 0 , 存在边则更新权值 */
		graph.set(vertex1, vertex4, weight2);
		assertEquals("Expected an update og weight", graph.sources(vertex4).get(vertex1)
				, (Integer)weight2);
		
		/*weight = 0 , 存在边则将其删除*/
		graph.set(vertex2, vertex4, 0);
		assertFalse("Expected a remove of edge",graph.sources(vertex4).containsKey(vertex2));
	}
	
	@Test
	public void testRemove() {
		Graph<String> graph = emptyInstance();

		final String vertex1 = "v1";
		final String vertex2 = "v2";
		final String vertex3 = "v3";
		final String vertex4 = "v4";
		final int weight = 1;

		graph.add(vertex1);
		graph.add(vertex2);

		/* 删除不存在的点 */
		final boolean judge1 = graph.remove(vertex3);
		assertFalse("Expected no such vertex", judge1);

		/* 删除一个存在的点 */
		final int sum0 = graph.vertices().size();
		final boolean judge2 = graph.remove(vertex1);
		final int sum1 = graph.vertices().size();
		assertTrue("Expected a successful remove", judge2);
		assertEquals("Expected a decrese of quantity", sum0 - 1, sum1);

		/* 设置边 */
		graph.set(vertex1, vertex4, weight);
		graph.set(vertex2, vertex4, weight);
		graph.set(vertex3, vertex1, weight);

		final int sum2 = graph.vertices().size();
		final boolean judge3 = graph.remove(vertex3);
		final boolean judge4 = graph.remove(vertex4);
		assertTrue("Expected a successful remove", judge3);
		assertTrue("Expected a successful remove", judge4);
		assertFalse("Expected remove of vertex", graph.vertices().contains(vertex3));
		assertFalse("Expected remove of vertex", graph.vertices().contains(vertex4));
		
		final int sum3 = graph.vertices().size();
		assertEquals("Expected a decrese of quantity", sum2 - 2, sum3);
	}

	
	@Test
	public void testVertices() {
		Graph<String> graph = emptyInstance();

		final String vertex1 = "v1";
		final String vertex2 = "v2";
		final String vertex3 = "v3";
		final String vertex4 = "v4";
		
		graph.add(vertex1);
		graph.add(vertex2);
		graph.add(vertex3);
		graph.add(vertex4);
		
		final int num = 4;
		assertNotEquals("Expected not an empty set",Collections.emptySet(),graph.vertices());
		assertEquals("Expected number of vertexs", num , graph.vertices().size());
	}
	
	@Test
	public void testSources() {
		Graph<String> graph = emptyInstance();

		final String vertex1 = "v1";
		final String vertex2 = "v2";
		final String vertex3 = "v3";
		final String vertex4 = "v4";
		final int weight = 1;

		/*empty graph*/
		assertEquals("Expected empty graph", Collections.emptyMap(),graph.sources(vertex3));
		
		graph.add(vertex1);
		graph.add(vertex2);
		graph.add(vertex3);
		
		/*没有对应的source*/
		assertEquals("Expected empty graph", Collections.emptyMap(),graph.sources(vertex3));
		
		graph.set(vertex1, vertex3, weight);
		graph.set(vertex2, vertex3, weight);
		graph.set(vertex3, vertex4, weight);
		
		/*测试sources的数量和string是否正确*/
		final int num = 2;
		assertEquals("Expected num of sources", num , graph.sources(vertex3).size());
		assertTrue("Expected inclusion of source",graph.sources(vertex3).containsKey(vertex1));
		assertTrue("Expected inclusion of source",graph.sources(vertex3).containsKey(vertex2));
		assertTrue("Expected inclusion of source",graph.sources(vertex4).containsKey(vertex3));
	}
	
	@Test
	public void testTargets() {
		Graph<String> graph = emptyInstance();

		final String vertex1 = "v1";
		final String vertex2 = "v2";
		final String vertex3 = "v3";
		final String vertex4 = "v4";
		final int weight = 1;
		
		/*empty graph*/
		assertEquals("Expected empty graph", Collections.emptyMap(),graph.targets(vertex3));
		
		graph.add(vertex1);
		graph.add(vertex2);
		graph.add(vertex3);
		graph.add(vertex4);
		
		/*没有对应的source*/
		assertEquals("Expected empty graph", Collections.emptyMap(),graph.sources(vertex3));
		
		graph.set(vertex3, vertex1, weight);
		graph.set(vertex3, vertex2, weight);
		graph.set(vertex4, vertex3, weight);
		
		/*测试sources的数量和string是否正确*/
		final int num = 2;
		assertEquals("Expected num of sources", num , graph.targets(vertex3).size());
		assertTrue("Expected inclusion of source",graph.targets(vertex3).containsKey(vertex1));
		assertTrue("Expected inclusion of source",graph.targets(vertex3).containsKey(vertex2));
		assertTrue("Expected inclusion of source",graph.targets(vertex4).containsKey(vertex3));
	}

}
