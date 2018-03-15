package com.snail.webgame.engine.component.scene.protocal.storage;

import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.AMFMessageKey;
import com.snail.webgame.engine.common.to.BaseTO;
import com.snail.webgame.engine.component.scene.cache.StorageHandleCache;

import flex.messaging.io.amf.ASObject;

/**
 * 游戏数据存储处理结果
 * @author zenggy
 *
 */
public class StorageProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");

	@Override
	public void execute(Request request, Response response) {
		ASObject message = request.getAmfMessage();
		ASObject messageBody = (ASObject)message.get(AMFMessageKey.MSG_BODY);
		ASObject mesasgeHeader = (ASObject)message.get(AMFMessageKey.MSG_HEADER);
		
		int result = Integer.parseInt(messageBody.get(AMFMessageKey.MSG_RESULT).toString());
		String dbName = (String)messageBody.get(AMFMessageKey.DB_NAME);
		String sqlKey = (String)messageBody.get(AMFMessageKey.SQL_KEY);
		String sqlCmd = (String)messageBody.get(AMFMessageKey.SQL_CMD);
		BaseTO baseTO = (BaseTO)messageBody.get(AMFMessageKey.BASE_TO);
		
		
		Object handle = messageBody.get(AMFMessageKey.STORAGE_HANDLE);
		if(handle != null){
			StorageHandle storageHandle = StorageHandleCache.getHandle(Integer.parseInt(handle.toString()));
			storageHandle.execute(result, baseTO);
		}
		
	}

}
