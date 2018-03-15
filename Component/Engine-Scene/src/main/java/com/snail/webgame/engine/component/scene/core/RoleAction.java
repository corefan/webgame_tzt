package com.snail.webgame.engine.component.scene.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.scene.protocal.datasyn.DataSynService;

/**
 * 角色动作
 * @author zenggy
 *
 */
public class RoleAction {

	private static final Logger logger = LoggerFactory.getLogger("logs");
	
	/**
	 * 切换角色动作，并通知客户端
	 * @param roleInfo 角色信息
	 * @param action 动作,多个动作用","组成动作队列
	 */
	public static void changeRoleAction(RoleInfo roleInfo, String action){
		if(roleInfo != null){
			roleInfo.set("action", action);
			DataSynService.sendDataSyn(roleInfo, roleInfo, true);
			
		}
	}
}
