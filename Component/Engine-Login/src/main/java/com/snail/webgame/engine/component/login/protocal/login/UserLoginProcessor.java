package com.snail.webgame.engine.component.login.protocal.login;

import java.util.List;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.cache.RoleLoginQueueInfoMap;
import com.snail.webgame.engine.component.login.cache.ServerMap;
import com.snail.webgame.engine.component.login.cache.TempMsgrMap;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;
import com.snail.webgame.engine.component.login.config.GameOtherConfig;
import com.snail.webgame.engine.component.login.core.RoleService;
import com.snail.webgame.engine.component.login.protocal.create.CreateRoleResp;
import com.snail.webgame.engine.component.login.protocal.loginqueue.LoginQueueResp;
import com.snail.webgame.engine.component.login.protocal.service.RoleMgtService;
import com.snail.webgame.engine.component.login.util.Sequence;

public class UserLoginProcessor extends ProtocolProcessor {

	private static final Logger logger = LoggerFactory.getLogger("logs");

	private RoleMgtService roleMgtService;
	private ChargeMgtService chargeMgtService ; 
	
	
	
	public void setRoleMgtService(RoleMgtService roleMgtService) {
		this.roleMgtService = roleMgtService;
	}

	
	public void setChargeMgtService(ChargeMgtService chargeMgtService) {
		this.chargeMgtService = chargeMgtService;
	}	
	public void execute(Request request, Response response) {

		Message message = request.getMessage();

		int sequenceId = Sequence.getSequenceId();

		TempMsgrMap.addMessage(sequenceId, message);
		UserLoginReq req = (UserLoginReq) message.getBody();
		GameMessageHead head = (GameMessageHead) message.getHeader();

		if (GameValue.IS_ALLOW_LOGIN == 0) {
			UserLoginResp resp = new UserLoginResp();
			head.setMsgType(0xA006);
			resp.setResult(ErrorCode.GAME_LOGIN_ERROR_1);

			message.setBody(resp);
			response.write(message);

			if (logger.isInfoEnabled()) {
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_1")
						+ ":result=" + resp.getResult() + ",roleId="
						+ resp.getRoleId());
			}

			return;
		}

		if (!RoleService.isCanLogin(1)) {
			UserLoginResp resp = new UserLoginResp();
			head.setMsgType(0xA006);
			resp.setResult(ErrorCode.GAME_LOGIN_ERROR_2);

			message.setBody(resp);
			response.write(message);

			if (logger.isInfoEnabled()) {
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_1")
						+ ":result=" + resp.getResult() + ",roleId="
						+ resp.getRoleId());
			}
			return;
		}

		if (req.getChargeAccount() == null
				|| req.getChargeAccount().trim().length() == 0) {
			UserLoginResp resp = new UserLoginResp();
			resp.setResult(ErrorCode.SYSTEM_ERROR);
			head.setMsgType(0xA006);
			message.setBody(resp);
			response.write(message);

			if (logger.isInfoEnabled()) {
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_1")
						+ ":result=" + resp.getResult() + ",chargeAccount="
						+ req.getChargeAccount());
			}
			return;
		}

		// 判断角色是否创建
		RoleInfo role = roleMgtService.getRoleInfo(req.getChargeAccount());
		long roleId = 0;
		if(role != null)
			roleId = role.getId();

		// 判断玩家下线时间是否超过6分钟,没超过6分钟则不管是否到达最大在线人数都让其登陆游戏
		boolean isLogin = true;
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		if (roleInfo != null) {
			long lastLogoutTime = roleInfo.getLogoutTime(); // 角色最后登录时间
			isLogin = RoleService.isOutLogoutTime(lastLogoutTime);
		}
		else {
			isLogin = false;
		}
		if(role != null)
			roleInfo.setSceneId(role.getSceneId());
		// 如果队列中有人在排队,那么不管是否达到最大登录人数,所有登录都加入排队
		List<String> list = RoleLoginQueueInfoMap.getList();
		if (!isLogin && list.size() > 0 && roleInfo != null
				&& !RoleLoginQueueInfoMap.isMessageLogin(req.getChargeAccount())) {
			LoginQueueResp queueResp = roleMgtService.addLoginQueue(roleId,
					req, head.getUserID2(), head.getUserID1(),
					roleInfo.getRoleName());

			Message message1 = new Message();

			GameMessageHead head1 = new GameMessageHead();

			head1.setUserID0((int)roleId);
			head1.setUserID1(head.getUserID1());
			head1.setUserID2(head.getUserID2());

			head1.setMsgType(0xA012);
			head1.setProtocolId(0xA012);

			message1.setBody(queueResp);

			message1.setHeader(head1);

			IoSession session = ServerMap
					.getServerSession(GameOtherConfig.getInstance().getGateServerName() + "-"
							+ head.getUserID1());

			if (session != null && session.isConnected()) {
				session.write(message1);
			}

			if (logger.isInfoEnabled()) {
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_8")
						+ ":result=" + queueResp.getResult() + ",chargeAccout="
						+ req.getAccount() + ",index=" + queueResp.getIndex()
						+ ",num=" + queueResp.getNum());
			}
			return;

		}

		if (!isLogin
				&& GameOtherConfig.getInstance().getOnlineNum() > 0
				&& GameOtherConfig.getInstance().getOnlineNum() <= RoleInfoMap
						.getOnlineSize())// 最大在线人数判断
		{
			// 角色存在并且不在离线允许时间内,返回排队
			if (roleId >= 0 && !isLogin) {
				LoginQueueResp queueResp = roleMgtService.addLoginQueue(roleId,
						req, head.getUserID2(), head.getUserID1(),
						roleInfo.getRoleName());

				Message message1 = new Message();

				GameMessageHead head1 = new GameMessageHead();

				head1.setUserID0((int)roleId);
				head1.setUserID1(head.getUserID1());
				head1.setUserID2(head.getUserID2());

				head1.setMsgType(0xA012);
				head1.setProtocolId(0xA012);

				message1.setBody(queueResp);

				message1.setHeader(head1);

				IoSession session = ServerMap
						.getServerSession(GameOtherConfig.getInstance().getGateServerName() + "-"
								+ head.getUserID1());

				if (session != null && session.isConnected()) {
					session.write(message1);
				}

				if (logger.isInfoEnabled()) {
					logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_8")
							+ ":result=" + queueResp.getResult()
							+ ",chargeAccout=" + req.getAccount() + ",index="
							+ queueResp.getIndex() + ",num="
							+ queueResp.getNum());
				}
				return;
			}

			UserLoginResp resp = new UserLoginResp();
			head.setMsgType(0xA006);
			resp.setResult(ErrorCode.GAME_LOGIN_ERROR_3);

			message.setBody(resp);
			response.write(message);

			if (logger.isInfoEnabled()) {
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_1")
						+ ":result=" + resp.getResult() + ",roleId="
						+ resp.getRoleId());
			}
			return;
		}

		if (roleId == 0)// 角色尚未创建
		{
			// 返回客户端通知进入创建角色界面
			CreateRoleResp resp = roleMgtService.getCreateRoleResp(req.getAccount());

			head.setMsgType(0xA008);
			message.setBody(resp);
			response.write(message);

			if (logger.isInfoEnabled()) {
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_2")
						+ ":result=" + resp.getResult() + ",account="
						+ req.getAccount());
			}
		}
		else if (roleId == -1) {
			UserLoginResp resp = new UserLoginResp();
			resp.setResult(ErrorCode.SYSTEM_ERROR);
			head.setMsgType(0xA006);
			message.setBody(resp);
			response.write(message);

			if (logger.isInfoEnabled()) {
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_2")
						+ ":result=" + resp.getResult() + ",account="
						+ req.getAccount());
			}
		}
		else {
			if (GameValue.GAME_VALIDATEIN_FLAG == 0)// 非验证
			{
				UserLoginResp resp = roleMgtService.userLogin(roleId, req,
						head.getUserID1());

				head.setMsgType(0xA006);

				message.setBody(resp);
				response.write(message);

				if (logger.isInfoEnabled()) {
					logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_1")
							+ ":result=" + resp.getResult() + ",roleId="
							+ resp.getRoleId());
				}
			}
			else// 发送计费验证
			{
				chargeMgtService.sendUserLogin(req, sequenceId, 0);
			}

		}
	}

}
