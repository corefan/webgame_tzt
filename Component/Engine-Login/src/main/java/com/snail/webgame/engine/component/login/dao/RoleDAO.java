package com.snail.webgame.engine.component.login.dao;


import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.db.SqlMapDaoSupport;

public class RoleDAO extends SqlMapDaoSupport {
	
	/**
	 * 查询角色
	 * @param account 帐号名称
	 * @return
	 */
	public RoleInfo getRoleInfo(String account){
		RoleInfo role = new RoleInfo();
		role.setAccount(account);
		role = (RoleInfo)getSqlMapClient("GAME_DB").query("selectRole", role);
		return role;
	}

	/**
	 * 判断角色名称是否存在
	 * @param roleName 角色名称
	 * @return
	 */
	public boolean checkRoleName(String roleName) {
		
		RoleInfo role = new RoleInfo();
		role.setRoleName(roleName);
		role = (RoleInfo)getSqlMapClient("GAME_DB").query("selectRoleByRoleName", role);
		if(role != null)
			return true;
		else
			return false;
	}
}
