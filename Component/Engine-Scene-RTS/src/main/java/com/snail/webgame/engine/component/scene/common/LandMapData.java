package com.snail.webgame.engine.component.scene.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LandMapData {

	protected byte[][] mapData;//地图阻挡
	protected float[][] elevation;//地图高度
	protected int tileWidth;
	protected int tileHeight;
	protected HashMap<String,BattlePoint> BattlePointMap = new HashMap<String,BattlePoint>();//出生点
	protected List<MapUnit> mapUnitList = new ArrayList<MapUnit>();//中立可摧毁部队
	
	public byte[][] getMapData() {
		return mapData;
	}
	public void setMapData(byte[][] mapData) {
		this.mapData = mapData;
	}
	public int getTileWidth() {
		return tileWidth;
	}
	public float[][] getElevation() {
		return elevation;
	}
	public void setElevation(float[][] elevation) {
		this.elevation = elevation;
	}
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}
	public int getTileHeight() {
		return tileHeight;
	}
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	public HashMap<String, BattlePoint> getBattlePointMap() {
		return BattlePointMap;
	}
	public void setBattlePointMap(HashMap<String, BattlePoint> battlePointMap) {
		BattlePointMap = battlePointMap;
	}
	public List<MapUnit> getMapUnitList() {
		return mapUnitList;
	}
	public void setMapUnitList(List<MapUnit> mapUnitList) {
		this.mapUnitList = mapUnitList;
	}
	
}
