package com.snail.webgame.engine.component.login.protocal.enthrallment;

import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;

/**
 * 防沉迷信息
 * @author miaozhe
 * @date  2010.07.14
 */

public class RoleEnthrallmentProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	private ChargeMgtService chargeMgtService ; 
	
	
	
	public void setChargeMgtService(ChargeMgtService chargeMgtService) {
		this.chargeMgtService = chargeMgtService;
	}
	public void execute(Request request, Response response) {
		
		Message message = request.getMessage();
		GameMessageHead head = (GameMessageHead) message.getHeader();
		int roleId = head.getUserID0();
		
 
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		//每次登录设置默认状态-1，向计费请求
		if(roleInfo.getPromptStatus() != -1)
		{
			roleInfo.setPromptStatus(-1);
		}
		 
		
		chargeMgtService.sendRefreshReq(roleId);
	}

}
