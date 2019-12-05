package circularobjectstest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.ConcreteCircularOrbit;
import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import fileinput.AllBufferedReader;
import fileinput.ReadStrategy;

import java.io.File;

import org.junit.jupiter.api.Test;

import track.Track;

class AllCircularObjectsTest {

  // Testing strategy 
  // Develop a test based on the corresponding function of the implementation
  //
  // Test for ConCreteCircularOrbit
  //   Partition for getter methods
  //     empty , mutiple elements
  //     Partition for add and remove methods
  //     empty , mutiple elements
  //     output: if methods returns true, graph is modified
  //             ie number of vertices increses or decreases by 1
  //             else graph unmodified
  //        Partition for add relation methods
  //     empty , multiple relation
  //        output: if the relation is not null
  //             the structure of the relationship map will change
  //             objects' tracks will change
  //  Test for WithPosition
  //    Move object from the current position to the position 
  //  Test for WithOutPosition
  //    Migrate object from the current track to the track t
  //  Test for specific applications' readFile methods
  //        Try empty or wrong input file
  //    Use Correct file and confirm the correctness of the read file

  @Test
  void testConcreteCircularOrbit() {
    CircularOrbit<String, String> c = new ConcreteCircularOrbit<String, String>();
    assertEquals("ConcreteCircularOrbit", c.getSystemName());

    String cet = new String("center");
    final String obj1 = new String("obj1");
    final String obj2 = new String("obj2");
    final String obj3 = new String("obj3");

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

    c.findTrack(1);
  }

  @Test
  void testAtomStrutrue() {
    AtomStructure a = new AtomStructure();
    String fileName = "txt/AtomicStructure.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    a.readFile(r.readFile(file));

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
    ReadStrategy r = new AllBufferedReader();
    s.readFile(r.readFile(file));

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
    ReadStrategy r = new AllBufferedReader();
    s.readFile(r.readFile(file));

    assertEquals("SocialNetworkCircle", s.getSystemName());

    s.removeCenterTrackObjectsRelation(s.getCentralObject(), 
        s.getTrackObject(s.getTracks().get(0)).get(0));
    s.removeTrackObjectsRelation(s.getTrackObject(s.getTracks().get(0)).get(0),
        s.getTrackObject(s.getTracks().get(1)).get(0));
  }

}
