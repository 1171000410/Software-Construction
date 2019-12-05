package applications;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import circularOrbit.CircularOrbit;
import physicalObject.Planet;

public class DrawMovingOrbit {

	private static int numOfTracks; // 轨道数目
	private static ArrayList<ArrayList<Planet>> T2E = new ArrayList<ArrayList<Planet>>(); // 物体所在轨道

	private ArrayList<Circle> centers = new ArrayList<Circle>();
	private ArrayList<Circle> orbiters = new ArrayList<Circle>();
	
	private static final double DEG_TO_RAD = Math.PI / 180; // 度数 * this constant = 弧度
	private static final int TIMER_DELAY = 25;

	// Abstraction function:
    //   represents a tool for drawing a static graphs
    // Representation invariant:
	//  numOfTracks is number of tracks
	//  numOfTrackObjects is number of objects per track
	//  centers is the list of central circles
	//  orbiters is the list of orbital circle
	//  DEG_TO_RAD is a constant that converts an angle to radians
	//	 numOfTracks is > 0 
    // Safety from rep exposure:
    //   All fields are private
    //   int is a primitive type so guaranteed immutable
	
	/**
	 * Constructor for a DrawMovingOrbit object. Uses the given circle as the center
	 * circle, creates an orbiter circle 1/8th the radius of the center and on the
	 * x-axis of the center circle to start. 
	 */
	public DrawMovingOrbit(Circle center) {
		for (int i = 0; i < numOfTracks; i++) {
			Circle newCenter = new Circle(center.center, center.radius + 50 * i, 1, 1 ,1 ,"");
			centers.add(newCenter);
			Point2D.Double d = new Point2D.Double();   //行星初始位置
			final double xCenter = newCenter.center.x;
			final double yCenter = newCenter.center.y;
			final double rad = newCenter.radius;
			for (int j = 0; j < T2E.get(i).size(); j++) {
				int tick = (int) (T2E.get(i).get(j).getSitha());
				int orientation;
				String colar = T2E.get(i).get(j).getColar();
				if (T2E.get(i).get(j).getOrientation().equals("CCW")) { // 逆时针
					orientation = -1;
				} else {
					orientation = 1;
				}
				Circle newOrbiter = new Circle(new Point2D.Double(), center.radius / 8, tick, orientation ,i ,colar);
				orbiters.add(newOrbiter);
				
				d.setLocation(xCenter + rad * Math.cos(tick * DEG_TO_RAD), yCenter + 
						rad * Math.sin(tick * DEG_TO_RAD));
				newOrbiter.setCenter(d);
			}
		}
		updateOrbiterLoc();
	}

	/**
	 * Get data from a multi-track system
	 * Create components, draw a moving graphics
	 * @param c label of the orbit system
	 */
	@SuppressWarnings("unchecked")
	public static void visualizes(@SuppressWarnings("rawtypes") CircularOrbit c) {
		numOfTracks = c.getT2E().size();
		T2E = c.getT2E();
		
		JFrame f = new JFrame(); // 窗口
		final DrawMovingOrbit d = new DrawMovingOrbit(new Circle(new Point2D.Double(500, 500), 100, 1, 1 ,1 ,""));

		final OrbitPanel o = d.getPanel();
		f.add(o, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Timer t = new Timer(TIMER_DELAY, null);  //计时器
		t.addActionListener(new ActionListener() {  //监听器
			@Override
			public void actionPerformed(ActionEvent e) {
				d.tick();
				o.repaint();
				t.restart();
			}
		});
		t.start();
	}
	
	/** Updates the location of the orbiter circle based on tick */
	private void updateOrbiterLoc() {
		for(int i = 0 ; i < orbiters.size() ; i++) {
			Circle orbiter = orbiters.get(i);
			Point2D.Double d = new Point2D.Double();
			final double xCenter = centers.get(orbiters.get(i).numOfTrack).center.x;
			final double yCenter = centers.get(orbiters.get(i).numOfTrack).center.y;
			final double rad = centers.get(orbiters.get(i).numOfTrack).radius;
			final int tick = orbiters.get(i).tick;
			final double longAxis = rad;
			final double shortAxis = rad/2;
			d.setLocation(xCenter +  longAxis * Math.cos(tick * DEG_TO_RAD), yCenter + shortAxis * Math.sin(tick * DEG_TO_RAD));
			orbiter.setCenter(d);
			
		}
	}

	/** Increases tick and recalculates the position of the orbiter */
	public void tick() {
		for(int i = 0 ; i < orbiters.size() ; i++) {
			final int orientation = orbiters.get(i).orientation;
			orbiters.get(i).tick +=orientation;
		}
		updateOrbiterLoc();
	}

	/** Draws all of the circles in this DrawMovingOrbit */
	public void draw(Graphics2D g) {
		for (int i = 0; i < numOfTracks; i++) {
			Circle center = centers.get(i);
			center.drawCenter(g);
		}
		
		for(int j = 0 ;j < orbiters.size() ; j++) {
			Circle orbiter = orbiters.get(j);
			orbiter.drawOrbit(g);
		}

	}

	/** Creates a new panel to draw this orbit */
	public OrbitPanel getPanel() {
		return new OrbitPanel();
	}

	/**
	 * OrbitPanel class inherited from JPanel
	 */
	@SuppressWarnings("serial")
	class OrbitPanel extends JPanel { // 面板

		public OrbitPanel() {

			// Quick and dirty way of making sure the panel is big enough
			setPreferredSize(new Dimension(1200, 1000));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.RED);
			g.fillArc(460, 460, 80 , 80 , 0, 360);
			g.setColor(Color.BLACK);
			DrawMovingOrbit.this.draw((Graphics2D) g);
		}
	}

	/**
	 * A class describing a circular object
	 */
	static class Circle {

		private Point2D.Double center;  //中心坐标
		private double radius;  //半径
		private int tick;   //记录在轨道的位置
		private int orientation; // 1或-1
		private int numOfTrack;  //轨道
		private String color;   //颜色

		/**Constructor*/
		public Circle(Point2D.Double c, double r, int t, int o , int nt , String color) {
			center = c;
			radius = r;
			this.tick = t;
			this.orientation = o;
			this.numOfTrack = nt ;
			this.color = color;
		}

		/**Set center of the circle*/
		public void setCenter(Point2D.Double c) {
			center = c;
		}

		/**Draw the graphics of the central orbit*/
		public void drawCenter(Graphics2D g) {
			int x = (int) (center.x - radius);
			int y = (int) (center.y - radius/2);
			int size = (int) (radius * 2);
			g.drawOval(x, y, size, size/2);
		}
		
		/**Draw the graphics of the orbiter*/
		public void drawOrbit(Graphics2D g) {
			int x = (int) (center.x - radius);
			int y = (int) (center.y - radius);
			int size = (int) (radius * 2);
			if(color.equals("Blue")) {
				g.setColor(Color.BLUE);
			}
			if(color.equals("Dark")) {
				g.setColor(Color.BLACK);
			}
			if(color.equals("Red")) {
				g.setColor(Color.RED);
			}
			if(color.equals("Yellow")) {
				g.setColor(Color.YELLOW);
			}
					
			g.fillOval(x, y, size, size);
		}
		
		public String toString() {
			return center.toString() + " r=" + radius;
		}
	}
}