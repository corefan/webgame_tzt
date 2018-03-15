package com.snail.webgame.engine.common.info;

/**
 * @author wangxf
 * @date 2012-9-5
 * 掉落物品对象
 */
public class DropInfo {
	private long dropId;	// 掉落ID
	private int itemNo; // 物品编号
	private int itemSort; // 物品类型1-道具，2-装备
	/*private float x; // 掉落点x坐标
	private float y; // 掉落点y坐标
	private float z; // 掉落点z坐标
	private long dropTime; // 物品掉落时间
	private int personalCoolingTimePeriod; // 物品个人保护冷却时间间隔(超过这个时间别人就可以捡了)
	private int clearCoolingTimePeriod; // 物品消失时间间隔
	private String owner; // 所属者
	private String mapId;	// 地图ID
	private boolean isPickUp;	// 是否已被拾取标识
*/	
	/*public boolean isPickUp() {
		return isPickUp;
	}

	public void setPickUp(boolean isPickUp) {
		this.isPickUp = isPickUp;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}*/

	public long getDropId() {
		return dropId;
	}

	public void setDropId(long dropId) {
		this.dropId = dropId;
	}

	/*public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public long getDropTime() {
		return dropTime;
	}

	public void setDropTime(long dropTime) {
		this.dropTime = dropTime;
	}

	public int getPersonalCoolingTimePeriod() {
		return personalCoolingTimePeriod;
	}

	public void setPersonalCoolingTimePeriod(int personalCoolingTimePeriod) {
		this.personalCoolingTimePeriod = personalCoolingTimePeriod;
	}

	public int getClearCoolingTimePeriod() {
		return clearCoolingTimePeriod;
	}

	public void setClearCoolingTimePeriod(int clearCoolingTimePeriod) {
		this.clearCoolingTimePeriod = clearCoolingTimePeriod;
	}*/

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public int getItemSort() {
		return itemSort;
	}

	public void setItemSort(int itemSort) {
		this.itemSort = itemSort;
	}

	/*public float getX() {
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
	}*/

}
