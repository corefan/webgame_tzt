package com.snail.webgame.engine.component.login.charge.validate;
import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * 验证帐号（不登录）的应答消息体
 * @author tangjie
 * @version 1.0
 * @created 24-三月-2008 11:38:11
 */
public class VerfiyAccountResp extends MessageBody {

	

	private int accType;
	private String account;
	private int result;
	private int accountId;
	private String userName;
	private int gmLevel;
	private String passport;
	private int points;
	private double accLimit;
	private String accInfo;
	private String secondStr;//计费返回的串
 
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
		ps.addString("userName", "chargeCode",1);
		ps.add("gmLevel", 1);
		ps.addString("passport", "chargeCode",1);
		ps.add("points", 1);
		ps.add("accLimit", 1);
		ps.addString("accInfo", "chargeCode",1);
		ps.addString("secondStr", "chargeCode",1);
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
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
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getGmLevel() {
		return gmLevel;
	}
	public void setGmLevel(int gmLevel) {
		this.gmLevel = gmLevel;
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
	public String getAccInfo() {
		return accInfo;
	}
	public void setAccInfo(String accInfo) {
		this.accInfo = accInfo;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSecondStr() {
		return secondStr;
	}
	public void setSecondStr(String secondStr) {
		this.secondStr = secondStr;
	}
	 
}