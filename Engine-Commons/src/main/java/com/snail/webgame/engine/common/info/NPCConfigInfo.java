package com.snail.webgame.engine.common.info;

/**
 * @author wangxf
 * @date 2012-8-1 NPC生成方式信息
 */
public class NPCConfigInfo {

	private int no; // NPC模型no
	private int refreshType; // 刷新类型
	private String mapId; // 地图Id
	private int count; // 刷新个数
	private float x; // x点坐标
	private float y; // y点坐标
	private float Z; // Z点坐标
	private String bornPoints; // 多个坐标点
	private float refreshRange; // 刷新半径
	private String refreshPoints;	// 刷新区域范围
	private int patrolMode;	// 巡逻模式(0:不巡逻，1：定点巡逻，2：随机巡逻)
	private float patrolRange;	// 巡逻半径
	private int patrolTimePeriod;	// 巡逻时间间隔
	private String patrolPoints;	// 固定巡逻点
	private int maxPursueTime;//追击最大时间
	private int maxAttackGrid;//最大攻击距离
	protected int maxAttackTime;//攻击速度
	private int refreshTimePeriod;	// 刷新时间间隔
	protected int initiativeAttackGrid;//主动攻击范围

	public String getRefreshPoints() {
		return refreshPoints;
	}

	public void setRefreshPoints(String refreshPoints) {
		this.refreshPoints = refreshPoints;
	}

	public int getRefreshTimePeriod() {
		return refreshTimePeriod;
	}

	public void setRefreshTimePeriod(int refreshTimePeriod) {
		this.refreshTimePeriod = refreshTimePeriod;
	}

	public int getMaxAttackTime() {
		return maxAttackTime;
	}

	public void setMaxAttackTime(int maxAttackTime) {
		this.maxAttackTime = maxAttackTime;
	}

	public int getNo() {
		return no;
	}

	public int getInitiativeAttackGrid() {
		return initiativeAttackGrid;
	}

	public void setInitiativeAttackGrid(int initiativeAttackGrid) {
		this.initiativeAttackGrid = initiativeAttackGrid;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getRefreshType() {
		return refreshType;
	}

	public void setRefreshType(int refreshType) {
		this.refreshType = refreshType;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBornPoints() {
		return bornPoints;
	}

	public void setBornPoints(String bornPoints) {
		this.bornPoints = bornPoints;
	}

	public float getRefreshRange() {
		return refreshRange;
	}

	public void setRefreshRange(float refreshRange) {
		this.refreshRange = refreshRange;
	}

	public int getPatrolMode() {
		return patrolMode;
	}

	public void setPatrolMode(int patrolMode) {
		this.patrolMode = patrolMode;
	}

	public float getPatrolRange() {
		return patrolRange;
	}

	public void setPatrolRange(float patrolRange) {
		this.patrolRange = patrolRange;
	}

	public int getPatrolTimePeriod() {
		return patrolTimePeriod;
	}

	public void setPatrolTimePeriod(int patrolTimePeriod) {
		this.patrolTimePeriod = patrolTimePeriod;
	}

	public String getPatrolPoints() {
		return patrolPoints;
	}

	public void setPatrolPoints(String patrolPoints) {
		this.patrolPoints = patrolPoints;
	}

	public int getMaxPursueTime() {
		return maxPursueTime;
	}

	public void setMaxPursueTime(int maxPursueTime) {
		this.maxPursueTime = maxPursueTime;
	}

	public int getMaxAttackGrid() {
		return maxAttackGrid;
	}

	public void setMaxAttackGrid(int maxAttackGrid) {
		this.maxAttackGrid = maxAttackGrid;
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
		return Z;
	}

	public void setZ(float z) {
		Z = z;
	}

}
