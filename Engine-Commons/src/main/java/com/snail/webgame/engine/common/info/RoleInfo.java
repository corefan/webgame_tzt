package com.snail.webgame.engine.common.info;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RoleInfo extends AIInfo {
	
	protected String account;	// 帐号
	protected String roleName;	// 角色名称
	protected int sex;//性别 1-女 2-男
	protected int faceMode; //外观模型
	protected int level;	// 角色等级
	
	// 登录服务器所需字段
	/**
	 * 登录状态，0-不在线 1-在线
	 */
	protected int loginStatus;
	
	protected String loginIp;	// 登录IP
	protected long loginTime;	// 最后登录时间
	protected long logoutTime;	// 最后退出时间
	protected Timestamp createTime;	// 创建时间
	/**
	 * 防沉迷状态（-1初始状态，0是防沉迷，1是非防沉迷）
	 */
	protected int promptStatus = -1;
	protected long calIndulgePromptTime=0;	// 计算沉迷在线时间
	protected long cumulativeOnlineTime=0;	// 累积在线时间
	/**
	 * 角色状态 0-正常 1- 角色被冻结 2-角色休假中	
	 */
	protected byte roleStatus;
	protected long roleTime;	// 休假到达时间
	// end 登录服务器
	
	protected int sceneId;	// 场景ID
	
	
	// 计费服务器所需字段
	protected int accountId;	// 计费帐号ID
	protected String loginId;	// 计费登录ID
	protected int GMLevel;	// GM级别
	protected String MD5Pass;	// 密码
	// end 计费服务器
	
	protected Map<Long, PackInfo> packMap = new HashMap<Long, PackInfo>();	// 包裹格子信息
	protected int maxGirdNum;	// 包裹最大格子数
	protected Map<Long, ItemInfo> itemMap = new HashMap<Long, ItemInfo>();	// 物品信息
	protected ConcurrentHashMap<Integer, UseItemInfo> useItemMap = new ConcurrentHashMap<Integer, UseItemInfo>();	// 正在使用的物品
	protected Map<Long, QuickBarInfo> quickBarMap = new HashMap<Long, QuickBarInfo>();	// 快捷栏信息
	protected int maxQuickBarNo;	// 快捷栏最大编号
	protected int tradeStatus;	// 交易状态 0-未交易状态，1-跟玩家交易请求中，2-跟玩家交易中

	protected Map<Long,EquipInfo> equipMap; //角色装备
	protected Map<Integer,EquipRel> equipRelMap;//以 partNO为key
	
	protected int side;//属于哪一方
	
	
	@Override
	public byte getSaveMode() {
		return ONLINE;
	}
	
	public int getMaxQuickBarNo() {
		return maxQuickBarNo;
	}

	public void setMaxQuickBarNo(int maxQuickBarNo) {
		this.maxQuickBarNo = maxQuickBarNo;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Map<Long, QuickBarInfo> getQuickBarMap() {
		return quickBarMap;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setQuickBarMap(Map<Long, QuickBarInfo> quickBarMap) {
		this.quickBarMap = quickBarMap;
	}

	public Map<Long, EquipInfo> getEquipMap() {
		return equipMap;
	}

	public void setEquipMap(Map<Long, EquipInfo> equipMap) {
		this.equipMap = equipMap;
	}

	public Map<Integer, EquipRel> getEquipRelMap() {
		return equipRelMap;
	}

	public void setEquipRelMap(Map<Integer, EquipRel> equipRelMap) {
		this.equipRelMap = equipRelMap;
	}

	public ConcurrentHashMap<Integer, UseItemInfo> getUseItemMap() {
		return useItemMap;
	}

	public void setUseItemMap(ConcurrentHashMap<Integer, UseItemInfo> useItemMap) {
		this.useItemMap = useItemMap;
	}

	
	public Map<Long, ItemInfo> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<Long, ItemInfo> itemMap) {
		this.itemMap = itemMap;
	}

	public int getMaxGirdNum() {
		return maxGirdNum;
	}

	public void setMaxGirdNum(int maxGirdNum) {
		this.maxGirdNum = maxGirdNum;
	}

	public Map<Long, PackInfo> getPackMap() {
		return packMap;
	}

	public void setPackMap(Map<Long, PackInfo> packMap) {
		this.packMap = packMap;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public long getCalIndulgePromptTime() {
		return calIndulgePromptTime;
	}

	public void setCalIndulgePromptTime(long calIndulgePromptTime) {
		this.calIndulgePromptTime = calIndulgePromptTime;
	}

	public int getGMLevel() {
		return GMLevel;
	}

	public void setGMLevel(int gMLevel) {
		GMLevel = gMLevel;
	}

	/*public String getGMRight() {
		return GMRight;
	}

	public void setGMRight(String gMRight) {
		GMRight = gMRight;
	}*/

	public String getMD5Pass() {
		return MD5Pass;
	}

	public void setMD5Pass(String mD5Pass) {
		MD5Pass = mD5Pass;
	}

	public int getPromptStatus() {
		return promptStatus;
	}

	public void setPromptStatus(int promptStatus) {
		this.promptStatus = promptStatus;
	}

	public long getLogoutTime() {
		return logoutTime;
	}
	public long getCumulativeOnlineTime() {
		return cumulativeOnlineTime;
	}

	public void setCumulativeOnlineTime(long cumulativeOnlineTime) {
		this.cumulativeOnlineTime = cumulativeOnlineTime;
	}

	public void setLogoutTime(long logoutTime) {
		this.logoutTime = logoutTime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getSceneId() {
		return sceneId;
	}
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public byte getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(byte roleStatus) {
		this.roleStatus = roleStatus;
	}
	public long getRoleTime() {
		return roleTime;
	}
	public void setRoleTime(long roleTime) {
		this.roleTime = roleTime;
	}
	public int getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}


	public int getFaceMode() {
		return faceMode;
	}

	public void setFaceMode(int faceMode) {
		this.faceMode = faceMode;
	}
	
}
