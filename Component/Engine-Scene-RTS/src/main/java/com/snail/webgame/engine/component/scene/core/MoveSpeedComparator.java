package com.snail.webgame.engine.component.scene.core;

import java.util.Comparator;

import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;

public class MoveSpeedComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		
		ArmyPhalanx armyPhalanx1 = (ArmyPhalanx) o1;
		ArmyPhalanx armyPhalanx2 = (ArmyPhalanx) o2;
		
		if(armyPhalanx1.getCurrMoveMaxTime() < armyPhalanx2.getCurrMoveMaxTime())
		{
			return 1;
		}
		else if(armyPhalanx1.getCurrMoveMaxTime() == armyPhalanx2.getCurrMoveMaxTime())
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}

}
