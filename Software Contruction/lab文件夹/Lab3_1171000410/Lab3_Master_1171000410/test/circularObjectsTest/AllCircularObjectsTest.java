package circularObjectsTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import circularOrbit.AtomStructure;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.SocialNetworkCircle;
import circularOrbit.StellarSystem;
import track.Track;

class AllCircularObjectsTest {

	@Test
	void testConcreteCircularOrbit() {
		CircularOrbit<String, String> c = new ConcreteCircularOrbit<String, String>();
		assertEquals("ConcreteCircularOrbit", c.getSystemName());

		String cet = new String("center");
		String obj1 = new String("obj1");
		String obj2 = new String("obj2");
		String obj3 = new String("obj3");

		c.readFile(null);
		c.addCenterObject(cet);
		assertEquals("center", c.getCentralObject());

		Track t = new Track(1);

		c.addTrack(t);
		assertEquals(1, c.getTracks().size());

		c.addTrackObject(t, obj1);
		c.addTrackObject(t, obj2);
		c.addTrackObject(t, obj3);
		c.addTrackObjectsRelation(obj1, obj2, 1);
		c.addCenterTrackObjectsRelation(cet, obj1, 1);
		c.findTrack(1);
		c.getT2E();
		c.getTrackObject(t);

		assertEquals(1, c.getE2E().size());
		assertEquals(1, c.getL2E().size());

		c.removeTrackObject(t, obj3);
		c.removeTrack(t);

		assertEquals(0, c.getTracks().size());
	}

	@Test
	void testAtomStrutrue() {
		AtomStructure a = new AtomStructure();
		String fileName = "txt/AtomicStructure.txt";
		File file = new File(fileName);
		a.readFile(file);

		assertEquals("AtomStructure", a.getSystemName());
		assertEquals(5, a.getTracks().size());
		
		Track t1 = a.getTracks().get(0);
		Track t2 = a.getTracks().get(1);
		a.transit(a.getTrackObject(t1).get(0), t2);

	}

	@Test
	void testStellarSystem() {
		StellarSystem s = new StellarSystem();
		String fileName = "txt/StellarSystem.txt";
		File file = new File(fileName);
		s.readFile(file);

		assertEquals("StellarSystem", s.getSystemName());
		assertEquals("Sun", s.getCentralObject().getName());
		assertEquals(8, s.getTracks().size());

		Track t = s.getTracks().get(0);
		s.getSitha(s.getTrackObject(t).get(0));
		s.move(s.getTrackObject(t).get(0), 0);
		assertEquals("Neptune", s.getTrackObject(t).get(0).getName());
	}	

	@Test
	void testSocialNetWork() {
		SocialNetworkCircle s = new SocialNetworkCircle();
		String fileName = "txt/SocialNetWorkCircle.txt";
		File file = new File(fileName);
		s.readFile(file);
		
		assertEquals("SocialNetworkCircle", s.getSystemName());			
	}	
	
}
