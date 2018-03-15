package com.snail.webgame.engine.component.scene.core;

import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;

public class CompPhalanx {

	private ArmyPhalanx armyPhalanx;
	private double d;
	
	public CompPhalanx(ArmyPhalanx armyPhalanx, double d) {
		 this.armyPhalanx = armyPhalanx;
		 this.d = d;
	}
	public ArmyPhalanx getArmyPhalanx() {
		return armyPhalanx;
	}
	public void setArmyPhalanx(ArmyPhalanx armyPhalanx) {
		this.armyPhalanx = armyPhalanx;
	}
	public double getD() {
		return d;
	}
	public void setD(double d) {
		this.d = d;
	}
	
	
	
}
