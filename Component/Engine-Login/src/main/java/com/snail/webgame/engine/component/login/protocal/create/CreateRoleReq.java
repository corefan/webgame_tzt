package com.snail.webgame.engine.component.login.protocal.create;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class CreateRoleReq extends MessageBody{
	
	private int IP;
	private String account;
	private String MD5Pass;
	private int verifyType;
	private String extendPass;
	private String validate;
	private int clientType;
	private String roleName;
	private String chargeAccount;
	

	protected void setSequnce(ProtocolSequence ps) 
	{
		ps.add("IP", 0);
		ps.addString("account", "flashCode", 0);
		ps.addString("MD5Pass", "flashCode", 0);
		ps.add("verifyType", 0);
		ps.addString("extendPass", "flashCode", 0);
		ps.addString("validate", "flashCode", 0);
		ps.add("clientType", 0);
		ps.addString("roleName", "flashCode", 0);
		ps.addString("chargeAccount", "flashCode", 0);
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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

	public String getMD5Pass() {
		return MD5Pass;
	}
	public void setMD5Pass(String pass) {
		MD5Pass = pass;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getRoleName() {
		return roleName;
	}
	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getIP() {
		return IP;
	}
	public void setIP(int ip) {
		IP = ip;
	}

	public String getChargeAccount() {
		return chargeAccount;
	}

	public void setChargeAccount(String chargeAccount) {
		this.chargeAccount = chargeAccount;
	}

}
