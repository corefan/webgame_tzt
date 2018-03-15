package com.snail.webgame.game.gatemgt.service;

import org.apache.mina.common.IoSession;

import com.snail.webgame.game.cache.GateServerMap;
import com.snail.webgame.game.common.GateServerInfo;
import com.snail.webgame.game.gatemgt.protocal.active.ActiveReq;


public class GateServerService {

 
	public   GateServerService()
	{
		
	} 
	 
	
	
	public  void activeGateConnect(ActiveReq req,IoSession session)
	{
		
		if(session.getAttribute("groupServerId")==null)
		{
			int gateServerId  =req.getGateServerId();
			int groupServerId = req.getGroupServerId();
			int encryptType = req.getEncryptType();
			int gateTotalNum = req.getNum();
			String encryptCode = req.getEncryptCode();
			
			GateServerInfo info = new GateServerInfo();
			info.setGroupServerId(groupServerId);
			info.setGateServerId(gateServerId);
			info.setRomateIP(req.getRomateIP());
			info.setRomatePort(req.getRomatePort());
			
			
			info.setEncryptType(encryptType);
			info.setEncryptCode(encryptCode);
			info.setNum(gateTotalNum);
			info.setCryptoType(req.getCryptoType());
			GateServerMap.addGateServer(info);
		
			session.setAttribute("groupServerId",groupServerId);
			session.setAttribute("gateServerId",gateServerId);
		}
		else
		{
			int groupServerId = (Integer) session.getAttribute("groupServerId");
			int gateServerId = req.getGateServerId();
			int gateTotalNum = req.getNum();
			
			GateServerInfo info = GateServerMap.getGateServerId(groupServerId,gateServerId);
			
			if(info!=null)
			{
				info.setNum(gateTotalNum);
			}
		}
	}
	/**
	 * 通讯服务器出现中断，清除缓存信息
	 * @param gateServerId
	 */
	public  static void interruptServer(int groupServerId,int gateServerId)
	{
		
		GateServerMap.removeGateServer(groupServerId,gateServerId);
		
	}
	
}
