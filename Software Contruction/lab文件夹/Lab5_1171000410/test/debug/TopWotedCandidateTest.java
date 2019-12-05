package debug;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TopWotedCandidateTest {

  @Test
  void testTopWotedCandidate() {
    int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
    int[] times = { 0, 5, 10, 15, 20, 25, 30 };
    TopVotedCandidate t = new TopVotedCandidate(persons, times);

    assertEquals(0, t.qq(3));
    assertEquals(1, t.qq(12));
    assertEquals(1, t.qq(25));
    assertEquals(0, t.qq(15));
    assertEquals(0, t.qq(24));
    assertEquals(1, t.qq(8));

  }

}
