package com.snail.webgame.engine.component.scene.protocal.move;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class MoveReq extends MessageBody{

	private String moveList;
	
	protected void setSequnce(ProtocolSequence ps) {
		ps.addString("moveList", "flashCode", 0);
	}

	public String getMoveList() {
		return moveList;
	}

	public void setMoveList(String moveList) {
		this.moveList = moveList;
	}


	 

}
