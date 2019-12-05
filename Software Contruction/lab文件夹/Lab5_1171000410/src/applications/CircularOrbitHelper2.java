package applications;

import circularorbit.CircularOrbit;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import physicalobject.Electron;

public class CircularOrbitHelper2 {

  private static int numOfTracks; // 轨道数目
  private static ArrayList<Integer> numOfTrackObjects = new ArrayList<Integer>(); // 
  private static ArrayList<ArrayList<Electron>> T2E = 
      new ArrayList<ArrayList<Electron>>(); // 物体所在轨道
  private static final double DEG_TO_RAD = Math.PI / 180; // 度数 * this constant = 弧度

  // Abstraction function:
  //   represents a tool for drawing a static graphs
  // Representation invariant:
  //  numOfTracks is number of tracks
  //  numOfTrackObjects is number of objects per track
  //  T2E is track and object mapping
  //  DEG_TO_RAD is a constant that converts an angle to radians
  //     numOfTracks is > 0 
  // Safety from rep exposure:
  //   All fields are private
  //   int is a primitive type so guaranteed immutable

  /**
   * Get data from a multi-track system.
   * Create components, draw graphics
   * @param c label of the orbit system
   */
  @SuppressWarnings("unchecked")
  public static void visualizes(@SuppressWarnings("rawtypes") CircularOrbit c) {
    numOfTracks = c.getT2E().size();
    T2E = c.getT2E();
    for (int i = 0; i < numOfTracks; i++) {
      numOfTrackObjects.add(i, T2E.get(i).size());
    }

    /*
     * 在 AWT 的事件队列线程中创建窗口和组件, 确保线程安全, 即 组件创建、绘制、事件响应 需要处于同一线程。
     */
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        // 创建窗口对象
        MyFrame frame = new MyFrame();
        // 显示窗口
        frame.setVisible(true);
      }
    });
  }

  /**
   * MyFrame class, inherited from JFrame.
   */
  @SuppressWarnings("serial")
  public static class MyFrame extends JFrame {

    public static final String TITLE = "Java图形绘制";

    public static final int WIDTH = 1500;
    public static final int HEIGHT = 1000;

    public MyFrame() {
      super();
      initFrame();
    }

    private void initFrame() {
      // 设置 窗口标题 和 窗口大小
      setTitle(TITLE);
      setSize(WIDTH, HEIGHT);

      // 设置窗口关闭按钮的默认操作(点击关闭时退出进程)
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      // 把窗口位置设置到屏幕的中心
      setLocationRelativeTo(null);

      // 设置窗口的内容面板
      MyPanel panel = new MyPanel(this);
      setContentPane(panel);
    }

  }

  /**
   * MyPanel class inherited from JPanel.
   */
  @SuppressWarnings("serial")
  public static class MyPanel extends JPanel {
    private MyFrame frame;

    public MyPanel(MyFrame frame) {
      super();
      this.frame = frame;
    }

    /**
     * 绘制面板的内容: 创建 JPanel 后会调用一次该方法绘制内容, 之后如果数据改变需要重新绘制.
     *  可调用 updateUI() 方法触发
     * 系统再次调用该方法绘制更新 JPanel 的内容。
     */
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      // 圆弧 / 扇形
      drawArc(g);
    }

    /**
     * 圆弧 / 扇形.
     */
    private void drawArc(Graphics g) {
      frame.setTitle("AtomStructure");
      Graphics2D g2d = (Graphics2D) g.create();

      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setColor(Color.BLUE);

      // 1.绘制多个同心圆:圆心坐标(400,400)
      for (int i = 0; i < numOfTracks; i++) {
        g2d.drawArc(350 - i * 50, 350 - i * 50, 100 + i * 100, 100 + i * 100, 0, 360);
        for (int j = 0; j < numOfTrackObjects.get(i); j++) {
          int radius = 50 + i * 50; // 半径
          double x = 400 + radius * Math.cos((numOfTrackObjects.get(i) + 10) * j * DEG_TO_RAD);
          double y = 400 + radius * Math.sin((numOfTrackObjects.get(i) + 10) * j * DEG_TO_RAD);

          int objX = (int) x;
          int objY = (int) y;

          g2d.fillArc(objX - 5, objY - 5, 10, 10, 0, 360); // 画轨道物体
        }
      }

      // 2. 填充一个中心
      g2d.setColor(Color.RED);
      g2d.fillArc(380, 380, 40, 40, 0, 360);

      g2d.dispose();
    }
  }
}
