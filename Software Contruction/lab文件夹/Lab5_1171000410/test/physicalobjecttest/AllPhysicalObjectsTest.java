package physicalobjecttest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import physicalobject.Electron;
import physicalobject.Friend;
import physicalobject.PhysicalObject;
import physicalobject.Planet;

class AllPhysicalObjectsTest {

  // Testing strategy
  // Orbital object is empty
  // Orbital object's name
  // Special properties of different types of orbital objects

  @Test
  void test() {
    PhysicalObject o = new Planet("o", "solid", "yellow", 1, 2, 1, "CW", 1);
    assertEquals("o", o.getName());

    Planet p = new Planet("p", "solid", "yellow", 1, 2, 1, "CW", 1);
    assertEquals("p", p.getName());
    assertEquals("solid", p.getState());
    assertEquals("yellow", p.getColar());
    assertEquals(1, p.getPlanetRadius());
    assertEquals(2, p.getTrackRadius());
    assertEquals(1, p.getSpeed());
    assertEquals("CW", p.getOrientation());
    assertEquals(1, p.getSitha());

    Electron e = new Electron("e");
    assertEquals("e", e.getName());

    Friend f = new Friend("J", 1, "F");
    assertEquals("J", f.getName());
    assertEquals(1, f.getFriendAge());
    assertEquals("F", f.getFriendGender());
    int code = f.hashCode();
    assertEquals(code, f.hashCode());

  }

}
