package centralObjectTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import centralObject.CentralObject;
import centralObject.CentralUser;
import centralObject.Nucleus;
import centralObject.Stellar;

class AllCentralObjectsTest {

	@Test
	void testCentralObject() {
		CentralObject c = new CentralObject("a");
		assertEquals("a", c.getName());
	}
	
	@Test
	void testCentralUser() {
		CentralUser c = new CentralUser("a", 15, "F");
		assertEquals("a", c.getName());
		assertEquals(15, c.getAge());
		assertEquals("F", c.getGender());
	}
	
	@Test
	void testNucleus() {
		Nucleus c = new Nucleus("a",1,1);
		assertEquals("a", c.getName());
	}
	
	@Test
	void testStellar() {
		Stellar c = new Stellar("a", 15, 15);
		assertEquals("a", c.getName());
		assertEquals(15, c.getStellarQuality());
		assertEquals(15, c.getStellarRadius());
	}


}
