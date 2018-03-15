package com.snail.webgame.engine.component.scene.event;

import com.snail.webgame.engine.common.pathfinding.mesh.Path3D;

public class MoveEvent extends Event {

	protected Path3D path;
	public MoveEvent(long targetId, byte eventActor, Path3D path) {
		super(targetId, eventActor, false);
		this.path = path;
	}
	public Path3D getPath() {
		return path;
	}
}
