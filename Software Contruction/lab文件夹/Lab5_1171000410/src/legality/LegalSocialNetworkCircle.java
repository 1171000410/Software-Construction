package legality;

import apis.CircularOrbitapis;
import centralobject.CentralUser;
import circularorbit.CircularOrbit;
import java.util.ArrayList;
import physicalobject.Friend;


public class LegalSocialNetworkCircle {

  // Abstraction function:
  //  represents security check on SocialNetworkCircle 

  /**judgeLegality.*/
  public int judgeLegality(CircularOrbit<CentralUser, Friend> c) {
    int min = 10000;

    for (int i = 1; i < c.getT2E().size(); i++) {
      ArrayList<Friend> list = c.getT2E().get(i);
      for (Friend f : list) {
        CircularOrbitapis<CentralUser, Friend> h = new CircularOrbitapis<CentralUser, Friend>();
        int d = 0;
        int counter = 0;
        for (Friend f2 : c.getT2E().get(0)) {
          d = h.getLogicalDistance(c, f, f2);
          if (d > 0 && d < min) {
            min = d;
          }
          if (d < 0) { //与第一轨道的某个人没联系counter就加一
            counter++;
          }

        }
        if (counter == c.getT2E().get(0).size()) { //与第一轨道的每个人都没联系
          System.out.println("与中心用户不连通的人不应出现在轨道系统中！");
          return 0;
        }

        if ((i + 1) != (min + 1)) {
          //    System.out.println("中心点和轨道物体距离出错！");
          //    System.out.println("出错的轨道用户："+f.getName());
          //    System.out.println("理论距离" + (i+1));
          //    System.out.println("实际距离" + (d+1));
          //    return 0;
        }

      }
    }
    return 1;

  }
}
