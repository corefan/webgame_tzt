package com.snail.webgame.engine.component.scene.protocal.fight.in;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class DuelPhalanxRe extends MessageBody {

	private String phalanxId;
	private int side;
	private int duelPosition;//单挑位置
	private String heroName;
	private int heroModel;
	private double duelAttackValue;
	private long duelMaxHP;
	private double duelAttackSpeed;
	
	protected void setSequnce(ProtocolSequence ps) {
		ps.addString("phalanxId", "flashCode", 0);
		ps.add("side", 0);
		ps.add("duelPosition", 0);
		ps.addString("heroName", "flashCode", 0);
		ps.add("heroModel", 0);
		ps.add("duelAttackValue", 0);
		ps.add("duelMaxHP", 0);
		ps.add("duelAttackSpeed", 0);
	}

	public String getPhalanxId() {
		return phalanxId;
	}

	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getDuelPosition() {
		return duelPosition;
	}

	public void setDuelPosition(int duelPosition) {
		this.duelPosition = duelPosition;
	}

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public int getHeroModel() {
		return heroModel;
	}

	public void setHeroModel(int heroModel) {
		this.heroModel = heroModel;
	}

	public double getDuelAttackValue() {
		return duelAttackValue;
	}

	public void setDuelAttackValue(double duelAttackValue) {
		this.duelAttackValue = duelAttackValue;
	}

	public long getDuelMaxHP() {
		return duelMaxHP;
	}

	public void setDuelMaxHP(long duelMaxHP) {
		this.duelMaxHP = duelMaxHP;
	}

	public double getDuelAttackSpeed() {
		return duelAttackSpeed;
	}

	public void setDuelAttackSpeed(double duelAttackSpeed) {
		this.duelAttackSpeed = duelAttackSpeed;
	}

}
