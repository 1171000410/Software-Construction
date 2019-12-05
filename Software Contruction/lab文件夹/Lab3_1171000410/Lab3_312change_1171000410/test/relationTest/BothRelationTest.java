package relationTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import relation.EE_IntimacyMap;
import relation.LE_IntimacyMap;

class BothRelationTest {

	@Test
	void testEE_IntimacyMap() {
		EE_IntimacyMap<String> eeMap = new EE_IntimacyMap<String>();
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
		LE_IntimacyMap<String, String> leMap = new LE_IntimacyMap<String, String>();
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
