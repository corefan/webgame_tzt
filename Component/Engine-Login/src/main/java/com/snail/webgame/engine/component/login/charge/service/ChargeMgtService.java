package com.snail.webgame.engine.component.login.charge.service;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.Flag;
import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.common.util.IPCode;
import com.snail.webgame.engine.common.util.TimeFormat;
import com.snail.webgame.engine.component.login.ChargeMessageHead;
import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.cache.ServerMap;
import com.snail.webgame.engine.component.login.cache.TempMsgrMap;
import com.snail.webgame.engine.component.login.charge.refresh.RefreshAccReq;
import com.snail.webgame.engine.component.login.charge.refresh.RefreshAccResp;
import com.snail.webgame.engine.component.login.charge.register.GameRegisterReq;
import com.snail.webgame.engine.component.login.charge.total.TotalOnlineReq;
import com.snail.webgame.engine.component.login.charge.userlogout.UserLogoutReq;
import com.snail.webgame.engine.component.login.charge.validate.VerfiyAccountResp;
import com.snail.webgame.engine.component.login.charge.validate.VerifyAccountReq;
import com.snail.webgame.engine.component.login.charge.validatein.VerifyAccountInReq;
import com.snail.webgame.engine.component.login.charge.validatein.VerifyAccountInResp;
import com.snail.webgame.engine.component.login.config.GameConfig;
import com.snail.webgame.engine.component.login.config.GameOtherConfig;
import com.snail.webgame.engine.component.login.core.RoleService;
import com.snail.webgame.engine.component.login.dao.RoleDAO;
import com.snail.webgame.engine.component.login.protocal.enthrallment.RoleEnthrallmentOffReq;
import com.snail.webgame.engine.component.login.protocal.enthrallment.RoleEnthrallmentResp;
import com.snail.webgame.engine.component.login.protocal.login.UserLoginReq;
import com.snail.webgame.engine.component.login.protocal.login.UserLoginResp;
import com.snail.webgame.engine.component.login.protocal.verify.UserVerifyReq;
import com.snail.webgame.engine.component.login.protocal.verify.UserVerifyResp;
 

public class ChargeMgtService {

	protected static final Logger logger=LoggerFactory.getLogger("logs");
	protected RoleDAO roleDAO = new RoleDAO();
	
	/**
	 * 发送注册计费的请求
	 * @param session
	 */
	public static void sendReqCharge(IoSession session)
	{
		Message message = new Message();
		
		
		ChargeMessageHead head = new ChargeMessageHead();
		head.setVersion(0x301);
		head.setMsgType(0x1000);
		
		GameRegisterReq req = new GameRegisterReq();
		
		req.setGameType(GameConfig.getInstance().getGameType());
		req.setServerId(GameConfig.getInstance().getGameServerId());
		req.setServerName(GameConfig.getInstance().getGameServerName());
		req.setMD5Pass("123");
		req.setIsNewReg(Flag.flag);
		
		message.setHeader(head);
		message.setBody(req);
		session.write(message);
		
		 if(logger.isInfoEnabled())
		 {
			 logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_24")+"......");
		 }
	}
	/**
	 * 发送注销请求
	 */
	public static void  sendChargeUnRegisterReq()
	{
		Message message = 	new Message();
		ChargeMessageHead head = new ChargeMessageHead();
		
		head.setVersion(0x301);
		head.setMsgType(0x1002);
 
		 IoSession session = ServerMap.getServerSession(ServerName.GAME_CHARGE_SERVER);
		
		if(session!=null&&session.isConnected())
		{
			message.setHeader(head);
 
			session.write(message);
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_25")+".........");
			}
			
		}
			
		
		 
	}

	/**
	 * 游戏激活消息
	 */
	public static void activeCharge(IoSession session)
	{
		Message message = new Message();
		ChargeMessageHead head = new ChargeMessageHead();
		head.setVersion(0x301);
		head.setMsgType(0xFFFF);
		message.setHeader(head);
		session.write(message);
		 if(logger.isInfoEnabled())
		 {
			 logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_0")+"......");
		 }
	}
	
	/**
	 * 发送注销的请求
	 * @param session
	 */
	public static void sendLogoutCharge(IoSession session)
	{
		Message message = new Message();
		ChargeMessageHead head = new ChargeMessageHead();
		head.setVersion(0x301);
		head.setMsgType(0x1002);
		message.setHeader(head);
		session.write(message);
	}
	
	
	public static void sendVerfiyAccount(int sequenceId ,UserVerifyReq loginReq)
	{
		
		
		IoSession session = ServerMap.getServerSession(ServerName.GAME_CHARGE_SERVER);
		
		if(session!=null&&session.isConnected())
		{
			Message message = new Message();
			
			ChargeMessageHead head = new ChargeMessageHead();
			head.setUserID0(sequenceId);
			head.setVersion(0x301);
			head.setMsgType(0x3000);
			VerifyAccountReq req = new VerifyAccountReq();
		
			req.setAccount(loginReq.getAccount());
			req.setMd5Pass(loginReq.getMd5Pass());
			String IP = IPCode.intToIP(loginReq.getIP());
			req.setAddress(IP);
			
		  //req.setVendorId();
			String validate = loginReq.getValidate();//验证串
			String reserve = loginReq.getReserved();//牛盾串
			
			if(validate!=null&&validate.trim().length()>0)
			{
				req.setAccType(2);
				req.setValidate(validate);
				
				if(reserve!=null&&reserve.trim().length()>0)
				{
					req.setValidate(validate+"$"+reserve);
				}
			}
			else if(reserve != null && reserve.trim().length() > 0)
			{
				//牛盾登陆方式
				req.setAccType(4);
				req.setValidate(reserve);
			}
			else
			{
				req.setAccType(1);
			}
			req.setClientType(loginReq.getClientType());
			message.setHeader(head);
			message.setBody(req);
			
			session.write(message);
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game","USER_LOGIN_REQ_SEND")+":account="+req.getAccount()+",ip="+IP);
			}
			
			
		}
		else
		{
	 
			Message message = 	TempMsgrMap.getMessage(sequenceId);
		 
			GameMessageHead head = (GameMessageHead) message.getHeader();
			
			IoSession session1 = ServerMap.getServerSession(GameOtherConfig.getInstance().getGateServerName()+"-"+head.getUserID1());
	 
			if(session1!=null&&session1.isConnected())
			{

				UserVerifyResp resp = new UserVerifyResp();
				 
				resp.setAccount(loginReq.getAccount());
				resp.setResult(ErrorCode.USER_LOGIN_CHARGE_ERROR_1);
				resp.setServerId(GameConfig.getInstance().getGameServerId());
				head.setProtocolId(0xA002);
				 

				message.setBody(resp);
				  
				session1.write(message);
				if(logger.isInfoEnabled())
				{
					logger.info(Resource.getMessage("game","USER_GET_LIST_RESP")+":result="+resp.getResult());
				}
			}
			
			TempMsgrMap.removeMessage(sequenceId);
		}
	}
	
	
	/**
	 * 验证帐号是否正确
	 * @param sequenceId
	 * @param chargeResp
	 */
	public void VerfiyAccount(int sequenceId, VerfiyAccountResp chargeResp)
	{
		UserVerifyResp resp = new UserVerifyResp();
		 
		Message message = TempMsgrMap.getMessage(sequenceId);
		
		if(message!=null)//计费应用这个请求的应答会返回多次，所以message可能为null
		{
			GameMessageHead head = (GameMessageHead) message.getHeader();
			 
			IoSession session = ServerMap.getServerSession(GameOtherConfig.getInstance().getGateServerName()+"-"+head.getUserID1());

			if(session!=null&&session.isConnected())
			{
				resp.setAccount(chargeResp.getAccount());
				 
				resp.setResult(chargeResp.getResult());
				resp.setServerId(GameConfig.getInstance().getGameServerId());
				head.setProtocolId(0xA002);
				message.setHeader(head);
				message.setBody(resp);
				  
				session.write(message);
				//保存该串
				String str = chargeResp.getSecondStr();
				if(str != null && str.trim().length() > 0)
				{
					RoleInfoMap.addSecondStr(chargeResp.getAccount(), chargeResp.getSecondStr());
				}
				
				if(logger.isInfoEnabled())
				{
					logger.info(Resource.getMessage("game","USER_LOGIN_RESULT")
							 +":result="+resp.getResult()+",account="+resp.getAccount());
				}
			} 
			else
			{
				 if(logger.isInfoEnabled())
				 {
					 logger.info(Resource.getMessage("game","USER_LOGIN_RESULT")
							 +":result="+resp.getResult()+",account="+resp.getAccount());
				 }
			}
		}
		else
		{
			if(logger.isWarnEnabled())
			{
				logger.warn(Resource.getMessage("game","USER_LOGIN_RESULT")+":System is not exist sequenceId="+sequenceId);
			}

		}
		
		TempMsgrMap.removeMessage(sequenceId);
	}

	
	
	/**
	 * 发送用户登录请求
	 */
	public void sendUserLogin(UserLoginReq loginReq,int sequenceId,int flag)
	{
		
		IoSession session = ServerMap.getServerSession(ServerName.GAME_CHARGE_SERVER);
		
		if(Flag.flag==0&&session!=null&&session.isConnected())
		{
			Message message = new Message();
			 
			ChargeMessageHead head = new ChargeMessageHead();
			head.setVersion(0x301);
			head.setUserID0(sequenceId);
			head.setUserID1(flag);//0 普通登录 1创建帐号
			head.setMsgType(0x2000);
			VerifyAccountInReq req = new VerifyAccountInReq();
			
			
			
			req.setAccount(loginReq.getAccount().toUpperCase());
			req.setMd5Pass(loginReq.getMd5Pass().toUpperCase());
			
			req.setClientType(loginReq.getClientType());
			
			String validate = loginReq.getValidate();//验证串
			String reserve = loginReq.getReserved();//牛盾串
			
			
			if(validate!=null&&validate.trim().length()>0)
			{
				req.setAccType(2);
				req.setValidate(validate);

				if(reserve!=null&&reserve.trim().length()>0)
				{
					req.setValidate(validate+"$"+reserve);
				}
			}
			else if(reserve != null && reserve.trim().length() > 0)
			{
				//牛盾登陆方式
				req.setAccType(4);
				req.setValidate(reserve);
			}
			else
			{
				req.setAccType(1);
			}

			//存在串的情况下必须原样返回计费串类型为2
			String secondStr = RoleInfoMap.getSecondStr(req.getAccount());
			if(secondStr != null && secondStr.trim().length() > 0)
			{
				req.setAccType(2);
				req.setValidate(secondStr);
			}
			
			String IP = IPCode.intToIP(loginReq.getIP());
			req.setAddress(IP);
			req.setPort(0);
			 
			message.setHeader(head);
			message.setBody(req);
			
			session.write(message);
			//移除计费串
			RoleInfoMap.removeSecondStr(req.getAccount());
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_7")+":account="+req.getAccount());
			}
		}
		else
		{
			RoleService.isCanLogin(0);//计费断开 重置登录
			
			
			Message message = 	TempMsgrMap.getMessage(sequenceId);
			
			UserLoginResp resp = new UserLoginResp();
			
			resp.setResult(ErrorCode.USER_LOGIN_CHARGE_ERROR_1);
			
			sendUserLoginResp(message,resp,1,loginReq.getChargeAccount());
			
			TempMsgrMap.removeMessage(sequenceId);
		}

	}
	
	
	
	/**
	 * 验证用户帐号登录应答处理
	 * @param chargeResp
	 */
	public void  userLoginMgt(VerifyAccountInResp chargeResp,int sequenceId,int flag)
	{
		
		Message message = 	TempMsgrMap.getMessage(sequenceId);
 
		if(message!=null)
		{
			if(flag==0)
			{
				userLogin(chargeResp,message);
			}
			else if(flag==1)
			{
				createRole(chargeResp,message);
			}
			TempMsgrMap.removeMessage(sequenceId);
		}
		else
		{
			if(logger.isWarnEnabled())
			{
				logger.warn("In the billing server returns a login response" +
						", unable to find the right message sequence number,sequenceId="+sequenceId);
			}
		}
	}
	
	
	/**
	 * 用户登录结果处理
	 * @param chargeResp
	 * @param message
	 */
	public void userLogin(VerifyAccountInResp chargeResp,Message message){}
	

	
	/**
	 * 用户登录创建结果处理
	 * @param chargeResp
	 * @param message
	 */
	public  void createRole(VerifyAccountInResp chargeResp,Message message){}
	
 
	/**
	 * 发送用户登录处理结果到客户端
	 * @param message
	 * @param resp
	 */
	public void sendUserLoginResp(Message message,UserLoginResp resp,int flag,String account)
	{
		 
 
			GameMessageHead  head = (GameMessageHead) message.getHeader();
			
			int gateServerId = head.getUserID1();
			if(resp.getResult() == 1)
			{
				//玩家登陆通知GMCC
				//Gmcc.gameRoleLogin(resp.getRoleId(), account, resp.getRoleName());
			}
			
			IoSession session1 = ServerMap.getServerSession(GameOtherConfig.getInstance().getGateServerName()+"-"+gateServerId);
			 
			if(session1!=null&&session1.isConnected())
			{
				head.setMsgType(0xA006);
			 
				message.setBody(resp);
				message.setHeader(head);
				session1.write(message);
				
				if(flag==0)
				{
					if(logger.isInfoEnabled())
					{
						logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_6")+":result="+resp.getResult()+",roleId="+resp.getRoleId()
								+",roleName="+resp.getRoleName());
					}
				}
				else
				{
					if(logger.isInfoEnabled())
					{
						logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_9")+":result="+resp.getResult()+",roleId="+resp.getRoleId()
								+",roleName="+resp.getRoleName());
					}
				}

			}
			else
			{
				if(flag==0)
				{
					if(logger.isInfoEnabled())
					{
						logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_10")+":result="+resp.getResult()+",roleId="+resp.getRoleId()
								+",roleName="+resp.getRoleName());
					}
				}
				else
				{
					if(logger.isInfoEnabled())
					{
						logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_11")+":result="+resp.getResult()+",roleId="+resp.getRoleId()
								+",roleName="+resp.getRoleName());
					}
				}
			}
			
			
	}
	
	/**
	 * 发送用户注销请求
	 * @param roleId
	 */
	public void sendUserLogout(int roleId)
	{
		RoleInfo roleInfo  = RoleInfoMap.getRoleInfo(roleId);
		
		if(roleInfo!=null)
		{
			sendLogoutMsg(roleInfo.getId(),roleInfo.getAccount(),roleInfo.getLoginId(),roleInfo.getGMLevel()
					,roleInfo.getAccountId(),roleInfo.getMD5Pass());
			
		}
	
	}
	
	protected void sendLogoutMsg(long roleId,String account,String loginId,int gmLevel,int accId,String MD5Pass)
	{
		//玩家下线通知GMCC
		//Gmcc.gameRoleLogout(roleId, account);
		
		
		IoSession session = ServerMap.getServerSession(ServerName.GAME_CHARGE_SERVER);
		
		if(session!=null&&session.isConnected())
		{
			Message message = new Message();
			ChargeMessageHead head = new ChargeMessageHead();
			head.setVersion(0x301);
			head.setMsgType(0x2002);
			head.setUserID0((int)roleId);
			
			UserLogoutReq req =  new UserLogoutReq();
			req.setAccount(account.toUpperCase());
			req.setLoginId(loginId);
			req.setGMLevel(gmLevel);
			req.setAccountId(accId);
			if(MD5Pass!=null)
			{
				req.setMD5Pass(MD5Pass.toUpperCase());
			}
			message.setHeader(head);
			message.setBody(req);
			
			session.write(message);
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_12")+":roleId="+roleId
						+",account="+account+",loginId="+loginId+",accId="+accId);
			}
		}
		else
		{
			if(logger.isErrorEnabled())
			{
				logger.error(Resource.getMessage("game", "CHARGE_SERVER_INFO_12")+":roleId="+roleId
						+",account="+account+",loginId="+loginId+",accId="+accId);
			}
		}
	
	}
	
	
 
	
	
	/**
	 * 发送定时刷新给计费
	 */
	public void sendRefreshReq(long roleId)
	{
		Message message = 	new Message();
		ChargeMessageHead head = new ChargeMessageHead();
		
		head.setVersion(0x301);
		head.setMsgType(0x2004);
		head.setUserID0((int)roleId);
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		
		if(roleInfo!=null)
		{
			IoSession session = ServerMap.getServerSession(ServerName.GAME_CHARGE_SERVER);
		
			if(session!=null&&session.isConnected())
			{
				RefreshAccReq req = new RefreshAccReq();
				req.setAccountId(roleInfo.getAccountId());
				req.setLoginId(roleInfo.getLoginId());
				req.setGMLevel(roleInfo.getGMLevel());
				
				
				message.setHeader(head);
				message.setBody(req);
				session.write(message);
			}
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_27")+":roleId="+roleId);
			}
		
		}
	}
	/**
	 * 向角色客户端发送“防沉迷”请求
	 * add by miaozhe 2010.07.12
	 * @param roleId
	 * @param promptType
	 * @param activeOnlineTime
	 * @param gateServerId
	 */
	public static void sendRoleEnthrallment(long roleId,int promptType,long activeOnlineTime, int gateServerId)
	{
		Message message = new Message();
		GameMessageHead head = new GameMessageHead();
		head.setMsgType(0xA016);//非沉迷提示请求
		head.setUserID0((int)roleId);
		
		RoleEnthrallmentResp resp = new RoleEnthrallmentResp();
		resp.setPromptType(promptType);
		resp.setCumulativeOnlineTime(activeOnlineTime);
	
		message.setHeader(head);
		message.setBody(resp);
		
	 	IoSession session = ServerMap.getServerSession(GameOtherConfig.getInstance().getGateServerName()+"-"+gateServerId);
		if(session!=null&&session.isConnected())
		{
			session.write(message);
		}
		
	}
	
	/**
	 * 向角色客户端发送“防沉迷”下线提示请求
	 * add by miaozhe 2010.07.12
	 * @param roleId
	 * @param promptId
	 * @param gateServerId
	 */
	public static void sendRoleEnthOff(long roleId,int promptId,int gateServerId)
	{
		Message message = new Message();
		GameMessageHead head = new GameMessageHead();
		head.setMsgType(0xA017);//非沉迷离线提示请求
		head.setUserID0((int)roleId);
		
		RoleEnthrallmentOffReq resp = new RoleEnthrallmentOffReq();
		resp.setPromptId(promptId);
	
		message.setHeader(head);
		message.setBody(resp);
		
	 	IoSession session = ServerMap.getServerSession(GameOtherConfig.getInstance().getGateServerName()+"-"+gateServerId);
		if(session!=null&&session.isConnected())
		{
			session.write(message);
		}
		
	}
	
	/**
	 * 向角色发送防沉迷请求
	 * @param resp
	 */
	public void refreshAccountResp(int roleId,RefreshAccResp resp)
	{
		
		int accId = resp.getAccountId();
		int result = resp.getResult();
		int point = resp.getPoints();
		//double time = resp.getAccLimit();
		double time = resp.getPoints();//add miaozhe 2010.07.13（根据计费开发人员的说明）
	
	 
		if(result==1||result==3)
		{
			//time -1非防沉迷，大于-1防沉迷（且为用户在线防沉迷时间）
			if(time==-1)
			{
				//----------begin add by miaozhe 发送非防沉迷请求给客户端------------------
				if(roleId>0)
				{
					//启动国内防沉迷
					if(GameOtherConfig.getInstance().getPromptFlag()==3)
					{
						RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
						if(roleInfo != null)
						{
							int promptType = 1;//0是防沉迷，1是非防沉迷
							//如果原状态不是“非防沉迷”请求，则向客户端发送“非防沉迷”请求
							if(roleInfo.getPromptStatus() != 1)
							{
								//向客户端发请求
								sendRoleEnthrallment(roleId, promptType, 
										roleInfo.getCumulativeOnlineTime(), roleInfo.getGateServerId());
								//修改防沉迷状态为“非防沉迷”
								roleInfo.setPromptStatus(promptType);
							}
						}
						
					}
				}
				//--------------end add by miaozhe 发送非防沉迷请求给客户端-----------------
			}
			else
			{
				if(roleId>0)
				{
					//启动国内防沉迷
					if(GameOtherConfig.getInstance().getPromptFlag()==3)
					{
						//----------begin add by miaozhe 发送非防沉迷请求给客户端------------------
						RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
						if(roleInfo != null)
						{
							roleInfo.setCumulativeOnlineTime((long)time);
							
							//3小时向客户端防沉迷提示
							if(time*1000>=(3*3600*1000L) &&
									time*1000<(3*3600*1000L + 10*60*1000L))
							{
								int promptId = 3;//3离线标示
								//向客户端发请求
								sendRoleEnthOff(roleId, promptId, roleInfo.getGateServerId());
							}
							//3小时10分钟向客户段发登出请求
							else if(time*1000>=(3*3600*1000L + 10*60*1000L))
							{
								//用户下线
								//GMCmdMgtService.sendRoleOutMsg(roleId,roleInfo.getGateServerId(),ErrorCode.GM_CMD_ERROR_12);
							}
							else 
							{
								int promptType = 0;//0是防沉迷，1是非防沉迷
								//如果原状态不是“防沉迷”请求，则向客户端发送“防沉迷”请求
								if(roleInfo.getPromptStatus() != 0)
								{
									//修改防沉迷状态为“防沉迷”
									roleInfo.setPromptStatus(promptType);
								}
								//向客户端发请求
								sendRoleEnthrallment(roleId, promptType,
										roleInfo.getCumulativeOnlineTime(), roleInfo.getGateServerId());
							}
						}
						
						//--------------end add by miaozhe 发送非防沉迷请求给客户端-----------------
					}
				}
			}
			
		}
		
		if(logger.isInfoEnabled())
		{
				logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_15")+": accountId="+accId
						+",result="+result+",roleId="+roleId
						+",point="+point+",time="+time);
		}
	}
	
	
	
	
	
	
	/**
	 * 发送定时统计人数
	 */
	public void sendTotalOnlineReq(int onlineNum)
	{
		Message message = 	new Message();
		ChargeMessageHead head = new ChargeMessageHead();
		
		head.setVersion(0x301);
		head.setMsgType(0x6000);

		IoSession session = ServerMap.getServerSession(ServerName.GAME_CHARGE_SERVER);
		
		if(session!=null&&session.isConnected())
		{
			TotalOnlineReq req = new TotalOnlineReq();
			req.setArgNum(3);
			req.setMsgType(0x801D);
			req.setTime(TimeFormat.format(System.currentTimeMillis()));
			req.setPlayNum(onlineNum);
			message.setHeader(head);
			message.setBody(req);
			session.write(message);
		}
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_28")+":onlineNum="+onlineNum);
		}
			
		
		 
	}
	
}
