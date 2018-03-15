package com.snail.webgame.engine.common.info;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近可视、消失物体
 * 
 * @author zenggy
 * 
 */
public class ViewChangeInfo {
	/**
	 * 新附近的玩家
	 */
	private List<Long> newRoleIds = new ArrayList<Long>();
	/**
	 * 新附近的NPC
	 */
	private List<Long> newNpcIds = new ArrayList<Long>();
	/**
	 * 新附近的掉落物品
	 */
	//private List<Long> newDropIds = new ArrayList<Long>();
	/**
	 * 新附近的掉落包
	 */
	private List<Long> newDropBagIds = new ArrayList<Long>();
	/**
	 * 附近新出现的玩家
	 */
	private List<Long> addRoleIds = new ArrayList<Long>();
	/**
	 * 附近 消失的玩家
	 */
	private List<Long> delRoleIds = new ArrayList<Long>();
	/**
	 * 附近新出现的NPC
	 */
	private List<Long> addNPCIds = new ArrayList<Long>();
	/**
	 * 附近 消失的NPC
	 */
	private List<Long> delNPCIds = new ArrayList<Long>();
	/**
	 * 附近新出现的掉落物品
	 *//*
	private List<Long> addDropIds = new ArrayList<Long>();
	*//**
	 * 附近 消失的掉落物品
	 *//*
	private List<Long> delDropIds = new ArrayList<Long>();*/
	/**
	 * 附近新出现的掉落包
	 */
	private List<Long> addDropBagIds = new ArrayList<Long>();
	/**
	 * 附近 消失的掉落包
	 */
	private List<Long> delDropBagIds = new ArrayList<Long>();

	public List<Long> getAddRoleIds() {
		return addRoleIds;
	}

	public void setAddRoleIds(List<Long> addRoleIds) {
		this.addRoleIds = addRoleIds;
	}

	public List<Long> getDelRoleIds() {
		return delRoleIds;
	}

	public void setDelRoleIds(List<Long> delRoleIds) {
		this.delRoleIds = delRoleIds;
	}

	public List<Long> getAddNPCIds() {
		return addNPCIds;
	}

	public void setAddNPCIds(List<Long> addNPCIds) {
		this.addNPCIds = addNPCIds;
	}

	public List<Long> getDelNPCIds() {
		return delNPCIds;
	}

	public void setDelNPCIds(List<Long> delNPCIds) {
		this.delNPCIds = delNPCIds;
	}

	public List<Long> getNewRoleIds() {
		return newRoleIds;
	}

	public void setNewRoleIds(List<Long> newRoleIds) {
		this.newRoleIds = newRoleIds;
	}

	public List<Long> getNewNpcIds() {
		return newNpcIds;
	}

	public void setNewNpcIds(List<Long> newNpcIds) {
		this.newNpcIds = newNpcIds;
	}

	public List<Long> getNewDropBagIds() {
		return newDropBagIds;
	}

	public void setNewDropBagIds(List<Long> newDropBagIds) {
		this.newDropBagIds = newDropBagIds;
	}

	public List<Long> getAddDropBagIds() {
		return addDropBagIds;
	}

	public void setAddDropBagIds(List<Long> addDropBagIds) {
		this.addDropBagIds = addDropBagIds;
	}

	public List<Long> getDelDropBagIds() {
		return delDropBagIds;
	}

	public void setDelDropBagIds(List<Long> delDropBagIds) {
		this.delDropBagIds = delDropBagIds;
	}

	/*public List<Long> getNewDropIds() {
		return newDropIds;
	}

	public void setNewDropIds(List<Long> newDropIds) {
		this.newDropIds = newDropIds;
	}

	public List<Long> getAddDropIds() {
		return addDropIds;
	}

	public void setAddDropIds(List<Long> addDropIds) {
		this.addDropIds = addDropIds;
	}

	public List<Long> getDelDropIds() {
		return delDropIds;
	}

	public void setDelDropIds(List<Long> delDropIds) {
		this.delDropIds = delDropIds;
	}*/

}
