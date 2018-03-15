package com.snail.webgame.engine.component.scene.core;

import java.util.ArrayList;
import java.util.HashMap;

import com.snail.webgame.engine.common.pathfinding.astar.GameMap;
import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.DefendInfo;
import com.snail.webgame.engine.component.scene.common.RoleFight;

public interface IFightCore {
	
	
	/**
	 * 改变部队士气状态
	 */
	public void changeFightMorale(RoleFight roleFight,
			ArmyPhalanx defendArmyPhalanx,ArmyPhalanx attackArmyPhalanx,int face,
			ArmyPhalanx[] armyPhalanxList,ArrayList<Integer> roleList);

	
	/**
	 * 技能攻击
	 * @param fightId
	 * @param lineup
	 * @param attackPhalanx
	 * @param armyPhalanx
	 * @return
	 */
	public void calculateSkillAttrack(RoleFight roleFight,ArmyPhalanx attackPhalanx, ArmyPhalanx[]  armyPhalanx);

	
	
	
	/**
	 * 进行战斗计算
	 * 攻击、暴击、命中 提升当前士气点/100  的效果（士气只对士兵起作用）
	 * @param attackPhalanx
	 * @param defendPhalanx
	 */
	public DefendInfo calculateCommAttrack(RoleFight roleFight,ArmyPhalanx attackPhalanx,ArmyPhalanx defendPhalanx, ArmyPhalanx[]  armyPhalanx);
	
	
	/**
	 * 获得攻击 防御 移动口号
	 * @param armyPhalanx
	 * @param flag 0-移动 1-攻击 2-防御
	 * @return
	 */
	public String getSlogan(ArmyPhalanx armyPhalanx,int flag);
	
	
	
}
