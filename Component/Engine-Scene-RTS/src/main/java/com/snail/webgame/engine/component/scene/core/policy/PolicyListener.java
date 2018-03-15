package com.snail.webgame.engine.component.scene.core.policy;

import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.RoleFight;

/**
 * 策略监听器
 * @author zenggy
 *
 */
public interface PolicyListener {
	/**
	 * 策略处理
	 * @param roleFight
	 * @param armyPhalanx
	 * @param armyPhalanxList
	 */
	public void execute(RoleFight roleFight, ArmyPhalanx armyPhalanx, ArmyPhalanx[] armyPhalanxList, int errorTime);
}
