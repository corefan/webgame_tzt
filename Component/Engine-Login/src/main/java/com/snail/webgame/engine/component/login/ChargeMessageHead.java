package com.snail.webgame.engine.component.login;

import org.epilot.ccf.core.protocol.MessageHeader;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ChargeMessageHead extends MessageHeader{
	private int Length;
	private int Version;
	private int UserID0;
	private int UserID1;
	private int UserID2;
	private int UserID3;
	private int reserve;
	private int MsgType ;

	public long getProtocolId() {
		
		return MsgType;
	}
	public void setProtocolId(long protocolId) {
		
		this.MsgType = (int) protocolId;
	}

 
	public int getLength() {
		return Length;
	}
	public void setLength(int length) {
		Length = length;
	}
	public int getVersion() {
		return Version;
	}
	public void setVersion(int version) {
		Version = version;
	}
	public int getUserID0() {
		return UserID0;
	}
	public void setUserID0(int userID0) {
		UserID0 = userID0;
	}
	public int getUserID1() {
		return UserID1;
	}
	public void setUserID1(int userID1) {
		UserID1 = userID1;
	}
	public int getUserID2() {
		return UserID2;
	}
	public void setUserID2(int userID2) {
		UserID2 = userID2;
	}
	public int getUserID3() {
		return UserID3;
	}
	public void setUserID3(int userID3) {
		UserID3 = userID3;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	public int getMsgType() {
		return MsgType;
	}
	public void setMsgType(int msgType) {
		MsgType = msgType;
	}
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		
	 
		ps.add("Version", 1);
		ps.add("UserID0", 1);
		ps.add("UserID1", 1);
		ps.add("UserID2", 1);
		ps.add("UserID3", 1);
		ps.add("reserve", 1);
		ps.add("MsgType", 1);
	}
}
