package com.snail.webgame.engine.component.login.protocal.verify;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UserVerifyReq extends MessageBody {

	private int IP;
	private String account;
	private String md5Pass;
	private int verifyType;
	private String extendPass;
	private String validate;
	private int clientType;
	private String Reserved;
	
	protected void setSequnce(ProtocolSequence ps) {
		 
		ps.add("IP", 0);
		ps.addString("account", "flashCode", 0)	;
		ps.addString("md5Pass", "flashCode", 0)	;
		ps.add("verifyType", 0);
		ps.addString("extendPass", "flashCode", 0)	;
		ps.addString("validate", "flashCode", 0)	;		
		ps.add("clientType", 0);		
		ps.addString("Reserved", "flashCode", 0)	;
		
	}

	public int getIP() {
		return IP;
	}

	public void setIP(int ip) {
		IP = ip;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMd5Pass() {
		return md5Pass;
	}

	public void setMd5Pass(String md5Pass) {
		this.md5Pass = md5Pass;
	}

	public int getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(int verifyType) {
		this.verifyType = verifyType;
	}

	public String getExtendPass() {
		return extendPass;
	}

	public void setExtendPass(String extendPass) {
		this.extendPass = extendPass;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public String getReserved() {
		return Reserved;
	}

	public void setReserved(String reserved) {
		Reserved = reserved;
	}

}
