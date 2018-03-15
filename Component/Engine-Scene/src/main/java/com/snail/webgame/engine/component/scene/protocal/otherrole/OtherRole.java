package com.snail.webgame.engine.component.scene.protocal.otherrole;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

import com.snail.webgame.engine.component.scene.protocal.buff.BuffResp;
import com.snail.webgame.engine.component.scene.protocal.equip.queryrel.EquipRelRe;

public class OtherRole extends MessageBody {

	protected long roleId;	// 角色ID
	protected String roleName;	// 角色名称
	protected int sex;//性别
	protected int level;	// 角色等级
	protected int sceneId;	// 场景ID
	protected float currX;	// 当前X坐标
	protected float currY;	// 当前Y坐标
	protected float currZ;	// 当前Z坐标
	protected String mapId;	// 地图ID
	protected int maxHP;//最大生命值
	protected int maxMP;//最大法力值
	protected int currHP;	// 角色当前生命值
	protected int currMP;	// 当前法力值
	protected String currAttack;	// 当前攻击点
	protected String currDefend;	// 当前防御力
	protected String action;	// 动作
	protected int currMaxMoveTime;//移动时间
	protected int faceMode;//角色模型
	protected int status;//角色状态
	protected String moveList;//移动路径
	private int equipRelCount;//装备栏里装备个数
	private List<EquipRelRe> equipRelList;//装备栏里的装备
	private int buffCount;
	private List<BuffResp> buffList;
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("roleId", 0);
		ps.addString("roleName", "flashCode", 0);
		ps.add("sex", 0);
		ps.add("level", 0);
		ps.add("sceneId", 0);
		ps.add("currX", 0);
		ps.add("currY", 0);
		ps.add("currZ", 0);
		ps.addString("mapId", "flashCode", 0);
		ps.add("maxHP", 0);
		ps.add("maxMP", 0);
		ps.add("currHP", 0);
		ps.add("currMP", 0);
		ps.addString("currAttack", "flashCode", 0);
		ps.addString("currDefend", "flashCode", 0);
		ps.addString("action", "flashCode", 0);
		ps.add("currMaxMoveTime", 0);
		ps.add("faceMode", 0);
		ps.add("status", 0);
		ps.addString("moveList", "flashCode", 0);
		ps.add("equipRelCount", 0);
		ps.addObjectArray("equipRelList", "com.snail.webgame.engine.component.scene.protocal.equip.queryrel.EquipRelRe", "equipRelCount");
		ps.add("buffCount", 0);
		ps.addObjectArray("buffList", "com.snail.webgame.engine.component.scene.protocal.buff.BuffResp", "buffCount");
	
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getSceneId() {
		return sceneId;
	}
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}
	public float getCurrX() {
		return currX;
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
	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	public int getCurrHP() {
		return currHP;
	}
	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}
	public int getBuffCount() {
		return buffCount;
	}
	public void setBuffCount(int buffCount) {
		this.buffCount = buffCount;
	}
	public List<BuffResp> getBuffList() {
		return buffList;
	}
	public void setBuffList(List<BuffResp> buffList) {
		this.buffList = buffList;
	}
	public int getCurrMP() {
		return currMP;
	}
	public void setCurrMP(int currMP) {
		this.currMP = currMP;
	}
	public String getCurrAttack() {
		return currAttack;
	}
	public void setCurrAttack(String currAttack) {
		this.currAttack = currAttack;
	}
	public String getCurrDefend() {
		return currDefend;
	}
	public void setCurrDefend(String currDefend) {
		this.currDefend = currDefend;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getEquipRelCount() {
		return equipRelCount;
	}
	public void setEquipRelCount(int equipRelCount) {
		this.equipRelCount = equipRelCount;
	}
	public List<EquipRelRe> getEquipRelList() {
		return equipRelList;
	}
	public void setEquipRelList(List<EquipRelRe> equipRelList) {
		this.equipRelList = equipRelList;
	}
	public int getCurrMaxMoveTime() {
		return currMaxMoveTime;
	}
	public void setCurrMaxMoveTime(int currMaxMoveTime) {
		this.currMaxMoveTime = currMaxMoveTime;
	}
	public int getFaceMode() {
		return faceMode;
	}
	public void setFaceMode(int faceMode) {
		this.faceMode = faceMode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMoveList() {
		return moveList;
	}
	public void setMoveList(String moveList) {
		this.moveList = moveList;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	
}
