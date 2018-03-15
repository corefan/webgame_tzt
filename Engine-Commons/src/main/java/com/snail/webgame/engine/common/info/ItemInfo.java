package com.snail.webgame.engine.common.info;

import com.snail.webgame.engine.common.to.BaseTO;

/**
 * @author wangxf
 * @date 2012-8-28 物品对象
 */
public class ItemInfo extends BaseTO{
	protected int itemNo; // 物品编号
	protected int itemNum; // 物品数量
	protected long roleId; // 角色ID

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/* (non-Javadoc)
	 * @see com.snail.webgame.engine.common.to.BaseTO#getSaveMode()
	 */
	@Override
	public byte getSaveMode() {
		// TODO Auto-generated method stub
		return 0;
	}

}
