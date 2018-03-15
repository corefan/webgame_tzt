package com.snail.webgame.engine.component.scene.common;

public class MapUnit {
	protected String unitId;
	protected int unitNo;
	protected int layerType;//1-地表 2-阻挡 3-漂浮物 4-方阵
	protected int x;
	protected int y;
	protected int elevation;
	protected String buildPoints;
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public int getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(int unitNo) {
		this.unitNo = unitNo;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getElevation() {
		return elevation;
	}
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
	public String getBuildPoints() {
		return buildPoints;
	}
	public void setBuildPoints(String buildPoints) {
		this.buildPoints = buildPoints;
	}
	public int getLayerType() {
		return layerType;
	}
	public void setLayerType(int layerType) {
		this.layerType = layerType;
	}
	
}
