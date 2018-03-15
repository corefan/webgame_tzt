package com.snail.webgame.engine.component.scene.protocal.datasyn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.epilot.ccf.core.util.ByteBufferDataHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.AMFMessageKey;
import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.common.info.AIInfo;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.common.to.BaseTO;
import com.snail.webgame.engine.component.scene.cache.ServerMap;
import com.snail.webgame.engine.component.scene.config.GameConfig;
import com.snail.webgame.engine.component.scene.core.SceneGameMapFactory;
import com.snail.webgame.engine.component.scene.util.FlashHandleStr;

import flex.messaging.io.amf.ASObject;

/**
 * 场景数据同步
 * 
 * @author zenggy
 * 
 */
public class DataSynService {
	private static final Logger logger=LoggerFactory.getLogger("logs");
	/**
	 * 同步数据到客户端
	 * @param roleIds 附近玩家
	 * @param to 同步对象
	 * @param serverName 服务器名称，会从ServerMap里取session
	 */
	public static void sendDataSynByAMF(List<Long> roleIds, String serverName, BaseTO to) {
		IoSession session = ServerMap.getServerSession(serverName);
		if(session == null){
			if(logger.isWarnEnabled()){
				logger.warn("Can not found session : " + serverName);
			}
			return;
		}
		for(Long roleId : roleIds){
			ASObject message = new ASObject();
	
			ASObject header = new ASObject();
			header.put(AMFMessageKey.MSG_TYPE, 0xAA01);
			header.put(AMFMessageKey.MSG_ROLE_ID, roleId);
			ASObject body = new ASObject();
			setBody(body, to);
			message.put(AMFMessageKey.MSG_HEADER, header);
			message.put(AMFMessageKey.MSG_BODY, body);
	
			session.write(message);
		}
	}

	private static void setBody(ASObject body, BaseTO to) {
		List<String> updateFields = to.getUpdateFields();

		try {
			if (updateFields != null && updateFields.size() > 0) {
				body.put("className", to.getClass().getName());
				for (String updateField : updateFields) {
					Method method = to.getClass().getMethod("get" + updateField,
							new Class[] {});
					if (method != null) {
						Object result = method.invoke(to, new Object[] {});
						if(result instanceof BaseTO)
						{
							ASObject subBody = new ASObject();
							setBody(subBody, (BaseTO)result);
							body.put(updateField, subBody);
						}
						else{
							body.put(updateField, result);
						}
					}
				}
				to.cleanUpdateFields();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 同步数据到客户端
	 * @param roleIds 接收人
	 * @param to 同步对象
	 */
	public static void sendDataSyn(List<Long> roleIds, BaseTO to) {
		String serverName = ServerName.GATE_SERVER_NAME + "-" + to.getGateServerId();
		IoSession session = ServerMap.getServerSession(serverName);
		if(session == null){
			if(logger.isWarnEnabled()){
				logger.warn("Can not found session : " + serverName);
			}
			return;
		}
		for(Long roleId : roleIds){
	
			ByteBuffer buffer = ByteBuffer.allocate(16,false);
			buffer.setAutoExpand(true);
				
			buffer.putInt(0);//设置长度
				
			ByteBufferDataHandle byteBufferDataHandle =new ByteBufferDataHandle(buffer);
			
			setHead(byteBufferDataHandle, roleId, 0xAA01);
			setBody(byteBufferDataHandle, to);
		
			int i = buffer.position();
				
				
			buffer.order(ByteOrder.BIG_ENDIAN).putInt(0,i-4);
			buffer.flip();
			
			session.write(buffer);
		}
	}
	/**
	 * 同步数据到客户端
	 * @param aiInfo 同步对象所属者 
	 * @param to 同步对象
	 * @param isSynArea 是否同步给附近的玩家
	 */
	public static void sendDataSyn(AIInfo aiInfo, BaseTO to, boolean isSynArea) {
		List<Long> roleIds = new ArrayList<Long>();
		if(aiInfo instanceof RoleInfo)
			roleIds.add(((RoleInfo)aiInfo).getId());
		if(isSynArea){
			GameMap3D gameMap = SceneGameMapFactory
					.getGameMap(aiInfo.getMapId());
			if (gameMap == null)
				return;
			
			int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
			int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
			int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
			roleIds.addAll(gameMap.getAreaOtherRole(aiInfo,
					refreshRadiiX, refreshRadiiY, refreshRadiiZ));
		}
		sendDataSyn(roleIds, to);
	}
	
	private static void setHead(ByteBufferDataHandle byteBufferDataHandle, long roleId, int msgType){
		writeBuffer(byteBufferDataHandle, 0, false);
		writeBuffer(byteBufferDataHandle, (int)roleId, false);
		writeBuffer(byteBufferDataHandle, 0, false);
		writeBuffer(byteBufferDataHandle, 0, false);
		writeBuffer(byteBufferDataHandle, 0, false);
		writeBuffer(byteBufferDataHandle, 0, false);
		writeBuffer(byteBufferDataHandle, 0, false);
		writeBuffer(byteBufferDataHandle, 0, false);
		writeBuffer(byteBufferDataHandle, 0, false);
		writeBuffer(byteBufferDataHandle, msgType, false);
		
	}
	private static void setBody(ByteBufferDataHandle byteBufferDataHandle, BaseTO to) {
		List<String> updateFields = to.getUpdateFields();

		try {
			if (updateFields != null && updateFields.size() > 0) {
				writeBuffer(byteBufferDataHandle, to.getClass().getSimpleName(), true);
				writeBuffer(byteBufferDataHandle, to.getId(), true);
				writeBuffer(byteBufferDataHandle, updateFields.size(), true);
				for (String updateField : updateFields) {
					Method method = to.getClass().getMethod("get" + updateField,
							new Class[] {});
					if (method != null) {
						Object result = method.invoke(to, new Object[] {});
						if(result instanceof BaseTO)
						{
							writeBuffer(byteBufferDataHandle, updateField, true);
							setBody(byteBufferDataHandle, (BaseTO)result);
						}
						else{
							writeBuffer(byteBufferDataHandle, updateField, true);
							writeBuffer(byteBufferDataHandle, result, true);
						}
					}
				}
				to.cleanUpdateFields();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void writeBuffer(ByteBufferDataHandle byteBufferDataHandle, Object obj, boolean flag){
		String type = obj.getClass().getSimpleName();
		if(type.equalsIgnoreCase("byte"))
		{
			if(flag)
			{
				byteBufferDataHandle.setBigData((byte)0);
			}
			byteBufferDataHandle.setBigData((Byte)obj);
		}
		else if(type.equalsIgnoreCase("Short"))
		{
			if(flag)
			{
				byteBufferDataHandle.setBigData((byte)1);
			}
			 byteBufferDataHandle.setBigData((Short)obj);
		}
		else if(type.equalsIgnoreCase("Integer"))
		{	
			if(flag)
			{
				byteBufferDataHandle.setBigData((byte)2);
			}
			 byteBufferDataHandle.setBigData((Integer)obj);
		}
		else if(type.equalsIgnoreCase("float"))
		{
			if(flag)
			{
				byteBufferDataHandle.setBigData((byte)3);
			}
			 byteBufferDataHandle.setBigData((Float)obj);
		}
		else if(type.equalsIgnoreCase("double"))
		{
			if(flag)
			{
				byteBufferDataHandle.setBigData((byte)7);
			}
			 byteBufferDataHandle.setBigData((Double)obj);
		}
		else if(type.equalsIgnoreCase("long"))
		{
			if(flag)
			{
				byteBufferDataHandle.setBigData((byte)9);
			}
			 byteBufferDataHandle.setBigData((Long)obj);
		}
		else if(type.indexOf("String")!=-1)
		{
			if(flag)
			{
				byteBufferDataHandle.setBigData((byte)4);
			}
			 byteBufferDataHandle.setBigData((String)obj,new FlashHandleStr());	
			
		}
		else if(type.startsWith("byte[]"))//byte数组对象
		{
			if(obj!=null)
			{
				byteBufferDataHandle.writeBigByte((byte[])obj);
			}
		}
	}
	
}
