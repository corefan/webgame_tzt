package com.snail.webgame.engine.component.scene.core.policy;

public interface Policy {
	/**
	 * 无效策略
	 */
	public static final int INVALID = -1;
	/**
	 * 不移动，自动攻击范围内的目标
	 */
	public static final int STOP_AUTO_ATTACK = 0;
	/**
	 * 移动
	 */
	public static final int MOVE = 1;
	/**
	 * 自动攻击攻击范围内的目标，不在目标范围内则进行移动，直到将其纳入攻击方范围内,移动过程中不能攻击
	 */
	public static final int MOVE_AUTO_ATTACK = 2;
	/**
	 * 自动攻击攻击范围内的目标，不在目标范围内则进行移动，追击指定时间后切换攻击目标,移动过程中不能攻击
	 */
	public static final int PURSUE_AUTO_ATTACK = 3;
	/**
	 * 释放技能
	 */
	public static final int SKILL = 4;
	/**
	 * 自动攻击范围内的目标,不在目标范围内则进行移动，追击指定时间后返回初始位置,移动过程中不能攻击
	 */
	public static final int PURSUE_BACK_AUTO_ATTACK = 5;
}
