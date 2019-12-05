package legalitytest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import centralobject.CentralUser;
import centralobject.Stellar;
import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import fileinput.AllBufferedReader;
import fileinput.ReadStrategy;

import java.io.File;
import legality.LegalSocialNetworkCircle;
import legality.LegalStellarSystem;

import org.junit.jupiter.api.Test;

import physicalobject.Friend;
import physicalobject.Planet;

class BothLegalityTest {

  // Testing strategy
  // Enter the correct situation
  // Input illegal situation
  // compare output is expected to be consistent

  @Test
  void testLegal_StellarSystem() {
    CircularOrbit<Stellar, Planet> ste = new StellarSystem();
    String fileName = "txt/StellarSystem.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    ste.readFile(r.readFile(file));
    LegalStellarSystem myJudge = new LegalStellarSystem();
    assertEquals(1, myJudge.judgeLegality(ste));
  }

  @Test
  void testLegal_SocialNetworkCircle() {
    CircularOrbit<CentralUser, Friend> soc = new SocialNetworkCircle();
    String fileName = "txt/SocialNetworkCircle.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    soc.readFile(r.readFile(file));
    LegalSocialNetworkCircle myJudge = new LegalSocialNetworkCircle();
    assertEquals(1, myJudge.judgeLegality(soc));
  }

  @Test
  void testIlleagal_StellarSystem() {
    CircularOrbit<Stellar, Planet> ste = new StellarSystem();
    String fileName = "txt/StellarSystem.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    ste.readFile(r.readFile(file));
    ste.addCenterObject(null);
    ste.addTrackObject(ste.getTracks().get(0), new Planet("1", "1", "1", 1, 2, 1, "CW", 1));
    ste.removeTrackObject(ste.getTracks().get(1), ste.getT2E().get(1).get(0));
    LegalStellarSystem myJudge = new LegalStellarSystem();
    assertEquals(0, myJudge.judgeLegality(ste));
    ste.addTrackObject(ste.getTracks().get(1), new Planet("1", "1", "1", 1E24, 2E24, 1, "CW", 1));
    assertEquals(0, myJudge.judgeLegality(ste));
  }

  @Test
  void testIlleagal_SocialNetworkCircle() {
    CircularOrbit<CentralUser, Friend> soc = new SocialNetworkCircle();
    String fileName = "txt/SocialNetworkCircle.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    soc.readFile(r.readFile(file));
    soc.addTrackObject(soc.getTracks().get(1), new Friend("1", 1, "F"));
    LegalSocialNetworkCircle myJudge = new LegalSocialNetworkCircle();
    assertEquals(0, myJudge.judgeLegality(soc));

    //  soc.addTrack(new Track(1));
    //  Friend f = new Friend("1", 1, "F");
    //  soc.addTrackObject(soc.getTracks().get(2), f);
    //  soc.addTrackObjectsRelation(soc.getT2E().get(0).get(0), f, 1);
    //  assertEquals(0,myJudge.judgeLegality(soc));
  }

}
