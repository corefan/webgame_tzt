package com.snail.webgame.engine.component.scene.protocal.fight.in;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RoleInFightArmy extends MessageBody{

	private int roleId;
	private String rolePic;
	private String roleName;
	private int roleLevel;
	private int roleRace;
	private String armyId;
	private int heroNo;
	private int side;
	private int count;
	private List list;
	
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("roleId", 0);
		ps.addString("rolePic", "flashCode", 0);
		ps.addString("roleName", "flashCode", 0);
		ps.add("roleLevel", 0);
		ps.add("roleRace", 0);
		ps.addString("armyId", "flashCode", 0);
		ps.add("heroNo", 0);
		ps.add("side", 0);
		ps.add("count", 0);
		ps.addObjectArray("list", "com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightRe", "count");
		
	}

	public int getRoleId() {
		return roleId;
	}

	public int getHeroNo() {
		return heroNo;
	}

	public void setHeroNo(int heroNo) {
		this.heroNo = heroNo;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRolePic() {
		return rolePic;
	}

	public void setRolePic(String rolePic) {
		this.rolePic = rolePic;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public int getRoleRace() {
		return roleRace;
	}

	public void setRoleRace(int roleRace) {
		this.roleRace = roleRace;
	}

	public String getArmyId() {
		return armyId;
	}

	public void setArmyId(String armyId) {
		this.armyId = armyId;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

}
