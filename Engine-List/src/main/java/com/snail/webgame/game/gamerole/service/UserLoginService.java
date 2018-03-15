package com.snail.webgame.game.gamerole.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.game.cache.ChargeServerMap;
import com.snail.webgame.game.cache.GateServerMap;
import com.snail.webgame.game.cache.UserSeqMap;
import com.snail.webgame.game.charge.validate.VerifyAccountReq;
import com.snail.webgame.game.common.ChargeMessageHead;
import com.snail.webgame.game.common.GameMessageHead;
import com.snail.webgame.game.common.GateServerInfo;
import com.snail.webgame.game.common.ServerConfigList;
import com.snail.webgame.game.config.ListConfig;
import com.snail.webgame.game.config.ListOtherConfig;
import com.snail.webgame.game.gamerole.protocal.login.ServerInfo;
import com.snail.webgame.game.gamerole.protocal.login.UserLoginReq;
import com.snail.webgame.game.gamerole.protocal.login.UserLoginResp;
 
 

public class UserLoginService {
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	 
	
	
	public static void sendVerfiyAccount(int sequenceId ,UserLoginReq loginReq,String ip,int port )
	{
		
		
		IoSession session = ChargeServerMap.getSession(ServerName.GAME_CHARGE_SERVER);
		
		if(session!=null&&session.isConnected())
		{
			Message message = new Message();
			
			ChargeMessageHead head = new ChargeMessageHead();
			head.setUserID0(sequenceId);
			head.setVersion(0x301);
			head.setMsgType(0x3000);
			VerifyAccountReq req = new VerifyAccountReq();
		
			req.setAccount(loginReq.getAccount());
			req.setMd5Pass(loginReq.getMd5Pass());
			req.setAddress(ip);
			req.setPort(port);
			req.setVendorId(ListConfig.getInstance().getVendorId());
			String validate = loginReq.getValidate();
			if(validate!=null&&validate.trim().length()>0)
			{
				req.setAccType(2);
				req.setValidate(validate);
				
			}
			else
			{
				req.setAccType(1);
			}
			req.setClientType(loginReq.getClientType());
			message.setHeader(head);
			message.setBody(req);
			
			session.write(message);
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("list","USER_LOGIN_REQ_SEND")+":account="+req.getAccount());
			}
			
			
		}
		else
		{
			IoSession session1 =  UserSeqMap.getSession(sequenceId);
	 
			if(session1!=null&&session1.isConnected())
			{

				 UserLoginResp resp = new UserLoginResp();
				 resp.setAccount(loginReq.getAccount());
				 resp.setResult(ErrorCode.USER_LOGIN_CHARGE_ERROR_1);
				 
				 Message message =  new Message();
				 
				 GameMessageHead reqHead = new GameMessageHead();
				 
				 reqHead.setProtocolId(0xA002);
				 
				 message.setHeader(reqHead);
				 message.setBody(resp);
				  
				 session1.write(message);
				 if(logger.isInfoEnabled())
				 {
					 logger.info(Resource.getMessage("list","USER_GET_LIST_RESP")
							 +":result="+resp.getResult()+",count="+resp.getCount());
				 }
			}
		}

		 
		 
	}
	
	
	/**
	 * 获得当前游戏服务器列表,通讯服务器已经注册
	 * 到列表服务器上
	 * @return
	 */
	public   static ArrayList<ServerInfo>  getServerList()
	{
		//查询数据库返回列表
			
		List<ServerConfigList>  list =  ListConfig.getInstance().getServerList();
		ArrayList<ServerInfo> serverList = new ArrayList<ServerInfo>();
		
		for(int i =0;i<list.size();i++)
		{
			ServerConfigList configList = list.get(i);
			
			int totalNum = 0;
			HashMap<Integer,GateServerInfo> map = GateServerMap.getGateMap(configList.getGroupServerId());
 
			if(map!=null)
			{   
				int gateServerId = 0;
				int gateServerNum = Integer.MAX_VALUE;
				int encryptType = 0;
				String encryptCode = "";
				String romateIP = "";
				int romatePort = 0;
				int cryptoType = 0;
				
				Set set = map.keySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext())
				{
					int key = (Integer) iterator.next();

					GateServerInfo info = map.get(key);
					
					
					if(info.getNum()<=gateServerNum)
					{
						gateServerId = info.getGateServerId();
						gateServerNum = info.getNum();
						encryptType = info.getEncryptType();
						encryptCode = info.getEncryptCode();
						romateIP = info.getRomateIP();
						romatePort = info.getRomatePort();
						cryptoType = info.getCryptoType();
					}
					
					totalNum = totalNum + info.getNum();
				}
					 
				if(gateServerId>0)
				{
					ServerInfo info = new ServerInfo();
				 
		 
					info.setServerName(configList.getServerName());
					info.setGroupServerId(configList.getGroupServerId());
					info.setGateServerId(gateServerId);
					info.setEncryptType(encryptType);
					info.setEncryptCode(encryptCode);
					info.setRomateIP(romateIP);
					info.setRomatePort(romatePort);
					byte[][] b = ListOtherConfig.getInstance().getClientData();
					if(b.length>0&&cryptoType>=0)
					{
						info.setClientData(ListOtherConfig.getInstance().getClientData()[cryptoType]);
					}
					
					if(totalNum<1500)
					{
						info.setStatus(0);
					}
					else if(totalNum<2500)
					{
						info.setStatus(1);
					}
					else
					{
						info.setStatus(2);
					}
			
					
					serverList.add(info);	
				}
			}
		}
		return serverList;
	}
}
