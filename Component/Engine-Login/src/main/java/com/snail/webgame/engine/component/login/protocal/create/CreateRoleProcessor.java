package com.snail.webgame.engine.component.login.protocal.create;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.cache.TempMsgrMap;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;
import com.snail.webgame.engine.component.login.protocal.login.UserLoginReq;
import com.snail.webgame.engine.component.login.protocal.login.UserLoginResp;
import com.snail.webgame.engine.component.login.protocal.service.RoleMgtService;
import com.snail.webgame.engine.component.login.util.Sequence;

public class CreateRoleProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	private RoleMgtService roleMgtService;
	private ChargeMgtService chargeMgtService;
	
	
	public void setRoleMgtService(RoleMgtService roleMgtService) {
		this.roleMgtService = roleMgtService;
	}


	public void setChargeMgtService(ChargeMgtService chargeMgtService) {
		this.chargeMgtService = chargeMgtService;
	}


	public void execute(Request request, Response response) 
	{
		Message message = request.getMessage();
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		head.setMsgType(0xA006);
		
		CreateRoleReq req = (CreateRoleReq) message.getBody();
		
		//服务器尚未开放
		if(GameValue.IS_ALLOW_LOGIN==0)
		{
			UserLoginResp resp = new UserLoginResp();
			resp.setResult(ErrorCode.GAME_LOGIN_ERROR_1);
			message.setBody(resp);
			response.write(message);
				
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_3")+":result="+resp.getResult()+",roleId="+resp.getRoleId());
			}
		}
		else if(GameValue.GAME_VALIDATEIN_FLAG==0)//非验证登录
		{
			UserLoginResp resp = roleMgtService.createRoleInfo(req,head.getUserID1());

			message.setBody(resp);
			response.write(message);
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_3")+":result="+resp.getResult()+",roleId="+resp.getRoleId());
			}
		}
		else//验证登录
		{
			UserLoginResp resp = roleMgtService.createRoleInfo(req,head.getUserID1());
			if(resp.getResult()!=1)
			{
				message.setBody(resp);
				response.write(message);
				
				if(logger.isInfoEnabled())
				{
					logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_2")+":result="+resp.getResult()+",roleId="+resp.getRoleId());
				}
			}
			else
			{
				//创建角色成功后 发送登录请求
				UserLoginReq loginReq = new UserLoginReq();
				
				int sequenceId = Sequence.getSequenceId();
		
				GameMessageHead header = (GameMessageHead) message.getHeader();
				header.setMsgType(0xA007);
				
				loginReq.setIP(req.getIP());
				loginReq.setAccount(req.getAccount());
				loginReq.setMd5Pass(req.getMD5Pass());
				loginReq.setValidate(req.getValidate());
				loginReq.setClientType(req.getClientType());
				loginReq.setChargeAccount(req.getChargeAccount());
				
				Message message1 = new Message();
				message1.setHeader(header);
				message1.setBody(loginReq);
				
				TempMsgrMap.addMessage(sequenceId,message1);
				chargeMgtService.sendUserLogin(loginReq, sequenceId, 0);
			}
		}
	}
}
