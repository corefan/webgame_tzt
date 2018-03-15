package com.snail.webgame.unite.ui.core;

import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.snail.webgame.unite.common.value.ComValue;
import com.snail.webgame.unite.common.value.DBMSGCode;
import com.snail.webgame.unite.config.DBConfig;
import com.snail.webgame.unite.config.SYSConfig;
import com.snail.webgame.unite.core.DBConnService;
import com.snail.webgame.unite.dao.DBUtilDao;
import com.snail.webgame.unite.main.DB2MainApp;
import com.snail.webgame.unite.ui.DBGUI;
import com.snail.webgame.unite.ui.value.BtnName;
import com.snail.webgame.unite.ui.value.DBType;
import com.snail.webgame.unite.ui.value.TxtName;
import com.snail.webgame.unite.util.ParamUtil4Verify;

/**
 * UI服务
 * @author panxj
 * @version 1.0 2010-7-27
 */

public class UIService {	
	/**
	 * 修改某个数据的配置
	 * @param space
	 * @param url
	 * @param port
	 * @param dbName
	 * @param user
	 * @param password
	 * @return
	 */
	public static boolean changeDBConfig(String dbSource ,String url,String port,String dbName,String user,String password)
	{
		Properties p  = DBConfig.getInstance().getDBPoolConfig(dbSource);
		if(p != null)
		{
			String sqlType = p.getProperty("url").split(":")[1];
			String urlTmp = SYSConfig.getDBUrlTemplete(sqlType);
			
			if(urlTmp == "")
				return false;
			urlTmp = urlTmp.replace("{ip}", url);
			urlTmp = urlTmp.replace("{port}", port);
			urlTmp = urlTmp.replace("{database}", dbName);
			
			p.setProperty("url",urlTmp);		
			p.setProperty("username", user);		
			p.setProperty("password", password);
			DBConnService.removeConnection(dbSource);
			return DBUtilDao.testDao(dbSource);
		}		
		if(ComValue.gui_start_flag == 1)
		{
			AdaptCompService.addConsole(2, AdaptCompService.getDBSpaceName(dbSource), DBMSGCode.DBCONFIG_ERROR_CODE);
		}			
		return false;		
	}	
	
	/**
	 * 加载初始数据
	 * @param txt
	 * @param txtName
	 * @return
	 */
	public static JTextField initTxtValue(JTextField txt,TxtName txtName)
	{		
		switch (txtName)
		{
			case fromUrl:	
			case gotoUrl:
			case fromLogUrl:			
			case gotoLogUrl:
				txt.setText(getTxtValue(AdaptCompService.getBtnName(txtName),DBType.url));					
				break;
			case fromPort:
			case gotoPort:
			case fromLogPort:
			case gotoLogPort:
				txt.setText(getTxtValue(AdaptCompService.getBtnName(txtName),DBType.port));				
				break;
			case fromDBName:
			case gotoDBName:
			case fromLogDBName:
			case gotoLogDBName:
				txt.setText(getTxtValue(AdaptCompService.getBtnName(txtName),DBType.dbName));			
				break;
			case fromUser:
			case gotoUser:
			case fromLogUser:
			case gotoLogUser:
				txt.setText(getTxtValue(AdaptCompService.getBtnName(txtName),DBType.user));					
				break;
			case fromPassword:
			case gotoPassword:
			case fromLogPassword:
			case gotoLogPassword:
				txt.setText(getTxtValue(AdaptCompService.getBtnName(txtName),DBType.password));			
				break;
			default:
				break;
		}		
		return txt;				
	}	
	
	/**
	 *数据迁移
	 */
	public static void moveDB(String moveType)
	{	
		DB2MainApp.uniteService.move(moveType);
		if(ComValue.gui_start_flag == 1)
		{
			JOptionPane.showMessageDialog(DBGUI.getDBFrm(),"迁移完成","提示",JOptionPane.OK_OPTION);
		}		
	}
	
	public static void uniteDB(String uniteSign)
	{	
		if(!ParamUtil4Verify.isUnNull(uniteSign))
		{	
			if(ComValue.gui_start_flag == 1)
			{
				JOptionPane.showMessageDialog(DBGUI.getDBFrm(),"合服标识不存在!","提示",JOptionPane.OK_OPTION);
			}
			else if(ComValue.gui_start_flag == 0)
			{
				System.out.println("合服标识不存在!");
			}				
			return;
		}
		DB2MainApp.uniteService.unite(uniteSign);
		if(ComValue.gui_start_flag == 1)
		{		
			JOptionPane.showMessageDialog(DBGUI.getDBFrm(),"合服完成","提示",JOptionPane.OK_OPTION);
		}
		else if(ComValue.gui_start_flag == 0)
		{
			System.out.println("合服完成");
		}		
	}
	
	/**
	 * 初始化数据配置到文本框
	 * @param space
	 * @param dbType
	 * @return
	 */	
	private static String getTxtValue(String compName,DBType dbType)
	{		
		if(!BtnName.getName(compName))
		{
			return null;
		}		
		BtnName btnName = BtnName.valueOf(compName);
		Properties p  = DBConfig.getInstance().getDBPoolConfig(AdaptCompService.getDBSource(btnName));
		if(p != null)
		{
			switch(dbType)
			{
				case  url:
					String url = p.getProperty("url");			
					return url.substring(url.indexOf("//")+2, url.indexOf(":",24));	
				case  port:
					String port = p.getProperty("url");	
					return port.substring(port.indexOf(":",24)+1,port.indexOf(";"));
				case  dbName:
					String dbName = p.getProperty("url");	
					return dbName.substring(dbName.indexOf("=",59)+1,dbName.length());
				case  user:
					return p.getProperty("username");
				case  password:
					return p.getProperty("password");
				default:
					break;
			}				
		}	
		return "";		
	}
}
