package com.snail.webgame.engine.component.mail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatContentDAO {
//
//	private static final Logger logger=LoggerFactory.getLogger("logs");
//	/**
//	 * 保存聊天内容到数据库 
//	 * @param chatTime
//	 * @param roleName
//	 * @param channelType
//	 * @param targetRoleName
//	 * @param ip
//	 * @param content
//	 * @return
//	 */
//	public  static boolean saveChatContentDAO(
//			String roleName,
//			int channelType,
//			String targetRoleName,
//			String ip,
//			String content) {
//
//		Connection conn = null;
//		PreparedStatement psmt = null;
//		boolean flag = false;
//		try
//		{		
//			String sql = "insert into GAME_CHATCONTENTLOG_INFO(D_CHATDATE,S_ROLENAME,"+
//							 "I_CHANEELTYPE,S_TARGETROLENAME,S_OPERATORIP,S_CHATCONTENT)"+
//							 " values (?,?,?,?,?,?)";
//			
//			conn = DBConnection.getConnection("GAME_LOG_DB");
//			
//
//			psmt = conn.prepareStatement(sql);
//
//			psmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//			psmt.setString(2, roleName);
//			psmt.setInt(3, channelType);
//			psmt.setString(4, targetRoleName);
//			psmt.setString(5, ip);
//			psmt.setString(6, content);
//
//			
//			
//			if(psmt.executeUpdate() == 1)
//			{		
//				flag = true;
//			}
//		}
//		catch(SQLException e)
//		{	
//			if(logger.isErrorEnabled())
//			{
//				//保存邮件异常 
//				logger.error("",e);
//			}
//		}
//		finally
//		{
//			if(psmt!=null)
//			{
//				try {
//					psmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}		
//			}
//			if(conn!=null)
//			{
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return flag;
//	}
//	
}
