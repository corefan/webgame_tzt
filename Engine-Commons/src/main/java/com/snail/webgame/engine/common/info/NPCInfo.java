package com.snail.webgame.engine.common.info;

public class NPCInfo extends AIInfo{
	
	protected int no;
	protected String name;	// 名称
	protected int npcType;	// NPC类型
	protected int faceMode;	// 模型资源
	protected int level;	// 等级
	//protected String mapId;	// 所在地图ID
	//protected int hp;	// 血量
	//protected int mp;	// 魔法值
	//protected double currAttack;	// 当前攻击点
	//protected double currDefend;	// 当前防御力
	protected float bornX;	// 出生的X点坐标
	protected float bornY;	// 出生的Y点坐标
	protected float bornZ;	// 出生的Z点坐标
	protected int baseSpeed;	// 基础移动速度
	protected long time;	// 开始移动时间或者开始攻击时间或者死亡时间
	//protected int status;	// npc当前状态（0：静止，1：巡逻，2：战斗，3：死亡）
	protected int patrolMode;	// 巡逻模式(0:不巡逻，1：定点巡逻，2：随机巡逻)
	protected double patrolRange;	// 巡逻半径
	protected int patrolTimePeriod;	// 巡逻时间间隔
	protected Point3D[] patrolPoints;	// 固定巡逻点
	protected int currPatrolPointPos;	// 固定巡逻点的第几个巡逻点
	protected int refreshTimePeriod;	// 刷新时间间隔
	protected double guardRange;	// 警戒范围
	//protected Path path = null;	// 移动路径
	protected String moveList;	// 移动路径
	protected int maxHP;//最大生命值
	protected int maxMP;//最大法力值
	protected int initiativeAttackGrid;//主动攻击范围
	protected int function;	// npc的功能(2的倍数)0-无功能，1-买卖，2-对话,4-任务；存在多个功能时，数字相加，比如：存在买卖和对话功能就为1+2=3

	public Point3D[] getPatrolPoints() {
		return patrolPoints;
	}

	public void setPatrolPoints(Point3D[] patrolPoints) {
		this.patrolPoints = patrolPoints;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
	
	public int getBaseSpeed() {
		return baseSpeed;
	}

	public void setBaseSpeed(int baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	public int getPatrolMode() {
		return patrolMode;
	}

	public void setPatrolMode(int patrolMode) {
		this.patrolMode = patrolMode;
	}

	public int getInitiativeAttackGrid() {
		return initiativeAttackGrid;
	}

	public void setInitiativeAttackGrid(int initiativeAttackGrid) {
		this.initiativeAttackGrid = initiativeAttackGrid;
	}

	public int getCurrPatrolPointPos() {
		return currPatrolPointPos;
	}

	public void setCurrPatrolPointPos(int currPatrolPointPos) {
		this.currPatrolPointPos = currPatrolPointPos;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}

	public int getFunction() {
		return function;
	}

	public void setFunction(int function) {
		this.function = function;
	}

	public double getPatrolRange() {
		return patrolRange;
	}

	public void setPatrolRange(double patrolRange) {
		this.patrolRange = patrolRange;
	}

	public int getPatrolTimePeriod() {
		return patrolTimePeriod;
	}

	public void setPatrolTimePeriod(int patrolTimePeriod) {
		this.patrolTimePeriod = patrolTimePeriod;
	}

	public int getRefreshTimePeriod() {
		return refreshTimePeriod;
	}

	public void setRefreshTimePeriod(int refreshTimePeriod) {
		this.refreshTimePeriod = refreshTimePeriod;
	}

	public double getGuardRange() {
		return guardRange;
	}

	public void setGuardRange(double guardRange) {
		this.guardRange = guardRange;
	}

	@Override
	public byte getSaveMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFaceMode() {
		return faceMode;
	}

	public void setFaceMode(int faceMode) {
		this.faceMode = faceMode;
	}

	public int getNpcType() {
		return npcType;
	}

	public void setNpcType(int npcType) {
		this.npcType = npcType;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
/*	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public double getCurrAttack() {
		return currAttack;
	}

	public void setCurrAttack(double currAttack) {
		this.currAttack = currAttack;
	}
	
	public double getCurrDefend() {
		return currDefend;
	}

	public void setCurrDefend(double currDefend) {
		this.currDefend = currDefend;
	}*/


	public long getTime() {
		return time;
	}

	public float getBornX() {
		return bornX;
	}

	public void setBornX(float bornX) {
		this.bornX = bornX;
	}

	public float getBornY() {
		return bornY;
	}

	public void setBornY(float bornY) {
		this.bornY = bornY;
	}

	public float getBornZ() {
		return bornZ;
	}

	public void setBornZ(float bornZ) {
		this.bornZ = bornZ;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getMoveList() {
		return moveList;
	}

	public void setMoveList(String moveList) {
		this.moveList = moveList;
	}

}
