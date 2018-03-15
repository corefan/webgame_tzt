package com.snail.webgame.engine.component.mail.protocal.mail.mailSend;



import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.mail.common.GameMessageHead;
import com.snail.webgame.engine.component.mail.protocal.mail.service.MailMgtService;
 


public class SendProcessor extends ProtocolProcessor
{
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	private MailMgtService mailMgtService;
	
	
	public void setMailMgtService(MailMgtService mailMgtService) {
		this.mailMgtService = mailMgtService;
	}
	

	public void execute(Request request, Response response)
	{

		Message message = request.getMessage();
		GameMessageHead header = (GameMessageHead)message.getHeader();
		
		SendReq req = (SendReq)message.getBody();
		int roleId = header.getUserID0();
		SendResp resp = mailMgtService.sendMail(req, roleId);
		
		header.setMsgType(0xD007);
		message.setHeader(header);
		message.setBody(resp);		
		
		if(response.write(message));
		{
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("mail","GAME_MAIL_RESP_4")+":result="+resp.getResult()+",sendRoleId="+roleId);
			}
		}
		
		

		
		
	}


}
