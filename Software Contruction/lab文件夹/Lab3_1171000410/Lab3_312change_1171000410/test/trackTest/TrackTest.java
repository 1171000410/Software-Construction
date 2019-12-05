package trackTest;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import track.Track;

class TrackTest {

	@Test
	void test() {
		Track t = new Track(1,1,1);
		assertEquals(1, t.getA());
		assertEquals(1, t.getB());
		assertEquals(1, t.getC());
		
	}

}
