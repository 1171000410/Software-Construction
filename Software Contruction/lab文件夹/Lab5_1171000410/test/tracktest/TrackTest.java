package tracktest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import track.Track;

class TrackTest {

  // Testing strategy
  // Compare the radius of the track to the same as expected

  @Test
  void test() {
    Track t = new Track(1);
    assertEquals(1, t.getRadius());
  }

}
