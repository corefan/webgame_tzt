package com.snail.webgame.engine.component.mail.protocal.mail.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.component.mail.cache.MailListMap;
import com.snail.webgame.engine.component.mail.cache.RoleLoginMap;
import com.snail.webgame.engine.component.mail.cache.ServerMap;
import com.snail.webgame.engine.component.mail.cache.WordListMap;
import com.snail.webgame.engine.component.mail.common.GameMessageHead;
import com.snail.webgame.engine.component.mail.common.MailInfo;
import com.snail.webgame.engine.component.mail.common.RoleInfo;
import com.snail.webgame.engine.component.mail.config.MailOtherConfig;
import com.snail.webgame.engine.component.mail.dao.MailDAO;
import com.snail.webgame.engine.component.mail.protocal.chat.service.ChatMgtService;
import com.snail.webgame.engine.component.mail.protocal.mail.allMailList.ListReq;
import com.snail.webgame.engine.component.mail.protocal.mail.allMailList.ListResp;
import com.snail.webgame.engine.component.mail.protocal.mail.allMailList.MailInfoResp;
import com.snail.webgame.engine.component.mail.protocal.mail.allMailRead.AllMailReadResp;
import com.snail.webgame.engine.component.mail.protocal.mail.mailDelete.DeleteReq;
import com.snail.webgame.engine.component.mail.protocal.mail.mailDelete.DeleteResp;
import com.snail.webgame.engine.component.mail.protocal.mail.mailRead.ReadMailReq;
import com.snail.webgame.engine.component.mail.protocal.mail.mailRead.ReadMailResp;
import com.snail.webgame.engine.component.mail.protocal.mail.mailSend.SendReq;
import com.snail.webgame.engine.component.mail.protocal.mail.mailSend.SendResp;
import com.snail.webgame.engine.component.mail.protocal.mail.remind.MailRemindResp;


public class MailMgtService implements IMailMgtService
{
	private static final Logger logger=LoggerFactory.getLogger("logs");
	public MailDAO mailDAO = new MailDAO();
	public ChatMgtService chatMgtService = new ChatMgtService();
	public MailMgtService()
	{
		
	}
	/**
	 * 发送邮件
	 * @param req
	 * @return
	 */
	public  SendResp sendMail(SendReq req, long sendRoleId)
	{	
		SendResp resp = new SendResp();	
		
		byte mailType = req.getMailType();//1-系统2-玩家
		String sendRoleName = "";
		if(mailType == 1){
			sendRoleName = Resource.getMessage("game", "GAME_SYSTEM");
			if(sendRoleId > 0){
				resp.setResult(ErrorCode.SYSTEM_ERROR);
				return resp;
			}
		}
		else{
			RoleInfo roleInfo = (RoleInfo)RoleLoginMap.getRoleInfo(sendRoleId);
			if(roleInfo == null){
				resp.setResult(ErrorCode.SYSTEM_ERROR);
				return resp;
			}
			sendRoleName = roleInfo.getRoleName();
		}
		
		String recRoleName = req.getReceRoleName();
		String mailTitle = req.getMailTitle();
		String mailContent = req.getMailContent();
		byte smallType = req.getSmallType();
		String attachment = req.getAttachment();
		String reserve = req.getReserve();
		
		return roleSysMail(resp,mailType,sendRoleId,sendRoleName,recRoleName,mailTitle,mailContent,smallType,attachment,reserve);
		
	}
	
	
	public SendResp roleSysMail(SendResp resp,byte mailType,long sendRoleId,String sendRoleName,String recRoleName,String mailTitle,String mailContent,byte smallType,String attachment,String reserve)
	{
		
		long receiveId = mailDAO.isExist(recRoleName);
		//判断邮件接收者是否存在	
		if( receiveId == 0)
		{			
			resp.setResult(ErrorCode.MAIL_RECEIVER_NOEXIST);			
			if(logger.isErrorEnabled())
			{
				logger.error("recRoleName="+recRoleName+", mail receiver is not exist !");
			}
			return resp;
		}
		else
		{			
			HashMap<Long, MailInfo> mailMap = MailListMap.getMailInfo(receiveId);
			//	判断邮箱是否超过限制 
			if(mailMap != null && mailMap.size() >= GameValue.ROLE_MAIL_MAX_NUM &&mailType!=1)
			{
				resp.setResult(ErrorCode.MAILSPACE_NOT_ENOUGH);
 
				if(logger.isInfoEnabled())
				{
					logger.info("roleId="+receiveId+","+Resource.getMessage("mail", "GAME_MAIL_CLEW_1")+"!");
				}
				//发送提醒给接收人 
				chatMgtService.sendMailContain(ErrorCode.MAIL_NUM_ERROR_1,receiveId,recRoleName);
				sendMailSpaceNotEnough(receiveId,ErrorCode.MAIL_NUM_ERROR_1);
				return resp;
			}
			else
			{ 
				if(mailMap != null && mailMap.size() >= GameValue.ROLE_MAIL_NUM_AWAKE )
				{
					sendMailSpaceNotEnough(receiveId,ErrorCode.MAIL_NUM_ERROR_0);
					chatMgtService.sendMailContain(ErrorCode.MAIL_NUM_ERROR_0,receiveId,recRoleName);
				}
				
				if(mailType!=1)//系统不屏蔽
				{
					mailTitle = WordListMap.replaceWord(mailTitle,MailOtherConfig.getInstance().getWorldType());
					mailContent = WordListMap.replaceWord(mailContent,MailOtherConfig.getInstance().getWorldType());
				}
				
				MailInfo mailInfo = new MailInfo();
				mailInfo.setMailType(mailType);
				mailInfo.setSendRoleId(sendRoleId);
				mailInfo.setSendRoleName(sendRoleName);
				mailInfo.setReceRoleId(receiveId);
				mailInfo.setReceRoleName(recRoleName);
				mailInfo.setMailTitle(mailTitle);
				mailInfo.setContent(mailContent);
				mailInfo.setSubType(smallType);
				mailInfo.setAttachment(attachment);
				mailInfo.setReserve(reserve);
				mailInfo.setSendTime(new Timestamp(System.currentTimeMillis()));
				mailInfo.setFlag((byte)0);
				
				if(mailDAO.saveMail(mailInfo))			
				{		
					MailListMap.addMailInfo(mailInfo);
					resp.setResult(1);
				}
				else
				{
					resp.setResult(ErrorCode.SAVE_MAIL_FAILED);
				}
				//若接收者在线，则主动发送新邮件数量给接收者
				if(RoleLoginMap.isExitRoleInfo(receiveId))
				{
					sendNewMail(receiveId, 1);
					if(logger.isInfoEnabled())
					{
						logger.info(Resource.getMessage("mail", "GAME_MAIL_RESP_1")+":result="+resp.getResult()+",recRoleId="+receiveId);
					}
				}  
			}
			return resp;
		}
	}
	
	/**
	 * 删除邮件
	 * @param req
	 * @param roleId
	 * @return
	 */
	public DeleteResp deleteMail(DeleteReq req,int roleId)
	{	
		DeleteResp resp = new DeleteResp();
		
		if(RoleLoginMap.isExitRoleInfo(roleId))
		{
			String[] mailIds = req.getMailId().split(",");
			List<MailInfo> list = new ArrayList<MailInfo>();
			HashMap<Long, MailInfo> mailMap = MailListMap.getMailInfo(roleId);
			if(mailMap != null){
				for(String mailId : mailIds){
					MailInfo mailInfo = mailMap.get(Long.parseLong(mailId));
					if(mailInfo != null)
						list.add(mailInfo);
				}
			}
		    if(mailDAO.delete(list)){	
		    	for(MailInfo mailInfo : list){
		    		mailMap.remove(mailInfo.getId());
		    	}
		    	resp.setResult(1);
		    }
		    else{
		    	resp.setResult(ErrorCode.SYSTEM_ERROR);
		    }
			
			resp.setMailId(req.getMailId());
			 
			return resp;		
		}
		else
		{	
			resp.setResult(ErrorCode.DELETE_MAIL_ERROR);
			return resp;
		}
	}
	/**
	 * 读取邮件 
	 * @param req
	 * @param roleId
	 * @return
	 */
	public ReadMailResp readMail(ReadMailReq req,int roleId)
	{	
		ReadMailResp resp = new  ReadMailResp();
 
		HashMap<Long, MailInfo> mailMap = MailListMap.getMailInfo(roleId);
		if(mailMap != null){
			MailInfo mailInfo = mailMap.get(req.getMailId());
			if(mailInfo == null){
				resp.setResult(ErrorCode.MAIL_NOT_EXIST);
				return resp;
			}
			if(mailInfo.getReadState() == 0){
				mailInfo.setReadState((byte)1);
				mailDAO.updateMailRead(mailInfo);
			}
			
			resp.setMailId(mailInfo.getId());
			resp.setMailType(mailInfo.getMailType());
			resp.setSendRoleId(mailInfo.getSendRoleId());
			resp.setSendRoleName(mailInfo.getSendRoleName());
			resp.setReceRoleId(mailInfo.getReceRoleId());
			resp.setReceRoleName(mailInfo.getReceRoleName());
			resp.setMailTitle(mailInfo.getMailTitle());
			resp.setMailContent(mailInfo.getContent());
			resp.setSendTime(mailInfo.getSendTime().getTime());
			resp.setSmallType(mailInfo.getSubType());
			resp.setFlag(mailInfo.getFlag());
			resp.setAttachment(mailInfo.getAttachment());
			resp.setReserve(mailInfo.getReserve());
			resp.setResult(1);
			return resp;
		}
		else{
			resp.setResult(ErrorCode.MAIL_NOT_EXIST);
			return resp;
		}
	}
	
	/**
	 * 获得邮件列表 
	 * @param req
	 * @param roleId
	 * @return
	 */
	public ListResp getMailList(ListReq req,int roleId)
	{	
		ListResp resp = new ListResp();
		byte type = req.getType();
		int index = req.getIndex();
		int count = req.getCount();
		

		
		resp.setType(type);
		resp.setIndex(index);
		List<MailInfoResp> mailInfoList = new ArrayList<MailInfoResp>();
		LinkedHashMap<Long, MailInfo> mailMap = MailListMap.getMailInfo(roleId);
		int mailSum = 0;
		if(mailMap != null){
			mailSum = mailMap.size();
			if(index<mailSum)
			{
				Set<Long> keys = mailMap.keySet();
				int i = 0;
				for(Long key : keys){
					if(i >= index){
						MailInfo mailInfo = mailMap.get(key);
						
						if(mailInfo != null){
							if(type != 0){
								if(mailInfo.getMailType() == type && mailInfoList.size() < count){
									MailInfoResp mailInfoResp = new MailInfoResp();
									mailInfo.copyTo(mailInfoResp);
									mailInfoResp.setSendTime(mailInfo.getSendTime().getTime());
									mailInfoList.add(mailInfoResp);
								}
							}
							else {
								if(mailInfoList.size() < count){
									MailInfoResp mailInfoResp = new MailInfoResp();
									mailInfo.copyTo(mailInfoResp);
									mailInfoResp.setSendTime(mailInfo.getSendTime().getTime());
									mailInfoList.add(mailInfoResp);
								}
							}
						}
					}
					i ++;
				}
			}
		}
		resp.setMailSum(mailSum);
		resp.setCount(mailInfoList.size());
		resp.setMailInfoList(mailInfoList);
		resp.setResult(1);
		return resp;
	}
	/**
	 * 设置邮件全部已读 
	 * @param req
	 * @param roleId
	 * @return
	 */
	public AllMailReadResp setAllMailRead(int roleId)
	{
		AllMailReadResp allReadResp = new AllMailReadResp();
		
		LinkedHashMap<Long, MailInfo> mailMap = MailListMap.getMailInfo(roleId);
		if(mailMap != null){
			List<MailInfo> list = new ArrayList<MailInfo>();
			Set<Long> keys = mailMap.keySet();
			for(Long key : keys){
				MailInfo mailInfo = mailMap.get(key);
				if(mailInfo != null && mailInfo.getReadState() == 0){
					mailInfo.setReadState((byte)1);
					list.add(mailInfo);
				}
			}
			if(mailDAO.updateMailRead(list))
			{
				allReadResp.setResult(1);
			}
			else
			{
				allReadResp.setResult(ErrorCode.SET_ALLMAILREAD_ERROR);
				return allReadResp;
			}
		}
		allReadResp.setResult(1);
		
		return allReadResp;
	}

	/**
	 * 发送邮件快满提醒
	 * @param roleId
	 * @param messageId
	 */
	public void sendMailSpaceNotEnough(long roleId,int messageId)
	{
		
		 //玩家在线发送邮件快满提醒
		if(RoleLoginMap.isExitRoleInfo(roleId))
		{
			RoleInfo roleInfo = RoleLoginMap.getRoleInfo(roleId);
			
			Message message = new Message();
			GameMessageHead head = new GameMessageHead();
			
			MailRemindResp resp = new MailRemindResp();
			resp.setResult(messageId);
			resp.setType(1);
			
			head.setMsgType(0xD016);
			head.setUserID0((int)roleId);
			message.setHeader(head);
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+roleInfo.getGateServerId());
			
			if(session!=null&&session.isConnected())
			{ 
				session.write(message);
	 
			}
		}
	}
	
	/**
	 * 发送新邮件提醒
	 * @param roleId
	 * @param messageId
	 */
	public static void sendNewMail(long roleId,int messageId)
	{
		
		 //玩家在线发送新邮件提醒
		if(RoleLoginMap.isExitRoleInfo(roleId))
		{
			RoleInfo roleInfo = RoleLoginMap.getRoleInfo(roleId);
			
			Message message = new Message();
			GameMessageHead head = new GameMessageHead();
			
			MailRemindResp resp = new MailRemindResp();
			resp.setResult(messageId);
			resp.setType(2);
			
			head.setMsgType(0xD016);
			head.setUserID0((int)roleId);
			message.setHeader(head);
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+roleInfo.getGateServerId());
			
			if(session!=null&&session.isConnected())
			{ 
				session.write(message);
	 
			}
		}
	}
	
}