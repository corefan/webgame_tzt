package com.snail.webgame.engine.component.pack.dao;

import java.util.Map;

import com.snail.webgame.engine.common.info.QuickBarInfo;
import com.snail.webgame.engine.db.SqlMapDaoSupport;

public class RoleQuickBarDAO extends SqlMapDaoSupport {
	
	/**
	 * 查询该角色的快捷栏信息
	 * @param roleId 角色ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Long, QuickBarInfo> getQuickBarByRoleId(long roleId) {
		QuickBarInfo quickBarInfo = new QuickBarInfo();
		quickBarInfo.setRoleId(roleId);
		Map<Long, QuickBarInfo> quickBarList = null;
		quickBarList = getSqlMapClient("GAME_DB").queryMap("selectQuickBarByRoleId", quickBarInfo, "id");
		return quickBarList;
	}
	
	/**
	 * 添加快捷栏对应关系
	 * @param info
	 * @return
	 * @author wangxf
	 * 2012-9-5
	 */
	public boolean addQuickBarRel(QuickBarInfo info) {
		return getSqlMapClient("GAME_DB").insert("insertQuickBar", info);
	}

	/**
	 * 删除快捷栏关联关系
	 * @param id
	 * @return
	 * @author wangxf
	 * @date 2012-9-6
	 */
	public boolean delQuickBarRel(long id) {
		QuickBarInfo info = new QuickBarInfo();
		info.setId(id);
		return getSqlMapClient("GAME_DB").delete("deleteQuickBar", info);
	}

	/**
	 * 更新快捷栏
	 * @param quickBarInfo
	 * @return
	 * @author wangxf
	 * @date 2012-9-6
	 */
	public boolean updateQuickBar(QuickBarInfo quickBarInfo) {
		return getSqlMapClient("GAME_DB").update("updateQuickBar", quickBarInfo);
	}
	
	/**
	 * 更新角色快捷栏信息
	 * @param roleId
	 * @author wangxf
	 * @param QuickBarGirdMap 
	 * @date 2012-8-27
	 *//*
	public void update(long roleId, Map<Integer, QuickBarInfo> quickBarMap) {
		// 查询数据库中该角色的快捷栏信息
		List<QuickBarInfo> oldQuickBarList = getQuickBarByRoleId(roleId);
		// 新增加的快捷栏对象
		List<Object> addQuickBarList = new ArrayList<Object>();
		// 删除的快捷栏对象
		List<Object> delQuickBarList = new ArrayList<Object>();
		// 更新的快捷栏格子对象
		List<QuickBarInfo> updateQuickBarList = new ArrayList<QuickBarInfo>();
		// 不需要删除快捷栏格子对象
		List<QuickBarInfo> noDelQuickBarList = new ArrayList<QuickBarInfo>();
		if (quickBarMap != null && quickBarMap.size() > 0) {
			for (Entry<Integer, QuickBarInfo> entry : quickBarMap.entrySet()) {
				QuickBarInfo newQuickBarInfo = entry.getValue();
				if (newQuickBarInfo != null) {
					// 如果旧快捷栏集合中没有对象，则表示是新增加的快捷栏格子对象
					if (oldQuickBarList == null || oldQuickBarList.size() == 0) {
						addQuickBarList.add(newQuickBarInfo);
						// 跳出本次循环
						continue;
					}
					for (int i = 0; i < oldQuickBarList.size(); i++) {
						QuickBarInfo oldQuickBarInfo = oldQuickBarList.get(i);
						// 如果新快捷栏对象跟数据库中的快捷栏对象主键相同
						if (newQuickBarInfo.getId() == oldQuickBarInfo.getId()) {
							// 如果新快捷栏对象属性存在变动，则加入需要更新的list中
							if (newQuickBarInfo.getQuickBarNo() != oldQuickBarInfo.getQuickBarNo() || 
									newQuickBarInfo.getPackSort() != oldQuickBarInfo.getPackSort() || 
									newQuickBarInfo.getGirdNo() != oldQuickBarInfo.getGirdNo()) {
								updateQuickBarList.add(newQuickBarInfo);
							}
							// 存在主键一样的，则就是不需要删除的
							noDelQuickBarList.add(oldQuickBarInfo);
							// 结束内循环
							break;
						}
						// 如果数据库中没有该格子对象，则是新增加的格子对象，放入新增加的list中
						else if (i == oldQuickBarList.size() - 1) {
							addQuickBarList.add(newQuickBarInfo);
						}
					}
				}
			}
		}
		// 删除需要更新的，剩下的就是需要删除的
		oldQuickBarList.removeAll(noDelQuickBarList);
		// 如果旧快捷栏对象集合中还有剩下的对象，则是需要删除的对象，加入到需要删除的list中
		if (oldQuickBarList != null && oldQuickBarList.size() > 0) {
			for(QuickBarInfo delQuickBar : oldQuickBarList) {
				delQuickBarList.add(delQuickBar);
			}
		}
		
		if (addQuickBarList.size() > 0) {
			getSqlMapClient("GAME_DB").insertBatch("insertQuickBarBatch", addQuickBarList);
		}
		
		if (delQuickBarList.size() > 0) {
			getSqlMapClient("GAME_DB").deleteBatch("deleteQuickBarBatch", delQuickBarList);
		}
		
		if (updateQuickBarList.size() > 0) {
			for (QuickBarInfo updateQuickBar : updateQuickBarList) {
				getSqlMapClient("GAME_DB").update("updateQuickBar", updateQuickBar);
			}
		}
		
	}*/
	
}
