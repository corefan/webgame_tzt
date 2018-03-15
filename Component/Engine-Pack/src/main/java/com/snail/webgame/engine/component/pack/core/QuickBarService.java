package com.snail.webgame.engine.component.pack.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.info.QuickBarInfo;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.pack.dao.RoleQuickBarDAO;

public class QuickBarService {
	private static RoleQuickBarDAO dao = new RoleQuickBarDAO();
	
	/**
	 * 添加快捷栏关联关系
	 * @param quickBarInfo
	 * @param roleInfo
	 * @author wangxf
	 * @date 2012-9-6
	 */
	public static int addQuickBarRel(QuickBarInfo quickBarInfo,
			RoleInfo roleInfo) {
		int result = -1;
		long id = quickBarInfo.getId();
		// 新增的快捷栏
		if (id == -1) {
			// 在数据库中保存该记录
			if (dao.addQuickBarRel(quickBarInfo)) {
				// 放入角色对象缓存中
				roleInfo.getQuickBarMap().put(quickBarInfo.getId(), quickBarInfo);
				result = 1;
			}
			else {
				// 数据库更新失败，返回系统错误
				result = ErrorCode.SYSTEM_ERROR;
			}
		}
		// 更换已有快捷栏的对应关系
		else {
			// 如果角色对象缓存中没有该快捷栏，则返回错误
			if (!roleInfo.getQuickBarMap().containsKey(id)) {
				result = ErrorCode.MODIFY_ROLE_QUICKBAR_ERROR_3;
			}
			else {
				// 存在的话更新数据库
				if (dao.updateQuickBar(quickBarInfo)) {
					// 更新缓存对象
					roleInfo.getQuickBarMap().put(quickBarInfo.getId(), quickBarInfo);
					result = 1;
				}
				else {
					// 数据库更新失败，返回系统错误
					result = ErrorCode.SYSTEM_ERROR;
				}
			}
		}
		
		return result;
		
	}

	/**
	 * 删除快捷栏关联关系
	 * @param id
	 * @param roleInfo
	 * @author wangxf
	 * @date 2012-9-6
	 */
	public static int delQuickBarRel(long id, RoleInfo roleInfo) {
		int result = -1;
		// 如果角色对象缓存中没有该快捷栏，则返回错误
		if (!roleInfo.getQuickBarMap().containsKey(id)) {
			result = ErrorCode.MODIFY_ROLE_QUICKBAR_ERROR_3;
		}
		else {
			// 从数据库中删除该记录
			if (dao.delQuickBarRel(id)) {
				// 删除角色缓存中的记录
				roleInfo.getQuickBarMap().remove(id);
				result = 1;
			}
			else {
				result = ErrorCode.SYSTEM_ERROR;
			}
		}
		
		return result;
		
	}

	/**
	 * 变更快捷栏格子
	 * @param roleInfo
	 * @param oldQuickBarNo
	 * @param newQuickBarNo
	 * @author wangxf
	 * @date 2012-9-6
	 */
	public static int exchangeQuickBar(RoleInfo roleInfo, int oldQuickBarNo,
			int newQuickBarNo) {
		int result = -1;
		// 新旧编号一样，不做处理
		if (oldQuickBarNo == newQuickBarNo) {
			result = 1;
			return result;
		}
		// 查询原快捷栏在角色对象缓存中是否存在
		long oldId = queryQuickBarInRole(roleInfo, oldQuickBarNo);
		// 如果不存在，则返回错误信息
		if (oldId == -1) {
			result = ErrorCode.MODIFY_ROLE_QUICKBAR_ERROR_3;
		}
		// 存在该快捷栏
		else {
			// 原快捷栏对象
			QuickBarInfo oldQuickBar = roleInfo.getQuickBarMap().get(oldId);
			// 查询新快捷栏对象是否存在
			long newId = queryQuickBarInRole(roleInfo, newQuickBarNo);
			// 存在，更新2个对象的快捷栏编号
			if (newId != -1) {
				// 新快捷栏对象
				QuickBarInfo newQuickBar = roleInfo.getQuickBarMap().get(newId);
				QuickBarInfo oldClone = (QuickBarInfo) oldQuickBar.clone();
				QuickBarInfo newClone = (QuickBarInfo) newQuickBar.clone();
				oldClone.setQuickBarNo(newQuickBarNo);
				newClone.setQuickBarNo(oldQuickBarNo);
				// 更新数据库记录
				if (dao.updateQuickBar(oldClone) && dao.updateQuickBar(newClone)) {
					// 更新缓存
					roleInfo.getQuickBarMap().put(oldClone.getId(), oldClone);
					roleInfo.getQuickBarMap().put(newClone.getId(), newClone);
					result = 1;
				}
				else {
					result = ErrorCode.SYSTEM_ERROR;
				}
			}
			// 不存在
			else {
				// 更新原快捷栏对象的快捷栏编号就可以
				// 克隆一个旧对象
				QuickBarInfo clone = (QuickBarInfo) oldQuickBar.clone();
				// 更新快捷栏编号为新的编号
				clone.setQuickBarNo(newQuickBarNo);
				// 更新数据库中该快捷栏的记录
				if (dao.updateQuickBar(clone)) {
					// 更新缓存
					roleInfo.getQuickBarMap().put(clone.getId(), clone);
					result = 1;
				}
				else {
					result = ErrorCode.SYSTEM_ERROR;
				}
			}
		}
		return result;
	}

	/**
	 * 根据快捷栏编号查询该快捷栏是否已创建
	 * @param roleInfo
	 * @param oldQuickBarNo
	 * @return
	 * @author wangxf
	 * @date 2012-9-6
	 */
	private static long queryQuickBarInRole(RoleInfo roleInfo, int oldQuickBarNo) {
		long id = -1;
		Map<Long, QuickBarInfo> quickBarMap = roleInfo.getQuickBarMap();
		for (Entry<Long, QuickBarInfo> entry : quickBarMap.entrySet()) {
			QuickBarInfo tmpObj = entry.getValue();
			if (tmpObj != null) {
				if (tmpObj.getQuickBarNo() == oldQuickBarNo) {
					id = tmpObj.getId();
					return id;
				}
			}
			
		}
		return id;
	}
	
	/**
	 * 将快捷栏信息加入到角色对象属性中
	 * @param quickBarList
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * 2012-8-31
	 */
	public static Map<Long, QuickBarInfo> addQuickBarToRole(
			List<QuickBarInfo> quickBarList, RoleInfo roleInfo) {
		Map<Long, QuickBarInfo> quickBarMap = roleInfo.getQuickBarMap();
		if (quickBarMap == null) {
			quickBarMap = new HashMap<Long, QuickBarInfo>();
			roleInfo.setQuickBarMap(quickBarMap);
		}
		for (QuickBarInfo quickBarInfo : quickBarList) {
			if (quickBarInfo != null) {
				quickBarMap.put(quickBarInfo.getId(), quickBarInfo);
			}
		 }
		return quickBarMap;
	}

	/**
	 * 根据关联id查询快捷栏id
	 * @param relId
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * @param relType 
	 * @date 2012-9-12
	 */
	public static long queryQuickBarId(long relId, int relType, RoleInfo roleInfo) {
		long quickBarId = -1;
		Map<Long, QuickBarInfo> quickBarMap = roleInfo.getQuickBarMap();
		if (quickBarMap != null && quickBarMap.size() > 0) {
			for (Entry<Long, QuickBarInfo> entry : quickBarMap.entrySet()) {
				QuickBarInfo tmpObj = entry.getValue();
				if (tmpObj.getRelType() == relType && tmpObj.getRelId() == relId) {
					quickBarId = tmpObj.getId();
					return quickBarId;
				}
			}
		}
		return quickBarId;
	}
}
