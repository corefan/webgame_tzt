package com.snail.webgame.engine.component.scene.event;

public class MoveEndEvent extends Event {

	protected float endX;
	protected float endY;
	protected float endZ;
	
	public MoveEndEvent(long targetId, byte eventActor, float endX, float endY, float endZ) {
		super(targetId, eventActor, false);
		this.endX = endX;
		this.endY = endY;
		this.endZ = endZ;
	}

	public float getEndX() {
		return endX;
	}

	public float getEndY() {
		return endY;
	}

	public float getEndZ() {
		return endZ;
	}

}
