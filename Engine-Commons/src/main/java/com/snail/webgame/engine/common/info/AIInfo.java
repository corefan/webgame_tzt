package com.snail.webgame.engine.common.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snail.webgame.engine.common.pathfinding.mesh.Path3D;
import com.snail.webgame.engine.common.to.BaseTO;

public class AIInfo extends BaseTO {
	/**
	 * 当前地图ID
	 */
	protected String mapId;
	/**
	 * 当前X坐标
	 */
	protected float currX;
	/**
	 * 当前Y坐标
	 */
	protected float currY;
	/**
	 * 当前Z坐标
	 */
	protected float currZ;

	protected float aimX; // 目标X
	protected float aimY; // 目标Y
	protected float aimZ; // 目标Z
	protected int currMoveTime;
	protected int currMoveMaxTime; // 移动速度
	protected int currAttackTime;
	protected int currAttackMaxTime;// 攻击速度
	protected int currMaxAttackGrid;// 最大攻击距离
	protected int currPursueTime;// 当前追击时间
	protected int maxPursueTime;// 最大追击时间
	protected float pursueRevertX;// 追击后还原坐标X
	protected float pursueRevertY;// 追击后还原坐标Y
	protected float pursueRevertZ;// 追击后还原坐标Z
	protected int status; // 0-待机 1-移动 2-死亡 3-追击 4-攻击
	protected int battleStatus;// 战斗状态：0-非战斗状态 1-战斗状态
	protected Path3D path = null;

	protected long attackAim;// 攻击目标
	protected int attackAimType;// 攻击目标类型：0-NPC 1-玩家

	protected int currHP; // 角色当前生命值
	protected int currMP; // 当前法力值
	
	protected int maxHP;	// 当前生命值上限
	protected int minHP;	// 当前生命值下限
	protected int initialHP;	// 初始生命值
	protected int maxMP;	// 当前法力值上限
	protected int minMP;	// 当前法力值下限
	protected int initialMP;	// 初始法力值
	
	
	protected double currAttack; // 当前攻击点
	protected double currDefend; // 当前防御力
	/**
	 * 角色当前动作,多个动作组成的动作队列用","分隔 0：待机、1：走、2：跑、3：功击
	 */
	protected String action;

	protected List<Long> otherRoleIds = new ArrayList<Long>();// 附近角色

	protected List<Long> npcIds = new ArrayList<Long>(); // 附近的npc

	
	protected List<Long> dropBagIds = new ArrayList<Long>(); // 附近的掉落物品

	protected List<String> attackActorList = new ArrayList<String>();// 攻击者列表

	protected Map<Integer, SkillInfo> skillInfoMap = new HashMap<Integer, SkillInfo>();// 技能

	protected long lastSkillTime;// 最后一次放技能时间
	
	protected List<BuffInfo> buffList = new ArrayList<BuffInfo>();

	@Override
	public byte getSaveMode() {
		return ONLINE;
	}


	public String getMapId() {
		return mapId;
	}

	public List<Long> getDropBagIds() {
		return dropBagIds;
	}

	public void setDropBagIds(List<Long> dropBagIds) {
		this.dropBagIds = dropBagIds;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public float getAimX() {
		return aimX;
	}

	public float getCurrX() {
		return currX;
	}

	public void setCurrX(float currX) {
		this.currX = currX;
	}

	public float getCurrY() {
		return currY;
	}

	public void setCurrY(float currY) {
		this.currY = currY;
	}

	public float getCurrZ() {
		return currZ;
	}

	public void setCurrZ(float currZ) {
		this.currZ = currZ;
	}

	public void setAimX(float aimX) {
		this.aimX = aimX;
	}

	public float getAimY() {
		return aimY;
	}

	public long getLastSkillTime() {
		return lastSkillTime;
	}

	public void setLastSkillTime(long lastSkillTime) {
		this.lastSkillTime = lastSkillTime;
	}

	public void setAimY(float aimY) {
		this.aimY = aimY;
	}

	public List<BuffInfo> getBuffList() {
		return buffList;
	}


	public void setBuffList(List<BuffInfo> buffList) {
		this.buffList = buffList;
	}


	public float getAimZ() {
		return aimZ;
	}

	public void setAimZ(float aimZ) {
		this.aimZ = aimZ;
	}

	public int getCurrMoveTime() {
		return currMoveTime;
	}

	public void setCurrMoveTime(int currMoveTime) {
		this.currMoveTime = currMoveTime;
	}

	public int getCurrMoveMaxTime() {
		return currMoveMaxTime;
	}

	public void setCurrMoveMaxTime(int currMoveMaxTime) {
		this.currMoveMaxTime = currMoveMaxTime;
	}

	public int getCurrAttackTime() {
		return currAttackTime;
	}

	public void setCurrAttackTime(int currAttackTime) {
		this.currAttackTime = currAttackTime;
	}

	public int getCurrAttackMaxTime() {
		return currAttackMaxTime;
	}

	public void setCurrAttackMaxTime(int currAttackMaxTime) {
		this.currAttackMaxTime = currAttackMaxTime;
	}

	public int getCurrMaxAttackGrid() {
		return currMaxAttackGrid;
	}

	public Map<Integer, SkillInfo> getSkillInfoMap() {
		return skillInfoMap;
	}

	public void setSkillInfoMap(Map<Integer, SkillInfo> skillInfoMap) {
		this.skillInfoMap = skillInfoMap;
	}

	public void setCurrMaxAttackGrid(int currMaxAttackGrid) {
		this.currMaxAttackGrid = currMaxAttackGrid;
	}

	public int getCurrPursueTime() {
		return currPursueTime;
	}

	public void setCurrPursueTime(int currPursueTime) {
		this.currPursueTime = currPursueTime;
	}

	public int getMaxPursueTime() {
		return maxPursueTime;
	}

	public void setMaxPursueTime(int maxPursueTime) {
		this.maxPursueTime = maxPursueTime;
	}

	public float getPursueRevertX() {
		return pursueRevertX;
	}

	public void setPursueRevertX(float pursueRevertX) {
		this.pursueRevertX = pursueRevertX;
	}

	public float getPursueRevertY() {
		return pursueRevertY;
	}

	public void setPursueRevertY(float pursueRevertY) {
		this.pursueRevertY = pursueRevertY;
	}

	public float getPursueRevertZ() {
		return pursueRevertZ;
	}

	public void setPursueRevertZ(float pursueRevertZ) {
		this.pursueRevertZ = pursueRevertZ;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBattleStatus() {
		return battleStatus;
	}

	public void setBattleStatus(int battleStatus) {
		this.battleStatus = battleStatus;
	}

	public Path3D getPath() {
		return path;
	}

	public void setPath(Path3D path) {
		this.path = path;
	}

	public long getAttackAim() {
		return attackAim;
	}

	public void setAttackAim(long attackAim) {
		this.attackAim = attackAim;
	}

	public int getAttackAimType() {
		return attackAimType;
	}

	public void setAttackAimType(int attackAimType) {
		this.attackAimType = attackAimType;
	}

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}

	public int getCurrMP() {
		return currMP;
	}

	public void setCurrMP(int currMP) {
		this.currMP = currMP;
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
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Long> getOtherRoleIds() {
		return otherRoleIds;
	}

	public int getMaxHP() {
		return maxHP;
	}


	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}


	public int getMinHP() {
		return minHP;
	}


	public void setMinHP(int minHP) {
		this.minHP = minHP;
	}


	public int getInitialHP() {
		return initialHP;
	}


	public void setInitialHP(int initialHP) {
		this.initialHP = initialHP;
	}


	public int getMaxMP() {
		return maxMP;
	}


	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}


	public int getMinMP() {
		return minMP;
	}


	public void setMinMP(int minMP) {
		this.minMP = minMP;
	}


	public int getInitialMP() {
		return initialMP;
	}


	public void setInitialMP(int initialMP) {
		this.initialMP = initialMP;
	}


	public void setOtherRoleIds(List<Long> otherRoleIds) {
		this.otherRoleIds = otherRoleIds;
	}

	public List<Long> getNpcIds() {
		return npcIds;
	}

	public void setNpcIds(List<Long> npcIds) {
		this.npcIds = npcIds;
	}

	public List<String> getAttackActorList() {
		return attackActorList;
	}

	public void setAttackActorList(List<String> attackActorList) {
		this.attackActorList = attackActorList;
	}

	public void addNpcId(long npcId) {
		if (!this.npcIds.contains(npcId))
			this.npcIds.add(npcId);
	}

	public void addOtherRoleId(long roleId) {
		if (!this.otherRoleIds.contains(roleId))
			this.otherRoleIds.add(roleId);
	}

	public void resetAimPoint() {
		this.aimX = -1;
		this.aimY = -1;
		this.aimZ = -1;
	}
}
