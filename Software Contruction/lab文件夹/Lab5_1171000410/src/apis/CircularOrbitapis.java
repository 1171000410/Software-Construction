package apis;

import circularorbit.CircularOrbit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import physicalobject.Friend;
import physicalobject.Planet;
import relation.EeIntimacyMap;

public class CircularOrbitapis<L, E> {

  // Abstraction function:
  //  represent a series of common calculations can be performed on it
  //  Based on the specific multi-track structure 
  //  constructed by the three applications selected

  /**
   * Calculating the entropy of the distribution of objects on each orbit in a
   * multi-track system.
   * 
   * @param c label of a multi-track system
   * @return entropy of the system
   .*/
  @SuppressWarnings("rawtypes")
  public double getObjectDistributionEntropy(CircularOrbit c) {
    assert c != null;
    @SuppressWarnings("unchecked")
    ArrayList<ArrayList<E>> t2e = c.getT2E(); // 轨道上的物体

    double entropy = 0; // 熵值
    double sum = 0; // 轨道物体总数
    double p = 0;

    for (int i = 0; i < t2e.size(); i++) {
      sum += t2e.get(i).size(); //计算轨道物体总数
    }

    for (int i = 0; i < t2e.size(); i++) {
      p = t2e.get(i).size() / sum;
      if (p == 0) { //空轨道
        break;
      }

      entropy -= p * Math.log(p);
    }

    assert entropy >= 0;
    return entropy;
  }

  /**
   * Get logical distance of two objects in the orbit system.
   * @param c label of specific track type
   * @param e1 label of one object
   * @param e2 label of another object
   * @return int value of logical distance
   */
  @SuppressWarnings("rawtypes")
  public int getLogicalDistance(CircularOrbit c, E e1, E e2) {
    assert c != null;
    assert e1 != null;
    assert e2 != null;
    @SuppressWarnings("unchecked")
    EeIntimacyMap<E> e2e = c.getE2E();

    E now = e1;
    E friend = e1;
    int i = 0;
    int distance = 0;
    Queue<E> queue = new LinkedList<E>();
    ArrayList<E> visited = new ArrayList<E>();
    if (e1 == e2) {
      return distance;
    }
    queue.add(now);
    visited.add(now);
    while (!queue.isEmpty()) {
      now = queue.poll();
      distance++;
      while (i < e2e.getAdjacentObjects(now).size()) {
        friend = e2e.getAdjacentObjects(now).get(i);
        if (friend == e2) { // 找到了即返回当前的距离
          assert distance >= 0;
          return distance;
        }
        if (!visited.contains(friend)) {
          queue.add(friend);
          visited.add(friend);
        }
        i++;
      }
      i = 0;
    }
    return -1; // 找不到说明P1和P2没关系

  }

  /**
   * Get physical distance of two objects in the orbit system.
   * Method only for the stellar system
   * @param c label of specific track type
   * @param e1 label of one object
   * @param e2 label of another object
   * @return double value of logical distance
   */
  @SuppressWarnings("rawtypes")
  public double getPhysicalDistance(CircularOrbit c, E e1, E e2) {
    assert c != null;
    assert e1 != null;
    assert e2 != null;

    Planet p1 = (Planet) e1;
    Planet p2 = (Planet) e2;

    double sitha1 = p1.getSitha();
    double sitha2 = p2.getSitha();

    double x1 = p1.getTrackRadius() * Math.sin(sitha1);
    double y1 = p1.getTrackRadius() * Math.cos(sitha1);
    double x2 = p2.getTrackRadius() * Math.sin(sitha2);
    double y2 = p2.getTrackRadius() * Math.cos(sitha2);

    double distance = Math.sqrt(Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));

    return distance;
  }

  /**Get the difference between two orbit system of the same type.
   * @param c1 label of one system
   * @param c2 label of another system
   * @return Differce label of two orbits' difference
   */
  @SuppressWarnings("rawtypes")
  public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
    assert c1 != null;
    assert c2 != null;
    int tracksNumDifference; // 轨道数目差异
    ArrayList<Integer> specificTracksNumDifference = new ArrayList<Integer>(); // 特定轨道数目差异
    ArrayList<String> objectsDifference = new ArrayList<String>(); // 轨道物体差距

    int diff;
    int i;
    Difference d;

    @SuppressWarnings("unchecked")
    ArrayList<ArrayList<E>> t2e1 = c1.getT2E(); // 轨道1上的物体
    @SuppressWarnings("unchecked")
    ArrayList<ArrayList<E>> t2e2 = c2.getT2E(); // 轨道2上的物体

    if (c1.getSystemName() != c2.getSystemName()) {
      System.out.println("错误！必须比较同类型的轨道！");
    }

    if (c1.getSystemName() == "AtomStructure") {
      tracksNumDifference = t2e1.size() - t2e2.size();
      if (tracksNumDifference >= 0) { // 前一个轨道数更多
        for (i = 0; i < t2e2.size(); i++) {
          diff = t2e1.get(i).size() - t2e2.get(i).size();
          specificTracksNumDifference.add(diff);
        }
        for (i = t2e2.size(); i < t2e1.size(); i++) {
          diff = t2e1.get(i).size();
          specificTracksNumDifference.add(diff);
        }
      } else { // 后一个轨道数更多
        for (i = 0; i < t2e1.size(); i++) {
          diff = t2e1.get(i).size() - t2e2.get(i).size();
          specificTracksNumDifference.add(diff);
        }
        for (i = t2e1.size(); i < t2e2.size(); i++) {
          diff = 0 - t2e2.get(i).size();
          specificTracksNumDifference.add(diff);
        }
      }

      d = new Difference(tracksNumDifference, specificTracksNumDifference, null);
      System.out.println("AtomStructure");
      d.printAtomStructrueDifference();

    } else if (c1.getSystemName() == "StellarSystem") {
      tracksNumDifference = t2e1.size() - t2e2.size();
      if (tracksNumDifference >= 0) { // 前一个轨道数更多
        String s = "";
        for (i = 0; i < t2e2.size(); i++) {
          diff = t2e1.get(i).size() - t2e2.get(i).size();
          specificTracksNumDifference.add(diff);
          Planet p1 = (Planet) (t2e1.get(i).get(0));
          Planet p2 = (Planet) (t2e1.get(i).get(0));
          if (p1.getName() == p2.getName()) {
            s = "无";
          } else {
            s = p1.getName() + "-" + p2.getName();
          }
          objectsDifference.add(s);
        }
        for (i = t2e2.size(); i < t2e1.size(); i++) {
          diff = t2e1.get(i).size();
          specificTracksNumDifference.add(diff);

          Planet p1 = (Planet) (t2e1.get(i).get(0));
          s = p1.getName() + "-无";

          objectsDifference.add(s);

        }
      } else { // 后一个轨道数更多
        String s = "";
        for (i = 0; i < t2e1.size(); i++) {
          diff = t2e1.get(i).size() - t2e2.get(i).size();
          specificTracksNumDifference.add(diff);

          if (diff == 0) {
            s = "无";
          } else {
            //  Planet p1 = (Planet) (T2E1.get(i).get(0));
            //  Planet p2 = (Planet) (T2E1.get(i).get(0));
            //
            //  s = p1.getName() + "-" + p2.getName();
          }
          objectsDifference.add(s);
          s = "";
        }
        for (i = t2e1.size(); i < t2e2.size(); i++) {
          diff = 0 - t2e2.get(i).size();
          specificTracksNumDifference.add(diff);

          Planet p2 = (Planet) (t2e2.get(i).get(0));
          s = "无-" + p2.getName();

          objectsDifference.add(s);
          s = "";
        }
      }
      d = new Difference(tracksNumDifference, specificTracksNumDifference, objectsDifference);
      System.out.println("StellarSystem");
      d.printOtherDifference();
    } else { //"SocialNetworkCircle"
      tracksNumDifference = t2e1.size() - t2e2.size();
      if (tracksNumDifference >= 0) { // 前一个轨道数更多
        String s = "";
        String s1 = "";
        String s2 = "";
        for (i = 0; i < t2e1.size(); i++) {
          diff = t2e1.get(i).size() - t2e2.get(i).size();
          specificTracksNumDifference.add(diff);
          boolean flag1 = true;
          boolean flag2 = true;

          for (int j = 0; j < t2e1.get(i).size(); j++) { // 找轨道2中没有的轨道1物体
            Friend p1 = (Friend) (t2e1.get(i).get(j));
            for (int k = 0; k < t2e2.get(i).size(); k++) {
              Friend p2 = (Friend) (t2e2.get(i).get(k));
              if (p1.getName().equals(p2.getName())) {
                flag1 = false;
                break; // 在轨道2找到该物体相等的物体，直接退出
              }
            }
            if (flag1) {
              //    s1 = s1.concat(p1.getName() + " ");
            }
            flag1 = true;
          }

          for (int j = 0; j < t2e2.get(i).size(); j++) { // 找轨道1中没有的轨道2物体
            Friend p2 = (Friend) (t2e2.get(i).get(j));
            for (int k = 0; k < t2e1.get(i).size(); k++) {
              Friend p1 = (Friend) (t2e1.get(i).get(k));
              if (p1.getName().equals(p2.getName())) {
                flag2 = false;
                break; // 在轨道2找到该物体相等的物体，直接退出
              }
            }
            if (flag2) {
              s2 = s2.concat(p2.getName() + " ");
            }
            flag2 = true;
          }

          s = "{" + s1 + "}-{" + s2 + "}";
          objectsDifference.add(s);
          s = "";
          s1 = "";
          s2 = "";
        }

        for (i = t2e2.size(); i < t2e1.size(); i++) {
          diff = t2e1.get(i).size();
          specificTracksNumDifference.add(diff);

          for (int k = 0; k < t2e1.get(i).size(); k++) {
            Friend p1 = (Friend) (t2e2.get(i).get(k));
            s1 = s1.concat(p1.getName() + " ");

          }
          s = "{" + s1 + "}-无";

          objectsDifference.add(s);
          s = "";
          s1 = "";

        }

      } else { // 后一个轨道数更多
        String s = "";
        String s1 = "";
        String s2 = "";
        for (i = 0; i < t2e1.size(); i++) {
          diff = t2e1.get(i).size() - t2e2.get(i).size();
          specificTracksNumDifference.add(diff);
          boolean flag1 = true;
          boolean flag2 = true;

          for (int j = 0; j < t2e1.get(i).size(); j++) { // 找轨道2中没有的轨道1物体
            Friend p1 = (Friend) (t2e1.get(i).get(j));
            for (int k = 0; k < t2e2.get(i).size(); k++) {
              Friend p2 = (Friend) (t2e1.get(i).get(k));
              if (p1.getName().equals(p2.getName())) {
                flag1 = false;
                break; // 在轨道2找到该物体相等的物体，直接退出
              }
            }
            if (flag1) {
              s1 = s1.concat(p1.getName() + " ");
            }
            flag1 = true;
          }

          for (int j = 0; j < t2e1.get(i).size(); j++) { // 找轨道1中没有的轨道2物体
            Friend p2 = (Friend) (t2e2.get(i).get(j));
            for (int k = 0; k < t2e1.get(i).size(); k++) {
              Friend p1 = (Friend) (t2e2.get(i).get(k));
              if (p1.getName().equals(p2.getName())) {
                flag2 = false;
                break; // 在轨道2找到该物体相等的物体，直接退出
              }
            }
            if (flag2) {
              s2 = s2.concat(p2.getName() + " ");
            }
            flag2 = true;
          }

          s = "{" + s1 + "}-{" + s2 + "}";
          objectsDifference.add(s);
          s = "";
          s1 = "";
          s2 = "";
        }

        for (i = t2e2.size(); i < t2e1.size(); i++) {
          diff = t2e1.get(i).size();
          specificTracksNumDifference.add(diff);

          for (int k = 0; k < t2e1.get(i).size(); k++) {
            Friend p1 = (Friend) (t2e2.get(i).get(k));
            s1 = s1.concat(p1.getName() + " ");
          }
          s = "{" + s1 + "}-无";
          objectsDifference.add(s);
          s = "";
          s1 = "";
        }
      }
      d = new Difference(tracksNumDifference, specificTracksNumDifference, objectsDifference);
      System.out.println("SocialNetworkCircle");
      d.printOtherDifference();
    }
    return d;
  }
}
