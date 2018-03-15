package com.snail.webgame.engine.component.scene.info;

import java.util.ArrayList;
import java.util.List;

import org.critterai.nav.TriNavMesh;

import com.snail.webgame.engine.common.info.Point3D;

public class LandMapData {
	private int maxX;
	private int maxY;
	private int maxZ;
	private TriNavMesh mesh;
	private List<Point3D> points = new ArrayList<Point3D>();

	public TriNavMesh getMesh() {
		return mesh;
	}

	public void setMesh(TriNavMesh mesh) {
		this.mesh = mesh;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public int getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(int maxZ) {
		this.maxZ = maxZ;
	}

	public void addPoint(Point3D point){
		points.add(point);
	}

	public List<Point3D> getPoints() {
		return points;
	}
	
	
}
