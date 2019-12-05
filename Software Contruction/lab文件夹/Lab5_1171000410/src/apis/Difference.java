package apis;

import java.util.ArrayList;

public class Difference {
  private final int tracksNumDifference; //轨道数目差异
  private ArrayList<Integer> specificTracksNumDifference = 
                      new ArrayList<Integer>(); //特定轨道物体数目差异
  private ArrayList<String> objectsDifference = new ArrayList<String>(); //轨道物体差距

  // Abstraction function:
  //   represents difference between two orbit systems
  // Representation invariant:
  //  tracksNumDifference is difference in the number of tracks
  //  specificTracksNumDifference is difference in the number of specific orbital objects
  //  objectsDifference is Orbital object gap
  //    specificTracksNumDifference.size() == objectsDifference.size()
  // Safety from rep exposure:
  //   All fields are private
  //   int is a primitive type so guaranteed immutable

  /**Constructor.*/
  public Difference(int a, ArrayList<Integer> b, ArrayList<String> c) {
    this.tracksNumDifference = a;
    this.specificTracksNumDifference = b;
    this.objectsDifference = c;
  }

  private void checkRep() {
    assert specificTracksNumDifference.size() == objectsDifference.size();
  }

  /**
   * Print difference another orbit systems.
   */
  public void printOtherDifference() {
    System.out.println("轨道数差异：" + tracksNumDifference);
    for (int i = 1; i <= specificTracksNumDifference.size(); i++) {
      System.out.printf("轨道%d的物体数量差异：%d； 物体差异：%s\n", i, 
          specificTracksNumDifference.get(i - 1),
          objectsDifference.get(i - 1));
    }
    checkRep();
  }

  /**
   * Print difference between two AtomStructrues.
   */
  public void printAtomStructrueDifference() {
    System.out.println("轨道数差异：" + tracksNumDifference);
    for (int i = 1; i <= specificTracksNumDifference.size(); i++) {
      System.out.printf("轨道%d的物体数量差异：%d\n", i, specificTracksNumDifference.get(i - 1));
    }
  }

}
