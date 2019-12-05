package debug;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FindMedianSortedArraysTest {

  @Test
  void testFindMedianSortedArrays() {
    FindMedianSortedArrays f = new FindMedianSortedArrays();
    int[] aa = { 1, 3 };
    int[] bb = { 2 };
    assertEquals(2, f.findMedianSortedArrays(aa, bb));

    aa = new int[] { 1, 2 };
    bb = new int[] { 3, 4 };
    assertEquals(2.5, f.findMedianSortedArrays(aa, bb));

    aa = new int[] { 1, 1, 1 };
    bb = new int[] { 5, 6, 7 };
    assertEquals(3.0, f.findMedianSortedArrays(aa, bb));

    aa = new int[] { 1, 1 };
    bb = new int[] { 1, 2, 3 };
    assertEquals(1.0, f.findMedianSortedArrays(aa, bb));
  }

}
