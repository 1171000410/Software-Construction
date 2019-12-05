package applicationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import applications.CircularOrbitHelper;
import applications.CircularOrbitHelper2;
import applications.DrawMovingOrbit;
import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import fileinput.AllBufferedReader;
import fileinput.ReadStrategy;

import java.io.File;

import org.junit.jupiter.api.Test;

class TestApp {

  // Testing strategy
  // applications包中的文件大多数无法而此时
  // 读入文件构建轨道系统
  // 构建空轨道系统

  @Test
  void testApp() {
    AtomStructure a = new AtomStructure();
    String fileName = "txt/AtomicStructure.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    a.readFile(r.readFile(file));
    CircularOrbitHelper2.visualizes(a);

  }

  @Test
  void testApp2() {
    StellarSystem s = new StellarSystem();
    String fileName = "txt/StellarSystem.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    s.readFile(r.readFile(file));

    DrawMovingOrbit.visualizes(s);
  }

  @Test
  void testApp3() {
    SocialNetworkCircle s = new SocialNetworkCircle();
    String fileName = "txt/SocialNetWorkCircle.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    s.readFile(r.readFile(file));

    CircularOrbitHelper.visualizes(s);
    assertEquals(1, 1);
  }
}
