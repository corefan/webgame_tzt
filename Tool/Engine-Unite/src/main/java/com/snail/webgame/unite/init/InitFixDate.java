package com.snail.webgame.unite.init;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.unite.common.value.DBPath;
import com.snail.webgame.unite.core.FixDBUtilService;

/**
 * DB修复
 * @author panxj
 * @version 1.0 2010-10-15
 */

public class InitFixDate {
	private static final Logger logger = LoggerFactory.getLogger("logs");
	private static InitFixDate me = null;
	private static final Logger log =LoggerFactory.getLogger("logs");
	private InitFixDate()
	{
		init();
	}	

	public static InitFixDate getInstance()
	{
		if(me == null)
		{
			me = new InitFixDate();
		}
		return me;
	}
	
	private void init()
	{		
//		File file = new File(InitFixDate.class.getClass().getResource(DBPath.FIX_DATA_PATH).getPath());
//		if(file.isFile())
//		{
			InputStream is = InitFixDate.class.getClass().getResourceAsStream(DBPath.FIX_DATA_PATH);
			if(is == null)
			{
				log.error("fix_update.m can not be finded,system will exit!");
				System.exit(0);
			}
			BufferedInputStream bis = null;
			DataInputStream dis = null;
			try
			{
				bis = new BufferedInputStream(is,1024); 
				dis = new DataInputStream(bis);
				byte length = dis.readByte();				
				for(byte i = 0; i<length; i++)
				{					
					FixDBUtilService.fixDB(dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF());					
				}	
				length = dis.readByte();
				for(byte i = 0; i<length; i++)
				{
					String sql = dis.readUTF();
					byte loops = dis.readByte();
					for(byte j=0;j<loops;j++)
					{
						FixDBUtilService.fixDB(dis.readUTF(),sql);
					}					
				}
				if(logger.isInfoEnabled())
				{
					logger.info("Init StartId fixData Success!");
				}
			}			
			catch (IOException e)
			{
				if(logger.isErrorEnabled())
				{
					logger.error("Init StartId fixData Failure!",e);
				}
			}
			finally
			{
				try
				{
					if(dis != null)
					{
						dis.close();										
					}
					if(bis != null)
					{
						bis.close();
					}
				}
				catch (IOException e)
				{					
				}
			}
//		}
	}	
}
