package P3;

import static org.junit.Assert.*;

import org.junit.Test;

import P3.FriendshipGraph;

public class FriendshipGraphTest {
	private static final FriendshipGraph graph = new FriendshipGraph();
	private static final Person rachel = new Person("Rachel");
	private static final Person ross = new Person("Ross");
	private static final Person ben = new Person("Ben");
	private static final Person kramer = new Person("Kramer");
	private static final Person jack = new Person("Jack");
	private static final Person paul = new Person("Paul");
	private static final Person trump = new Person("Trump");
	
	/**
	 * Tests that assertions are enabled.
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	/**
	 * Tests addVertex.
	 */
	@Test
    public void testaddVertex() {
		graph.addVertex(jack);
		graph.addVertex(paul);
		graph.addVertex(trump);
		
        assertTrue("expected inclusion relationship", graph.map.containsKey(jack));
        assertTrue("expected inclusion relationship", graph.map.containsKey(paul));
        assertTrue("expected inclusion relationship", graph.map.containsKey(trump));
        
    }
	
	
	/**
	 * Tests addEdge.
	 */
	@Test
    public void testaddEdge() {
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		
        assertTrue("expected connection", graph.map.get(rachel).contains(ross));
        assertTrue("expected connection", graph.map.get(ross).contains(rachel));
        assertTrue("expected connection", graph.map.get(ross).contains(ben));
        assertTrue("expected connection", graph.map.get(ben).contains(ross));
        assertFalse("expected unconnection", graph.map.get(rachel).contains(kramer));
    }
	
	/**
	 * Tests getDistance.
	 */
	@Test
	public void getDistanceTest() {
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		
		assertEquals(1, graph.getDistance(rachel, ross));
		assertEquals(2, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(-1, graph.getDistance(rachel, kramer));
	}
}
