package com.snail.webgame.game.gamerole.protocal.login;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UserLoginResp extends MessageBody {

	private String account;
	private int result;
	private int count;
	private List serverList;
 
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List getServerList() {
		return serverList;
	}

	public void setServerList(List serverList) {
		this.serverList = serverList;
	}

 
 
	protected void setSequnce(ProtocolSequence ps) {
	 
		
	
		ps.add("result", 0);
		ps.addString("account", "flashCode", 0);
		ps.add("count", 0);
		ps.addObjectArray("serverList", "com.snail.webgame.list.gamerole.login.ServerInfo", "count");

	
	}

}
