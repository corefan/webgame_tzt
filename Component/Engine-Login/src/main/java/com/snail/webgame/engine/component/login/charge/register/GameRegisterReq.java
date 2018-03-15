package com.snail.webgame.engine.component.login.charge.register;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class GameRegisterReq extends MessageBody{

 
	private int gameType;
	private int serverId;
	private String serverName;
	private String MD5Pass;
	private int isNewReg;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("gameType", 1);
		ps.add("serverId", 1);
		ps.addString("serverName", "chargeCode", 1);
		ps.addString("MD5Pass", "chargeCode", 1);
		ps.add("isNewReg", 1);
		
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getMD5Pass() {
		return MD5Pass;
	}

	public void setMD5Pass(String pass) {
		MD5Pass = pass;
	}

	public int getIsNewReg() {
		return isNewReg;
	}

	public void setIsNewReg(int isNewReg) {
		this.isNewReg = isNewReg;
	}

}
