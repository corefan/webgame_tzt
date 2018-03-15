package com.snail.webgame.engine.component.login.charge.total;

 
import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class TotalOnlineReq extends MessageBody{

 
	private int argNum;
	private int msgType;
	private String time;
	private int playNum;
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("argNum", 1);
		ps.add("msgType", 1);
		ps.addString("time", "chargeCode", 1);
		ps.add("playNum", 1);	
		
		
	}
	public int getArgNum() {
		return argNum;
	}
	public void setArgNum(int argNum) {
		this.argNum = argNum;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getPlayNum() {
		return playNum;
	}
	public void setPlayNum(int playNum) {
		this.playNum = playNum;
	}
	
}
