package com.snail.webgame.engine.component.pack.dao;


import java.util.List;
import java.util.Map;

import com.snail.webgame.engine.common.info.PackInfo;
import com.snail.webgame.engine.db.SqlMapDaoSupport;

public class RolePackDAO extends SqlMapDaoSupport {
	
	/**
	 * 查询该角色的物品信息
	 * @param roleId 角色ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Long, PackInfo> getPackByRoleId(long roleId) {
		PackInfo packInfo = new PackInfo();
		packInfo.setRoleId(roleId);
		Map<Long, PackInfo> packMap = null;
		packMap = getSqlMapClient("GAME_DB").queryMap("selectPackByRoleId", packInfo, "id");
		return packMap;
	}
	
	@SuppressWarnings("unchecked")
	public List<PackInfo> getAllPackInfo() {
		List<PackInfo> listItems = null;
		listItems = getSqlMapClient("GAME_DB").queryList("selectAllPack");
		return listItems;
	}
	
	/**
	 * 添加包裹关系
	 * @param packGirdInfo
	 * @return
	 * @author wangxf
	 * 2012-9-5
	 */
	public boolean addPackRel(PackInfo packInfo) {
		return getSqlMapClient("GAME_DB").insert("insertPack", packInfo);
	}
	
	/**
	 * 更新背包关系
	 * @param packInfo
	 * @return
	 * @author wangxf
	 * @date 2012-9-6
	 */
	public boolean updatePack(PackInfo packInfo) {
		return getSqlMapClient("GAME_DB").update("updatePack", packInfo);
	}
	
	public boolean deletePack(PackInfo packInfo) {
		return getSqlMapClient("GAME_DB").delete("deletePack", packInfo);
	}
	
}
