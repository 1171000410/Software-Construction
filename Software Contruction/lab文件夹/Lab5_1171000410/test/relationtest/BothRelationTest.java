package relationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import relation.EeIntimacyMap;
import relation.LeIntimacyMap;

class BothRelationTest {

  // Testing strategy
  //   Partition for add(label)
  //      graph: empty, contains multiple vertices
  //      label: exists in graph, doesn't exist in graph
  //      output: if add() returns true, graph is modified
  //              ie number of vertices increases by 1
  //              else graph unmodified
  //      observe with vertices()
  //
  //   Partition for remove(label)
  //      graph: empty, contains multiple vertices
  //      label: exists in graph, doesn't exist in graph,
  //             exists in edges, doesnt exist in an edge
  //      output: if remove() returns true, graph is modified
  //              ie number of vertices decreases by 1
  //              else graph unmodified
  //      observe with vertices(), sources(), targets()
  //   
  //   Partition for set(source,target,weight)
  //      graph: empty, contains multiple vertices    
  //      source: exists in graph, doesn't exist in graph
  //      target: exists in graph, doesn't exist in graph
  //      No edge exists from source to target,
  //      An edge exists from source to target,
  //      weight: 0, > 0
  //      observe with sources(), targets(), vertices()

  @Test
  void testEE_IntimacyMap() {
    EeIntimacyMap<String> eeMap = new EeIntimacyMap<String>();
    String s = new String("s");
    String t = new String("t");

    eeMap.put(s, t, 1);
    eeMap.put(s, t, 1);
    assertEquals(1, eeMap.size());

    ArrayList<String> list = new ArrayList<String>();
    list.add(t);
    assertEquals(list, eeMap.getAdjacentObjects(s));
    list.remove(t);
    list.add(s);
    assertEquals(list, eeMap.getAdjacentObjects(t));

    assertEquals(1, eeMap.getNumOfClosers(s, 0.5));

    eeMap.remove(s, t);
    assertEquals(0, eeMap.size());
  }

  @Test
  void testLE_IntimacyMap() {
    LeIntimacyMap<String, String> leMap = new LeIntimacyMap<String, String>();
    String s = new String("s");
    String t = new String("t");

    leMap.put(s, t, 1);
    leMap.put(s, t, 1);
    assertEquals(1, leMap.size());

    Set<String> set = new HashSet<String>();
    set.add(t);
    assertEquals(set, leMap.getValues());

    leMap.remove(s, t);
    assertEquals(0, leMap.size());

  }
}
