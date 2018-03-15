package com.snail.webgame.engine.component.login.charge.validate;
import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * 验证帐号（不登录）的请求消息体
 * @author tangjie
 * @version 1.0
 * @created 24-三月-2008 11:38:11
 */
public class VerifyAccountReq extends MessageBody {


 
	private int accType;
	private String account;
	private String md5Pass;
	private String address;
	private int port;
	private int vendorId;
	private String validate;
	private int clientType;
 

	public VerifyAccountReq(){
		
	}

	/**
	 * 设置协议字段顺序
	 * @return
	 * 
	 * @param ps
	 */
	protected void setSequnce(ProtocolSequence ps){
	 
		ps.add("accType", 1);
		ps.addString("account", "chargeCode",1);
		ps.addString("md5Pass", "chargeCode",1);
		ps.addString("address", "chargeCode",1);
		ps.add("port", 1);
		ps.add("vendorId", 1);
		ps.addString("validate", "chargeCode",1);
		ps.add("clientType", 1);
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

	public String getMd5Pass() {
		return md5Pass;
	}

	public void setMd5Pass(String md5Pass) {
		this.md5Pass = md5Pass;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
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

	
}