package com.snail.webgame.unite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.snail.webgame.unite.common.value.ComValue;
import com.snail.webgame.unite.common.value.DBMSGCode;
import com.snail.webgame.unite.core.DBConnService;
import com.snail.webgame.unite.ui.core.AdaptCompService;

/**
 * 数据库连接工具
 * @author panxj
 * @version 1.0 2010-9-29
 */

public class DBUtilDao {
	
	/**
	 * 测试连接
	 * @param space
	 * @return
	 */
	public static boolean testDao(String dbSource)
	{	
		Connection conn = null;	
		try
		{			
			conn = DBConnService.getConnection(dbSource);
			if(conn!=null)
			{	
				if(ComValue.gui_start_flag == 1)
				{
					AdaptCompService.addConsole(1, AdaptCompService.getDBSpaceName(dbSource), DBMSGCode.TESTCONN_SUC_CODE);
				}
				return true;
			}			
		}
		catch (SQLException e)
		{	
			if(ComValue.gui_start_flag == 1)
			{
				AdaptCompService.addConsole(3, AdaptCompService.getDBSpaceName(dbSource), e.getMessage());
			}
			else
			{
				e.printStackTrace();
			}
			return false;
		}
		finally
		{			
			try
			{			
				if(conn != null)
				{
					conn.close();
				}				
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return false;			
	}	

	/**
	 * 数据库字段比对与修复
	 * @param dbSource
	 * @param table
	 * @param field
	 * @param value
	 * @return
	 */
	public static boolean isExistDB4Fix(String dbSource, String table, String field, String value)
	{
		Connection conn = null;
		PreparedStatement psmt = null;
		try 
		{
			conn = DBConnService.getConnection(dbSource);
			String sql = (new StringBuilder("if not exists (select 1 from SYSCOLUMNS where ID = OBJECT_ID(N'")).append(table).append("') and NAME = '").append(field).append("')\n").append("alter Table ").append(table).append(" add ").append(field).append(" ").append(value).append(" null").toString();
			psmt = conn.prepareStatement(sql);
			psmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{				
				if (psmt != null)
				{
					psmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch (SQLException e) 
			{
			}
		}	
	}

	/**
	 * 添加新表
	 * @param dbSource
	 * @param sql
	 * @return
	 */
	public static boolean isExistDB4Fix(String dbSource, String sql)
	{
		Connection conn = null;
		PreparedStatement psmt = null;
		try 
		{
			conn = DBConnService.getConnection(dbSource);			
			psmt = conn.prepareStatement(sql);
			psmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{				
				if (psmt != null)
				{
					psmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch (SQLException e) 
			{
			}
		}		
	}
}
