package com.snail.webgame.engine.common.info;

public class UseItemInfo {

	private int itemId; // 物品ID
	private String function; // 功能类型
	private int effectTime; // 道具效果持续时间
	private int effectTimeUse; // 已使用效果时间
	private long userTime; // 道具开始使用时间
	private long endTime; // 道具结束时间
	private String Parameter1;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public int getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(int effectTime) {
		this.effectTime = effectTime;
	}

	public int getEffectTimeUse() {
		return effectTimeUse;
	}

	public void setEffectTimeUse(int effectTimeUse) {
		this.effectTimeUse = effectTimeUse;
	}

	public long getUserTime() {
		return userTime;
	}

	public void setUserTime(long userTime) {
		this.userTime = userTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getParameter1() {
		return Parameter1;
	}

	public void setParameter1(String parameter1) {
		Parameter1 = parameter1;
	}

}
