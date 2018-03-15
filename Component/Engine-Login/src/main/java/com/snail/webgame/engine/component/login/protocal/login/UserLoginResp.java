package com.snail.webgame.engine.component.login.protocal.login;

 
import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UserLoginResp extends MessageBody {

	 
	private int result;
	private int roleId;
	private int sceneId;
	private String roleName;
	private int vendorId;
	
	protected void setSequnce(ProtocolSequence ps) {
		 
		ps.add("result", 0);
		ps.add("roleId", 0);
		ps.add("sceneId", 0);
		ps.addString("roleName", "flashCode", 0);
		ps.add("vendorId", 0);
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
