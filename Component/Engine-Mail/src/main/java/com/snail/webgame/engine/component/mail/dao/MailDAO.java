package com.snail.webgame.engine.component.mail.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.mail.cache.MailListMap;
import com.snail.webgame.engine.component.mail.common.MailInfo;
import com.snail.webgame.engine.db.SqlMapDaoSupport;
import com.snail.webgame.engine.db.session.client.SqlMapClient;

public class MailDAO extends SqlMapDaoSupport
{
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	public boolean loadMailInfo(){
		List<MailInfo> mailList = getSqlMapClient("GAME_DB").queryList("getMailInfoList");
		if(mailList != null){
			for(MailInfo mail : mailList){
				MailListMap.addMailInfo(mail);
			}
		}
		return true;
	}

	/**
	 * 判断邮件接收者是否存在
	 * @param roleName
	 * @return
	 */
	public long isExist(String roleName)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setRoleName(roleName);
		roleInfo = (RoleInfo)getSqlMapClient("GAME_DB").query("selectRoleByRoleName", roleInfo);
		if(roleInfo != null)
			return roleInfo.getId();
		return 0;
	}
	
	/**
	 * 保存邮件到数据库 
	 * @param mailType
	 * @param sendRoleId
	 * @param sendRoleName
	 * @param receiveId
	 * @param recRoleName
	 * @param mailTitle
	 * @param content
	 * @param smallType
	 * @param attachment
	 * @param reserve
	 * @return
	 */
	public boolean saveMail(MailInfo mailInfo) {
		return getSqlMapClient("GAME_DB").insert("insertMail", mailInfo);
	}
	
	/**
	 * 删除邮件 
	 * @param req
	 */
	public boolean delete(List<MailInfo> list) 
	{
		SqlMapClient client = getSqlMapClient("GAME_DB", ExecutorType.BATCH, false);
		for(MailInfo mail : list){
			client.delete("deleteMail", mail);
		}
		client.commit();
		return true;
	}
	
	/**
	 * 更新邮件读取状态
	 * @param mailInfo
	 * @return
	 */
	public boolean updateMailRead(MailInfo mailInfo) 
	{
		return getSqlMapClient("GAME_DB").update("updateMailIsRead", mailInfo);
	}
	
	/**
	 * 更新邮件读取状态
	 */
	public boolean updateMailRead(List<MailInfo> list) 
	{
		SqlMapClient client = getSqlMapClient("GAME_DB", ExecutorType.BATCH, false);
		for(MailInfo mail : list){
			client.delete("updateMailIsRead", mail);
		}
		client.commit();
		return true;
	}
	
	 
	
}

