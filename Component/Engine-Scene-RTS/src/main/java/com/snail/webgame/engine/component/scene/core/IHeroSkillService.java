package com.snail.webgame.engine.component.scene.core;

import java.util.ArrayList;
import java.util.List;

import com.snail.webgame.engine.common.info.BuffInfo;
import com.snail.webgame.engine.component.scene.common.ArmyInfo;
import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.DefendInfo;
import com.snail.webgame.engine.component.scene.common.RoleFight;
import com.snail.webgame.engine.component.scene.protocal.fight.control.ControlResp;

public interface IHeroSkillService {

	/**
	 * 方阵使用技能
	 * @param armyPhalanx
	 */
	public ControlResp phalanxReleaseSkill(long fightId,List<Integer> roleList,ArmyPhalanx armyPhalanx,int skillType, int skillLevel,
			int aimX,int aimY,String aimPhalanxId,ArmyPhalanx  armyPhalanxs[]  );
	/**
	 * 
	 * 英雄释放buff型范围技能
	 */
	public void releaseHeroRankSkill(RoleFight roleFight,ArmyPhalanx armyPhalanx, int skillType, int skillLevel,
			ArmyPhalanx  armyPhalanxs[],int aimX,int aimY, String reserve);
	/**
	 * 死亡时触发技能效果
	 */
	public void triggerEffectOfDead(RoleFight roleFight, ArmyPhalanx attackPhalanx, ArmyPhalanx defendPhalanx, ArmyPhalanx  armyPhalanxs[], 
			DefendInfo defendInfo);
	/**
	 * 有一定几率触发被动技能
	 * @param fightId
	 * @param armyPhalanx
	 * @param armyPhalanxs
	 * @param 1-攻击时候触发  2-被攻击时候触发
	 */
	public void addArmyPhalanxsBeBuff(RoleFight roleFight,ArmyPhalanx armyPhalanx,
			ArmyPhalanx  armyPhalanxs[],ArmyPhalanx defendPhalanx,int flag,DefendInfo defendInfo);
	
	public void checkPhalanxBuff(long fightId,ArrayList<Integer> roleList,ArmyPhalanx armyPhalanx,int errorTime);
	/**
	 * 清除方阵所有BUFF
	 * @param fightId
	 * @param roleList
	 * @param armyPhalanx
	 */
	public void cleanPhalanxBuff(long fightId,List<Integer> roleList,ArmyPhalanx armyPhalanx);
	public String getPhalanxBuff(ArmyPhalanx armyPhalanx);
	/**
	 * 添加方阵buffer
	 * @param fightId 战斗ID
	 * @param roleList 战场角色ID集合
	 * @param attackPhalanx 进攻者方阵
	 * @param aimArmyPhalanxs 目标方阵
	 * @param skillType 技能类型
	 * @param skillLevel 技能等级
	 * @param itemNo 道具编号（如果是道具攻击）
	 */
	public void addPhalanxBuff(long fightId,List<Integer> roleList,
			ArmyPhalanx attackPhalanx,ArmyPhalanx aimArmyPhalanxs, int skillType,int skillLevel,int skillKeepTime,
			String reserve, ArmyPhalanx  armyPhalanxs[]);
	
	public boolean addPhalanxBuff(long fightId, List<Integer> roleList, ArmyPhalanx armyPhalanx, BuffInfo buff);
}
