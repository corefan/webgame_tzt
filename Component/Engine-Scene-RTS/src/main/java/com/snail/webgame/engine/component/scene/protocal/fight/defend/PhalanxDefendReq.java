package com.snail.webgame.engine.component.scene.protocal.fight.defend;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class PhalanxDefendReq extends MessageBody{

 
	private long fightId;
	private String attackPhalanxId;
	private String aimPhalanxId;
	private int aimLoss;
	private long aimLossHP;
	private int aimCurrNum;
	private long aimCurrHP;
	private String defendSlogan;
	private int face;
	private byte flag;//伤害标识(1-暴击 2-MISS)
	private String reserve;
	
	
	
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("fightId", 0);
		ps.addString("attackPhalanxId", "flashCode", 0);
		ps.addString("aimPhalanxId", "flashCode", 0);
		ps.add("aimLoss", 0);
		ps.add("aimLossHP", 0);
		ps.add("aimCurrNum", 0);
		ps.add("aimCurrHP", 0);
		ps.addString("defendSlogan", "flashCode", 0);
		ps.add("face", 0);
		ps.add("flag", 0);
		ps.addString("reserve", "flashCode", 0);
		
	}




	public long getFightId() {
		return fightId;
	}




	public void setFightId(long fightId) {
		this.fightId = fightId;
	}




	public String getAttackPhalanxId() {
		return attackPhalanxId;
	}




	public void setAttackPhalanxId(String attackPhalanxId) {
		this.attackPhalanxId = attackPhalanxId;
	}




	public String getAimPhalanxId() {
		return aimPhalanxId;
	}




	public void setAimPhalanxId(String aimPhalanxId) {
		this.aimPhalanxId = aimPhalanxId;
	}




	public int getAimLoss() {
		return aimLoss;
	}




	public void setAimLoss(int aimLoss) {
		this.aimLoss = aimLoss;
	}


 



	public long getAimLossHP() {
		return aimLossHP;
	}




	public void setAimLossHP(long aimLossHP) {
		this.aimLossHP = aimLossHP;
	}




	public int getAimCurrNum() {
		return aimCurrNum;
	}




	public void setAimCurrNum(int aimCurrNum) {
		this.aimCurrNum = aimCurrNum;
	}




	public long getAimCurrHP() {
		return aimCurrHP;
	}




	public void setAimCurrHP(long aimCurrHP) {
		this.aimCurrHP = aimCurrHP;
	}




	public String getDefendSlogan() {
		return defendSlogan;
	}




	public void setDefendSlogan(String defendSlogan) {
		this.defendSlogan = defendSlogan;
	}




	public int getFace() {
		return face;
	}




	public void setFace(int face) {
		this.face = face;
	}




	public String getReserve() {
		return reserve;
	}




	public void setReserve(String reserve) {
		this.reserve = reserve;
	}




	public byte getFlag() {
		return flag;
	}




	public void setFlag(byte flag) {
		this.flag = flag;
	}

 
 
 
}
