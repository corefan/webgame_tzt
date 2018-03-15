package com.snail.webgame.game.common;

public class GateServerInfo {

	
	private int gateServerId;
	private int groupServerId;
	private String romateIP;
	private int romatePort;
	private int num;
	private int encryptType;
	private String encryptCode;
	private int cryptoType;
	
	public int getGateServerId() {
		return gateServerId;
	}
	public void setGateServerId(int gateServerId) {
		this.gateServerId = gateServerId;
	}
	 
	public int getGroupServerId() {
		return groupServerId;
	}
	public void setGroupServerId(int groupServerId) {
		this.groupServerId = groupServerId;
	}
	
	
	public String getRomateIP() {
		return romateIP;
	}
	public void setRomateIP(String romateIP) {
		this.romateIP = romateIP;
	}
	public int getRomatePort() {
		return romatePort;
	}
	public void setRomatePort(int romatePort) {
		this.romatePort = romatePort;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getEncryptType() {
		return encryptType;
	}
	public void setEncryptType(int encryptType) {
		this.encryptType = encryptType;
	}
	public String getEncryptCode() {
		return encryptCode;
	}
	public void setEncryptCode(String encryptCode) {
		this.encryptCode = encryptCode;
	}
	public int getCryptoType() {
		return cryptoType;
	}
	public void setCryptoType(int cryptoType) {
		this.cryptoType = cryptoType;
	}
 
	
}
