package apistest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import apis.CircularOrbitapis;
import centralobject.CentralUser;
import centralobject.Nucleus;
import centralobject.Stellar;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import fileinput.AllBufferedReader;
import fileinput.ReadStrategy;

import java.io.File;

import org.junit.jupiter.api.Test;
import physicalobject.Electron;
import physicalobject.Friend;
import physicalobject.Planet;

class Testapis {

  // Testing strategy 
  // 测试 getObjectDistributionEntropy
  // 轨道上有物体
  // 轨道为空
  //
  // 测试getLogicalDistance
  // 同一点的逻辑距离
  // 不同点的逻辑距离
  //
  // 测试getPhysicalDistance
  // 不同轨道上的行星
  // 返回物理距离
  //
  // 测试getDifference
  // 输入空轨道系统
  // 不空的轨道系统、
  // 比较输出结果

  @Test
  void testEntropy() {
    AtomStructure a = new AtomStructure();
    String fileName = "txt/AtomicStructure.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    a.readFile(r.readFile(file));

    CircularOrbitapis<Nucleus, Electron> c1 = new CircularOrbitapis<Nucleus, Electron>();
    double entropy = c1.getObjectDistributionEntropy(a);
    assertEquals(1.2681057323704885, entropy);
  }

  @Test
  void testLogicalDistance() {
    CircularOrbit<CentralUser, Friend> soc = new SocialNetworkCircle();
    String fileName = "txt/SocialNetworkCircle.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    soc.readFile(r.readFile(file));

    CircularOrbitapis<CentralUser, Friend> c = new CircularOrbitapis<CentralUser, Friend>();
    Friend f1 = soc.getT2E().get(0).get(0);
    Friend f2 = soc.getT2E().get(1).get(0);
    Friend f3 = soc.getT2E().get(0).get(1);

    int logicalDistance = c.getLogicalDistance(soc, f1, f2);
    int logicalDistance2 = c.getLogicalDistance(soc, f1, f3);
    int logicalDistance3 = c.getLogicalDistance(soc, f1, f1);

    assertEquals(0, logicalDistance3);
    assertEquals(1, logicalDistance);
    assertEquals(2, logicalDistance2);
  }

  @Test
  void testPhysicalDistance() {
    CircularOrbit<Stellar, Planet> ste = new StellarSystem();
    String fileName = "txt/StellarSystem.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    ste.readFile(r.readFile(file));

    CircularOrbitapis<Stellar, Planet> c = new CircularOrbitapis<Stellar, Planet>();
    Planet p1 = ste.getT2E().get(0).get(0);
    Planet p2 = ste.getT2E().get(1).get(0);

    double physicalDistance = c.getPhysicalDistance(ste, p1, p2);
    c.getLogicalDistance(ste, p1, p2);

    assertEquals(1350862.2647980922, physicalDistance);

  }

  @Test
  void testAtomStructureDifference() {
    AtomStructure a = new AtomStructure();
    String fileName = "txt/AtomicStructure.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    a.readFile(r.readFile(file));

    AtomStructure a2 = new AtomStructure();
    String fileName2 = "txt/AtomicStructure2.txt";
    File file2 = new File(fileName2);
    ReadStrategy r2 = new AllBufferedReader();
    a2.readFile(r2.readFile(file2));

    CircularOrbitapis<Nucleus, Electron> c = new CircularOrbitapis<Nucleus, Electron>();
    c.getDifference(a, a2);

  }

  @Test
  void testStellarSystemDifference() {
    CircularOrbit<Stellar, Planet> ste = new StellarSystem();
    String fileName = "txt/StellarSystem.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    ste.readFile(r.readFile(file));

    CircularOrbit<Stellar, Planet> ste2 = new StellarSystem();
    String fileName2 = "txt/StellarSystem2.txt";
    File file2 = new File(fileName2);
    ReadStrategy r2 = new AllBufferedReader();
    ste2.readFile(r2.readFile(file2));

    CircularOrbitapis<Stellar, Planet> c = new CircularOrbitapis<Stellar, Planet>();
    c.getDifference(ste, ste2);
  }

  @Test
  void testSocialNetworkDifference() {
    CircularOrbit<CentralUser, Friend> soc = new SocialNetworkCircle();
    String fileName = "txt/SocialNetworkCircle.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    soc.readFile(r.readFile(file));


    CircularOrbit<CentralUser, Friend> soc2 = new SocialNetworkCircle();
    String fileName2 = "txt/SocialNetworkCircle2.txt";
    File file2 = new File(fileName2);
    ReadStrategy r2 = new AllBufferedReader();
    soc2.readFile(r2.readFile(file2));

    CircularOrbitapis<CentralUser, Friend> c = new CircularOrbitapis<CentralUser, Friend>();
    c.getDifference(soc, soc2);
  }

  @Test
  void testAtomStructureDifference2() {
    AtomStructure a = new AtomStructure();
    String fileName = "txt/AtomicStructure2.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    a.readFile(r.readFile(file));

    AtomStructure a2 = new AtomStructure();
    String fileName2 = "txt/AtomicStructure.txt";
    File file2 = new File(fileName2);
    ReadStrategy r2 = new AllBufferedReader();
    a2.readFile(r2.readFile(file2));

    CircularOrbitapis<Nucleus, Electron> c = new CircularOrbitapis<Nucleus, Electron>();
    c.getDifference(a, a2);

  }

  @Test
  void testStellarSystemDifference2() {
    CircularOrbit<Stellar, Planet> ste = new StellarSystem();
    String fileName = "txt/StellarSystem2.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    ste.readFile(r.readFile(file));

    CircularOrbit<Stellar, Planet> ste2 = new StellarSystem();
    String fileName2 = "txt/StellarSystem.txt";
    File file2 = new File(fileName2);
    ReadStrategy r2 = new AllBufferedReader();
    ste2.readFile(r2.readFile(file2));

    CircularOrbitapis<Stellar, Planet> c = new CircularOrbitapis<Stellar, Planet>();
    c.getDifference(ste, ste2);
  }

}
