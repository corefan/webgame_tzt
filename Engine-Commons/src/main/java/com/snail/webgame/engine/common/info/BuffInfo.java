package com.snail.webgame.engine.common.info;

import com.snail.webgame.engine.common.to.BaseTO;

public class BuffInfo extends BaseTO {

	protected int buffNo;
	protected int type; //1-无法行动
	protected float value;
	protected int buffTime;
	protected long createTime;
	protected String reserve ;
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public int getBuffTime() {
		return buffTime;
	}
	public void setBuffTime(int buffTime) {
		this.buffTime = buffTime;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	@Override
	public byte getSaveMode() {
		return ONLINE;
	}
	public int getBuffNo() {
		return buffNo;
	}
	public void setBuffNo(int buffNo) {
		this.buffNo = buffNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
