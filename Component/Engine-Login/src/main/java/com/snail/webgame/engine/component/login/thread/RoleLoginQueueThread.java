package com.snail.webgame.engine.component.login.thread;

import java.util.List;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.Flag;
import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.cache.RoleLoginQueueInfoMap;
import com.snail.webgame.engine.component.login.cache.ServerMap;
import com.snail.webgame.engine.component.login.config.GameOtherConfig;
import com.snail.webgame.engine.component.login.info.RoleLoginQueueInfo;
import com.snail.webgame.engine.component.login.protocal.loginqueue.LoginQueueResp;

public class RoleLoginQueueThread extends Thread{
	private static final Logger logger=LoggerFactory.getLogger("logs");
	private volatile boolean cancel = false;
	private static int i = 0;
	
	public void run()
	{
		while(!cancel)
		{
			try
			{
				long begTime = System.currentTimeMillis();
				synchronized (Flag.OBJ_LOGIN_QUEUE) 
				{
					int flag = 0;
					if(GameOtherConfig.getInstance().getOnlineNum()>0
							&& GameOtherConfig.getInstance().getOnlineNum()>RoleInfoMap.getOnlineSize())
					{
						flag = 1;
						GameValue.CHECK_LOGIN_QUEUE = true;
					}
					else
					{
						flag = 0;
					}
					
					List<String> list = RoleLoginQueueInfoMap.getList();
					if(GameValue.CHECK_LOGIN_QUEUE && list != null && list.size() > 0)
					{
						if(i >= list.size())
						{
							i = 0;
							GameValue.CHECK_LOGIN_QUEUE = false;
						}
						
						String account = null;
						
						if(flag == 1 && list.size() > 0)
						{
							account = list.get(0);
							RoleLoginQueueInfoMap.addLoginList(account);
						}
						else
						{
							if(list.size() > i)
							{
								account = list.get(i);
							}
							else
							{
								i = 0;
								GameValue.CHECK_LOGIN_QUEUE = false;
								continue;
							}
						}
						
						RoleLoginQueueInfo rqInfo = RoleLoginQueueInfoMap.getRoleLoginQueueInfo(account);
						if(rqInfo != null)
						{
							
						}
						else
						{
							list.remove(account);
							continue;
						}

						Message message = new Message();
						
						GameMessageHead head = new GameMessageHead();
						
						head.setMsgType(0xB10C);
						
						head.setUserID0(rqInfo.getRoleId());
						
						head.setUserID1(rqInfo.getGateServerId());
						
						head.setUserID2(rqInfo.getSequenceId());
						
						message.setHeader(head);
						
						LoginQueueResp resp = new LoginQueueResp();
						resp.setResult(1);
						resp.setIndex(RoleLoginQueueInfoMap.getIndex(rqInfo));
						
						resp.setNum(RoleLoginQueueInfoMap.getAllNum());
						resp.setRoleName(rqInfo.getRoleName());
						resp.setAccount(account);
						resp.setFlag(flag);
						
						message.setBody(resp);
						
						IoSession session = ServerMap.getServerSession(GameOtherConfig.getInstance().getGateServerName()+"-"+rqInfo.getGateServerId());

						if(session!=null&&session.isConnected())
						{
							session.write(message);
						}				
						
						i++;
					}
					else
					{
						i = 0;
					}
					
					long endTime = System.currentTimeMillis();
					double costTime = (endTime - begTime)/1000d;
					if(logger.isInfoEnabled())
					{
						logger.info("[SYSTEM] Check user login queue thread cost time : " + costTime);
					}
				}
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				i = 0;
			}
		}

	}
	
	public static void setI()
	{
		i = 0;
	}
	
	public void cancel()
	{
		cancel = true;
	}
}
