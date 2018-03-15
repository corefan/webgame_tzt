package com.snail.webgame.engine.component.scene.info;

public class BuffXMlInfo {
	private int buffNo;
	private String buffName;
	private int type;
	private int keepTime; // buff持续时间s为单位

	public int getBuffNo() {
		return buffNo;
	}

	public void setBuffNo(int buffNo) {
		this.buffNo = buffNo;
	}

	public String getBuffName() {
		return buffName;
	}

	public void setBuffName(String buffName) {
		this.buffName = buffName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getKeepTime() {
		return keepTime;
	}

	public void setKeepTime(int keepTime) {
		this.keepTime = keepTime;
	}

}
