package legalityTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import centralObject.CentralUser;
import centralObject.Stellar;
import circularOrbit.CircularOrbit;
import circularOrbit.SocialNetworkCircle;
import circularOrbit.StellarSystem;
import legality.Legal_SocialNetworkCircle;
import legality.Legal_StellarSystem;
import physicalObject.Friend;
import physicalObject.Planet;

class BothLegalityTest {

	@Test
	void testLegal_StellarSystem() {
		CircularOrbit<Stellar, Planet> ste = new StellarSystem();
		String fileName = "txt/StellarSystem.txt";
		File file = new File(fileName);
		ste.readFile(file);
		Legal_StellarSystem myJudge = new Legal_StellarSystem();
		assertEquals(1,myJudge.judgeLegality(ste));
	}
	
	@Test
	void testLegal_SocialNetworkCircle() {
		CircularOrbit<CentralUser, Friend> soc = new SocialNetworkCircle();
		String fileName = "txt/SocialNetworkCircle.txt";
		File file = new File(fileName);
		soc.readFile(file);
		Legal_SocialNetworkCircle myJudge = new Legal_SocialNetworkCircle();
		assertEquals(1,myJudge.judgeLegality(soc));
	}

}
