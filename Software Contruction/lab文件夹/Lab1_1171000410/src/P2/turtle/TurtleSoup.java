/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;

import P2.turtle.PenColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle     the turtle context
	 * @param sideLength length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		turtle.color(PenColor.GREEN);
		for (int i = 0; i < 4; i++) {
			turtle.forward(sideLength);
			turtle.turn(90.0);
		}
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon; you
	 * should derive it and use it here.
	 * 
	 * @param sides number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 *         计算正多边形的内角，考虑到内角和是变化的，但是外角和为360degrees不变。
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		double angle = (sides - 2) * 180.0 / sides;
		return angle;

	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior angles.
	 * 
	 * @param angle size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides 有内角就可以求出外角，就可以推出边数
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		double external_angle = 180.0 - angle;
		int sides = (int) Math.ceil(360.0 / external_angle); // 向上取整
		return sides;
	}


	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
	 * draw.
	 * 
	 * @param turtle     the turtle context
	 * @param sides      number of sides of the polygon to draw
	 * @param sideLength length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		double rotate = 180.0 - calculateRegularPolygonAngle(sides);
		for (int i = 0; i < sides; i++) {
			turtle.forward(sideLength);
			turtle.turn(rotate);
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the Bearing towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle in
	 * the direction of the target point (targetX,targetY), given that the turtle is
	 * already at the point (currentX,currentY) and is facing at angle
	 * currentBearing. The angle must be expressed in degrees, where 0 <= angle <
	 * 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
	 * 
	 * @param currentBearing current direction as clockwise from north
	 * @param currentX       current location x-coordinate
	 * @param currentY       current location y-coordinate
	 * @param targetX        target point x-coordinate
	 * @param targetY        target point y-coordinate
	 * @return adjustment to Bearing (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
			int targetY) {
		double x, y;
		x = targetX - currentX;
		y = targetY - currentY;
		double degree = 90.0 - Math.atan2(y, x) / Math.PI * 180; // 与正上方向的夹角
		if (degree >= currentBearing)
			return degree - currentBearing;
		else
			return 360.0 - (currentBearing - degree);

	}

	/**
	 * Given a sequence of points, calculate the Bearing adjustments needed to get
	 * from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e. 0
	 * degrees). For each subsequent point, assumes that the turtle is still facing
	 * in the direction it was facing when it moved to the previous point. You
	 * should use calculateBearingToPoint() to implement this function.
	 * 
	 * @param xCoords list of x-coordinates (must be same length as yCoords)
	 * @param yCoords list of y-coordinates (must be same length as xCoords)
	 * @return list of Bearing adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1 备注：
	 *         java.util.ArrayList.get(int index) 方法会返回元素在此列表中的指定位置。
	 *         返回前一个向量到这个方向需要转动的角度的集合。
	 */
	public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
		List<Double> Angles = new ArrayList<Double>();
		double pre_angle = 0, angle = 0;
		int length = xCoords.size();
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		for (int i = 0; i < length - 1; i++) {
			x1 = xCoords.get(i);
			y1 = yCoords.get(i);
			x2 = xCoords.get(i + 1);
			y2 = yCoords.get(i + 1);
			angle = calculateBearingToPoint(pre_angle, x1, y1, x2, y2);
			Angles.add(i, angle); // 将角度添加到容器中
			pre_angle = angle;
		}
		return Angles;
	}

	/**
	 * Given a set of points, compute the convex hull, the smallest convex set that
	 * contains all the points in a set of input points. The gift-wrapping algorithm
	 * is one simple approach to this problem, and there are other algorithms too.
	 * 
	 * @param points a set of points with xCoords and yCoords. It might be empty,
	 *               contain only 1 point, two points or more.
	 * @return minimal subset of the input points that form the vertices of the
	 *         perimeter of the convex hull
	 */
	 public static Set<Point> convexHull(Set<Point> points) {
	    	Set<Point> ch = new HashSet<Point>();  //ch是要返回的点集
	    	Iterator<Point> iterator = points.iterator();  //将Set过渡到Arraylist
	    	List<Point> pointList = new ArrayList<Point>();
	    	while(iterator.hasNext()) {
	    		pointList.add(iterator.next());
	    	}
	    	if(pointList.size() == 0) {
	    		return ch;
	    	}
	    	double min_x = pointList.get(0).x();
	    	int min_i = 0;
	    	for (int i = 0; i < pointList.size(); i++) {  //获得最小的x坐标
				if (min_x > pointList.get(i).x()) {
					min_x = pointList.get(i).x();
					min_i = i;
				}
			}
	    	for (int i = 0; i < pointList.size(); i++) {
				if ((pointList.get(i).x()==min_x)&&
						(pointList.get(min_i).y()> pointList.get(i).y())) { //x相同取最小的y
					min_i = i;
				}
			}
	    	Collections.swap(pointList, 0, min_i);
	    	int toAdd = 0;  //待添加的点
	    	do{
	    		ch.add(pointList.get(toAdd));
	        	int j = (toAdd+1)%pointList.size(); //k是固定点外的一个基准点
	        	for(int i = 0;i < pointList.size();i++) {
	        		double ruler = (pointList.get(i).x()-pointList.get(toAdd).x())
	        				*(pointList.get(j).y()-pointList.get(toAdd).y()) 
	        				- (pointList.get(j).x()-pointList.get(toAdd).x())
	        				*(pointList.get(i).y()-pointList.get(toAdd).y());  //叉乘
	        		
	        		if(((pointList.get(i).x()-pointList.get(toAdd).x())   //判断共线
	        				/(pointList.get(j).x()-pointList.get(toAdd).x())
	        				==(pointList.get(i).y()-pointList.get(toAdd).y())
	        				/(pointList.get(j).y()-pointList.get(toAdd).y()))) {
	        			double distance_i = Math.pow(pointList.get(i).x()-pointList.get(toAdd).x(), 2)
	        					+Math.pow(pointList.get(i).y()-pointList.get(toAdd).y(), 2);
	        			
	        			double distance_j = Math.pow(pointList.get(j).x()-pointList.get(toAdd).x(), 2)
	        					+Math.pow(pointList.get(j).y()-pointList.get(toAdd).y(), 2);
	        			
	        			if(distance_i > distance_j) {  //共线取更远的点
	        				j = i;
	        			}
	        		}
	        		if(ruler > 0 && toAdd != i) {  
	        			j = i;
	        		}
	        	}
	        	toAdd = j;
			}while (toAdd != 0);
	    	return ch;
	    }

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can be
	 * as little or as much as you want.
	 * 
	 * @param turtle the turtle context
	 * 彩色五角星
	 */
	public static void drawPersonalArt(Turtle turtle) {
		for(int i = 0 ; i < 5 ; i++) {
			for(PenColor color : PenColor.values()) {
				turtle.color(color);
				turtle.forward(5);
			}
			turtle.turn(144);
		}
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		drawSquare(turtle, 40);
		drawPersonalArt(turtle);

		// draw the window
		turtle.draw();
	}

}
