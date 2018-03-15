package com.snail.webgame.engine.component.scene.protocal.in;
 
import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class InSceneResp extends MessageBody {
	
	protected int result;

	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		// TODO Auto-generated method stub
		
	}
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	 
	/*private int result;
	private long roleId;	// 角色ID
	private String roleName;	// 角色名称
	private int level;	// 角色等级
	private int sceneId;	// 场景ID
	private int currX;	// 当前X坐标
	private int currY;	// 当前Y坐标
	private String mapId;	// 地图ID
	private int currHP;	// 角色当前生命值
	private int currMP;	// 当前法力值
	private String currAttack;	// 当前攻击点
	private String currDefend;	// 当前防御力
	private String action;	// 动作
	private int currMaxMoveTime;//移动时间
	private int otherRoleCount;//场景其它角色个数
	private List<OtherRole> otherRoleList;//场景其它角色信息
	private int npcCount;	// 玩家角色附近的npc个数
	private List<NPCInfo> listNpcInfo;	// 玩家角色附近的npc信息
	
	protected void setSequnce(ProtocolSequence ps) {
		 
		ps.add("result", 0);
		ps.add("roleId", 0);
		ps.addString("roleName", "flashCode", 0);
		ps.add("level", 0);
		ps.add("sceneId", 0);
		ps.add("currX", 0);
		ps.add("currY", 0);
		ps.addString("mapId", "flashCode", 0);
		ps.add("currHP", 0);
		ps.add("currMP", 0);
		ps.addString("currAttack", "flashCode", 0);
		ps.addString("currDefend", "flashCode", 0);
		ps.addString("action", "flashCode", 0);
		ps.add("currMaxMoveTime", 0);
		ps.add("otherRoleCount", 0);
		ps.addObjectArray("otherRoleList", "com.snail.webgame.engine.component.scene.protocal.otherrole.OtherRole", "otherRoleCount");
		// 先用common里面的NPCInfo对象
		ps.addObjectArray("npcInfoList", "com.snail.webgame.engine.common.info.NPCInfo", "npcCount");
	}

	public int getCurrMaxMoveTime() {
		return currMaxMoveTime;
	}

	public void setCurrMaxMoveTime(int currMaxMoveTime) {
		this.currMaxMoveTime = currMaxMoveTime;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
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

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public int getCurrX() {
		return currX;
	}

	public void setCurrX(int currX) {
		this.currX = currX;
	}

	public int getCurrY() {
		return currY;
	}

	public void setCurrY(int currY) {
		this.currY = currY;
	}

	public List<NPCInfo> getListNpcInfo() {
		return listNpcInfo;
	}

	public void setListNpcInfo(List<NPCInfo> listNpcInfo) {
		this.listNpcInfo = listNpcInfo;
	}

	public int getNpcCount() {
		return npcCount;
	}

	public void setNpcCount(int npcCount) {
		this.npcCount = npcCount;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}

	public int getCurrMP() {
		return currMP;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setCurrMP(int currMP) {
		this.currMP = currMP;
	}

	public String getCurrAttack() {
		return currAttack;
	}

	public void setCurrAttack(String currAttack) {
		this.currAttack = currAttack;
	}

	public String getCurrDefend() {
		return currDefend;
	}

	public void setCurrDefend(String currDefend) {
		this.currDefend = currDefend;
	}

	public int getOtherRoleCount() {
		return otherRoleCount;
	}

	public void setOtherRoleCount(int otherRoleCount) {
		this.otherRoleCount = otherRoleCount;
	}

	public List<OtherRole> getOtherRoleList() {
		return otherRoleList;
	}

	public void setOtherRoleList(List<OtherRole> otherRoleList) {
		this.otherRoleList = otherRoleList;
	}
*/


}
