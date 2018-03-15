package com.snail.webgame.engine.component.scene.protocal.storage;

import com.snail.webgame.engine.common.to.BaseTO;

/**
 * 调用存储服务器返回结果处理
 * @author zenggy
 *
 */
public interface StorageHandle {
	/**
	 * 调用存储返回处理
	 * @param result 结果 1：成功
	 * @param to 新对象
	 */
	public void execute(int result, BaseTO to);
}
