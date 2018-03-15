package com.snail.webgame.engine.component.scene.protocal.fight.in;

 

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RoleInFightRe extends MessageBody{

	
	private String id;
	private int isShow;
	private int itemNo;
	private int gridNum;
	private long maxPhalanxHP;
	private int itemNum;
	private int maxItemNum;
	private int itemSort;
	private long maxHP;
	private long currHP;
	private long maxMP;
	private long currMP;
	private int currX;
	private int currY;
	private String buildPointList;
	private int status;
	private String buff;
	private int aimX;
	private int aimY;
	private String moveList;
	private int currMoveMaxTime;
	private int currAttackMaxTime;
	private int attackValue;
	private int defendValue;
	private int minGrid;
	private int maxGrid;
	private int morale;
	private int side;
	private String skillType;
	
	
	
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.addString("id","flashCode",0);
		ps.add("isShow", 0);
		ps.add("itemNo", 0);
		ps.add("gridNum", 0);
		ps.add("maxPhalanxHP", 0);
		ps.add("itemNum", 0);
		ps.add("maxItemNum", 0);
		ps.add("itemSort", 0);
		ps.add("maxHP", 0);
		ps.add("currHP", 0);
		ps.add("maxMP", 0);
		ps.add("currMP", 0);
		ps.add("currX", 0);
		ps.add("currY", 0);
		ps.addString("buildPointList", "flashCode", 0);
		ps.add("status", 0);
		ps.addString("buff","flashCode",0);
		ps.add("aimX", 0);
		ps.add("aimY", 0);
		ps.addString("moveList","flashCode",0);
		ps.add("currMoveMaxTime", 0);
		ps.add("currAttackMaxTime", 0);
		ps.add("attackValue", 0);
		ps.add("defendValue", 0);
		ps.add("minGrid", 0);
		ps.add("maxGrid", 0);
		ps.add("morale", 0);
		ps.add("side", 0);
		ps.addString("skillType", "flashCode", 0);
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}


	public String getBuildPointList() {
		return buildPointList;
	}




	public void setBuildPointList(String buildPointList) {
		this.buildPointList = buildPointList;
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




	public int getGridNum() {
		return gridNum;
	}




	public void setGridNum(int gridNum) {
		this.gridNum = gridNum;
	}




	public long getMaxPhalanxHP() {
		return maxPhalanxHP;
	}




	public void setMaxPhalanxHP(long maxPhalanxHP) {
		this.maxPhalanxHP = maxPhalanxHP;
	}




	public int getItemNum() {
		return itemNum;
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




	public long getMaxHP() {
		return maxHP;
	}




	public void setMaxHP(long maxHP) {
		this.maxHP = maxHP;
	}




	public long getCurrHP() {
		return currHP;
	}




	public void setCurrHP(long currHP) {
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




	public int getStatus() {
		return status;
	}




	public void setStatus(int status) {
		this.status = status;
	}




	public String getBuff() {
		return buff;
	}




	public void setBuff(String buff) {
		this.buff = buff;
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




	public int getCurrMoveMaxTime() {
		return currMoveMaxTime;
	}




	public void setCurrMoveMaxTime(int currMoveMaxTime) {
		this.currMoveMaxTime = currMoveMaxTime;
	}




	public int getCurrAttackMaxTime() {
		return currAttackMaxTime;
	}




	public void setCurrAttackMaxTime(int currAttackMaxTime) {
		this.currAttackMaxTime = currAttackMaxTime;
	}




	public int getAttackValue() {
		return attackValue;
	}




	public void setAttackValue(int attackValue) {
		this.attackValue = attackValue;
	}




	public int getDefendValue() {
		return defendValue;
	}




	public void setDefendValue(int defendValue) {
		this.defendValue = defendValue;
	}




	public int getMinGrid() {
		return minGrid;
	}




	public void setMinGrid(int minGrid) {
		this.minGrid = minGrid;
	}




	public int getMaxGrid() {
		return maxGrid;
	}




	public void setMaxGrid(int maxGrid) {
		this.maxGrid = maxGrid;
	}




	public int getMorale() {
		return morale;
	}




	public void setMorale(int morale) {
		this.morale = morale;
	}




	public int getSide() {
		return side;
	}




	public void setSide(int side) {
		this.side = side;
	}




	public String getSkillType() {
		return skillType;
	}




	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

}
