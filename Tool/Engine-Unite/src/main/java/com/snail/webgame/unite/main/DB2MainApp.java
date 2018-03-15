package com.snail.webgame.unite.main;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.unite.cmd.DBCMD;
import com.snail.webgame.unite.common.value.ComValue;
import com.snail.webgame.unite.config.SYSConfig;
import com.snail.webgame.unite.core.UniteHandle;
import com.snail.webgame.unite.thread.ShutdownThread;
import com.snail.webgame.unite.ui.DBGUI;
import com.snail.webgame.unite.util.ParamUtil2Change;

/**
 * 启动器
 * @author panxj
 * @version 1.0 2010-7-22
 */

public class DB2MainApp  {	
	public static UniteHandle uniteService = null;
	private static final Logger log =LoggerFactory.getLogger("logs");
	public static void main(String[] args) {
		byte startFlag = ComValue.gui_start_flag;
		//默认是命令行模式
		if(args!=null && args.length ==1)
		{			
			startFlag = ParamUtil2Change.getByte(args[0]);
		}
		DB2MainApp.start(startFlag, new UniteHandle(){
			@Override
			public void move(String sign) {
				// TODO Auto-generated method stub
				System.out.println(sign + " move....");
			}
			@Override
			public void unite(String sign) {
				// TODO Auto-generated method stub
				System.out.println(sign + " unite....");
			}
		});
	}
	/**
	 * 启动合服工具
	 * @param flag 0-命令行模式 1-窗口模式
	 */
	public static void start(byte flag, UniteHandle uniteService)
	{
		try {
			DB2MainApp.uniteService = uniteService;
			Properties properties = new Properties();
			InputStream is = DB2MainApp.class.getClass().getResourceAsStream("/log4j.properties");
			if(is == null)
			{
				log.error("log4j.properties can not be finded,system will exit!");
				System.exit(0);
			}
			properties.load(is);
			PropertyConfigurator.configure(properties);
			
			byte startFlag = flag;
			ComValue.gui_start_flag = startFlag;
			SYSConfig.getInstance();
			
			switch (startFlag)
			{
				case 0:
					DBCMD.getInstance();
					break;
				case 1:
					DBGUI.getInstance();
					break;
				default:
					break;
			}		
			Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
