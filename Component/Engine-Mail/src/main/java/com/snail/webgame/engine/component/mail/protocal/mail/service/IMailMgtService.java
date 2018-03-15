package com.snail.webgame.engine.component.mail.protocal.mail.service;

import com.snail.webgame.engine.component.mail.protocal.mail.allMailList.ListReq;
import com.snail.webgame.engine.component.mail.protocal.mail.allMailList.ListResp;
import com.snail.webgame.engine.component.mail.protocal.mail.allMailRead.AllMailReadResp;
import com.snail.webgame.engine.component.mail.protocal.mail.mailDelete.DeleteReq;
import com.snail.webgame.engine.component.mail.protocal.mail.mailDelete.DeleteResp;
import com.snail.webgame.engine.component.mail.protocal.mail.mailRead.ReadMailReq;
import com.snail.webgame.engine.component.mail.protocal.mail.mailRead.ReadMailResp;
import com.snail.webgame.engine.component.mail.protocal.mail.mailSend.SendReq;
import com.snail.webgame.engine.component.mail.protocal.mail.mailSend.SendResp;

public interface IMailMgtService {
	/**
	 * 发送邮件
	 * @param req
	 * @return
	 */
	public  SendResp sendMail(SendReq req, long sendRoleId);
	
	/**
	 * 删除邮件
	 * @param req
	 * @param roleId
	 * @return
	 */
	public DeleteResp deleteMail(DeleteReq req,int roleId);
	/**
	 * 读取邮件 
	 * @param req
	 * @param roleId
	 * @return
	 */
	public ReadMailResp readMail(ReadMailReq req,int roleId);
	
	/**
	 * 获得邮件列表 
	 * @param req
	 * @param roleId
	 * @return
	 */
	public ListResp getMailList(ListReq req,int roleId);
	/**
	 * 设置邮件全部已读 
	 * @param req
	 * @param roleId
	 * @return
	 */
	public AllMailReadResp setAllMailRead(int roleId);
}
