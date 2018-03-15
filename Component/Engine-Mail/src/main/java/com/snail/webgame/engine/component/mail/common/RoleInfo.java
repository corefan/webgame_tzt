package com.snail.webgame.engine.component.mail.common;

public class RoleInfo {

	private int roleId;
	private String roleName;
 
	private int raceId;
	private int gateServerId;
	private int chatStatus;
	private long chatTime;
	private long lastCommChatTime;
	private long lastWorldChatTime;
	private long lastNipChatTime;
	private int vendorId;
	private int vipLevel;
	private int snailVipLevel;
	private byte snailVipSwitch;
	
	
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public int getSnailVipLevel() {
		return snailVipLevel;
	}
	public void setSnailVipLevel(int snailVipLevel) {
		this.snailVipLevel = snailVipLevel;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
 
	public int getGateServerId() {
		return gateServerId;
	}
	public void setGateServerId(int gateServerId) {
		this.gateServerId = gateServerId;
	}
 
 
	public int getChatStatus() {
		return chatStatus;
	}
	public void setChatStatus(int chatStatus) {
		this.chatStatus = chatStatus;
	}
	public long getChatTime() {
		return chatTime;
	}
	public void setChatTime(long chatTime) {
		this.chatTime = chatTime;
	}
	public long getLastCommChatTime() {
		return lastCommChatTime;
	}
	public void setLastCommChatTime(long lastCommChatTime) {
		this.lastCommChatTime = lastCommChatTime;
	}
	public long getLastWorldChatTime() {
		return lastWorldChatTime;
	}
	public void setLastWorldChatTime(long lastWorldChatTime) {
		this.lastWorldChatTime = lastWorldChatTime;
	}
	public long getLastNipChatTime()
	{
		return lastNipChatTime;
	}
	public void setLastNipChatTime(long lastNipChatTime)
	{
		this.lastNipChatTime = lastNipChatTime;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public byte getSnailVipSwitch() {
		return snailVipSwitch;
	}
	public void setSnailVipSwitch(byte snailVipSwitch) {
		this.snailVipSwitch = snailVipSwitch;
	}
	
}
