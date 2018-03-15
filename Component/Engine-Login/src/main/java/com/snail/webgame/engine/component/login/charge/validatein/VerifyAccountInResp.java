package com.snail.webgame.engine.component.login.charge.validatein;
import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * 验证帐号的应答消息体
 * @author tangjie
 * @version 1.0
 * @created 24-三月-2008 11:38:11
 */
public class VerifyAccountInResp extends MessageBody {

	

	private int accType;
	private String account;
	private int result;
	private int accountId;
	private String loginId;
	private String userName;
	private int gmLevel;
	private String passport;
	private int points;
	private double accLimit;
	private int isFree;
	private String accInfo;
	private int loginType;
	private String hMacstr;
	private int issuerID;
 
	/**
	 * 设置协议字段顺序
	 * @return
	 * @param ps
	 */
	protected void setSequnce(ProtocolSequence ps){
		

		ps.add("accType", 1);
		ps.addString("account", "chargeCode",1);
		ps.add("result", 1);
		ps.add("accountId", 1);
		ps.addString("loginId", "chargeCode",1);
		ps.addString("userName", "chargeCode",1);
		ps.add("gmLevel", 1);
		ps.addString("passport", "chargeCode",1);
		ps.add("points", 1);
		ps.add("accLimit", 1);
		ps.add("isFree", 1);
		ps.addString("accInfo", "chargeCode",1);
		ps.add("loginType", 1);
		ps.addString("hMacstr", "chargeCode", 1);
		ps.add("issuerID", 1);
	}

	public int getAccType() {
		return accType;
	}

	public void setAccType(int accType) {
		this.accType = accType;
	}

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

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGmLevel() {
		return gmLevel;
	}

	public void setGmLevel(int gmLevel) {
		this.gmLevel = gmLevel;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public double getAccLimit() {
		return accLimit;
	}

	public void setAccLimit(double accLimit) {
		this.accLimit = accLimit;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

	public String getAccInfo() {
		return accInfo;
	}

	public void setAccInfo(String accInfo) {
		this.accInfo = accInfo;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getHMacstr() {
		return hMacstr;
	}

	public void setHMacstr(String macstr) {
		hMacstr = macstr;
	}

	public int getIssuerID() {
		return issuerID;
	}

	public void setIssuerID(int issuerID) {
		this.issuerID = issuerID;
	}
	
}