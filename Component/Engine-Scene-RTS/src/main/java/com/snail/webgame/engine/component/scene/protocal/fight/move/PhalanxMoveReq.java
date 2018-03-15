package com.snail.webgame.engine.component.scene.protocal.fight.move;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class PhalanxMoveReq extends MessageBody{

	private long fightId;
	private String phalanxId;
	private int itemNum;
	private int moveTime;
	private int currX;
	private int currY;
	private int moveX;
	private int moveY;
	private int aimX;
	private int aimY;
	private String moveList;
	private int moveType;
 
	
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("fightId", 0);
		ps.addString("phalanxId","flashCode", 0);
		ps.add("itemNum", 0);
		ps.add("moveTime", 0);
		ps.add("currX", 0);
		ps.add("currY", 0);
		ps.add("moveX", 0);
		ps.add("moveY", 0);
		ps.add("aimX", 0);
		ps.add("aimY", 0);
		ps.addString("moveList","flashCode", 0);
		ps.add("moveType", 0);
	}
	
 
	public int getMoveTime() {
		return moveTime;
	}


	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
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
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public int getCurrX() {
		return currX;
	}
	public void setCurrX(int currX) {
		this.currX = currX;
	}
	public int getCurrY() {
		return currY;
	}
	public void setCurrY(int currY) {
		this.currY = currY;
	}
	
	
	
	public int getMoveX() {
		return moveX;
	}

	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}

	public int getMoveY() {
		return moveY;
	}

	public void setMoveY(int moveY) {
		this.moveY = moveY;
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


	public String getMoveList() {
		return moveList;
	}


	public void setMoveList(String moveList) {
		this.moveList = moveList;
	}


	public int getMoveType() {
		return moveType;
	}


	public void setMoveType(int moveType) {
		this.moveType = moveType;
	}
	 
}
