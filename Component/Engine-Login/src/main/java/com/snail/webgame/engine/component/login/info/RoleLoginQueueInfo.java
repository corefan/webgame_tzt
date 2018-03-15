package com.snail.webgame.engine.component.login.info;

public class RoleLoginQueueInfo 
{
	private int IP;
	private String account;//玩家登陆帐号
	private String MD5Pass;
	private String validate;
	private int clientType;
	private String Reserved;
	private int gateServerId;
	private String roleName;
	private int roleId;
	private String chargeAccount;
	private int sequenceId;
	
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
	public int getGateServerId() {
		return gateServerId;
	}
	public void setGateServerId(int gateServerId) {
		this.gateServerId = gateServerId;
	}
	public String getMD5Pass() {
		return MD5Pass;
	}
	public void setMD5Pass(String pass) {
		MD5Pass = pass;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getChargeAccount() {
		return chargeAccount;
	}
	public void setChargeAccount(String chargeAccount) {
		this.chargeAccount = chargeAccount;
	}
	public int getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}

}
