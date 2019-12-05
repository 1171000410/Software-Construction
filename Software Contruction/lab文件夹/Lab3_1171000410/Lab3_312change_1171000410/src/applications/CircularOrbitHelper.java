package applications;

import javax.swing.*;
import circularOrbit.CircularOrbit;
import physicalObject.Friend;
import relation.EE_IntimacyMap;

import java.awt.*;
import java.util.ArrayList;

public class CircularOrbitHelper {

	private static int numOfTracks; // 轨道数目
	private static ArrayList<Integer> numOfTrackObjects = new ArrayList<Integer>(); // 	
	private static EE_IntimacyMap<Friend> E2E = new EE_IntimacyMap<Friend>(); // 轨道物体间的映射
	private static ArrayList<ArrayList<Friend>> T2E = new ArrayList<ArrayList<Friend>>(); // 物体所在轨道
	private static final double DEG_TO_RAD = Math.PI / 180;  // 度数 * this constant = 弧度
	
	// Abstraction function:
    //   represents a tool for drawing a static graphs
    // Representation invariant:
	//  numOfTracks is number of tracks
	//  numOfTrackObjects is number of objects per track
	//  E2E is mapping between orbital objects
	//  T2E is track and object mapping
	//  DEG_TO_RAD is a constant that converts an angle to radians
	//	 numOfTracks is > 0 
    // Safety from rep exposure:
    //   All fields are private
    //   int is a primitive type so guaranteed immutable
	
	/**
	 * Get data from a multi-track system
	 * Create components, draw graphics
	 * @param c label of the orbit system
	 */
	@SuppressWarnings("unchecked")
	public static void visualizes(@SuppressWarnings("rawtypes") CircularOrbit c) {
		numOfTracks = c.getT2E().size();

		E2E = c.getE2E();
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
	 * MyFrame class, inherited from JFrame
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
	 * MyPanel class inherited from JPanel
	 */
	@SuppressWarnings("serial")
	public static class MyPanel extends JPanel {
		private MyFrame frame;

		public MyPanel(MyFrame frame) {
			super();
			this.frame = frame;
		}

		/**
		 * 绘制面板的内容: 创建 JPanel 后会调用一次该方法绘制内容, 之后如果数据改变需要重新绘制, 可调用 updateUI() 方法触发
		 * 系统再次调用该方法绘制更新 JPanel 的内容。
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// 圆弧 / 扇形
			drawArc(g);
		}

		/**
		 * 圆弧 / 扇形
		 */
		private void drawArc(Graphics g) {
			frame.setTitle("SocialNetWork");
			Graphics2D g2d = (Graphics2D) g.create();

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.BLUE);

			// 1.绘制多个同心圆:圆心坐标(400,400)
			for (int i = 0; i < numOfTracks; i++) {
				g2d.drawArc(350 - i * 50, 350 - i * 50, 100 + i * 100, 100 + i * 100, 0, 360);

				ArrayList<Friend> list = T2E.get(i);
				for (Friend f : list) {
					int radius = 50 + i * 50; // 半径
					double x = 400 + radius * Math.cos(f.hashCode() * DEG_TO_RAD);
					double y = 400 + radius * Math.sin(f.hashCode() * DEG_TO_RAD);

					int objX = (int) x;
					int objY = (int) y;

					g2d.fillArc(objX - 10, objY - 10, 20, 20, 0, 360); // 画轨道物体
					if (i == 0) {
						g2d.drawLine(400, 400, objX, objY);
					}
				}
			}

			for (int i = 0; i < numOfTracks; i++) {
				ArrayList<Friend> list1 = T2E.get(i);
				for (Friend f1 : list1) {   //轨道1所有朋友
					for (int j = i + 1; j < numOfTracks; j++) {
						ArrayList<Friend> list2 = T2E.get(j);
						for (Friend f2 : list2) {	//轨道2所有朋友
							if (E2E.getAdjacentObjects(f1).contains(f2)) {   //有关系
								int r1 = 50 + i * 50; // 半径1
								int r2 = 50 + j * 50; // 半径2
								
								double x1 = 400 + r1 * Math.cos(f1.hashCode() * DEG_TO_RAD);
								double y1 = 400 + r1 * Math.sin(f1.hashCode() * DEG_TO_RAD);
								int objX1 = (int) x1;
								int objY1 = (int) y1;
								
								double x2 = 400 + r2 * Math.cos(f2.hashCode() * DEG_TO_RAD);
								double y2 = 400 + r2 * Math.sin(f2.hashCode() * DEG_TO_RAD);
								int objX2 = (int) x2;
								int objY2 = (int) y2;
								
								g2d.drawLine(objX1, objY1, objX2, objY2);
							}
						}
					}
				}
			}
			
			// 2. 填充一个中心
			g2d.setColor(Color.RED);
			g2d.fillArc(380, 380, 40, 40, 0, 360);

			g2d.dispose();
		}
	}
}
