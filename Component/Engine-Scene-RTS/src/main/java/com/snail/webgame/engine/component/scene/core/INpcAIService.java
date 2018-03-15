package com.snail.webgame.engine.component.scene.core;

import java.util.List;

import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.DefendInfo;
import com.snail.webgame.engine.component.scene.common.NpcHatredInfo;

/**
 * 怪物智能攻击相关逻辑处理
 */
public interface INpcAIService {

	/**
	 * 伤害仇恨计算
	 * @param armyPhalanx
	 * @param defendInfo
	 */
	public void accumulateHatredValue(ArmyPhalanx armyPhalanx,DefendInfo defendInfo);
	/**
	 * 获取仇恨值最高的方阵
	 * @param hatredList
	 * @return
	 */
	public String getMaxHatredPhalanxId(List<NpcHatredInfo> hatredList,ArmyPhalanx[] armyPhalanxList);
}
