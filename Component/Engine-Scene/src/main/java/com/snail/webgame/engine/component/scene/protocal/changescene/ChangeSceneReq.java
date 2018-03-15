package com.snail.webgame.engine.component.scene.protocal.changescene;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ChangeSceneReq extends MessageBody{

	private float currX;
	private float currY;
	private float currZ;
	
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("currX", 0);
		ps.add("currY", 0);
		ps.add("currZ", 0);
	}

	public float getCurrX() {
		return currX;
	}

	public void setCurrX(float currX) {
		this.currX = currX;
	}

	public float getCurrY() {
		return currY;
	}

	public void setCurrY(float currY) {
		this.currY = currY;
	}

	public float getCurrZ() {
		return currZ;
	}

	public void setCurrZ(float currZ) {
		this.currZ = currZ;
	}

}
