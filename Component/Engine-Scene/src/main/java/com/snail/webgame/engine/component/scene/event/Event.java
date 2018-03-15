package com.snail.webgame.engine.component.scene.event;

/**
 * 事件
 * @author zenggy
 *
 */
public abstract class Event {

	/**
	 * 事件发起者
	 * 0 NPC, 1 玩家
	 */
	protected byte eventActor;
	
	/**
	 * 事件目标
	 */
	protected long targetId;
	
	/**
	 * 创建时间
	 */
	protected long time;
	
	/**
	 * 是否覆盖相同事件
	 */
	protected boolean replace;
	/**
	 * 构造事件
	 * @param targetId 事件目标
	 * @param eventActor 事件发起者
	 * @param replace eventActor
	 */
	public Event(long targetId, byte eventActor, boolean replace) {
		this.targetId = targetId;
		this.eventActor = eventActor;
		this.replace = replace;
		this.time = System.currentTimeMillis();
	}

	public byte getEventActor() {
		return eventActor;
	}

	public long getTargetId() {
		return targetId;
	}

	public long getTime() {
		return time;
	}


	public boolean isReplace() {
		return replace;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null){
			Event e = (Event)obj;
			if(e.getTargetId() == this.targetId && e.getClass().getName().equals(this.getClass().getName()))
				return true;
			return false;
		}
		return false;
	}
}
