package com.snail.webgame.engine.component.scene.event;

/**
 * @author wangxf
 * @date 2012-8-6
 * 事件发起者
 */
public interface EventActor {
	
	/**
	 * NPC发起事件
	 */
	public static final byte EVENT_ACTOR_NPC = 0;
	
	/**
	 * 玩家发起事件
	 */
	public static final byte EVENT_ACTOR_PLAYER = 1;
}
