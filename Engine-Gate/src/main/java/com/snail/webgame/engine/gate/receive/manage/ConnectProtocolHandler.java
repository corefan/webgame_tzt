package com.snail.webgame.engine.gate.receive.manage;



import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Set;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.TransportType;
import org.apache.mina.transport.socket.nio.SocketSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.gate.cache.RoleReqTimeMap;
import com.snail.webgame.engine.gate.cache.SequenceMap;
import com.snail.webgame.engine.gate.cache.ServerMap;
import com.snail.webgame.engine.gate.common.ConnectConfig;
import com.snail.webgame.engine.gate.common.RoleReqTime;
import com.snail.webgame.engine.gate.common.SocketConfig;
import com.snail.webgame.engine.gate.config.Command;
import com.snail.webgame.engine.gate.config.WebGameConfig;
import com.snail.webgame.engine.gate.util.IdentityMap;
import com.snail.webgame.engine.gate.util.MessageServiceManage;
import com.snail.webgame.engine.gate.util.SequenceId;


public class ConnectProtocolHandler extends IoHandlerAdapter {
	
	
  private  static String xmlStr = "<cross-domain-policy>" +
			"<allow-access-from domain=\""
  				+WebGameConfig.getInstance().getFlashConfig().getDoamin()
  				+"\" to-ports=\""+WebGameConfig.getInstance().getFlashConfig().getPort()
  				+"\" />"
			+ "</cross-domain-policy>";

	private static final Logger log =LoggerFactory.getLogger("logs");
    
	
	private static int encryptType =  WebGameConfig.getInstance().getEncryptType();
	
	
	private MessageServiceManage msgmgt = new MessageServiceManage();
	
	public void exceptionCaught(IoSession session, Throwable arg1)
			throws Exception {
		session.close();
		arg1.printStackTrace();
	}


	public void messageReceived(IoSession session, Object message) throws Exception {
 

		 if(message instanceof String )
		{
				 
			if(((String) message).startsWith("<policy-file-request/>"))
			{
				if(session.isConnected())
				{
					session.write(xmlStr);//返回flash的安全校验
					 
				}
			}
		}
		else if(message instanceof byte[])
		 {
			
			if(encryptType>0)
			{
				String name = "";
				
				if(encryptType==1)
				{
					name ="DES";
				}
				else if(encryptType==2)
				{
					name ="BlowFish";
				}
				else if(encryptType==3)
				{
					name ="DESede";
				}
				else if(encryptType==4)
				{
					name ="AES";
				}
				byte b[] = MessageServiceManage.getDecodeMessage(name, (byte[]) message);
				
				if(b==null)
				{
					if(log.isWarnEnabled())
					{
						InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
					 	String ip = address.getAddress().getHostAddress();
						String port = String.valueOf(address.getPort());
						log.warn("System  occure decrypt error! close this session,ip="+ip+",port="+port);
					}
					session.close();
					return ;
				}
				
				
				byte [] lenghtMss = MessageServiceManage.int2bytes(b.length);
				
				
				byte newMessage [] = new byte[b.length+4];
				
				System.arraycopy(b, 0, newMessage, 4, b.length);
				System.arraycopy(lenghtMss, 0, newMessage, 0, lenghtMss.length);
				
				
				
				b = null;
				lenghtMss = null;
				message = null;
				
				message = newMessage;
					
			}
			
			
			 if(!session.isConnected())
			 {
				return ;
			 }
			 int msgType = msgmgt.getMessageType((byte[]) message);
			 
			 int roleId = msgmgt.getRoleId((byte[]) message);
			 
			 //断线重连
			 if(IdentityMap.isExistRole(roleId) && session.getAttribute("identity") == null){
				IoSession oldSession = IdentityMap.getSession(roleId);
				session.setAttribute("identity", oldSession.getAttribute("identity"));
				session.setAttribute("SequenceId", oldSession.getAttribute("SequenceId"));
				session.setAttribute("lastReqTime", oldSession.getAttribute("lastReqTime"));
				session.setAttribute("freqNum", oldSession.getAttribute("freqNum"));
				IdentityMap.addSession(roleId, session);
				oldSession.setAttribute("identity", null);
				oldSession.setAttribute("SequenceId", null);
				 
			 }
			 
			 if(msgType==Command.GAME_CLIENT_ACTIVE_REQ)//游戏客户端激活链接
			 {
				
				 session.write(message);
				 return ;
			 }
			 else if(msgType==Command.USER_VERIFY_ROLE_REQ||msgType==Command.USER_LOGIN_REQ
					 ||msgType==Command.USER_CREATE_ROLE_REQ
					 ||msgType==Command.USER_CHECK_ROLE_REQ
					 ||msgType==Command.USER_LOGIN_QUEUE_REQ)//用户登录游戏服务器
			 {
				 if(WebGameConfig.getInstance().getDefenseFlag()==1)//启动服务端防御
				 {
					 String lastReqTimeStr = (String) session.getAttribute("lastReqTime");
					 String freqNumStr = (String) session.getAttribute("freqNum");
					 
				 
					 if(lastReqTimeStr!=null&& freqNumStr!=null)
					 {
						 int freqNum = Integer.valueOf(freqNumStr);
						 long lastReqTime = Long.valueOf(lastReqTimeStr);
						 

						 if(System.currentTimeMillis()-lastReqTime<200)
						 {
							 freqNum = freqNum + 1;
						 }
						 else
						 {
							 freqNum = 1;
						 }
						 
						 session.setAttribute("lastReqTime",String.valueOf(System.currentTimeMillis()));
						 session.setAttribute("freqNum",String.valueOf(freqNum));
						 
						 if(freqNum>30)
						 {
							if(log.isWarnEnabled())
							{
								InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
								String ip = address.getAddress().getHostAddress();
								log.warn("Server received a lot of messages sent by the client: MsgType ="
											+msgType+",ip="+ip+",num="+freqNum);
							}
						 }
						 
//						 if(freqNum>100)
//						 {
//							if(log.isWarnEnabled())
//							{
//								InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
//								String ip = address.getAddress().getHostAddress();
//								log.warn("Close this session,Server received a lot of messages sent by the client: MsgType ="
//												+msgType+",ip="+ip+",num="+freqNum);
//							}
//							
//							session.removeAttribute("lastReqTime");
//							session.removeAttribute("freqNum");
//							session.close();
//						 }
						 
					
						 
						 if(freqNum>30)
						 {
							 return ;
						 }
						
					 }
					 else
					 {
						 session.setAttribute("lastReqTime",String.valueOf(System.currentTimeMillis()));
						 session.setAttribute("freqNum",String.valueOf(1));
					 }
					 
					 
				 }
				 
				
				 
				 int sequenceId = SequenceId.getSequenceId();
				 
				 session.setAttribute("SequenceId", sequenceId);
				 
				 SequenceMap.addSession(sequenceId, session);
				 
				 msgmgt.setMsgSequence(sequenceId, (byte[]) message);
				 
				 if(msgType==Command.USER_LOGIN_REQ||msgType==Command.USER_CREATE_ROLE_REQ||msgType==Command.USER_VERIFY_ROLE_REQ)
				 {
					 InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
					 String ip = address.getAddress().getHostAddress();
					 msgmgt.setLoginIP(ip,(byte[]) message);
				 }
				 
				 
				 
				 
			 }
			 else
			 {
				 if(session.getAttribute("identity")==null)//用户没有通过身份验证
				 {
					 if(log.isWarnEnabled())
					 {
						 InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
						 String ip = address.getAddress().getHostAddress();
						 log.warn("user is not pass check identity,  MsgType :"+msgType+",IP="+ip);
					 }
					 //用户下线
					 session.write(msgmgt.getUserLogout(roleId,ErrorCode.USER_LOGIN_ERROR_3));
					 return ;
				 }
				 
			 }
			 //根据列表转发各种消息
			 String serverName = WebGameConfig.getInstance().getTramsitServer(msgType);
			
			 if(serverName!=null)
			 {
				 
				 if(serverName.startsWith(ServerName.GAME_SCENE_SERVER))
				 {
					 serverName = serverName +"-"+msgmgt.getSceneId((byte[]) message);
				 }
				 
				IoSession revSess  =ServerMap.getSession(serverName);
 
				if(revSess!=null&&revSess.isConnected())
				{
					 if(session.getAttribute("identity")!=null)
					 {
						 roleId = (Integer) session.getAttribute("identity");
						
						 msgmgt.setRoleId((byte[]) message, roleId);
						 
						 if(!serverName.startsWith(ServerName.GAME_SCENE_SERVER)&&
								 WebGameConfig.getInstance().getDefenseFlag()==1)//启动服务端防御
						 {
							 RoleReqTime reqTime =  RoleReqTimeMap.getRoleReqTime(roleId, msgType);
							 
							 if(reqTime!=null)
							 {
								 long lastReqTime = reqTime.getLastReqTime();
								 
						 
								 if(System.currentTimeMillis()-lastReqTime<200)
								 {
									 reqTime.setNum(reqTime.getNum()+1);
									 reqTime.setLastReqTime(System.currentTimeMillis());
								 }
								 else
								 {
									 reqTime.setNum(1);
									 reqTime.setLastReqTime(System.currentTimeMillis());
								 }
								 
								 if(reqTime.getNum()>30)
								 {
									InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
									String ip = address.getAddress().getHostAddress();
									if(log.isWarnEnabled())
									{
										log.warn("Server received a lot of messages sent by the client: MsgType ="
													+msgType+",roleId="+roleId+",ip="+ip+",num="+reqTime.getNum());
									}
								 }
								 
//								 if(reqTime.getNum()>100)
//								 {
//									InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
//									String ip = address.getAddress().getHostAddress();
//									if(log.isWarnEnabled())
//									{
//										log.warn("Close this session,Server received a lot of messages sent by the client: MsgType ="
//														+msgType+",roleId="+roleId+",ip="+ip+",num="+reqTime.getNum());
//									}
//									RoleReqTimeMap.removeRoleReq(roleId);
//									session.close();
//								 }
								 
								 if(reqTime.getNum()>30)
								 {
									 return;
								 }
							 }
							 else
							 {
								 reqTime = new RoleReqTime();
								 reqTime.setLastReqTime(System.currentTimeMillis());
								 reqTime.setNum(1);
								 
								 RoleReqTimeMap.addRoleReqTime(roleId, msgType, reqTime);
							 }
						 }
						 
						 
						 
					 }
					 
					 msgmgt.setGateServerId((byte[]) message,  WebGameConfig.getInstance().getLocalConfig().getGateServerId());
					
					 InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
					 String ip = address.getAddress().getHostAddress();
					 msgmgt.setClientIP((byte[]) message, ip) ;
					 
					 
					 revSess.write(message);
					 
					 
			    }
				else
				{
					if(msgType==Command.USER_VERIFY_ROLE_REQ)
					{						
						
						session.write(msgmgt.getUserVerifyResp(ErrorCode.GATE_SERVER_ERROR_1));
						
 
						
					}
				}
				
			 }
			 else
			 {
				 if(log.isWarnEnabled())
				{
					InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
					String ip = address.getAddress().getHostAddress();
					log.warn("It is not exist receiveServer for  MsgType :"+msgType+",ip="+ip);
				}
			}
		 }
 
	}


	public void messageSent(IoSession session, Object arg1) throws Exception {
	

	}


	public void sessionClosed(IoSession session) throws Exception {
		
		
		if(session.getAttribute("identity")!=null)
		{
			int roleId = (Integer) session.getAttribute("identity");
			
			 
			 //通知场景服务器
			 HashMap<String, ConnectConfig> map =  WebGameConfig.getInstance().getConnectConfig();
			 
			 Set<String> set = map.keySet();
			 
			 for(String key :set)
			 {
				 if(key.equalsIgnoreCase(ServerName.GAME_SERVER_NAME))
				 {
					 IoSession gameSession = ServerMap.getSession(key);
					if(gameSession!=null&&gameSession.isConnected())
					{
						 msgmgt.reportUserStatus(gameSession, roleId, Command.USER_LOGOUT_RESP);
					}
				 }
				 if(key.equalsIgnoreCase(ServerName.MAIL_SERVER_NAME)){
					//通知聊天服务器
					 IoSession chatSess = ServerMap.getSession(ServerName.MAIL_SERVER_NAME);
			
					 if(chatSess!=null&&chatSess.isConnected())
					 {
						 msgmgt.reportUserStatus(chatSess, roleId, Command.USER_LOGOUT_RESP);
						 
					 }
				 }
				 
				 if(key.startsWith(ServerName.GAME_SCENE_SERVER)){
					 IoSession sceneSession = ServerMap.getSession(key);
					 if(sceneSession != null && sceneSession.isConnected()){
						 msgmgt.reportUserStatus(sceneSession, roleId, Command.USER_LOGOUT_RESP);
					 }
				 }
			 }
			 
			
			 //游戏客户端关闭，清除所有缓存信息
			 IdentityMap.removeSession(roleId);
			 RoleReqTimeMap.removeRoleReq(roleId);
		}
		else
		{
			if(session.getAttribute("Account") != null)
			{
				String account = (String)session.getAttribute("Account");
				//通知游戏服务器
				 IoSession gameSess = ServerMap.getSession(ServerName.GAME_SERVER_NAME);
				 if(gameSess!=null&&gameSess.isConnected())
				 {
					 msgmgt.reportUpdateLoginQueue(gameSess, account);
				 }
			}
			
			if(session.getAttribute("SequenceId")!=null)
			{
				int sequenceId = (Integer) session.getAttribute("SequenceId");
				SequenceMap.removeSession(sequenceId);
			}
		}
		
		
		
	}


	public void sessionCreated(IoSession session) throws Exception {
 
		
	}


	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
			
		session.close();
		
	}


	public void sessionOpened(IoSession session) throws Exception {

		if (session.getTransportType() == TransportType.SOCKET) {
			SocketConfig socketConfig = WebGameConfig.getInstance().getScoketConfig();

			if (socketConfig != null) {
					((SocketSessionConfig) session.getConfig())
							.setSendBufferSize(socketConfig.getSocketSendBuffer());
		
					((SocketSessionConfig) session.getConfig())
							.setReceiveBufferSize(socketConfig.getSocketReceiveBuffer());
			
					((SocketSessionConfig) session.getConfig())
							.setKeepAlive(socketConfig.isKeepAlive());
					 
					((SocketSessionConfig) session.getConfig())
							.setTcpNoDelay(socketConfig.isTcpNoDelay());
					int state = socketConfig.getIdleState();
					int timeout = socketConfig.getTimeout();
					if (state == 1) {
						session.setIdleTime(IdleStatus.READER_IDLE, timeout);
					} else if (state == 2) {
						session.setIdleTime(IdleStatus.WRITER_IDLE, timeout);
					} else if (state == 3) {
						session.setIdleTime(IdleStatus.BOTH_IDLE, timeout);
					}
				}
			}
		}
	
 
}
