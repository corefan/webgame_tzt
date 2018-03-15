package com.snail.webgame.engine.component.scene.common;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.snail.webgame.engine.common.info.BuffInfo;
import com.snail.webgame.engine.common.info.SkillInfo;
import com.snail.webgame.engine.common.pathfinding.astar.Path;

 
public class ArmyPhalanx {
	
	/**
	 * 部队ID
	 */
	protected String armyId;
	/**
	 * 方阵ID
	 */
	protected String phalanxId;
	/**
	 * 方阵所占格子数量
	 */
	protected int gridNum;
	/**
	 * 角色ID
	 */
	protected int roleId;
	/**
	 * 战斗所属方
	 */
	protected int side;
	/**
	 * 0-显示 1-不显示
	 */
	protected int isShow;
	/**
	 * 兵种编号
	 */
	protected int itemNo;
	/**
	 * 兵种当前数量
	 */
	protected int itemNum;
	/**
	 * 兵种最大数量
	 */
	protected int maxItemNum;
	/**
	 * 兵种类型  1-英雄 2-士兵 3-建筑
	 */
	protected int itemSort;
	/**
	 * 兵种子类型   1-刀盾兵 2-长枪兵 3-骑兵 4-火枪 5-火炮 6-船
	 */
	protected int itemSubSort;
	/**
	 * 当前格子坐标
	 */
	protected int currX;
	/**
	 * 当前格子坐标
	 */
	protected int currY;
	/**
	 * 之前的格子坐标
	 */
	protected int beforeX;
	/**
	 * 之前的格子坐标
	 */
	protected int beforeY;
	/**
	 * 当前世界坐标
	 */
	protected float currStageX;
	/**
	 * 当前世界坐标
	 */
	protected float currStageY;
	/**
	 * 移动优先级，级别高的，优先移动
	 */
	protected byte moveLevel;
	protected int beforePolicy;
	/**
	 * 策略
	 */
	protected int policy;
	/**
	 * 本次使用的技能类型
	 */
	protected int skillType;
	/**
	 * 技能释放次数
	 */
	protected int skillPersistCount;
	/**
	 * 最后施展技能的时间
	 */
	protected long lastSkillTime;
	/**
	 * 攻击目标方阵ID
	 */
	protected String aimPhalanxId;
	/**
	 * 攻击目标坐标
	 */
	protected int attackAimX;
	/**
	 * 攻击目标坐标
	 */
	protected int attackAimY;
	/**
	 * 移动目标坐标
	 */
	protected int aimX;
	/**
	 * 移动目标坐标
	 */
	protected int aimY;
	/**
	 * 当前移动时间(毫秒)
	 */
	protected int currMoveTime;
	/**
	 * 当前攻击时间(毫秒)
	 */
	protected int currAttackTime;
	/**
	 * 当前追击时间(毫秒)
	 */
	protected int currPursueTime;
	/**
	 * 追击前X坐标 
	 */
	protected int pursuePreX;
	/**
	 * 追击前Y坐标
	 */
	protected int pursuePreY;
	
	/**
	 * 最大血量
	 */
	protected double maxHP;
	/**
	 * 当前血量
	 */
	protected double currHP;
	/**
	 * 最大魔法值
	 */
	protected long maxMP;
	/**
	 * 当前魔法值
	 */
	protected long currMP;
	/**
	 * 单个兵血量
	 */
	protected double eveHP;
	/**
	 * 单个兵魔法值
	 */
	protected int eveMP;
	/**
	 * 当前最大攻击距离
	 */
	protected int currMaxAttackGrid;
	/**
	 * 当前最小攻击距离
	 */
	protected int currMinAttackGrid;
	/**
	 * 最大攻击距离
	 */
	protected int maxAttackGrid;
	/**
	 * 最小攻击距离
	 */
	protected int minAttackGrid;
	/**
	 * 当前攻击速度
	 */
	protected int attackMaxTime;
	/**
	 * 初始攻击速度
	 */
	protected int baseAttackMaxTime;
	/**
	 * 当前移动速度
	 */
	protected int currMoveMaxTime;
	/**
	 * 初始移动速度
	 */
	protected int baseMoveMaxTime;
	/**
	 * 子弹速度
	 */
	protected int bulletMaxTime;
	/**
	 * 视野
	 */
	protected int eyeShot;
	/**
	 * 最大追击时间
	 */
	protected int maxPursueTime;
	/**
	 * 士气
	 */
	protected int morale;
	/**
	 * 基础最小攻击值
	 */
	protected double baseMinAttackValue;
	/**
	 * 基础最大攻击值
	 */
	protected double baseMaxAttackValue;
	/**
	 * 最小攻击值
	 */
	protected double minAttackValue;
	/**
	 * 最大攻击值
	 */
	protected double maxAttackValue;
	/**
	 * 基础防御值
	 */
	protected double baseDefendValue;
	/**
	 * 防御值
	 */
	protected double defendValue;
	/**
	 * 1-死亡 2-移动中 3-移动停止等待中
	 */
	protected int status;
	/**
	 * 是否发送移动指命给客户端
	 */
	protected boolean moveSendFlag = false;
	/**
	 * Buff列表
	 */
	protected List <BuffInfo> buffList;
	/**
	 * 攻击类型
	 */
	protected int attackType;
	/**
	 * 防御类型
	 */
	protected int defendType;
	/**
	 * 攻击不到的建筑
	 */
	protected String boycottBuildingIds = "";
	/**
	 * 底图有路
	 */
	protected String baseMapBuildingIds = "";
	
	/**
	 * 受到伤害列表 
	 */
	protected List<DefendInfo> defendList = new ArrayList<DefendInfo>();
	/**
	 * 仇恨列表
	 */
	protected List<NpcHatredInfo> hatredList = new ArrayList<NpcHatredInfo>();//方阵仇恨列表
 
	/**
	 * 所占格子坐标
	 */
	protected List<Point> buildPointList  = null;
 
	/**
	 * 移动路线
	 */
	protected Path path = null;
	
	/**
	 * 技能
	 */
	protected HashMap<Integer,SkillInfo> skillMap = new HashMap<Integer,SkillInfo>();

	/**
	 * 显示的技能
	 */
	protected List<String> skillShowMap = new ArrayList<String>();
	
	/**
	 * 处理策略时间
	 */
	protected long executeTime;
	
	public String getArmyId() {
		return armyId;
	}

	public void setArmyId(String armyId) {
		this.armyId = armyId;
	}

	public String getPhalanxId() {
		return phalanxId;
	}

	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}

	public int getGridNum() {
		return gridNum;
	}

	public void setGridNum(int gridNum) {
		this.gridNum = gridNum;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public int getItemNum() {
		return itemNum;
	}

	public int getAttackType() {
		return attackType;
	}

	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}

	public int getDefendType() {
		return defendType;
	}

	public void setDefendType(int defendType) {
		this.defendType = defendType;
	}

	public double getEveHP() {
		return eveHP;
	}

	public void setEveHP(double eveHP) {
		this.eveHP = eveHP;
	}

	public int getEveMP() {
		return eveMP;
	}

	public void setEveMP(int eveMP) {
		this.eveMP = eveMP;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public int getMaxItemNum() {
		return maxItemNum;
	}

	public void setMaxItemNum(int maxItemNum) {
		this.maxItemNum = maxItemNum;
	}

	public int getItemSort() {
		return itemSort;
	}

	public void setItemSort(int itemSort) {
		this.itemSort = itemSort;
	}

	public int getCurrX() {
		return currX;
	}

	public void setCurrX(int currX) {
		this.currX = currX;
	}

	public long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(long executeTime) {
		this.executeTime = executeTime;
	}

	public int getCurrY() {
		return currY;
	}

	public void setCurrY(int currY) {
		this.currY = currY;
	}

	public int getBeforeX() {
		return beforeX;
	}

	public void setBeforeX(int beforeX) {
		this.beforeX = beforeX;
	}

	public int getBeforeY() {
		return beforeY;
	}

	public void setBeforeY(int beforeY) {
		this.beforeY = beforeY;
	}

	public float getCurrStageX() {
		return currStageX;
	}

	public void setCurrStageX(float currStageX) {
		this.currStageX = currStageX;
	}

	public float getCurrStageY() {
		return currStageY;
	}

	public void setCurrStageY(float currStageY) {
		this.currStageY = currStageY;
	}

	public byte getMoveLevel() {
		return moveLevel;
	}

	public void setMoveLevel(byte moveLevel) {
		this.moveLevel = moveLevel;
	}

	public int getPolicy() {
		return policy;
	}

	public void setPolicy(int policy) {
		this.policy = policy;
	}

	public int getSkillType() {
		return skillType;
	}

	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}

	public long getLastSkillTime() {
		return lastSkillTime;
	}

	public void setLastSkillTime(long lastSkillTime) {
		this.lastSkillTime = lastSkillTime;
	}

	public String getAimPhalanxId() {
		return aimPhalanxId;
	}

	public void setAimPhalanxId(String aimPhalanxId) {
		this.aimPhalanxId = aimPhalanxId;
	}

	public int getAttackAimX() {
		return attackAimX;
	}

	public void setAttackAimX(int attackAimX) {
		this.attackAimX = attackAimX;
	}

	public int getAttackAimY() {
		return attackAimY;
	}

	public void setAttackAimY(int attackAimY) {
		this.attackAimY = attackAimY;
	}

	public int getAimX() {
		return aimX;
	}

	public void setAimX(int aimX) {
		this.aimX = aimX;
	}

	public int getAimY() {
		return aimY;
	}

	public void setAimY(int aimY) {
		this.aimY = aimY;
	}

	public int getCurrMoveTime() {
		return currMoveTime;
	}

	public void setCurrMoveTime(int currMoveTime) {
		this.currMoveTime = currMoveTime;
	}

	public int getCurrAttackTime() {
		return currAttackTime;
	}

	public void setCurrAttackTime(int currAttackTime) {
		this.currAttackTime = currAttackTime;
	}

	public int getCurrPursueTime() {
		return currPursueTime;
	}

	public void setCurrPursueTime(int currPursueTime) {
		this.currPursueTime = currPursueTime;
	}

	public double getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(double maxHP) {
		this.maxHP = maxHP;
	}

	public double getCurrHP() {
		return currHP;
	}

	public void setCurrHP(double currHP) {
		this.currHP = currHP;
	}

	public long getMaxMP() {
		return maxMP;
	}

	public void setMaxMP(long maxMP) {
		this.maxMP = maxMP;
	}

	public long getCurrMP() {
		return currMP;
	}

	public void setCurrMP(long currMP) {
		this.currMP = currMP;
	}

	public int getCurrMaxAttackGrid() {
		return currMaxAttackGrid;
	}

	public void setCurrMaxAttackGrid(int currMaxAttackGrid) {
		this.currMaxAttackGrid = currMaxAttackGrid;
	}

	public int getCurrMinAttackGrid() {
		return currMinAttackGrid;
	}

	public void setCurrMinAttackGrid(int currMinAttackGrid) {
		this.currMinAttackGrid = currMinAttackGrid;
	}

	public int getMaxAttackGrid() {
		return maxAttackGrid;
	}

	public void setMaxAttackGrid(int maxAttackGrid) {
		this.maxAttackGrid = maxAttackGrid;
	}

	public int getMinAttackGrid() {
		return minAttackGrid;
	}

	public void setMinAttackGrid(int minAttackGrid) {
		this.minAttackGrid = minAttackGrid;
	}

	public int getAttackMaxTime() {
		return attackMaxTime;
	}

	public void setAttackMaxTime(int attackMaxTime) {
		this.attackMaxTime = attackMaxTime;
	}

	public int getBaseAttackMaxTime() {
		return baseAttackMaxTime;
	}

	public void setBaseAttackMaxTime(int baseAttackMaxTime) {
		this.baseAttackMaxTime = baseAttackMaxTime;
	}

	public int getCurrMoveMaxTime() {
		return currMoveMaxTime;
	}

	public void setCurrMoveMaxTime(int currMoveMaxTime) {
		this.currMoveMaxTime = currMoveMaxTime;
	}

	public int getBaseMoveMaxTime() {
		return baseMoveMaxTime;
	}

	public void setBaseMoveMaxTime(int baseMoveMaxTime) {
		this.baseMoveMaxTime = baseMoveMaxTime;
	}

	public int getEyeShot() {
		return eyeShot;
	}

	public void setEyeShot(int eyeShot) {
		this.eyeShot = eyeShot;
	}

	public int getMaxPursueTime() {
		return maxPursueTime;
	}

	public void setMaxPursueTime(int maxPursueTime) {
		this.maxPursueTime = maxPursueTime;
	}

	public int getMorale() {
		return morale;
	}

	public void setMorale(int morale) {
		this.morale = morale;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isMoveSendFlag() {
		return moveSendFlag;
	}

	public void setMoveSendFlag(boolean moveSendFlag) {
		this.moveSendFlag = moveSendFlag;
	}


	public List<BuffInfo> getBuffList() {
		return buffList;
	}

	public void setBuffList(List<BuffInfo> buffList) {
		this.buffList = buffList;
	}

	public String getBoycottBuildingIds() {
		return boycottBuildingIds;
	}

	public void setBoycottBuildingIds(String boycottBuildingIds) {
		this.boycottBuildingIds = boycottBuildingIds;
	}

	public String getBaseMapBuildingIds() {
		return baseMapBuildingIds;
	}

	public void setBaseMapBuildingIds(String baseMapBuildingIds) {
		this.baseMapBuildingIds = baseMapBuildingIds;
	}

	public List<DefendInfo> getDefendList() {
		return defendList;
	}

	public void setDefendList(List<DefendInfo> defendList) {
		this.defendList = defendList;
	}

	public List<NpcHatredInfo> getHatredList() {
		return hatredList;
	}

	public void setHatredList(List<NpcHatredInfo> hatredList) {
		this.hatredList = hatredList;
	}

	public List<Point> getBuildPointList() {
		return buildPointList;
	}

	public void setBuildPointList(List<Point> buildPointList) {
		this.buildPointList = buildPointList;
	}
	public String getBuildPointListToString(){
		if(this.buildPointList != null && this.buildPointList.size() > 0){
			StringBuffer s = new StringBuffer();
			for(Point p : this.buildPointList){
				s.append(p.x);
				s.append(",");
				s.append(p.y);
				s.append(",");
			}
			if(s.length() > 0){
				return s.substring(0, s.length()-1);
			}
		}
		return null;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public HashMap<Integer, SkillInfo> getSkillMap() {
		return skillMap;
	}

	public void setSkillMap(HashMap<Integer, SkillInfo> skillMap) {
		this.skillMap = skillMap;
	}

	public List<String> getSkillShowMap() {
		return skillShowMap;
	}

	public void setSkillShowMap(List<String> skillShowMap) {
		this.skillShowMap = skillShowMap;
	}

	public double getDefendValue() {
		return defendValue;
	}

	public void setDefendValue(double defendValue) {
		this.defendValue = defendValue;
	}

	public double getBaseMinAttackValue() {
		return baseMinAttackValue;
	}

	public void setBaseMinAttackValue(double baseMinAttackValue) {
		this.baseMinAttackValue = baseMinAttackValue;
	}

	public double getBaseMaxAttackValue() {
		return baseMaxAttackValue;
	}

	public void setBaseMaxAttackValue(double baseMaxAttackValue) {
		this.baseMaxAttackValue = baseMaxAttackValue;
	}

	public double getMinAttackValue() {
		return minAttackValue;
	}

	public void setMinAttackValue(double minAttackValue) {
		this.minAttackValue = minAttackValue;
	}

	public double getMaxAttackValue() {
		return maxAttackValue;
	}

	public void setMaxAttackValue(double maxAttackValue) {
		this.maxAttackValue = maxAttackValue;
	}

	public double getBaseDefendValue() {
		return baseDefendValue;
	}

	public void setBaseDefendValue(double baseDefendValue) {
		this.baseDefendValue = baseDefendValue;
	}

	public int getBulletMaxTime() {
		return bulletMaxTime;
	}

	public void setBulletMaxTime(int bulletMaxTime) {
		this.bulletMaxTime = bulletMaxTime;
	}

	public int getSkillPersistCount() {
		return skillPersistCount;
	}

	public void setSkillPersistCount(int skillPersistCount) {
		this.skillPersistCount = skillPersistCount;
	}

	public int getPursuePreX() {
		return pursuePreX;
	}

	public void setPursuePreX(int pursuePreX) {
		this.pursuePreX = pursuePreX;
	}

	public int getPursuePreY() {
		return pursuePreY;
	}

	public void setPursuePreY(int pursuePreY) {
		this.pursuePreY = pursuePreY;
	}

	public int getItemSubSort() {
		return itemSubSort;
	}

	public void setItemSubSort(int itemSubSort) {
		this.itemSubSort = itemSubSort;
	}

	public int getBeforePolicy() {
		return beforePolicy;
	}

	public void setBeforePolicy(int beforePolicy) {
		this.beforePolicy = beforePolicy;
	}

}
