package com.snail.webgame.engine.common.info;

import java.util.List;

/**
 * @author wangxf
 * @date 2012-9-7 掉落包
 */
public class DropBag {
	protected long id;
	protected int modelId; // 掉落包模型ID
	protected float x; // 掉落点x坐标
	protected float y; // 掉落点y坐标
	protected float z; // 掉落点z坐标
	protected long dropTime; // 掉落时间
	protected String ownerId; // 所属者ID
	protected List<DropInfo> dropInfoList; // 掉落物品集合
	protected int protectTimePeriod; // 物品个人保护冷却时间间隔(超过这个时间别人就可以捡了)
	protected int clearTimePeriod; // 消失时间间隔
	protected String mapId;	// 地图ID

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public long getDropTime() {
		return dropTime;
	}

	public void setDropTime(long dropTime) {
		this.dropTime = dropTime;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public List<DropInfo> getDropInfoList() {
		return dropInfoList;
	}

	public void setDropInfoList(List<DropInfo> dropInfoList) {
		this.dropInfoList = dropInfoList;
	}

	public int getProtectTimePeriod() {
		return protectTimePeriod;
	}

	public void setProtectTimePeriod(int protectTimePeriod) {
		this.protectTimePeriod = protectTimePeriod;
	}

	public int getClearTimePeriod() {
		return clearTimePeriod;
	}

	public void setClearTimePeriod(int clearTimePeriod) {
		this.clearTimePeriod = clearTimePeriod;
	}

}
