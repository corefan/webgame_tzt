package com.snail.webgame.engine.component.scene.protocal.fight.propeffect;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class PropEffectReq extends MessageBody{

 
	private long fightId;
	private String phalanxId;
	private int flag;//1-加血 2-加魔 3-加血加魔
	private long addHP;
	private long addMP;
	private int addItemNum;
	
	protected void setSequnce(ProtocolSequence ps) {
		 
		ps.add("fightId", 0);
		ps.addString("phalanxId","flashCode", 0);
		ps.add("flag", 0);
		ps.add("addHP", 0);		
		ps.add("addMP", 0);		
		ps.add("addItemNum", 0);				
		
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	public String getPhalanxId() {
		return phalanxId;
	}

	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public long getAddHP() {
		return addHP;
	}

	public void setAddHP(long addHP) {
		this.addHP = addHP;
	}

	public long getAddMP() {
		return addMP;
	}

	public void setAddMP(long addMP) {
		this.addMP = addMP;
	}

	public int getAddItemNum() {
		return addItemNum;
	}

	public void setAddItemNum(int addItemNum) {
		this.addItemNum = addItemNum;
	}
	
}
