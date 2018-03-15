package com.snail.webgame.game.gatemgt.protocal.active;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ActiveReq extends MessageBody {

 
	private String serverName;
	private int gateServerId;
	private int flag;
	private int groupServerId;
	private String romateIP;
	private int romatePort;
	private int encryptType;
	private String encryptCode;
	private int num;
	private int cryptoType;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.addString("serverName", "flashCode", 0);
		ps.add("gateServerId", 0);
		ps.add("flag", 0);
		ps.add("groupServerId", 0);
		ps.addString("romateIP",  "flashCode", 0);
		ps.add("romatePort", 0);
		ps.add("encryptType", 0);
		ps.addString("encryptCode","flashCode", 0);
		ps.add("num", 0);
		ps.add("cryptoType", 0);
	}


	public String getServerName() {
		return serverName;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	public int getGateServerId() {
		return gateServerId;
	}


	public void setGateServerId(int gateServerId) {
		this.gateServerId = gateServerId;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
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


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public int getCryptoType() {
		return cryptoType;
	}


	public void setCryptoType(int cryptoType) {
		this.cryptoType = cryptoType;
	}
	 

}
