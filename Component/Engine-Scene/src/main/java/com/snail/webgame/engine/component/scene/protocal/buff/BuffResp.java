package com.snail.webgame.engine.component.scene.protocal.buff;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class BuffResp extends MessageBody {

	private long buffId;
	private int buffNo;
	private long buffAim;
	private int buffAimType;
	private int buffType;
	private float value;
	private int buffTime;
	private int flag;
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("buffId", 0);
		ps.add("buffNo", 0);
		ps.add("buffAim", 0);
		ps.add("buffAimType", 0);
		ps.add("buffType", 0);
		ps.add("value", 0);
		ps.add("buffTime", 0);
		ps.add("flag", 0);
	}
	public long getBuffId() {
		return buffId;
	}
	public void setBuffId(long buffId) {
		this.buffId = buffId;
	}
	public long getBuffAim() {
		return buffAim;
	}
	public void setBuffAim(long buffAim) {
		this.buffAim = buffAim;
	}
	public int getBuffAimType() {
		return buffAimType;
	}
	public void setBuffAimType(int buffAimType) {
		this.buffAimType = buffAimType;
	}
	public int getBuffType() {
		return buffType;
	}
	public void setBuffType(int buffType) {
		this.buffType = buffType;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public int getBuffNo() {
		return buffNo;
	}
	public void setBuffNo(int buffNo) {
		this.buffNo = buffNo;
	}
	public int getBuffTime() {
		return buffTime;
	}
	public void setBuffTime(int buffTime) {
		this.buffTime = buffTime;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

}
