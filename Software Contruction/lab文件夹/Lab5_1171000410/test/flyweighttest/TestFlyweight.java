package flyweighttest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import track.Track;

import org.junit.jupiter.api.Test;

import circularorbit.AtomStructure;
import fileinput.AllBufferedReader;
import fileinput.ReadStrategy;
import flyweight.ElectronFactory;
import flyweight.ElectronFlyweight;

class TestFlyweight {

  @Test
  void electronFlyweightTest() {
	AtomStructure a = new AtomStructure();
    String fileName = "txt/AtomicStructure.txt";
    File file = new File(fileName);
    ReadStrategy r = new AllBufferedReader();
    a.readFile(r.readFile(file));
    
	Track t = a.getTracks().get(0);
	ElectronFactory factory = new ElectronFactory();
	ElectronFlyweight e = factory.getFlyweight(t);  //获得电子
	
	assertEquals(e, e);
  }

}
