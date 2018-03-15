package com.snail.webgame.engine.component.scene.protocal.fight.service;

import com.snail.webgame.engine.component.scene.protocal.fight.control.ControlReq;
import com.snail.webgame.engine.component.scene.protocal.fight.control.ControlResp;
import com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightReq;
import com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightResp;
import com.snail.webgame.engine.component.scene.protocal.fight.manual.ManualReq;
import com.snail.webgame.engine.component.scene.protocal.fight.out.RoleOutFightReq;
import com.snail.webgame.engine.component.scene.protocal.fight.out.RoleOutFightResp;
import com.snail.webgame.engine.component.scene.protocal.fight.prop.UsePropReq;
import com.snail.webgame.engine.component.scene.protocal.fight.prop.UsePropResp;


public interface IFightMgtService {

	
 
	
	
	
	//用户进入战斗场景获得战斗场景中所有数据
	public RoleInFightResp roleGetFigthInit(RoleInFightReq req, int roleId,int gateServerId);
	
	
 
	
	/**
	 * 移除用户登录
	 * @param req
	 * @param roleId
	 * @return
	 */
	public  RoleOutFightResp roleOutFightControl(RoleOutFightReq req,int roleId);
	
	
	/**
	 * 处理玩家各种策略
	 * @param roleId
	 * @param req
	 */
	public ControlResp changeRoleAim(int roleId ,ControlReq req);
	

	/**
	 * 自动、手动切换
	 * @param roleId
	 * @param req
	 */
	public void controlArmy(int roleId ,ManualReq req);
	
	/**
	 * 使用道具
	 * @param roleId
	 * @param req
	 * @return
	 */
	public UsePropResp useProp(int roleId ,UsePropReq req);
	
	/**
	 * 中途加入战场
	 * @param req
	 */
//	public void mgtGetArmyQueue(SysReq req);
	
	
	
}
