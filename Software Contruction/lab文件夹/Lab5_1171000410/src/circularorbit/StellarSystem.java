package circularorbit;

import applications.MyOrbitScenes;
import centralobject.Stellar;
import exception.DependencyException;
import exception.GrammarException;
import exception.LabelException;
import exception.NumberException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.Planet;

import track.Track;

public class StellarSystem extends WithPosition<Stellar, Planet> 
    implements CircularOrbit<Stellar, Planet> {

  // Abstraction function:
  // represents StellarSystem inherited from WithPosition<Stellar, Planet>
  // Central object is Stellar and the orbital object is Planet
  // Representation invariant:
  // labelSet is not empty

  
  /** Constructor. */
  public StellarSystem() {
  }

  /** Get name of StellarSystem. */
  @Override
  public String getSystemName() {
    return "StellarSystem";
  }

  /** Read file and create StellarSystem. */
  @Override
  public void readFile(String fileStr) {
	  Set<String> labelSet = new HashSet<String>(); //存储已出现名称的set
      String[] line = fileStr.split("\n");
      int counter = line.length;
      int lineIndex = 0;
      String p = "(\\w+) ::= <(.+)>";

      while (lineIndex < counter) {
        try {
          Pattern pattern = Pattern.compile(p); // 构造正则表达式
          Matcher mentioned = pattern.matcher(line[lineIndex]); // 匹配正则表达式

          if (mentioned.find()) {

            String[] args = mentioned.group(2).split("[,]");
            if (args.length == 3) {
              Stellar stellar = new Stellar(args[0], translateData(args[1]), 
                  translateData(args[2]));
              addCenterObject(stellar);
              if (labelSet.contains(args[0])) {
                throw new LabelException(":恒星名称已出现! Wrong:" + args[0]);
              }
              labelSet.add(args[0]);

              if (args[0].split("\\s").length > 1) {
                throw new LabelException(":恒星名称不是Label类型! Wrong:" + args[0]);
              }
            } else if (args.length == 8) {

              final Planet planet = new Planet(args[0], args[1], args[2], 
                  translateData(args[3]), translateData(args[4]),
                  translateData(args[5]), args[6], Double.parseDouble(args[7]));
              if (translateData(args[3]) > translateData(args[4])) {
                throw new DependencyException(":行星半径不能大于轨道半径! Wrong:行星" + args[0]);
              }

              if (labelSet.contains(args[0])) {
                throw new LabelException(":行星名称已出现! Wrong:" + args[0]);
              }
              labelSet.add(args[0]);

              char[] orientationChar = planet.getOrientation().toCharArray();
              for (int i = 0; i < orientationChar.length; i++) {
                if (Character.isLowerCase(orientationChar[i])) {
                  throw new GrammarException(":行星方向使用了小写字母! Wrong:" 
                + planet.getOrientation());
                }
              }
              setObjectSitha(planet, Double.parseDouble(args[7]));

              Track track = new Track(translateData(args[4]));
              addTrack(track);
              addTrackObject(track, planet);
            } else {
              throw new NumberException(":信息的分量个数不正确! Wrong:" + args.length);
            }
            
            args = null;
          }
        } catch (NumberException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        } catch (GrammarException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        } catch (LabelException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        } catch (DependencyException e) {
          MyOrbitScenes.log.log(Level.SEVERE, e.getExpMsg());
          getExceptionsList().add(e.getExpMsg());
        }

        // 对轨道半径进行排序
        for (int i = 0; i < getTracks().size(); i++) {

          for (int j = i + 1; j < getTracks().size(); j++) {
            if (getTracks().get(i).getRadius() > getTracks().get(j).getRadius()) {
              Track e = getTracks().get(i); // 中间变量 ，交换轨道
              getTracks().set(i, getTracks().get(j));
              getTracks().set(j, e);

              ArrayList<Planet> list = getT2E().get(i); // 中间变量，交换轨道物体
              getT2E().set(i, getT2E().get(j));
              getT2E().set(j, list);
            }
          }

        }
        lineIndex ++;
        
      }

      labelSet.clear();
      System.gc();
  }

  /**
   * Translate a data from string type to double Numbers greater than 10,000.
   * according to scientific notation and Numbers less than 10000 are given
   * directly
   * 
   * @param s label of the number string
   * @return double type of the number
   */

  public double translateData(String s) throws NumberException {
    assert s != null;
    String[] str = s.split("[e E]");
    if (str.length == 1) {
      if (Double.parseDouble(s) > 10000) {
        throw new NumberException(":未用科学记数法 Wrong:" + Double.parseDouble(s));
      }
      return Double.parseDouble(s);
    } else {
      double a = Double.parseDouble(str[0]);
      int b = Integer.parseInt(str[1]);

      if ((int) a > 9 || (int) a < 1) {
        throw new NumberException(":e之前的数字的整数部分必须在1到9范围内 Wrong:" + a);
      }
      if (b <= 3) {
        throw new NumberException(":e之后的数字只能是大于3的正整数 Wrong:" + b);
      }

      double ret = a * Math.pow(10, b);
      return ret;
    }
  }

}
