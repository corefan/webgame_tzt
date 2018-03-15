package com.snail.webgame.engine.component.login.protocal.service;


import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.login.cache.RoleLoginQueueInfoMap;
import com.snail.webgame.engine.component.login.dao.RoleDAO;
import com.snail.webgame.engine.component.login.info.RoleLoginQueueInfo;
import com.snail.webgame.engine.component.login.protocal.check.CheckNameReq;
import com.snail.webgame.engine.component.login.protocal.check.CheckNameResp;
import com.snail.webgame.engine.component.login.protocal.create.CreateRoleReq;
import com.snail.webgame.engine.component.login.protocal.create.CreateRoleResp;
import com.snail.webgame.engine.component.login.protocal.login.UserLoginReq;
import com.snail.webgame.engine.component.login.protocal.login.UserLoginResp;
import com.snail.webgame.engine.component.login.protocal.loginqueue.LoginQueueResp;

/**
 * 角色相关操作
 * @author zenggy
 *
 */
public abstract class RoleMgtService 
{
	protected RoleDAO roleDAO = new RoleDAO();
	
	/**
	 * 查询角色
	 * @param account 玩家帐号
	 * @return
	 */
	public RoleInfo getRoleInfo(String account){
		return roleDAO.getRoleInfo(account);
	}

	/**
	 * 通知进入创建角色
	 * @param account
	 * @return
	 */
	public abstract CreateRoleResp getCreateRoleResp(String account);

	/**
	 * 用户登录
	 * @param roleId
	 * @param req
	 * @param gateServerId
	 * @return
	 */
	public abstract UserLoginResp userLogin(long roleId, UserLoginReq req, int gateServerId);

	/**
	 * 创建角色后登录
	 * @param req
	 * @param gateServerId
	 * @param account
	 * @return
	 */
	public abstract UserLoginResp createRoleInfo(CreateRoleReq req, int gateServerId);

	/**
	 * 玩家注销
	 * @param roleId
	 */
	public abstract void userLogout(int roleId);
	



	/**
	 * 检查角色是否重复
	 * @param req
	 * @return
	 */
	public  CheckNameResp checkRoleName(CheckNameReq req)
	{
		CheckNameResp resp = new CheckNameResp();
		
		String roleName = req.getRoleName();
		
		if(roleName == null || roleName.trim().length() == 0)
		{
			resp.setResult(ErrorCode.SYSTEM_ERROR);
			return resp;
		}
		
		resp.setRoleName(roleName);
		resp.setResult(1);
		
		if(roleDAO.checkRoleName(roleName))
		{
			resp.setCheckResult(2);//角色重复
		}
		else
		{
			resp.setCheckResult(1);
		}
		
		return resp;
	}
	
	
	/**
	 * 获取排队队列
	 * @param roleId
	 * @return
	 */
	public LoginQueueResp getLoginQueueResp(String account) 
	{
		LoginQueueResp resp = new LoginQueueResp();
		resp.setResult(1);
		
		int index = RoleLoginQueueInfoMap.getIndex(account.toUpperCase());
		int num = RoleLoginQueueInfoMap.getAllNum();
		String roleName = RoleLoginQueueInfoMap.getRoleName(account.toUpperCase());
		
		if(index == -1 || roleName == null || roleName.trim().length() == 0)
		{
			resp.setResult(-1);
			return resp;
		}
		
		resp.setIndex(index);
		resp.setNum(num);
		resp.setRoleName(roleName);
		resp.setAccount(account);
		
		return resp;
	}

	/**
	 * 添加排队队列
	 * @param roleId
	 * @param req
	 * @param sequenceId
	 * @param gateServerId
	 * @param isLogin
	 * @param roleName
	 * @return
	 */
	public LoginQueueResp addLoginQueue(long roleId, UserLoginReq req, int sequenceId, int gateServerId, String roleName)
	{
		LoginQueueResp queueResp = new LoginQueueResp();
		queueResp.setResult(1);
		
		//角色存在并且不在离线允许时间内,返回排队
		RoleLoginQueueInfo roleLoginQueueInfo = new RoleLoginQueueInfo();
		roleLoginQueueInfo.setAccount(req.getChargeAccount());
		roleLoginQueueInfo.setIP(req.getIP());
		roleLoginQueueInfo.setClientType(req.getClientType());
		roleLoginQueueInfo.setMD5Pass(req.getMd5Pass());
		roleLoginQueueInfo.setReserved(req.getReserved());
		roleLoginQueueInfo.setValidate(req.getValidate());
		roleLoginQueueInfo.setGateServerId(gateServerId);
		roleLoginQueueInfo.setRoleName(roleName);
		roleLoginQueueInfo.setChargeAccount(req.getChargeAccount());
		roleLoginQueueInfo.setSequenceId(sequenceId);
		
		int index = RoleLoginQueueInfoMap.getIndex(roleLoginQueueInfo);
		int num = RoleLoginQueueInfoMap.getAllNum();
		queueResp.setIndex(index);
		queueResp.setNum(num);
		queueResp.setRoleName(roleName);
		queueResp.setAccount(req.getChargeAccount());

		return queueResp;
	}

	/**
	 * 移除断线账号
	 * @param account
	 */
	public void checkLoginQueue(String account) 
	{
		RoleLoginQueueInfoMap.removeQueueInfo(account);
	}

}
