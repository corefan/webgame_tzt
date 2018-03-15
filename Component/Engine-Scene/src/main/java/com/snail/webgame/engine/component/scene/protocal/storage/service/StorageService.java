package com.snail.webgame.engine.component.scene.protocal.storage.service;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.client.Client;

import com.snail.webgame.engine.common.AMFMessageKey;
import com.snail.webgame.engine.common.to.BaseTO;
import com.snail.webgame.engine.component.scene.cache.StorageHandleCache;
import com.snail.webgame.engine.component.scene.protocal.storage.StorageHandle;

import flex.messaging.io.amf.ASObject;

/**
 * 调用存储服务器操作类
 * @author zenggy
 *
 */
public class StorageService {

	/**
	 * 查询
	 * @param dbName 数据库名称
	 * @param sqlKey SQL语句ID
	 * @param to 对象
	 * @param handle 返回结果处理类
	 */
	public void query(String dbName, String sqlKey, BaseTO to, StorageHandle handle){
		send(dbName, sqlKey, AMFMessageKey.SQL_CMD_QUERY, to, handle);
	}
	
	/**
	 * 创建
	 * @param dbName 数据库名称
	 * @param sqlKey SQL语句ID
	 * @param to 对象
	 * @param handle 返回结果处理类
	 */
	public void insert(String dbName, String sqlKey, BaseTO to, StorageHandle handle){
		send(dbName, sqlKey, AMFMessageKey.SQL_CMD_INSERT, to, handle);
	}
	
	/**
	 * 更新
	 * @param dbName 数据库名称
	 * @param sqlKey SQL语句ID
	 * @param to 对象
	 * @param handle 返回结果处理类
	 */
	public void update(String dbName, String sqlKey, BaseTO to, StorageHandle handle){
		send(dbName, sqlKey, AMFMessageKey.SQL_CMD_UPDATE, to, handle);
	}
	
	/**
	 * 删除
	 * @param dbName 数据库名称
	 * @param sqlKey SQL语句ID
	 * @param to 对象
	 * @param handle 返回结果处理类
	 */
	public void delete(String dbName, String sqlKey, BaseTO to, StorageHandle handle){
		send(dbName, sqlKey, AMFMessageKey.SQL_CMD_DELETE, to, handle);
	}
	
	private void send(String dbName, String sqlKey, String sqlCmd, BaseTO to, StorageHandle handle){
		Client client = Client.getInstance();
		IoSession session = client.getSession("EngineStorageServer");
		ASObject message = new ASObject();
		
		ASObject header = new ASObject();
		header.put(AMFMessageKey.MSG_TYPE, 0xff01);
		
		ASObject body = new ASObject();
		body.put(AMFMessageKey.STORAGE_HANDLE, handle.hashCode());
		
		body.put(AMFMessageKey.SQL_CMD, sqlCmd);
		body.put(AMFMessageKey.DB_NAME, dbName);
		body.put(AMFMessageKey.SQL_KEY, sqlKey);
		body.put(AMFMessageKey.BASE_TO, to);
		
		message.put(AMFMessageKey.MSG_HEADER, header);
		message.put(AMFMessageKey.MSG_BODY, body);
		
		StorageHandleCache.addHandle(handle);
		session.write(message);
	}
}
