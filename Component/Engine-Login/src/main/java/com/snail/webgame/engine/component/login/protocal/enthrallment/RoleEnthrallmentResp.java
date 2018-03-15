package com.snail.webgame.engine.component.login.protocal.enthrallment;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;
/**
 * 防沉迷消息
 * @author miaozhe
 * @date  2010.07.14
 */

public class RoleEnthrallmentResp extends MessageBody {

	
	private int promptType;//0是防沉迷，1是非防沉迷
	private long cumulativeOnlineTime;//累积在线时间	
	
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("promptType", 0);
		ps.add("cumulativeOnlineTime", 0);
	}

	public int getPromptType() {
		return promptType;
	}

	public void setPromptType(int promptType) {
		this.promptType = promptType;
	}

	public long getCumulativeOnlineTime() {
		return cumulativeOnlineTime;
	}

	public void setCumulativeOnlineTime(long cumulativeOnlineTime) {
		this.cumulativeOnlineTime = cumulativeOnlineTime;
	}
}
