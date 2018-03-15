package com.snail.webgame.engine.common.info;

import com.snail.webgame.engine.common.to.BaseTO;

/**
 * @author wangxf
 * @date 2012-8-31 快捷栏类
 */
public class QuickBarInfo extends BaseTO {
	private int quickBarNo; // 包裹格子编号
	private int relType; // 对应关系类型(1:物品背包，2：技能)
	private long relId; // 关联ID
	private long roleId; // 角色ID

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snail.webgame.engine.common.to.BaseTO#getSaveMode()
	 */
	@Override
	public byte getSaveMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getQuickBarNo() {
		return quickBarNo;
	}

	public void setQuickBarNo(int quickBarNo) {
		this.quickBarNo = quickBarNo;
	}

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

}
