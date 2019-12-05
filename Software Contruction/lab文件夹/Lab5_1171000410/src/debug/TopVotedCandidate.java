package debug;

/**
 * In an election, the i-th vote was cast for persons[i] at time times[i].
 * 
 * Now, we would like to implement the following query function:
 * TopVotedCandidate.q(int t) will return the number of the person that was
 * leading the election at time t.
 * 
 * Votes cast at time t will count towards our query. In the case of a tie, the
 * most recent vote (among tied candidates) wins.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: ["TopVotedCandidate","q","q","q","q","q","q"],
 * [[[0,1,1,0,0,1,0],[0,5,10,15,20,25,30]],[3],[12],[25],[15],[24],[8]]
 *  Output:
 * [null,0,1,1,0,0,1] 
 * 
 * Explanation: 
 * At time 3, the votes are [0], and 0 is leading. 
 * At time 12, the votes are [0,1,1], and 1 is leading. 
 * At time 25, the votes are [0,1,1,0,0,1], and 1 is leading (as ties go to the most recent
 * vote.) 
 * This continues for 3 more queries at time 15, 24, and 8.
 * 
 * 
 * Note:
 * 
 * 1 <= persons.length = times.length <= 5000 
 * 0 <= persons[i] <= persons.length
 * times is a strictly increasing array with all elements in [0, 10^9].
 * TopVotedCandidate.q is called at most 10000 times per test case.
 * TopVotedCandidate.q(int t) is always called with t >= times[0].
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TopVotedCandidate {
  List<List<Vote>> aa;

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public TopVotedCandidate(int[] persons, int[] times) {
    aa = new ArrayList();
    Map<Integer, Integer> count = new HashMap(); //某人映射的选票数
    for (int i = 0; i < persons.length; ++i) {
      int p = persons[i];
      int t = times[i];
      //修改1：int c = count.getOrDefault(p, 1);
      //修改原因：c为p的选票数加上1
      int c = count.getOrDefault(p, 0) + 1;

      count.put(p, c);
      while (aa.size() <= c) {
        aa.add(new ArrayList<Vote>());
      }
      aa.get(c).add(new Vote(p, t));
    }
  }

  public int qq(int t) {
    int lo = 1;
    int hi = aa.size();
    while (lo < hi) {
      int mi = lo + (hi - lo) / 2;
      if (aa.get(mi).get(0).time <= t) {
        //修改2：mi+1
        //修改原因：二分法
        lo = mi + 1;
      } else {
        hi = mi;
      }
    }
    //修改3：对lo减一
    //修改原因：使得i对应list中vote的time都<=t
    int i = lo - 1;

    lo = 0;
    hi = aa.get(i).size();
    while (lo < hi) {
      int mi = lo + (hi - lo) / 2;
      //修改4：<=
      //修改原因：二分法
      if (aa.get(i).get(mi).time <= t) {
        lo = mi + 1;
      } else {
        hi = mi;
      }
    }
    //修改5：lo-1
    //修改原因：获得时间最接近且小于t的票
    int j = Math.max(lo - 1, 0);
    return aa.get(i).get(j).person;
  }
}
