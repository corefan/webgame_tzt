package com.snail.webgame.engine.component.scene.protocal.fight.control;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ControlReq extends MessageBody{

 
	private long fightId;
	private String id;
	private int policy;
	private int skillType;
	private int skillLevel;
	private int aimX;
	private int aimY;
	private String aimPhalanxId;
	
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("fightId", 0);
		ps.addString("id", "flashCode", 0);
		ps.add("policy", 0);
		ps.add("skillType",0);
		ps.add("skillLevel", 0);
		ps.add("aimX", 0);
		ps.add("aimY", 0);
		ps.addString("aimPhalanxId", "flashCode",0);
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
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

	public String getAimPhalanxId() {
		return aimPhalanxId;
	}

	public void setAimPhalanxId(String aimPhalanxId) {
		this.aimPhalanxId = aimPhalanxId;
	}
	
	 

}
