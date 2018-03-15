package com.snail.webgame.engine.component.scene.protocal.move;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

import com.snail.webgame.engine.component.scene.protocal.drop.TransDropBag;
import com.snail.webgame.engine.component.scene.protocal.npc.TransformNPCInfo;
import com.snail.webgame.engine.component.scene.protocal.otherrole.OtherRole;

public class AreaChangeResp extends MessageBody {
	private String delRoleId; // 移除的角色ID
	private String delNpcId;// 移除的NPC
	//private String delDropIds; // 移除掉落物品的id集合
	private String delDropBagIds; // 移除掉落包的id集合
	private int otherRolecount;
	private List<OtherRole> otherRoleList;
	private int npcCount;
	private List<TransformNPCInfo> npcInfoList;
	//private int dropCount;
	//private List<TransDropInfo> dropInfoList;
	private int dropBagCount;	// 新添加的掉落包
	private List<TransDropBag> dropBagList;

	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.addString("delRoleId", "flashCode", 0);
		ps.addString("delNpcId", "flashCode", 0);
		//ps.addString("delDropIds", "flashCode", 0);
		ps.addString("delDropBagIds", "flashCode", 0);
		ps.add("otherRolecount", 0);
		ps.addObjectArray(
				"otherRoleList",
				"com.snail.webgame.engine.component.scene.protocal.otherrole.OtherRole",
				"otherRolecount");
		ps.add("npcCount", 0);
		ps.addObjectArray(
				"npcInfoList",
				"com.snail.webgame.engine.component.scene.protocal.npc.TransformNPCInfo",
				"npcCount");
		/*ps.add("dropCount", 0);
		ps.addObjectArray(
				"dropInfoList",
				"com.snail.webgame.engine.component.scene.protocal.drop.TransDropInfo",
				"dropCount");*/
		ps.add("dropBagCount", 0);
		ps.addObjectArray(
				"dropBagList",
				"com.snail.webgame.engine.component.scene.protocal.drop.TransDropBag",
				"dropBagCount");

	}

	public String getDelRoleId() {
		return delRoleId;
	}

	public void setDelRoleId(String delRoleId) {
		this.delRoleId = delRoleId;
	}

	public String getDelNpcId() {
		return delNpcId;
	}

	public void setDelNpcId(String delNpcId) {
		this.delNpcId = delNpcId;
	}

	public int getOtherRolecount() {
		return otherRolecount;
	}

	public void setOtherRolecount(int otherRolecount) {
		this.otherRolecount = otherRolecount;
	}

	public List<OtherRole> getOtherRoleList() {
		return otherRoleList;
	}

	public void setOtherRoleList(List<OtherRole> otherRoleList) {
		this.otherRoleList = otherRoleList;
	}

	public int getNpcCount() {
		return npcCount;
	}

	public void setNpcCount(int npcCount) {
		this.npcCount = npcCount;
	}

	public List<TransformNPCInfo> getNpcInfoList() {
		return npcInfoList;
	}

	public void setNpcInfoList(List<TransformNPCInfo> npcInfoList) {
		this.npcInfoList = npcInfoList;
	}

	public String getDelDropBagIds() {
		return delDropBagIds;
	}

	public void setDelDropBagIds(String delDropBagIds) {
		this.delDropBagIds = delDropBagIds;
	}

	public int getDropBagCount() {
		return dropBagCount;
	}

	public void setDropBagCount(int dropBagCount) {
		this.dropBagCount = dropBagCount;
	}

	public List<TransDropBag> getDropBagList() {
		return dropBagList;
	}

	public void setDropBagList(List<TransDropBag> dropBagList) {
		this.dropBagList = dropBagList;
	}

	/*public String getDelDropIds() {
		return delDropIds;
	}

	public void setDelDropIds(String delDropIds) {
		this.delDropIds = delDropIds;
	}

	public int getDropCount() {
		return dropCount;
	}

	public void setDropCount(int dropCount) {
		this.dropCount = dropCount;
	}

	public List<TransDropInfo> getDropInfoList() {
		return dropInfoList;
	}

	public void setDropInfoList(List<TransDropInfo> dropInfoList) {
		this.dropInfoList = dropInfoList;
	}*/

}
