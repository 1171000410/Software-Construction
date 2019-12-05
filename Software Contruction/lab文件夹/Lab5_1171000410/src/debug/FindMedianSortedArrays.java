package debug;

/**
 * Given two ordered integer arrays nums1 and nums2, with size m and n
 * Find out the median (double) of the two arrays.
 * You may suppose nums1 and nums2 cannot be null at the same time.
 *
 * <p>Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The output would be 2.0
  
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The output would be 2.5

 * Example 3:
 * nums1 = [1, 1, 1]
 * nums2 = [5, 6, 7]
 * The output would be 3.0

 * Example 4:
 * nums1 = [1, 1]
 * nums2 = [1, 2, 3]
 * The output would be 1.0
 */

public class FindMedianSortedArrays {

  /**findMedianSortedArrays.*/
  public double findMedianSortedArrays(int[] aa, int[] bb) {
    int m = aa.length;
    int n = bb.length;
    if (m > n) { //交换过程,为了保证m<=n
      int[] temp = aa;
      aa = bb;
      bb = temp;
      int tmp = m;
      m = n;
      n = tmp;
    }
    //修改1：halfLen = (m + n + 1) / 2
    //修改原因：保证m+n为奇数的时候，中位数在左边的part
    int imin = 0;
    int imax = m;
    int halfLen = (m + n + 1) / 2;
    while (imin <= imax) {
      int i = (imin + imax + 1) / 2;
      int j = halfLen - i;
      if (i < imax && bb[j - 1] > aa[i]) {
        imin = i + 1; //i太小
      } else if (i > imin && aa[i - 1] > bb[j]) {
        imax = i - 1; //i太大
      } else { //i大小刚好命中目标
        int maxLeft = 0;
        if (i == 0) { //临界
          maxLeft = bb[j - 1];
        } else if (j == 0) { //临界
          maxLeft = aa[i - 1];
        } else {
          maxLeft = Math.max(aa[i - 1], bb[j - 1]); //左边最大的
        }

        //修改2：(m + n) % 2 != 0
        //修改原因：此时为m+n为奇数的情况，返回左边最大的
        if ((m + n) % 2 != 0) {
          return maxLeft;
        }
        int minRight = 0;
        if (i == m) { //临界
          minRight = bb[j];
        } else if (j == n) { //临界
          minRight = aa[i];
        } else {
          minRight = Math.min(bb[j], aa[i]); //右边最小的
        }
        return (maxLeft + minRight) / 2.0; //m+n为偶数时
      }
    }
    return 0.0;
  }

}
