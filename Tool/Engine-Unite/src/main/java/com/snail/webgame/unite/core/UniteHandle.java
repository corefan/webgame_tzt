package com.snail.webgame.unite.core;

public abstract class UniteHandle {
	/**
	 * 合服业务逻辑处理方法
	 * @param uniteSign
	 */
	public abstract void unite(String sign);
	/**
	 * 角色迁移业务逻辑处理方法
	 */
	public abstract void move(String sign);
}
