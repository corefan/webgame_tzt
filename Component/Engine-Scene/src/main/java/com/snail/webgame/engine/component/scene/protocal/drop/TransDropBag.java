package com.snail.webgame.engine.component.scene.protocal.drop;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * 2012-9-7
 * 
 */
public class TransDropBag extends MessageBody{
	protected long id;
	protected int modelId; // 掉落包模型ID
	protected float x; // 掉落点x坐标
	protected float y; // 掉落点y坐标
	protected float z; // 掉落点z坐标
	protected long dropTime; // 掉落时间
	protected String ownerId; // 所属者ID
	protected int protectTime; // 物品个人保护冷却时间(超过这个时间别人就可以捡了)
	protected int clearTime; // 消失时间

	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("id", 0);
		ps.add("modelId", 0);
		ps.add("x", 0);
		ps.add("y", 0);
		ps.add("z", 0);
		ps.add("dropTime", 0);
		ps.addString("ownerId", "flashCode", 0);
		ps.add("protectTime", 0);
		ps.add("clearTime", 0);
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public long getDropTime() {
		return dropTime;
	}

	public void setDropTime(long dropTime) {
		this.dropTime = dropTime;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public int getProtectTime() {
		return protectTime;
	}

	public void setProtectTime(int protectTime) {
		this.protectTime = protectTime;
	}

	public int getClearTime() {
		return clearTime;
	}

	public void setClearTime(int clearTime) {
		this.clearTime = clearTime;
	}

}
