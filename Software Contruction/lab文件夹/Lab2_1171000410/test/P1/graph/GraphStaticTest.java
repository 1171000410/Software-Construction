/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // TODO test other vertex label types in Problem 3.2
    // We try to use Integer to prove that we do indeed support different types of labels.
    @Test
    public void testInteger() {
    	Graph<Integer> graph = new ConcreteEdgesGraph<Integer>();
    	final int vertex1 = 1;
    	final int vertex2 = 2;
    	final int vertex3 = 3;
    	final int vertex4 = 4;

		final int weight1 = 1;

    	final int sum0 = graph.vertices().size();
		final boolean judge1 = graph.add(vertex1);
		assertTrue("Expected one addition of vertex", judge1);
		final int sum1 = graph.vertices().size();
		assertEquals("Expected an increase in quantity", sum0 + 1, sum1);
		
		final int pre_weight = graph.set(vertex2, vertex4, weight1);
		assertEquals("Excepted no previous edge", 0 ,pre_weight);
		graph.set(vertex3, vertex4, weight1);
		assertTrue("Expected an addition of edge", graph.sources(vertex4).containsKey(vertex2));
		assertTrue("Expected an addition of edge", graph.sources(vertex4).containsKey(vertex3));
		assertTrue("Expected an addition of edge", graph.targets(vertex3).containsKey(vertex4));
		
		final int sum2 = graph.vertices().size();
		final boolean judge2 = graph.remove(vertex1);
		final int sum3 = graph.vertices().size();
		assertTrue("Expected a successful remove", judge2);
		assertEquals("Expected a decrese of quantity", sum2 - 1, sum3);
		
    }

}
