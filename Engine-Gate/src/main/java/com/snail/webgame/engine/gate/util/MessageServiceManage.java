package com.snail.webgame.engine.gate.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Set;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.common.util.CodeUtil;
import com.snail.webgame.engine.gate.cache.RoleReqTimeMap;
import com.snail.webgame.engine.gate.cache.SequenceMap;
import com.snail.webgame.engine.gate.cache.ServerMap;
import com.snail.webgame.engine.gate.common.ConnectConfig;
import com.snail.webgame.engine.gate.config.Command;
import com.snail.webgame.engine.gate.config.WebGameConfig;


public class MessageServiceManage {

	private static final Logger log =LoggerFactory.getLogger("logs");
	
	public ByteBuffer getNewMsgHeader(int MsgType,int roleId)
	{
		 
		 ByteBuffer buffer= ByteBuffer.allocate(29, false);
		 buffer.setAutoExpand(true);
		 buffer.putInt(0);//长度

		 buffer.putInt(0x00000100);//版本号

		 buffer.putInt(roleId);//UserID0

		 buffer.putInt(WebGameConfig.getInstance().getLocalConfig().getServerId());//UserID1

		 buffer.putInt(0);//UserID2

		 buffer.putInt(0);//UserID3

		 buffer.putInt(0);//UserID4
	
		 buffer.putInt(0);//UserID5

		 buffer.putInt(0);//UserID6

		 buffer.putInt(0);//UserID7

		 buffer.putInt(MsgType);
		 
		 
		 return buffer;
	}
	/**
	 * 获取玩家的账号
	 * @param roleId
	 * @param result
	*/
	public String checkLoginAccount(int sequenceId ,byte[] message)
	{
		byte[] l = new byte[2];
		System.arraycopy(message, 50, l, 0, 2);
		int len = byteArrayToInt(l);
		
		byte[] a = new byte[len];
		System.arraycopy(message, 52, a, 0, len);
		
		return new String(a, Charset.forName("UTF-8"));
	}
	 
	 public void sendActiveServerMessage(IoSession session,int flag)
	 {
		 
		 int  groupServerId = WebGameConfig.getInstance().getGameServerId();
		 int gateServerId = WebGameConfig.getInstance().getLocalConfig().getServerId();
		 ByteBuffer buffer= getNewMsgHeader(Command.GAME_SERVER_ACTIVE_REQ,0);
		 
		 
		 buffer.put((byte) 4);
		 buffer.put(encodeStringB(ServerName.GATE_SERVER_NAME+"-"+gateServerId));
		
		 buffer.put((byte) 2);
		 buffer.putInt(flag); 
		 
		 buffer.put((byte) 4);
		 buffer.put(encodeStringB(String.valueOf(groupServerId))); 
		 
		 int length = buffer.position();
		 buffer.putInt(0, length-4);
		 buffer.flip();
		 
		 session.write(buffer);
		 
	 }
	 

	/**
	 * 判断游戏客户端能否登录,若能登录保存用户session
	 * @param roleId
	 * @param result
	*/
	public int[] checkLoginResult(int sequenceId ,byte[] message)
	{
		int[] ret = new int[3];
		
		byte c [] = new byte[4];
		System.arraycopy(message, 45, c, 0, 4);
		int result = byteArrayToInt(c);
		
	
		
		byte b [] = new byte[4];
		System.arraycopy(message, 50, b, 0, 4);
		
		int roleId = byteArrayToInt(b);
		
		
		byte d [] = new byte[4];
		System.arraycopy(message, 55, d, 0, 4);
		
		int sceneId = byteArrayToInt(d);
		
		IoSession userSess = SequenceMap.getSession(sequenceId); 
		if(result==1&&userSess!=null&&userSess.isConnected()&&roleId>0)
		{
		
			userSess.setAttribute("identity",roleId);
			
			
			//这个帐号被其他用户登录踢出那个用户下线
			if(IdentityMap.isExistRole(roleId))
			{
				//（如果存在 说明游戏服务器上没有发送迫使下线的信息）
				IoSession oldSession = IdentityMap.getSession(roleId);
				if(oldSession!=null&&oldSession.isConnected())
				{
					 
					if(oldSession.getAttribute("identity")!=null)
					{
						
						oldSession.write(getUserLogout(roleId,70102));
						oldSession.removeAttribute("identity");
					}
				
					oldSession.close();
				}
				
				//替换掉这个session
				IdentityMap.addSession(roleId, userSess);
				
				
			}
			else
			{
				IdentityMap.addSession(roleId, userSess);
				
				 setRoleId((byte[]) message, roleId);
				 setSceneId((byte[]) message, sceneId);
				 setGateServerId((byte[]) message,  WebGameConfig.getInstance().getLocalConfig().getGateServerId());
				 
				//广播此用户登录信息
				reportUserLogin(message);
			}
		}
		else if(result==1&&roleId>0)
		{
			
			//用户已经登录成功但是断开,必须向游戏服务器发送注销请求
			 IoSession tradeSess = ServerMap.getSession(ServerName.GAME_SERVER_NAME);
			 if(tradeSess!=null&&tradeSess.isConnected())
			 {
				 reportUserStatus(tradeSess,roleId,Command.USER_LOGOUT_RESP);
			 }
			 
			 
			IoSession sceneSession = ServerMap.getSession(ServerName.GAME_SCENE_SERVER + "-" + sceneId);
			if(sceneSession != null && sceneSession.isConnected()){
				reportUserStatus(sceneSession, roleId, Command.USER_LOGOUT_RESP);
			}
		}
	 
		ret[0] = result;
		ret[1] = roleId;
		ret[2] = sceneId;
		return ret;
	}
	 
	
	public byte[] getUserVerifyResp(int result)
	{
		 ByteBuffer buffer= getNewMsgHeader(Command.USER_VERIFY_ROLE_RESP,0);
		 buffer.put((byte) 2);
		 buffer.putInt(result);
		 
		 int length = buffer.position();
		 buffer.putInt(0, length-4);
		 buffer.flip();
		 
		 byte b [] = new byte[length];
		 buffer.get(b);
		 
		 return b;
		 
	}
	/**
	 * 迫使用户下线
	 * @param session
	 * @param roleId
	 */
	public  byte[]  getUserLogout(int roleId,int result)
	{
		 ByteBuffer buffer= getNewMsgHeader(Command.USER_LOGOUT_RESP,roleId);
		 buffer.put((byte) 2);
		 buffer.putInt(result);
		 buffer.put((byte) 2);
		 buffer.putInt(roleId);
		 
		 
		 int length = buffer.position();
		 buffer.putInt(0, length-4);
		 buffer.flip();
		 
		 
		 byte b [] = new byte[length];
		 buffer.get(b);
		 
		 return b;
		 
		 
	}
	
	/**
	 * 向其他服务器广播用户登录信息
	 * @param message
	 */
	public void reportUserLogin(byte[] message)
	{
	
		 
		 IoSession chatSess = ServerMap.getSession(ServerName.MAIL_SERVER_NAME);
		 if(chatSess!=null&&chatSess.isConnected())
		 {
			 chatSess.write(message);
		 } 
	}
	
	
	
	
//	public static void sendClientMessage(IoSession session,byte[] message)
//	{
//		int encryptType =  WebGameConfig.getInstance().getEncryptType();
//		String encryptCode =  WebGameConfig.getInstance().getEncryptCode();
//		
//		if(encryptType>0)
//		{
//			String name = "";
//			
//			if(encryptType==1)
//			{
//				name ="DES";
//			}
//			else if(encryptType==2)
//			{
//				name ="BlowFish";
//			}
//			else if(encryptType==3)
//			{
//				name ="DESede";
//			}
//			else if(encryptType==4)
//			{
//				name ="aes";
//			}
//			byte b[] = getEncryptMessage(name, encryptCode.getBytes(),  message);
//			
//			if(b==null)
//			{
//				if(log.isWarnEnabled())
//				{
//					 log.warn("System  occure encrypt error!");
//				}
//				return;
//			}
//			byte [] lenghtMss = MessageServiceManage.int2bytes(b.length);
//			
//			
//			byte newMessage [] = new byte[b.length+4];
//			
//			System.arraycopy(b, 0, newMessage, 4, b.length);
//			System.arraycopy(lenghtMss, 0, newMessage, 0, lenghtMss.length);
//			
//			b = null;
//			lenghtMss = null;
//			message = null;
//			
//			session.write(newMessage);
//		}
//		else
//		{
//			session.write(message);
//		}
//		
//		
//		
//		
//	}
//	
 
	
	
	/**
	 * 向其他服务器广播用户退出信息
	 * @param message
	 */
	public void reportUserLogout(byte[] message)
	{
		int roleId = getRoleId(message);
		
		int result = getLogoutResult(message);

		IoSession sendSess = IdentityMap.getSession(roleId);
		
		if(sendSess!=null&&sendSess.isConnected())
		{
			
			//用户下线
			sendSess.write(getUserLogout(roleId,result));
		
			//用户退出，清除用户记录
			sendSess.removeAttribute("identity");
			IdentityMap.removeSession(roleId);
			
			//sendSess.close();//无法确保关闭前消息到达，客户端关闭连接，如果确保将会产生性能问题
		}
		
		
		
		RoleReqTimeMap.removeRoleReq(roleId);
		
		 HashMap<String, ConnectConfig> map =  WebGameConfig.getInstance().getConnectConfig();
		 
		 Set<String> set = map.keySet();
		 
		 for(String key :set)
		 {
			 if(key.startsWith(ServerName.GAME_SCENE_SERVER)){
				IoSession gameSess = ServerMap.getSession(key);
				if(gameSess!=null&&gameSess.isConnected())
				{
					reportUserStatus(gameSess, roleId, Command.USER_LOGOUT_RESP);
				}
			 }
		 }
		 
		if(result==ErrorCode.USER_LOGIN_ERROR_2)//这个用户是被迫下线的 
		{
			
			return ;
		}

		IoSession chatSess = ServerMap.getSession(ServerName.MAIL_SERVER_NAME);
		if(chatSess!=null&&chatSess.isConnected())
		{
			chatSess.write(message);
		}
		IoSession gameSession = ServerMap.getSession(ServerName.GAME_SERVER_NAME);
		if(gameSession!=null&&gameSession.isConnected())
		{
			 reportUserStatus(gameSession, roleId, Command.USER_LOGOUT_RESP);
		}
	}
	
	
 
		
	/**
	 * 向游戏服务器报告当前用户状态
	 * @param session
	 * @param identity
	 * @param roleId
	 */
	public void reportUserStatus(IoSession session,int roleId, int msgType)
	{
		 ByteBuffer buffer= getNewMsgHeader(msgType,roleId);
		
		 buffer.put((byte) 2);
		 buffer.putInt(1);
		 buffer.put((byte) 2);
		 buffer.putInt(roleId);
		 
		 int length = buffer.position();
		 buffer.putInt(0, length-4);
		 buffer.flip();
		 session.write(buffer);
	}
	 /**
	  * 获得该消息的类型
	  * @return
	  */
	 public int getMessageType(byte[] b)
	 {
		 if(b.length>=44)
		 {

			 byte[] m = new byte[4];
			 m[0]=b[40];
			 m[1]=b[41];
			 m[2]=b[42];
			 m[3]=b[43];
			 int k = byteArrayToInt(m);
			 if(k==0)
			 {
				 for(int i=0;i<32;i++)
				 {
					 System.out.print(b[i]+",");
				 }
			 }
			 m =null;
			 return k;
		 }
		  
		return 0;
	 }
	 
	 /**
	  * 获得消息中的sequenceId
	  * @param b
	  * @return
	  */
	 public int getMsgSequence(byte[] b)
	 {
		 if(b.length>=44)
		 {

			 byte[] m = new byte[4];
			 m[0]=b[16];
			 m[1]=b[17];
			 m[2]=b[18];
			 m[3]=b[19];
			 int sequence = byteArrayToInt(m);
			 
			 m = null;
			 
			 return sequence;
		 }
		return 0;
	 }
	 /**
	  * 放置一个sequenceId
	  * @param sequenceId
	  * @param b
	  */
	 public void setMsgSequence(int sequenceId,byte[] b)
	 {
		 if(b.length>=44)
		 {

			 byte[] m =  int2bytes(sequenceId);
			 b[16]=m[0];
			 b[17]=m[1];
			 b[18]=m[2];
			 b[19]=m[3];
			 
			 m = null;
		 }
	 }
	 
	 /**
	  * 前面有一个字节 表示类型 所以从45开始 而不是44
	  * @param IP
	  * @param b
	  */
	 public void setLoginIP(String IP,byte[] b)
	 {
		 int i = IPtoInt(IP);
		 if(b.length>=45)
		 {
			 byte[] m =  int2bytes(i);
			 b[45]=m[0];
			 b[46]=m[1];
			 b[47]=m[2];
			 b[48]=m[3];
			 
			 m = null;
		 }
		 
		 
		 
	 }
	 
		public static int IPtoInt(String ip)
		{  	
			int res=0;
			if(ip!=null&&ip.length()>0)
			{
			  String[] aip=ip.split("[.]");
			  if(aip!=null&&aip.length==4)
			  {
				  for ( int i=0;i<4;i++)
				  {
					  res = res+Integer.parseInt(aip[i]);
					  if(i<3)
					  res = res<<8;				  
				  }
			  }
			}
			  return res;
		}	
	 /**放置用户角色ID,以及通讯服务器ID
	  * @param b
	  */
	 public void setRoleId(byte[] b ,int roleId)
	 {
		 
	 
		 if(b!=null&&b.length>=44)
		 {
			 byte[] m = int2bytes(roleId);
			 
			 b[8]=m[0];
			 b[9]=m[1];
			 b[10]=m[2];
			 b[11]=m[3];
			 
			 m = null;
		 }
	 }
	 
	 /**放置场景ID
	  * @param b
	  */
	 public void setSceneId(byte[] b ,int sceneId)
	 {
		 
	 
		 if(b!=null&&b.length>=44)
		 {
			 byte[] m = int2bytes(sceneId);
			 
			 b[20]=m[0];
			 b[21]=m[1];
			 b[22]=m[2];
			 b[23]=m[3];
			 
			 m = null;
		 }
	 }
	 
 
	 
	 public void setGateServerId(byte[] b, byte[] m)
	 {
		 if(b!=null&&b.length>=44)
		 {
		 
			 
			 b[12]=m[0];
			 b[13]=m[1];
			 b[14]=m[2];
			 b[15]=m[3];
		 }
	 }
	 
	 
	 public void setClientIP(byte[] b,String IP)
	 {
		 if(b!=null&&b.length>=44)
		 {
			 int i = IPtoInt(IP);
			 byte[] m =  int2bytes(i);
			 
			 b[28]=m[0];
			 b[29]=m[1];
			 b[30]=m[2];
			 b[31]=m[3];
		 }
	 }
	 
	 public static byte[] getDecodeMessage(String name, byte[] b)
	 {
		 if(b!=null&&b.length>=4)
		 {
			byte m[]= new byte[4];
			System.arraycopy(b, 0, m, 0, 4);
			int length = byteArrayToInt(m);
			 
			byte k [] = new byte[length];
			 
			System.arraycopy(b, 4, k, 0, length);
			
			byte [] p = CodeUtil.decrypt(k, CodeUtil.getSecretKey(name), name);
			return p;
			
		 }
			return null;
	 }
	 
	 
	 public static byte[] getEncryptMessage(String name,byte[] b)
	 {
		 if(b!=null&&b.length>=4)
		 {
			byte m[]= new byte[4];
			System.arraycopy(b, 0, m, 0, 4);
			int length = byteArrayToInt(m);
			 
			byte k [] = new byte[length];
			 
			System.arraycopy(b, 4, k, 0, length);
			
			byte [] p = CodeUtil.encrypt(k, CodeUtil.getSecretKey(name), name);
			return p;
			
		 }
			return null;
	 }
	 
	 
	 
	 /**
	  * 获得退出的结果
	  * @param message
	  * @return
	  */
	 public int getLogoutResult(byte[] message)
	 {
			byte c [] = new byte[4];
			System.arraycopy(message, 45, c, 0, 4);
			int result = byteArrayToInt(c);
			
			c = null;
			
			return result;
	 }
	 
	 
	 /**
	  * 获得用户ID 
	  * @param b
	  * @return
	  */
	 public int getRoleId(byte[] b)
	 {
		 if(b!=null&&b.length>=44)
		 {
			 byte[] m = new byte[4];
			 m[0]=b[8];
			 m[1]=b[9];
			 m[2]=b[10];
			 m[3]=b[11];
			 
			 int roleId = byteArrayToInt(m);
			 
			 m =null;
			 return roleId;
		 }
		return 0;
	 }
	 
 
	 
	 
	 
	 /**
	  * 获得场景服务器ID 
	  * @param b
	  * @return
	  */
	 public int getSceneId(byte[] b)
	 {
		 if(b!=null&&b.length>=44)
		 {
			 byte[] m = new byte[4];
			 m[0]=b[20];
			 m[1]=b[21];
			 m[2]=b[22];
			 m[3]=b[23];
			 return byteArrayToInt(m);
		 }
		return 0;
	 }
	 
	 
	 public static int byteArrayToInt(byte[] b) {
	        int value = 0;
	        for (int i = 0; i < b.length; i++) {
	            int shift = (b.length - 1 - i) * 8;
	            value += (b[i] & 0xFF) << shift;
	        }
	        return value;
	    } 
	 public static long byteArrayToLong(byte[] b) {
	        long value = 0;
	        for (int i = 0; i < b.length; i++) {
	            long shift = i * 8;
	            value += (b[i] & 0xFF) << shift;
	        }
	        return value;
	    } 
	 
	 public static byte[] int2bytes(int n) {
		 byte[] b = new byte[4];
		 for(int i = 0;i < 4;i++){
		 b[i] = (byte)(n >> (24 - i * 8)); 
		 }
		 return b;
	}
	 
	 public byte[] encodeStringB(String str) {
		 
			if(str!=null&&str.length()>0)
			{
				byte [] m = null ;
				try {
					m = str.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					 
				}
				
				if(m!=null&&m.length>0)
				{
					byte n []  =new byte[m.length+2];
					
					System.arraycopy(m, 0, n,2, m.length);
					byte k[] = shortToBytes((short) m.length);
					System.arraycopy(k, 0, n,0, 2);
					
					return n;
				}
				
			}
			return shortToBytes((short) 0);
		}
	 
	 
		public  byte[] shortToBytes(short sNum){
		    byte[] bytesRet = new byte[2];
		    bytesRet[0] = (byte) ((sNum >> 8) & 0xFF);
		    bytesRet[1] = (byte) (sNum & 0xFF);
		    return bytesRet;
		}
		
		/**
		 * 向游戏服务器广播登录队列
		 * @param session
		 * @param account
		 */
		public void reportUpdateLoginQueue(IoSession session, String account)
		{
			 ByteBuffer buffer= getNewMsgHeader(Command.CHECK_LOGIN_QUEUE_RESP,0);
			
			 buffer.put((byte) 2);
			 buffer.putInt(1);
			 buffer.put((byte) 4);
			 buffer.put(encodeStringB(account));
			 int length = buffer.position();
			 buffer.putInt(0, length-4);
			 buffer.flip();
			 
			 session.write(buffer);
		}
		/**
		 * 给前置服务器发送网关服务器信息
		 * @param session
		 * @param flag 
		 */
		public void setActiveListServerMessage(IoSession session, int flag) {
			 int groupServerId = WebGameConfig.getInstance().getGameServerId();
			 int gateServerId = WebGameConfig.getInstance().getLocalConfig().getServerId();
			 String romateIP = WebGameConfig.getInstance().getLocalConfig().getRomateIP();
			 int romatePort = Integer.valueOf(WebGameConfig.getInstance().getLocalConfig().getRomatePort());
			 int encryptType = WebGameConfig.getInstance().getEncryptType();
			 String trueEncryptCode = WebGameConfig.getInstance().getTrueEncryptCode();
			 int curNum = IdentityMap.getSize();
			 int cryptoType = WebGameConfig.getInstance().getCryptoType();
			 ByteBuffer buffer= getNewMsgHeader(Command.REPORT_SERVER_NUM,0);
			 
			 // 服务器的名称
			 buffer.put((byte) 4);
			 buffer.put(encodeStringB(ServerName.GATE_SERVER_NAME + "-" + gateServerId));
			
			 // 接入服务器Id
			 buffer.put((byte) 2);
			 buffer.putInt(gateServerId); 
			 
			 // 是否是刚启动
			 buffer.put((byte) 2);
			 buffer.putInt(flag); 
			 
			 // 服务器的组ID
			 buffer.put((byte) 2);
			 buffer.putInt(groupServerId); 
			 
			 // 服务器外网地址
			 buffer.put((byte) 4);
			 buffer.put(encodeStringB(romateIP)); 
			 
			 // 服务器外网端口
			 buffer.put((byte) 2);
			 buffer.putInt(romatePort); 
			 
			 // 加密类型
			 buffer.put((byte) 2);
			 buffer.putInt(encryptType); 
			 
			 // 加密key
			 buffer.put((byte) 4);
			 buffer.put(encodeStringB(trueEncryptCode)); 
			 
			 // 当前连接数
			 buffer.put((byte) 2);
			 buffer.putInt(curNum); 
			 
			 // 客户端数据流类型
			 buffer.put((byte) 2);
			 buffer.putInt(cryptoType);
			 
			 int length = buffer.position();
			 buffer.putInt(0, length-4);
			 buffer.flip();
			 
			 session.write(buffer);
		}
}
