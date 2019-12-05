package APIStest;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import APIS.CircularOrbitAPIS;
import centralObject.CentralUser;
import centralObject.Nucleus;
import centralObject.Stellar;
import circularOrbit.AtomStructure;
import circularOrbit.CircularOrbit;
import circularOrbit.SocialNetworkCircle;
import circularOrbit.StellarSystem;
import physicalObject.Electron;
import physicalObject.Friend;
import physicalObject.Planet;

class TestAPIS {

	@Test
	void testEntropy() {
		AtomStructure a = new AtomStructure();
		String fileName = "txt/AtomicStructure.txt";
		File file = new File(fileName);
		a.readFile(file);
		
		CircularOrbitAPIS<Nucleus, Electron> c1 = new CircularOrbitAPIS<Nucleus, Electron>();
		double entropy = c1.getObjectDistributionEntropy(a);
		assertEquals(1.2681057323704885, entropy);
	}

	@Test
	void testLogicalDistance() {
		CircularOrbit<CentralUser, Friend> soc = new SocialNetworkCircle();
		String fileName = "txt/SocialNetworkCircle.txt";
		File file = new File(fileName);
		soc.readFile(file);
		
		CircularOrbitAPIS<CentralUser, Friend> c = new CircularOrbitAPIS<CentralUser, Friend>();
		Friend f1 = soc.getT2E().get(0).get(0);
		Friend f2 = soc.getT2E().get(1).get(0);
		Friend f3 = soc.getT2E().get(0).get(1);
		
		int logicalDistance = c.getLogicalDistance(soc, f1, f2);
		int logicalDistance2 = c.getLogicalDistance(soc, f1, f3);
		int logicalDistance3 = c.getLogicalDistance(soc, f1, f1);
		
		
		assertEquals(0, logicalDistance3);
		assertEquals(1, logicalDistance);
		assertEquals(-1, logicalDistance2);
	}
	
	@Test
	void testPhysicalDistance() {
		CircularOrbit<Stellar, Planet> ste = new StellarSystem();
		String fileName = "txt/MyStellarSystem.txt";
		File file = new File(fileName);
		ste.readFile(file);
		
		CircularOrbitAPIS<Stellar, Planet> c = new CircularOrbitAPIS<Stellar, Planet>();
		Planet p1 = ste.getT2E().get(0).get(0);
		Planet p2= ste.getT2E().get(1).get(0);
		
		double physicalDistance = c.getPhysicalDistance(ste, p1, p2);
		c.getLogicalDistance(ste, p1, p2);
		assertEquals(1350862.2647980922, physicalDistance);
		
	}
}
