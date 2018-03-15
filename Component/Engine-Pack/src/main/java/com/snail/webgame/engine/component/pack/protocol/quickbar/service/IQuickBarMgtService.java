package com.snail.webgame.engine.component.pack.protocol.quickbar.service;

import com.snail.webgame.engine.component.pack.protocol.quickbar.QuickBarResp;
import com.snail.webgame.engine.component.pack.protocol.quickbar.add.QuickBarAddReq;
import com.snail.webgame.engine.component.pack.protocol.quickbar.exchange.QuickBarExchangeReq;

/**
 * @author wangxf
 * @date 2012-8-23
 * 角色包裹逻辑处理类
 */
public interface IQuickBarMgtService {

	/**
	 * 加载角色快捷栏信息
	 * @param roleId
	 * @return
	 * @author wangxf
	 * @date 2012-8-31
	 */
	public QuickBarResp loadRoleQuickBar(long roleId);

	/**
	 * 变更快捷栏
	 * @param roleId
	 * @param req
	 * @return
	 * @author wangxf
	 * @date 2012-9-3
	 */
	public void exchangeQiuckBar(long roleId, QuickBarExchangeReq req);

	/**
	 * 添加快捷栏关联
	 * @param roleId
	 * @param req
	 * @return
	 * @author wangxf
	 * @date 2012-9-5
	 */
	public void addQuickBar(long roleId, QuickBarAddReq req);

	/**
	 * 删除快捷栏
	 * @param roleId
	 * @param id
	 * @return
	 * @author wangxf
	 * @date 2012-9-6
	 */
	public void delQuickBar(int roleId, long id);
}
