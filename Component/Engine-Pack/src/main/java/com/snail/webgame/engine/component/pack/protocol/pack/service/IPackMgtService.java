package com.snail.webgame.engine.component.pack.protocol.pack.service;

import com.snail.webgame.engine.component.pack.protocol.pack.PackResp;
import com.snail.webgame.engine.component.pack.protocol.pack.del.PackDelResp;
import com.snail.webgame.engine.component.pack.protocol.pack.exchange.PackExchangeReq;

/**
 * @author wangxf
 * @date 2012-8-23
 * 角色包裹逻辑处理类
 */
public interface IPackMgtService {

	/**
	 * 加载角色背包
	 * @param roleId
	 * @return
	 * @author wangxf
	 * @date 2012-8-23
	 */
	public PackResp loadPack(long roleId);

	/**
	 * 整理角色背包
	 * @param roleId
	 * @return
	 * @author wangxf
	 * @date 2012-8-23
	 */
	public void arrangePack(long roleId);

	/**
	 * 调整背包位置
	 * @param roleId
	 * @param req
	 * @return
	 * @author wangxf
	 * @param quickBarResp 
	 * @date 2012-8-27
	 */
	public void exchangePack(long roleId, PackExchangeReq req);
	
	/**
	 * 删除背包
	 * @param id
	 * @param roleId
	 * @author wangxf
	 * @return 
	 * @date 2012-9-7
	 */
	public PackDelResp delPack(long roleId, long id);

}
