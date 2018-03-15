package com.snail.webgame.engine.component.scene.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.snail.webgame.engine.common.pathfinding.astar.GameMap;
import com.snail.webgame.engine.component.scene.core.FightCalculate;

public class RoleFight {

	protected long fightId;
	protected int fightType;
	protected String roomId;
	protected int landForm;
	protected int maxAttackNum;
	protected int maxDefendNum;
	protected String fightMapType;
	protected ArmyInfo[] armyInfo;
	protected int sideNum;//交战方数目
	protected long endTime1;//游戏准备结束时间
	protected long endTime2;//游戏结束时间
	protected HashMap<Integer,Integer> controlMap = new HashMap<Integer,Integer>();
	protected ArrayList<Integer> roleList = new ArrayList<Integer>();

	protected ArmyPhalanx[] armyPhalanxs;
	protected boolean bool = false;//中途加入变量
	protected int queueStatus ;//再次确认是否战场结束 0-没有确认过 1-确认中 2-确认完毕
	protected int queueCurrTime ;
	protected int totalNum ;
	protected GameMap map;
	protected int side0;//中途加入出现点
	protected int side1;//中途加入出现点
	
	protected int visitNum ;// 观察人数
	protected String deadCityBuild = "";//被摧毁的城市建筑物ID（逗号分隔）
	protected boolean backFlag = false;//true 已经使用了撤退令
	
	protected HashMap<String, RevivePhalanx> revivePhalanxMap = new HashMap<String, RevivePhalanx>();//复活方阵
	
	protected FightCalculate fightCalculate;
	
	
	public long getFightId() {
		return fightId;
	}
	public void setFightId(long fightId) {
		this.fightId = fightId;
	}
	public int getFightType() {
		return fightType; 
	}
	public void setFightType(int fightType) {
		this.fightType = fightType;
	}
	public FightCalculate getFightCalculate() {
		return fightCalculate;
	}
	public void setFightCalculate(FightCalculate fightCalculate) {
		this.fightCalculate = fightCalculate;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public int getLandForm() {
		return landForm;
	}
	public void setLandForm(int landForm) {
		this.landForm = landForm;
	}
	public int getMaxAttackNum() {
		return maxAttackNum;
	}
	public void setMaxAttackNum(int maxAttackNum) {
		this.maxAttackNum = maxAttackNum;
	}
	public int getMaxDefendNum() {
		return maxDefendNum;
	}
	public void setMaxDefendNum(int maxDefendNum) {
		this.maxDefendNum = maxDefendNum;
	}
	public String getFightMapType() {
		return fightMapType;
	}
	public void setFightMapType(String fightMapType) {
		this.fightMapType = fightMapType;
	}
	public ArmyInfo[] getArmyInfo() {
		return armyInfo;
	}
	public void setArmyInfo(ArmyInfo[] armyInfo) {
		this.armyInfo = armyInfo;
	}
	public int getSideNum() {
		return sideNum;
	}
	public void setSideNum(int sideNum) {
		this.sideNum = sideNum;
	}
	public long getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(long endTime1) {
		this.endTime1 = endTime1;
	}
	public long getEndTime2() {
		return endTime2;
	}
	public void setEndTime2(long endTime2) {
		this.endTime2 = endTime2;
	}
	public HashMap<Integer, Integer> getControlMap() {
		return controlMap;
	}
	public void setControlMap(HashMap<Integer, Integer> controlMap) {
		this.controlMap = controlMap;
	}
	public ArrayList<Integer> getRoleList() {
		return roleList;
	}
	public void setRoleList(ArrayList<Integer> roleList) {
		this.roleList = roleList;
	}
	public ArmyPhalanx[] getArmyPhalanxs() {
		return armyPhalanxs;
	}
	public void setArmyPhalanxs(ArmyPhalanx[] armyPhalanxs) {
		this.armyPhalanxs = armyPhalanxs;
	}
	public boolean isBool() {
		return bool;
	}
	public void setBool(boolean bool) {
		this.bool = bool;
	}
	public int getQueueStatus() {
		return queueStatus;
	}
	public void setQueueStatus(int queueStatus) {
		this.queueStatus = queueStatus;
	}
	public int getQueueCurrTime() {
		return queueCurrTime;
	}
	public void setQueueCurrTime(int queueCurrTime) {
		this.queueCurrTime = queueCurrTime;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public GameMap getMap() {
		return map;
	}
	public void setMap(GameMap map) {
		this.map = map;
	}
	public int getSide0() {
		return side0;
	}
	public void setSide0(int side0) {
		this.side0 = side0;
	}
	public int getSide1() {
		return side1;
	}
	public void setSide1(int side1) {
		this.side1 = side1;
	}
	public int getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}
	public String getDeadCityBuild() {
		return deadCityBuild;
	}
	public void setDeadCityBuild(String deadCityBuild) {
		this.deadCityBuild = deadCityBuild;
	}
	public boolean isBackFlag() {
		return backFlag;
	}
	public void setBackFlag(boolean backFlag) {
		this.backFlag = backFlag;
	}
	 
	public ArmyPhalanx getArmyPhalanx(String phalanxId){
		if(this.armyPhalanxs != null && this.armyPhalanxs.length > 0){
			for(ArmyPhalanx armyPhalanx : this.armyPhalanxs){
				if(armyPhalanx != null && armyPhalanx.getPhalanxId().equals(phalanxId))
					return armyPhalanx;
			}
		}
		return null;
	}
	
	public ArmyInfo getArmyInfo(int roleId){
		if(this.armyInfo != null && this.armyInfo.length > 0){
			for(ArmyInfo armyInfo : this.armyInfo){
				if(armyInfo != null && armyInfo.getRoleId() == roleId){
					return armyInfo;
				}
			}
		}
		return null;
	}
	public ArmyInfo getArmyInfo(String armyId){
		if(this.armyInfo != null && this.armyInfo.length > 0){
			for(ArmyInfo armyInfo : this.armyInfo){
				if(armyInfo != null && armyInfo.getArmyId().equals(armyId)){
					return armyInfo;
				}
			}
		}
		return null;
	}
	
	public void addPhalanx(ArmyPhalanx phalanx){
		if(this.armyPhalanxs != null){
			for(int i = 0; i < this.armyPhalanxs.length; i ++){
				if(armyPhalanxs[i] == null){
					armyPhalanxs[i] = phalanx;
					totalNum ++;
					break;
				}
			}
		}
	}
	
	public HashMap<String, RevivePhalanx> getRevivePhalanxMap() {
		return revivePhalanxMap;
	}
	public void setRevivePhalanxMap(HashMap<String, RevivePhalanx> revivePhalanxMap) {
		this.revivePhalanxMap = revivePhalanxMap;
	}
	public void delPhalanx(String phalanxId){
		if(this.armyPhalanxs != null){
			for(int i = 0; i < this.armyPhalanxs.length; i ++){
				if(armyPhalanxs[i] != null && armyPhalanxs[i].getPhalanxId().equals(phalanxId)){
					armyPhalanxs[i] = null;
					totalNum --;
					break;
				}
			}
		}
	}
}
