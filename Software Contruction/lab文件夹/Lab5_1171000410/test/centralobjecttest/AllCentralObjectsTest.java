package centralobjecttest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import centralobject.CentralObject;
import centralobject.CentralUser;
import centralobject.Nucleus;
import centralobject.Stellar;
import org.junit.jupiter.api.Test;

class AllCentralObjectsTest {

  // Testing strategy
  // 中心物体为空 
  // 中心物体的name
  // 不同类型中心物体的特殊属性

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
    Nucleus c = new Nucleus("a");
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
