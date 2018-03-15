package com.snail.webgame.engine.component.scene.core;

import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.scene.protocal.changescene.ChangeSceneReq;
import com.snail.webgame.engine.component.scene.protocal.changescene.ChangeSceneResp;
import com.snail.webgame.engine.component.scene.protocal.fight.attack.AttackReq;
import com.snail.webgame.engine.component.scene.protocal.fight.skill.SkillAttackReq;
import com.snail.webgame.engine.component.scene.protocal.in.InSceneReq;
import com.snail.webgame.engine.component.scene.protocal.in.InSceneResp;
import com.snail.webgame.engine.component.scene.protocal.move.MoveEndValidateReq;
import com.snail.webgame.engine.component.scene.protocal.move.MoveReq;
import com.snail.webgame.engine.component.scene.protocal.out.OutSceneReq;
import com.snail.webgame.engine.component.scene.protocal.rebirth.RebirthResp;

public interface ISceneMgtService {

	/**
	 * 进入场景
	 * 
	 * @param roleId
	 * @return
	 */
	public InSceneResp inScene(long roleId, InSceneReq req,
			int gateServerId);

	/**
	 * 退出场景
	 * 
	 * @param roleId
	 */
	public boolean outScene(long roleId, OutSceneReq req);

	/**
	 * 人物移动
	 */
	public void roleMove(long roleId, MoveReq req);

	/**
	 * 验证移动停止坐标
	 * 
	 * @param roleId
	 * @param req
	 */
	public void validateEndPoint(int roleId, MoveEndValidateReq req);

	/**
	 * 攻击目标
	 * @param roleId
	 * @param req
	 */
	public void attack(int roleId, AttackReq req);
	/**
	 * 技能攻击
	 * @param roleId
	 * @param req
	 */
	public void skillAttack(int roleId, SkillAttackReq req);

	/**
	 * 切换场景
	 * @param roleId
	 * @param req
	 * @return
	 */
	public ChangeSceneResp changeScene(int roleId, ChangeSceneReq req);
	
	/**
	 * 保存玩家基础信息
	 * @param roleInfo
	 */
	public void saveRoleBase(RoleInfo roleInfo);

	/**
	 * 玩家复活
	 * @param roleId
	 * @param rebirthType
	 * @return
	 * @author wangxf
	 * @date 2012-11-23
	 */
	public RebirthResp rebirth(int roleId, int rebirthType);

	/**
	 * 重置玩家位置
	 * @param roleId
	 * @author wangxf
	 * @date 2013-1-16
	 */
	public void resetPosition(int roleId);

}
