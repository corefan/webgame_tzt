package com.snail.webgame.engine.component.scene.protocal.npc;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

import com.snail.webgame.engine.component.scene.protocal.buff.BuffResp;

/**
 * @author wangxf
 * @date 2012-8-6
 * 反馈给客户端的npc对象信息
 */
public class TransformNPCInfo extends MessageBody{
	private long id;	// NPC ID
	private int no;	// NPC编号
	private String name;	// NPC名称
	private int npcType;	// NPC类型
	private int level;	// NPC等级
	private float currX;	// 当前X坐标
	private float currY;	// 当前Y坐标
	private float currZ;	// 当前Z坐标
	private String mapId;	// 地图ID
	private int maxHP;
	private int maxMP;
	private int currHP;	// NPC当前生命值
	private int currMP;	// 当前法力值
	private String currAttack;	// 当前攻击点
	private String currDefend;	// 当前防御力
	private String action;	// 动作
	private int currMaxMoveTime;	//移动时间
	private int faceMode;	//NPC模型
	private int status;	//NPC状态
	private String moveList;	//移动路径
	private int function;	// NPC功能
	private int buffCount;
	private List<BuffResp> buffList;
	
	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("id", 0);
		//ps.add("no", 0);
		ps.addString("name", "flashCode", 0);
		ps.add("npcType", 0);
		ps.add("level", 0);
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
		//ps.add("function", 0);
		ps.add("buffCount", 0);
		ps.addObjectArray("buffList", "com.snail.webgame.engine.component.scene.protocal.buff.BuffCount", "buffCount");
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getFunction() {
		return function;
	}

	public void setFunction(int function) {
		this.function = function;
	}

	public void setName(String name) {
		this.name = name;
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

}
