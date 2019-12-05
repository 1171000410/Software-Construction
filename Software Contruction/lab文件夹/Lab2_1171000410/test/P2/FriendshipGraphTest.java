package P2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import P2.FriendshipGraph;
import P2.Person;

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
		
        assertTrue("expected inclusion relationship", graph.vertices().contains(jack));
        assertTrue("expected inclusion relationship", graph.vertices().contains(paul));
        assertTrue("expected inclusion relationship", graph.vertices().contains(trump));
        
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
		
        assertTrue("expected connection", graph.targets(rachel).containsKey(ross));
        assertTrue("expected connection", graph.targets(ross).containsKey(rachel));
        assertTrue("expected connection", graph.targets(ross).containsKey(ben));
        assertTrue("expected connection", graph.targets(ben).containsKey(ross));
        assertFalse("expected unconnection", graph.targets(rachel).containsKey(kramer));
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
	
	@Test
	public void testPerson() {
		final Person rachel2 = new Person("Rachel"); 
		assertEquals("Expected proper name","Rachel",rachel.getName());
		assertTrue("Expected the same name",rachel.nameSameWith(rachel2.getName()));
		assertFalse("Expected not the same name",rachel.nameSameWith(ross.getName()));
	}
	
}
