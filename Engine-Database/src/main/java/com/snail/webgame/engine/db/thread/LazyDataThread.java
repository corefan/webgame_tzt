package com.snail.webgame.engine.db.thread;

import java.util.Iterator;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.Flag;
import com.snail.webgame.engine.common.to.BaseTO;
import com.snail.webgame.engine.db.cache.LazySaveMap;
import com.snail.webgame.engine.db.session.SqlMapClientFactory;

/**
 * 定时存储线程
 * @author zenggy
 *
 */
public class LazyDataThread extends Thread {
	private volatile boolean cancel = false;
	private int saveTime = 0;
	private int executeSaveTime = 300;//最低多长时间保存一次（秒）
	private int maxUpdateNum = 2000; //每次最多保存数据量
	private static final Logger log =LoggerFactory.getLogger("logs");
	
	public LazyDataThread(){}
	
	/**
	 * 构造方法
	 * @param updateTime 每次保存间隔时间(秒)，默认值=300秒, 传入值比默认值小无效
	 * @param updateNum 每次最多保存数据量, 默认值=2000
	 */
	public LazyDataThread(int updateTime, int updateNum){
		if(updateTime > this.executeSaveTime)
			this.executeSaveTime = updateTime;
		this.maxUpdateNum = updateNum;
	}
	@Override
	public void run() {
		while(!cancel){
			long begTime = System.currentTimeMillis();
			
			if(saveTime >= executeSaveTime){
				saveTime = 0;
				int num = 0;
				synchronized (Flag.OBJ_DB) {
					if(!LazySaveMap.isEmpty()){
						
						for(String dbName: LazySaveMap.keySet()){
							Map<String, BaseTO> lazyBaseMap = LazySaveMap.getLazyMap(dbName);
							if(lazyBaseMap != null && !lazyBaseMap.isEmpty()){
								
								SqlSessionFactory factory = SqlMapClientFactory.getSessionFactory(dbName);
								SqlSession session = factory.openSession(ExecutorType.BATCH, false);
								Iterator<String> it = lazyBaseMap.keySet().iterator();
								while(it.hasNext()){
									String lazyKey = it.next();
									BaseTO to = lazyBaseMap.get(lazyKey);
									if(to != null){
										session.update(to.getSqlKey(), to);
										num ++;
										lazyBaseMap.remove(lazyKey);
										it = lazyBaseMap.keySet().iterator();
										if(num >= maxUpdateNum)
											break;
									}
								}
								session.commit();
								session.close();
								if(num >= maxUpdateNum)
									break;
							}
							
						}
					}
				}
				long endTime = System.currentTimeMillis();
				int time = (int)(endTime - begTime)/1000;
				if(log.isWarnEnabled())
				{
					log.warn("[SYSTEM] Engine-Database LazyDataThread cost time : "+time + ", num=" + num);
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//累积本次线程时间
			long endTime = System.currentTimeMillis();
			int time = (int)(endTime - begTime)/1000;
			if(time<=0)
			{
				time =1;
			}
			saveTime += time;
		}
	}
	
	/**
	 * 关闭线程
	 * 更新完缓存里所有剩余数据
	 * @return
	 */
	public boolean cancel(){
		this.cancel = true;
		if(log.isWarnEnabled())
		{
			log.warn("[SYSTEM] Engine-Database LazyDataThread Cancel please wait....");
		}
		long begTime = System.currentTimeMillis();
		int num = 0;
		synchronized (Flag.OBJ_DB) {
			if(!LazySaveMap.isEmpty()){
				
				for(String dbName: LazySaveMap.keySet()){
					Map<String, BaseTO> lazyBaseMap = LazySaveMap.getLazyMap(dbName);
					if(lazyBaseMap != null && !lazyBaseMap.isEmpty()){
						
						SqlSessionFactory factory = SqlMapClientFactory.getSessionFactory(dbName);
						SqlSession session = factory.openSession(ExecutorType.BATCH, false);
						Iterator<String> it = lazyBaseMap.keySet().iterator();
						while(it.hasNext()){
							String lazyKey = it.next();
							BaseTO to = lazyBaseMap.get(lazyKey);
							if(to != null){
								session.update(to.getSqlKey(), to);
								num ++;
							}
						}
						session.commit();
						session.close();
					}
					
				}
				LazySaveMap.clear();
			}
		}
		long endTime = System.currentTimeMillis();
		int time = (int)(endTime - begTime)/1000;
		if(log.isWarnEnabled())
		{
			log.warn("[SYSTEM] Engine-Database LazyDataThread Cancel cost time : "+time + ", num=" + num);
		}
		return true;
	}
	
}
