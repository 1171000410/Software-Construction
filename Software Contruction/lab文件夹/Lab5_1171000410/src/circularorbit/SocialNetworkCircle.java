package circularorbit;

import applications.BuildRelation;
import applications.MyOrbitScenes;
import centralobject.CentralUser;
import exception.DependencyException;
import exception.GrammarException;
import exception.LabelException;
import exception.NumberException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.Friend;

public class SocialNetworkCircle extends WithoutPosition<CentralUser, Friend>
    implements CircularOrbit<CentralUser, Friend> {

  private Set<Friend> friendSet = new HashSet<Friend>();
  private Set<Integer> hashCodeSet = new HashSet<Integer>(110000);
  private HashMap<Integer, Friend> friendMap = new HashMap<Integer, Friend>();

  // Abstraction function:
  // represents SocialNetworkCircle inherited from WithoutPosition<CentralUser,
  // Friend>
  // Central object is CentralUser and the orbital object is Friend
  // Representation invariant:
  // friendSet and hashCodeSet are not empty
  // the size of friendSet is larger
  //
  // Safety from rep exposure:
  // All fields are private and final
  // friendSet is mutable type, so operations use defensive copies and
  // immutable wrappers to avoid sharing the rep's objects to clients

  public void checkRep() {
    assert hashCodeSet.size() > 0;
//    assert friendSet.size() > hashCodeSet.size();
  }

  /** Constructor. */
  public SocialNetworkCircle() {
  }

  /** Get name of SocialNetworkCircle. */
  @Override
  public String getSystemName() {
    return "SocialNetworkCircle";
  }

  public Set<Friend> getFriends() {
    checkRep();
    return Collections.unmodifiableSet(friendSet);
  }

  /** Read file and create SocialNetworkCircle. */
  @Override
  public void readFile(String fileStr) {

      String regex = "\\s+";
      String newFlieStr = fileStr.replaceAll(regex, ""); // 去掉空格
      String[] args = { "(CentralUser)::=<(.{1,20})>", 
          "(SocialTie)::=<(.{1,30})>", "(Friend)::=<(.{1,20})>" };

      Pattern pattern1 = Pattern.compile(args[0]); // 构造正则表达式
      Matcher mentioned1 = pattern1.matcher(newFlieStr); // 匹配CentralUser

      if (mentioned1.find()) {
        String[] str = mentioned1.group(2).split(",");
        CentralUser centralUser = new CentralUser(str[0], Integer.parseInt(str[1]), str[2]);
        addCenterObject(centralUser);
        try {
          if (str[0].split("^[0-9a-zA-Z]+").length > 1) {
            throw new GrammarException(":CentralUser姓名不是Label类型! Wrong:" + str[0]);
          }
        } catch (GrammarException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        }

        try {
          if (Integer.parseInt(str[1]) <= 0) {

            throw new GrammarException(":CentralUser年龄不是正整数! Wrong:" 
            + Integer.parseInt(str[1]));
          }
        } catch (GrammarException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        }

        try {
          if (!str[2].equals("M") && !str[2].equals("F")) {
            throw new GrammarException(":性别必须取M|F之一 Wrong:" + str[2]);
          }
        } catch (GrammarException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        }
      }

      Pattern pattern2 = Pattern.compile(args[2]); // 构造正则表达式
      Matcher mentioned2 = pattern2.matcher(newFlieStr); // 匹配Friend
      while (mentioned2.find()) {
        String[] str = mentioned2.group(2).split(",");
        try {
          if (str.length != 3) {
            throw new NumberException(":信息的分量个数不正确! Wrong:" + str[0]);
          }
        } catch (NumberException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        }

        Friend friend = new Friend(str[0], Integer.parseInt(str[1]), str[2]);
        friendSet.add(friend);
        friendMap.put(friend.hashCode(), friend);
      }

      Pattern pattern3 = Pattern.compile(args[1]); // 构造正则表达式
      Matcher mentioned3 = pattern3.matcher(newFlieStr); // 匹配SocialTie
      while (mentioned3.find()) {
        String[] str = mentioned3.group(2).split(",");
        Friend friend1;
        Friend friend2;
        if (getCentralObject().getName().equals(str[0])) {
          friend1 = findFriend(str[1]);
          if(friend1 == null) {
        	continue;
          }
          addCenterTrackObjectsRelation(getCentralObject(), friend1, Float.parseFloat(str[2]));
        } else if (getCentralObject().getName().equals(str[1])) {
          friend1 = findFriend(str[0]);
          if(friend1 == null) {
        	continue;
          }
          addCenterTrackObjectsRelation(getCentralObject(), friend1, Float.parseFloat(str[2]));
        } else { // 添加轨道物体的关系
          try {
            friend1 = findFriend(str[0]);
            friend2 = findFriend(str[1]);
            addTrackObjectsRelation(friend1, friend2, Float.parseFloat(str[2]));

            if(friend1 == null || friend2 == null) {
              continue;
              }
            Integer h = str[0].hashCode() + str[1].hashCode();
            try {
              if (hashCodeSet.contains(h)) {
                throw new LabelException(":两条社交关系不能连接同样的人! Wrong:" 
              + str[0] + " and " + str[1]);
              }
              hashCodeSet.add(h);
            } catch (LabelException e) {
              MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
              getExceptionsList().add(e.getExpMsg());
            }

            try {
              if (str[0].equals(str[1])) {
                throw new LabelException(":不能出现指向自己的社交关系! Wrong:" + str[0]);
              }
            } catch (LabelException e) {
              MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
              getExceptionsList().add(e.getExpMsg());
            }
          } catch (DependencyException e) {
            MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
            getExceptionsList().add(e.getExpMsg());
          }
        }

        try {
          if (Float.parseFloat(str[2]) > 1 || Float.parseFloat(str[2]) <= 0) {
            throw new GrammarException(":亲密度必须是在(0,1]内的小数! Wrong:" + str[2]);
          }
        } catch (GrammarException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        }

        try {
          if (str[2].length() > 5) {
            throw new GrammarException(":亲密度最多3位小数! Wrong:" + str[2]);
          }
        } catch (GrammarException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        }

      }

      BuildRelation b = new BuildRelation();
      b.buildRelation(this, friendSet);

      checkRep();

  }
  
  /**
   * Get a friend from the friendSet by name.
   * 
   * @param name label of specified friend name
   * @return friend who owns the name
   */
  public Friend findFriend(String name) throws DependencyException {
    assert name != null;

    return friendMap.get(name.hashCode());
//    for (Friend f : friendSet) {
//      if (f.getName().equals(name)) {
//        return f;
//      }
//    }
//    return null;
//    throw new DependencyException(":Friend未定义就在SocialTie中使用 Wrong:" + name);
  }
}