/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

//import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //
    //  测试toString()方法：
    //  当Graph为空的时候
	//  当Graph不为空的时候
    //  匹配string，判断是否相等
        
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void testToString() {
    	Graph<String> graph = emptyInstance();
    	
    	/*测试空Graph*/
    	assertEquals("Expected an empty graph", "This is An Empty Graph",graph.toString());
    	
    	final String vertex1 = "v1";
		final String vertex2 = "v2";
		final String vertex3 = "v3";
		final int weight = 1;
		graph.set(vertex1, vertex2, weight);
		graph.set(vertex2, vertex3, weight);
		assertEquals("Expected string of graph", "v1->v2:1"+"\n"+"v2->v3:1"+"\n",graph.toString());    	
    }
       
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    // 测试getString()方法：
    // 设置特定点和其字符串
    // 判断该点获取到的字符串和以上字符串是否相等
    //
    // 测试getOneSources()方法：
    // 设置特定点
    // 点的source为空
    // 点的source不为空：sources个数，sources字符
	// 判断是否相等
	//    
	// 测试getOneTargets()方法：
    // 设置特定点
    // 点的target为空
    // 点的target不为空：targets个数，targets字符
	// 判断是否相等   
	//    
	// 测试setSource()方法：
	// 分开测试weight的情况 : 0 , > 0
	// weight为0检查source是否被删除
	// weight > 0 , 检查边是否被添加或更新
	// 若更新则边的权值会变化
	// 包括previous weight的测试
	//    
	// 测试setTarget()方法：
	// 分开测试weight的情况 : 0 , > 0
	// weight为0检查target是否被删除
	// weight > 0 , 检查边是否被添加或更新
	// 若更新则边的权值会变化
	// 包括previous weight的测试   
	//    
    // 测试Vertex中toStiring()方法
    // 设置source,target,weight
    // 判断输出字符串与指定字符串是否相等
    //  
    // TODO tests for operations of Vertex
    @Test 
    public void testGetString() {
    	Vertex<String> vertex = new Vertex<>("v1");
  
    	assertEquals("Expected source of an edge", "v1" , vertex.getString());
    }
    
    @Test
    public void testGetOneSources() {
    	final Vertex<String> vertex1 = new Vertex<>("v1");
    	final Vertex<String> vertex2 = new Vertex<>("v2");
    	final Vertex<String> vertex3 = new Vertex<>("v3");
    	final int weight = 1;
    	final int num = 2;
    	
    	Map<String, Integer> sources = new HashMap<String, Integer>();
    	
    	/*测试空sources*/
    	assertEquals("Expected empty source",Collections.emptyMap(),sources);
    	
    	vertex1.setSource(vertex2.getString(), weight);
    	vertex1.setSource(vertex3.getString(), weight);
    	
    	/*sources中包含的点及其个数相等*/
    	assertTrue("Excepted contained sources",vertex1.getOneSources().containsKey(vertex2.getString()));
    	assertTrue("Excepted contained sources",vertex1.getOneSources().containsKey(vertex3.getString()));
    	assertEquals("Excepted number of sources", num , vertex1.getOneSources().keySet().size());
    }
    
    @Test
    public void testGetOneTargets() {
    	final Vertex<String> vertex1 = new Vertex<>("v1");
    	final Vertex<String> vertex2 = new Vertex<>("v2");
    	final Vertex<String> vertex3 = new Vertex<>("v3");
    	final int weight = 1;
    	final int num = 2;
    	
    	Map<String, Integer> targets = new HashMap<String, Integer>();
    	
    	/*测试空sources*/
    	assertEquals("Excepted empty source",Collections.emptyMap(),targets);
    	
    	vertex1.setTarget(vertex2.getString(), weight);
    	vertex1.setTarget(vertex3.getString(), weight);
    	
    	/*targets中包含的点及其个数相等*/
    	assertTrue("Expected contained targets",vertex1.getOneTargets().containsKey(vertex2.getString()));
    	assertTrue("Expected contained targets",vertex1.getOneTargets().containsKey(vertex3.getString()));
    	assertEquals("Expected number of targets", num , vertex1.getOneTargets().keySet().size());
    }
    
    @Test
    public void testSetSource() {
    	final Vertex<String> vertex1 = new Vertex<>("v1");
    	final Vertex<String> vertex2 = new Vertex<>("v2");
    	final Vertex<String> vertex3 = new Vertex<>("v3");
//    	final Vertex vertex4 = new Vertex("v4");
    	
    	final int weight = 1;
    	final int weight2 = 2;
    	final int weight0 = 0;
    	final int num = 2;
    	final int num2 = 1;
    	
    	assertNotEquals("Expected not a null string", null,vertex1.getString());
    	assertNotEquals("Expected not the same string", vertex2.getString(),vertex1.getString());
    	
    	/*add*/
    	final int pre_weight = vertex1.setSource(vertex2.getString(), weight);
    	assertEquals("Expected no previous weight", weight0, pre_weight);
    	
    	vertex1.setSource(vertex3.getString(), weight);
//    	vertex4.setSource(vertex3.getString(), weight);
	
//    	assertEquals("Expected correctly previous weight", weight, pre_weight);
    	assertEquals("Expected number of sources", num , vertex1.getOneSources().keySet().size());
    	assertTrue("Excepted contained sources",vertex1.getOneSources().containsKey(vertex2.getString()));
    	assertTrue("Excepted contained sources",vertex1.getOneSources().containsKey(vertex3.getString()));
    	
    	/*update*/
    	vertex1.setSource(vertex2.getString(), weight2);
    	assertEquals("Expected an updated weight",(Integer)weight2,vertex1.getOneSources().get(vertex2.getString()));
    	
    	/*remove*/
    	vertex1.setSource(vertex2.getString(),weight0);
    	assertEquals("Expected a decrese of quantity", num2,vertex1.getOneSources().keySet().size());
    	assertFalse("Expected removement of a source",vertex1.getOneSources().containsKey(vertex2.getString()));	
    }
    
    @Test
    public void testSetTarget() {
    	final Vertex<String> vertex1 = new Vertex<>("v1");
    	final Vertex<String> vertex2 = new Vertex<>("v2");
    	final Vertex<String> vertex3 = new Vertex<>("v3");
    	final int weight = 1;
    	final int weight2 = 2;
    	final int weight0 = 0;
    	final int num = 2;
    	final int num2 = 1;
    	
    	assertNotEquals("Expected not a null string", null,vertex1.getString());
    	assertNotEquals("Expected not the same string", vertex2.getString(),vertex1.getString());
    	
    	/*add并且测试pre_weight*/
    	final int pre_weight = vertex1.setTarget(vertex2.getString(), weight);
    	assertEquals("Expected no previous weight", weight0, pre_weight);
    	
    	vertex1.setTarget(vertex3.getString(), weight);
//    	assertEquals("Expected correctly previous weight", weight, pre_weight);
    	
    	assertEquals("Expected number of sources", num , vertex1.getOneTargets().keySet().size());
    	assertTrue("Excepted contained sources",vertex1.getOneTargets().containsKey(vertex2.getString()));
    	assertTrue("Excepted contained sources",vertex1.getOneTargets().containsKey(vertex3.getString()));
    	
    	/*update*/
    	vertex1.setTarget(vertex2.getString(), weight2);
    	assertEquals("Expected an updated weight",(Integer)weight2,vertex1.getOneTargets().get(vertex2.getString()));
    	
    	/*remove*/
    	vertex1.setTarget(vertex2.getString(),weight0);
    	assertEquals("Expected a decrese of quantity", num2,vertex1.getOneTargets().keySet().size());
    	assertFalse("Expected removement of a source",vertex1.getOneTargets().containsKey(vertex2.getString()));	
    }
    
    @Test
    public void testVertexToString() {
    	final Vertex<String> vertex1 = new Vertex<>("v1");
    	final Vertex<String> vertex2 = new Vertex<>("v2");
    	final int weight = 1;
    	
    	vertex1.setTarget(vertex2.getString(), weight);
    	assertEquals("Expected string of an edge", "v1->v2:1",vertex1.toString());
    }
    
    
    
}
