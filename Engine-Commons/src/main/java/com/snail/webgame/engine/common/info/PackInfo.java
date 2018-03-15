package com.snail.webgame.engine.common.info;

import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.to.BaseTO;

/**
 * @author wangxf
 * @date 2012-8-22 包裹格子信息
 */
public class PackInfo extends BaseTO implements Comparable<Object> {
	private int girdNo; // 包裹格子编号
	private int relType; // 关联类型 1-道具 2-装备
	private long relId;	// 关联id(对应道具和装备的id)
	private long roleId; // 角色ID
	/*private boolean isAddQuickBar;	// 是否已加入到快捷栏标识
	private int oldGirdNo;	// 原格子编号
*/
	/*public int getOldGirdNo() {
		return oldGirdNo;
	}

	public void setOldGirdNo(int oldGirdNo) {
		this.oldGirdNo = oldGirdNo;
	}*/

	public int getGirdNo() {
		return girdNo;
	}

	public void setGirdNo(int girdNo) {
		this.girdNo = girdNo;
	}

	/*public long getItemNo() {
		return itemNo;
	}

	public void setItemNo(long itemNo) {
		this.itemNo = itemNo;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}*/

	public int getRelType() {
		return relType;
	}

	public void setRelType(int relType) {
		this.relType = relType;
	}

	public long getRelId() {
		return relId;
	}

	public void setRelId(long relId) {
		this.relId = relId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/*public boolean isAddQuickBar() {
		return isAddQuickBar;
	}

	public void setAddQuickBar(boolean isAddQuickBar) {
		this.isAddQuickBar = isAddQuickBar;
	}*/

	/* (non-Javadoc)
	 * @see com.snail.webgame.engine.common.to.BaseTO#getSaveMode()
	 */
	@Override
	public byte getSaveMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {
		// 先判断物品类型->再判断物品ID->在判断物品数量
		PackInfo otherPack = (PackInfo) o;
		
		if (this.getRelType() < otherPack.getRelType()) {
			return -1;
		} else if (this.getRelType() > otherPack.getRelType()) {
			return 1;
		} else {
			RoleInfo roleInfo = RoleInfoMap.getRoleInfo(this.roleId);
			int selfItemNo = 0;
			int otherItemNo = 0;
			int selfItemNum = 0;
			int otherItemNum = 0;
			// 道具
			if (relType == 1) {
				ItemInfo self = roleInfo.getItemMap().get(this.relId);
				ItemInfo other = roleInfo.getItemMap().get(otherPack.relId);
				selfItemNo = self.getItemNo();
				selfItemNum = self.getItemNum();
				otherItemNo = other.getItemNo();
				otherItemNum = other.getItemNum();
			}
			// 装备
			else if (relType == 2) {
				EquipInfo self = roleInfo.getEquipMap().get(this.relId);
				EquipInfo other = roleInfo.getEquipMap().get(otherPack.relId);
				selfItemNo = self.getEquipNO();
				otherItemNo = other.getEquipNO();
			}
			
			if (selfItemNo < otherItemNo) {
				return -1;
			} else if (selfItemNo > otherItemNo) {
				return 1;
			} else {
				if (selfItemNum < otherItemNum) {
					return 1;
				}
				else if (selfItemNum > otherItemNum) {
					return -1;
				}
				else {
					return 0;
				}
			}
		}
	}

}
