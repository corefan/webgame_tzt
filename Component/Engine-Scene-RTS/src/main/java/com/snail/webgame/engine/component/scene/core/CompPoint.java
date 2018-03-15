package com.snail.webgame.engine.component.scene.core;

import java.awt.Point;

public class CompPoint {

	private Point point ;
	private double distance;
	
	
	public CompPoint (Point point, double distance)
	{
		this.point = point;
		this.distance = distance;
	}
	
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
}
