package com.snail.webgame.engine.component.pack.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.info.PackInfo;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.pack.dao.RolePackDAO;

/**
 * @author wangxf
 * 2012-8-29
 * 包裹逻辑处理类
 */
public class PackService {
	private static RolePackDAO dao = new RolePackDAO();
	
	/**
	 * 获取空格子编号并按大小排好序
	 * @param packGirdMap
	 * @return
	 * @author wangxf
	 * 2012-8-27
	 * @param maxGird 
	 */
	public static List<Integer> getEmptyGird(RoleInfo roleInfo) {
		Map<Long, PackInfo> packMap = roleInfo.getPackMap();
		int maxGird = roleInfo.getMaxGirdNum();
		List<Integer> emptyGirdList = new ArrayList<Integer>();
		for (int i = 1; i <= maxGird; i++) {
			emptyGirdList.add(i);
		}
		if (packMap != null && packMap.size() > 0) {
			for (Entry<Long, PackInfo> entry : packMap.entrySet()) {
				PackInfo tmpObj = entry.getValue();
				if (tmpObj != null) {
					emptyGirdList.remove(new Integer(tmpObj.getGirdNo()));
				}
			}
		}
		
		return emptyGirdList;
	}

	/**
	 * 将包裹信息放入角色包裹属性的map中
	 * @param packInfoList
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * 2012-8-31
	 */
	public static void addPackToRole(
			List<PackInfo> packInfoList, RoleInfo roleInfo) {
		Map<Long, PackInfo> packGirdMap = roleInfo.getPackMap();
		if (packGirdMap == null) {
			packGirdMap = new HashMap<Long, PackInfo>();
			roleInfo.setPackMap(packGirdMap);
		}
		for (PackInfo info : packInfoList) {
			packGirdMap.put(info.getId(), info);
		 }
	}

	/**
	 * 整理包裹
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * 2012-9-4
	 */
	public static void arrangePack(RoleInfo roleInfo) {
		// 获取角色包裹缓存对象
		Map<Long, PackInfo> packGirdMap = roleInfo.getPackMap();
		if (packGirdMap != null && packGirdMap.size() > 0) {
			List<PackInfo> packInfoList = convertPackMapToList(packGirdMap);
			// 对包裹中的物品排序
			Collections.sort(packInfoList);
			// 重置格子编号
			int girdNo = 1;
			for (PackInfo info : packInfoList) {
				info.setGirdNo(girdNo++);
			}
			// 更新数据库记录
			for (PackInfo info : packInfoList) {
				dao.updatePack(info);
			}
		}
	}
	
	/**
	 * 将存放包裹信息的map变成list
	 * @param packGirdMap
	 * @return
	 * @author wangxf
	 * 2012-8-31
	 */
	private static List<PackInfo> convertPackMapToList(
			Map<Long, PackInfo> packMap) {
		List<PackInfo> packList = new ArrayList<PackInfo>();
		for (Entry<Long, PackInfo> entry : packMap.entrySet()) {
			if (entry.getValue() != null) {
				packList.add(entry.getValue());
			}
		}
		return packList;
	}

	/**
	 * 调整包裹格子位置
	 * @param roleInfo
	 * @param oldGirdNo
	 * @param newGirdNo
	 * @return
	 * @author wangxf
	 * 2012-9-4
	 */
	public int exchangePack(RoleInfo roleInfo,
			int oldGirdNo, int newGirdNo) {
		int result = -1;
		// 新旧格子编号一样不做处理
		if (oldGirdNo == newGirdNo) {
			result = 1;
			return result;
		}
		// 查询原格子编号对应的背包对象ID
		long oldId = queryPackInRole(roleInfo, oldGirdNo);
		// 如果不存在，则返回错误信息
		if (oldId == -1) {
			result = ErrorCode.OPERATE_ROLE_PACK_ERROR_1;
		}
		// 存在该背包对象
		else {
			// 原背包对象
			PackInfo oldPackInfo = roleInfo.getPackMap().get(oldId);
			// 查询新背包编号对应的对象是否存在
			long newId = queryPackInRole(roleInfo, newGirdNo);
			// 存在，更新2个对象的格子编号
			if (newId != -1) {
				// 新背包对象
				PackInfo newPackInfo = roleInfo.getPackMap().get(newId);
				PackInfo oldClone = (PackInfo) oldPackInfo.clone();
				PackInfo newClone = (PackInfo) newPackInfo.clone();
				oldClone.setGirdNo(newGirdNo);
				newClone.setGirdNo(oldGirdNo);
				// 更新数据库记录
				if (dao.updatePack(oldClone) && dao.updatePack(newClone)) {
					// 更新缓存
					roleInfo.getPackMap().put(oldClone.getId(), oldClone);
					roleInfo.getPackMap().put(newClone.getId(), newClone);
					result = 1;
				}
				else {
					result = ErrorCode.SYSTEM_ERROR;
				}
			}
			// 不存在
			else {
				// 更新原被告对象的格子编号就可以
				// 克隆一个旧对象
				PackInfo clone = (PackInfo) oldPackInfo.clone();
				// 更新快捷栏编号为新的编号
				clone.setGirdNo(newGirdNo);
				// 更新数据库中该背包的记录
				if (dao.updatePack(clone)) {
					// 更新缓存
					roleInfo.getPackMap().put(clone.getId(), clone);
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
	 * @param oldGirdNo
	 * @param newGirdNo
	 * @param changeList
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * 2012-9-12
	 */
	public static int exchangePack(int oldGirdNo, int newGirdNo,
			List<PackInfo> changeList, RoleInfo roleInfo) {
		int result = -1;
		// 新旧格子编号一样不做处理
		if (oldGirdNo == newGirdNo) {
			result = 1;
			return result;
		}
		// 查询原格子编号对应的背包对象ID
		long oldId = queryPackInRole(roleInfo, oldGirdNo);
		// 如果不存在，则返回错误信息
		if (oldId == -1) {
			result = ErrorCode.OPERATE_ROLE_PACK_ERROR_1;
		}
		// 存在该背包对象
		else {
			// 原背包对象
			PackInfo oldPackInfo = roleInfo.getPackMap().get(oldId);
			// 查询新背包编号对应的对象是否存在
			long newId = queryPackInRole(roleInfo, newGirdNo);
			// 存在，更新2个对象的格子编号
			if (newId != -1) {
				// 新背包对象
				PackInfo newPackInfo = roleInfo.getPackMap().get(newId);
				PackInfo oldClone = (PackInfo) oldPackInfo.clone();
				PackInfo newClone = (PackInfo) newPackInfo.clone();
				oldClone.setGirdNo(newGirdNo);
				newClone.setGirdNo(oldGirdNo);
				// 更新数据库记录
				if (dao.updatePack(oldClone) && dao.updatePack(newClone)) {
					// 更新缓存
					roleInfo.getPackMap().put(oldClone.getId(), oldClone);
					roleInfo.getPackMap().put(newClone.getId(), newClone);
					changeList.add(oldClone);
					changeList.add(newClone);
					result = 1;
				}
				else {
					result = ErrorCode.SYSTEM_ERROR;
				}
			}
			// 不存在
			else {
				// 更新原被告对象的格子编号就可以
				// 克隆一个旧对象
				PackInfo clone = (PackInfo) oldPackInfo.clone();
				// 更新快捷栏编号为新的编号
				clone.setGirdNo(newGirdNo);
				// 更新数据库中该背包的记录
				if (dao.updatePack(clone)) {
					// 更新缓存
					roleInfo.getPackMap().put(clone.getId(), clone);
					changeList.add(clone);
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
	 * 根据格子编号查询对应的背包对象id
	 * @param roleInfo
	 * @param oldGirdNo
	 * @return
	 * @author wangxf
	 * @date 2012-9-6
	 */
	private static long queryPackInRole(RoleInfo roleInfo, int oldGirdNo) {
		long id = -1;
		Map<Long, PackInfo> packMap = roleInfo.getPackMap();
		for (Entry<Long, PackInfo> entry : packMap.entrySet()) {
			PackInfo tmpObj = entry.getValue();
			if (tmpObj != null) {
				if (tmpObj.getGirdNo() == oldGirdNo) {
					id = tmpObj.getId();
					return id;
				}
			}
		}
		return id;
	}

	/**
	 * 添加背包关联关系
	 * @param relId
	 * @param relType 1-道具，2-装备
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * @date 2012-9-6
	 */
	public static PackInfo addPackRel(long relId, int relType, RoleInfo roleInfo) {
		// 获取一个空余的格子编号
		int girdNo = getEmptyGird(roleInfo).get(0);
		PackInfo newPackInfo = new PackInfo();
		newPackInfo.setGirdNo(girdNo);
		newPackInfo.setRelType(relType);
		newPackInfo.setRelId(relId);
		newPackInfo.setRoleId(roleInfo.getId());
		if (dao.addPackRel(newPackInfo)) {
			// 将新的背包对象添加到角色缓存中
			roleInfo.getPackMap().put(newPackInfo.getId(), newPackInfo);
			return newPackInfo;
		}
		else {
			return null;
		}
	}

	/**
	 * 删除背包关联关系
	 * @param relId
	 * @param relType 1-道具，2-装备
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * @date 2012-9-7
	 */
	public static int delPackRel(long relId, int relType, RoleInfo roleInfo) {
		int result = -1;
		PackInfo packInfo = getPackByRelId(relId, relType, roleInfo);
		if (packInfo != null) {
			if (dao.deletePack(packInfo)) {
				roleInfo.getPackMap().remove(packInfo.getId());
				result = 1;
			}
			else {
				result = ErrorCode.SYSTEM_ERROR;
			}
		}
		return result;
	}
	
	/**
	 * 根据关联关系查找背包对象
	 * @param relId
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * @param relType 
	 * @date 2012-9-6
	 */
	public static PackInfo getPackByRelId(long relId, int relType, RoleInfo roleInfo) {
		PackInfo packInfo = null;
		Map<Long, PackInfo> packMap = roleInfo.getPackMap();
		for (Entry<Long, PackInfo> entry : packMap.entrySet()) {
			PackInfo tmpObj = entry.getValue();
			if (tmpObj != null) {
				if (tmpObj.getRelId() == relId && tmpObj.getRelType() == relType) {
					packInfo = tmpObj;
				}
			}
		}
		return packInfo;
	}

	/**
	 * 删除背包
	 * @param packInfo2	背包id
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * @date 2012-9-7
	 */
	public static int delPackRel(PackInfo packInfo, RoleInfo roleInfo) {
		int result = -1;
		// 从数据库中删除成功
		if (dao.deletePack(packInfo)) {
			// 从缓存中删除
			roleInfo.getPackMap().remove(packInfo.getId());
			result = 1;
		}
		// 操作失败，则返回错误
		else {
			result = ErrorCode.SYSTEM_ERROR;
		}
		
		return result;
	}
	
	/**
	 * 添加背包关联关系
	 * @param packInfo
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * @date 2012-9-12
	 */
	public static int addPackRel(PackInfo packInfo, RoleInfo roleInfo) {
		int result = -1;
		// 获取一个空余的格子编号
		int girdNo = getEmptyGird(roleInfo).get(0);
		packInfo.setGirdNo(girdNo);
		if (dao.addPackRel(packInfo)) {
			// 将新的背包对象添加到角色缓存中
			roleInfo.getPackMap().put(packInfo.getId(), packInfo);
			result = 1;
		}
		else {
			result = ErrorCode.SYSTEM_ERROR;
		}
		return result;
	}

	/**
	 * 更新背包关系
	 * @param packInfo
	 * @param roleInfo
	 * @author wangxf
	 * @date 2012-9-12
	 */
	public static int updatePackRel(PackInfo packInfo, RoleInfo roleInfo) {
		int result = -1;
		if (dao.updatePack(packInfo)) {
			result = 1;
		}
		else {
			result = ErrorCode.SYSTEM_ERROR;
		}
		return result;
		
	}

	/**
	 * 检查背包剩余格子是否可以放入需要添加的物品
	 * @param type
	 * @param num
	 * @param roleInfo
	 * @return
	 * @author wangxf
	 * @date 2012-9-13
	 */
	public static boolean isPackFull(int type, int num, RoleInfo roleInfo) {
		// 道具，检查背包是否剩余一个格子
		if (type == 1) {
			if (getEmptyGird(roleInfo).size() == 0) {
				return true;
			}
		}
		// 装备，检查背包剩余格子是否大于等于num
		else if (type == 2) {
			if (getEmptyGird(roleInfo).size() < num) {
				return true;
			}
		}
		return false;
	}
}
