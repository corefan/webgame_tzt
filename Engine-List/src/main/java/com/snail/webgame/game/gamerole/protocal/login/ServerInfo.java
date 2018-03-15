package com.snail.webgame.game.gamerole.protocal.login;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ServerInfo extends MessageBody {


	private String serverName;
	private int groupServerId;
 	private int gateServerId;
 	private int encryptType;
 	private String encryptCode;
 	private String romateIP;
 	private int romatePort;
 	private int status;
 	private byte[] clientData;
	private String reserved;
	
	protected void setSequnce(ProtocolSequence ps) {
		 
	
		ps.addString("serverName", "flashCode", 0);
		ps.add("groupServerId",0);
	 	ps.add("gateServerId", 0);
	 	ps.add("encryptType",0);
	 	ps.addString("encryptCode", "flashCode",0);
	 	ps.addString("romateIP", "flashCode", 0);
		ps.add("romatePort",0);
		ps.add("status",0);
		ps.add("clientData", 0);
		ps.addString("reserved", "flashCode", 0);
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

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	 
	public int getGroupServerId() {
		return groupServerId;
	}

	public void setGroupServerId(int groupServerId) {
		this.groupServerId = groupServerId;
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



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public byte[] getClientData() {
		return clientData;
	}



	public void setClientData(byte[] clientData) {
		this.clientData = clientData;
	}


 

 

}
