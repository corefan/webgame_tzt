package com.snail.webgame.engine.component.scene.core;

import com.snail.webgame.engine.common.info.AIInfo;
import com.snail.webgame.engine.common.info.DefendInfo;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Path3D;
import com.snail.webgame.engine.component.scene.event.AttackEvent;
import com.snail.webgame.engine.component.scene.event.Event;
import com.snail.webgame.engine.component.scene.event.MoveEvent;
import com.snail.webgame.engine.component.scene.event.SkillAttackEvent;

public interface ISceneCalculate {


	/**
	 * 处理各种行为事件 如移动 攻击 停止 等
	 * 
	 */

	public void mgtPhalanxPolicy(Event event, AIInfo aiInfo);

	/**
	 * 处理普通攻击，如果目标不在可攻击范围内，则先移动
	 * 
	 * @param aiInfo
	 * @param gameMap
	 */
	public void phalanxMoveAttack(AIInfo attack, GameMap3D gameMap, AttackEvent event);
	
	/**
	 * 追击
	 * @param attack 攻击方
	 * @param defend 被攻击方
	 */
	public void pursue(AIInfo attack, AIInfo defend, GameMap3D gameMap, AttackEvent event);
	
	
	/**
	 * 计算攻击伤害，并通知客户端
	 * @param attack 攻击方
	 * @param defend 被攻击方
	 */
	public DefendInfo calAttackDamage(AIInfo attack, AIInfo defend, Event event);

	/**
	 * 执行事件
	 * 
	 * @return 执行事件数
	 */
	public int calAllRolePolicy(String mapId, int battleStatus);

	/**
	 * 移动
	 */
	public void phalanxMove(AIInfo aiInfo, GameMap3D gameMap, MoveEvent event);

	/**
	 * 获得移动路径
	 * 
	 * @return
	 */
	public Path3D getPhalanxPath(GameMap3D map, float srcX, float srcY, float srcZ, float endX,
			float endY, float endZ);
	


	/**
	 * 执行移动
	 */
	public void move(GameMap3D gameMap, AIInfo aiInfo, Path3D path);
	
	

	/**
	 * 使用技能攻击
	 */
	public void phalanxReleaseSkill(AIInfo attack, SkillAttackEvent event);

	

}
