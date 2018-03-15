package com.snail.webgame.engine.component.scene.core.policy;

import com.snail.webgame.engine.common.pathfinding.astar.Path;
import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.RoleFight;
import com.snail.webgame.engine.component.scene.core.FightCalculate;
/**
 * 移动
 */
public class MovePolicyListener implements PolicyListener {

	public void execute(RoleFight roleFight, ArmyPhalanx armyPhalanx, ArmyPhalanx[] armyPhalanxList, int errorTime) {
		if (armyPhalanx.getCurrMoveTime() > 1
				&& armyPhalanx.getCurrMoveMaxTime() >= 0) {
			if (armyPhalanx.getAimX() >= 0 && armyPhalanx.getAimY() >= 0) {
				FightCalculate caculate = roleFight.getFightCalculate();
				Path path = caculate.getAimRankList(roleFight.getMap(), armyPhalanx, armyPhalanx.getCurrX(), armyPhalanx.getCurrY(),
						armyPhalanx.getAimX(), armyPhalanx.getAimY(), false, roleFight.getFightMapType(), roleFight.getFightId());
				caculate.move(roleFight.getFightId(), roleFight.getMap(), armyPhalanx, roleFight.getRoleList(), path, roleFight.getControlMap().get(armyPhalanx.getRoleId()), 0);

			}
			armyPhalanx.setCurrMoveTime(0);

		}
	}

}
