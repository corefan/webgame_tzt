package com.snail.webgame.engine.db.session.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.snail.webgame.engine.common.to.BaseTO;
import com.snail.webgame.engine.db.cache.LazySaveMap;

/**
 * 数据库操作类
 * @author zenggy
 *
 */
public class SqlMapClient {
	private SqlSession session;
	private String dbName;
	private boolean autoCommit = true;
	/**
	 * 缓存事务未提交数据, 待事务commit时取出更新
	 */
	private Map<Integer, List<BaseTO>> lazyMap = new HashMap<Integer, List<BaseTO>>();
	
	private static final int INSERT = 1;
	private static final int UPDATE = 2;
	private static final int DELETE = 3;
	
	/**
	 * 事务未提交时，保存数据至缓存，待事务commit时取出更新
	 * @param key
	 * @param to
	 */
	private void addLazy(Integer key, BaseTO to){
		if(lazyMap.containsKey(key)){
			lazyMap.get(key).add(to);
		}
		else{
			List<BaseTO> list = new ArrayList<BaseTO>();
			list.add(to);
			lazyMap.put(key, list);
		}
	}
	
	/**
	 * 构造方法
	 * @param session 会话
	 * @param dbName 数据库名称
	 * @param autoCommit 事务是否自动提交
	 */
	public SqlMapClient(SqlSession session, String dbName, boolean autoCommit) {
		this.session = session;
		this.autoCommit = autoCommit;
		this.dbName = dbName;
	}
	/**
	 * 构造方法
	 * @param session 会话
	 * @param dbName 数据库名称
	 */
	public SqlMapClient(SqlSession session, String dbName){
		this.session = session;
		this.dbName = dbName;
	}


	/**
	 * 查询
	 * 如果baseTo为定时保存对象，则会先根据主键取缓存数据，再则查询数据库
	 * @param sqlKey SQL语句Id
	 * @param to 查询条件对象
	 * @return 单个对象
	 */
	public BaseTO query(String sqlKey, BaseTO to) {
		BaseTO lazyTO = LazySaveMap.getLazyTO(to, dbName);
		if (lazyTO != null)
			return lazyTO;
		try {
			BaseTO result = session.selectOne(sqlKey, to);
			return result;
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
	}
	
	/**
	 * 查询
	 * @param sqlKey SQL语句Id
	 * @param obj 查询条件对象
	 * @return 单个对象
	 */
	public BaseTO query(String sqlKey, Object obj) {
		try {
			BaseTO result = session.selectOne(sqlKey, obj);
			return result;
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
	}

	/**
	 * 查询
	 * @param sqlKey SQL语句Id
	 * @param to 查询条件对象
	 * @return 多个对象
	 */
	public List queryList(String sqlKey, BaseTO to) {
		try {
			List result = session.selectList(sqlKey, to);
			return result;
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
	}
	
	/**
	 * 查询
	 * @param sqlKey SQL语句Id
	 * @param obj 查询条件对象
	 * @return 多个对象
	 */
	public List queryList(String sqlKey, Object obj) {
		try {
			List result = session.selectList(sqlKey, obj);
			return result;
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
	}
	
	/**
	 * 查询
	 * @param sqlKey SQL语句Id
	 * @param to 查询条件对象
	 * @param mapKey 返回map里的KEY
	 * @return 多个对象
	 */
	public Map queryMap(String sqlKey, BaseTO to, String mapKey) {
		try {
			Map result = session.selectMap(sqlKey, to, mapKey);
			return result;
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
	}
	
	/**
	 * 查询
	 * @param sqlKey SQL语句Id
	 * @param obj 查询条件对象
	 * @param mapKey 返回map里的KEY
	 * @return 多个对象
	 */
	public Map queryMap(String sqlKey, Object obj, String mapKey) {
		try {
			Map result = session.selectMap(sqlKey, obj, mapKey);
			return result;
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
	}
	
	
	/**
	 * 查询
	 * @param sqlKey SQL语句Id
	 * @param mapKey 返回map里的KEY
	 * @return 多个对象
	 */
	public Map queryMap(String sqlKey, String mapKey) {
		try {
			Map result = session.selectMap(sqlKey, mapKey);
			return result;
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
	}
	
	/**
	 * 查询
	 * @param sqlKey SQL语句Id
	 * @return 多个对象
	 */
	public List queryList(String sqlKey) {
		try {
			List to = session.selectList(sqlKey);
			return to;
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
	}

	/**
	 * 插入
	 * @param sqlKey SQL语句Id
	 * @param to 查询条件对象
	 * @return 是否成功
	 */
	public boolean insert(String sqlKey, BaseTO to) {
		int result = session.insert(sqlKey, to);
		if(autoCommit){
			session.commit();
			session.close();
		}
		if(result == 1)
			return true;
		return false;
	}

	/**
	 * 更新
	 * 如果to为定时保存对象，则放入缓存，暂时不更新至数据库
	 * @param sqlKey SQL语句Id
	 * @param to 更新对象
	 * @return 是否成功
	 */
	public boolean update(String sqlKey, BaseTO to) {
		if (to.getSaveMode() == BaseTO.LAZY){
			to.setSqlKey(sqlKey);
			BaseTO cloneTO = (BaseTO)to.clone();
			if(autoCommit)
				LazySaveMap.addLazyTO(cloneTO, dbName);
			else
				addLazy(UPDATE, cloneTO);
			return true;
		}
		else {
			int result = session.update(sqlKey, to);
			if(autoCommit){
				session.commit();
				session.close();
			}
			if(result == 1)
				return true;
			return false;
		}
		
	}

	/**
	 * 删除
	 * 如果to为定时保存对象，则同时删除缓存数据
	 * @param sqlKey SQL语句Id
	 * @param to 删除条件对象
	 * @return 是否成功
	 */
	public boolean delete(String sqlKey, BaseTO to) {
		if (to.getSaveMode() == BaseTO.LAZY){
			if(autoCommit)
				LazySaveMap.removeLazyTO(to, dbName);
			else
				addLazy(DELETE, to);
		}
		int result = session.delete(sqlKey, to);
		if(autoCommit){
			session.commit();
			session.close();
		}
		if(result == 1)
			return true;
		return false;
	}
	
	/**
	 * 删除
	 * @param sqlKey SQL语句Id
	 * @return 是否成功
	 */
	public boolean delete(String sqlKey) {
		int result = session.delete(sqlKey);
		if(autoCommit){
			session.commit();
			session.close();
		}
		if(result == 1)
			return true;
		return false;
	}
	
	/**
	 * 提交事务
	 * 如果该事务有定时保存对象保存至定时存储缓存
	 */
	public void commit(){
		this.session.commit();
		this.session.close();
		if(!autoCommit){
			Set<Integer> keys = lazyMap.keySet();
			for(Integer key : keys){
				List<BaseTO> list = lazyMap.get(key);
				if(list != null){
					for(BaseTO to : list){
						if(key == UPDATE){
							LazySaveMap.addLazyTO(to, dbName);
						}
						else if(key == DELETE){
							LazySaveMap.removeLazyTO(to, dbName);
						}
					}
				}
			}
		}
		lazyMap.clear();
	}
	
	/**
	 * 关闭数据库操作
	 */
//	public void close(){
//		this.session.close();
//		lazyMap.clear();
//	}
	
	/**
	 * 事务回滚
	 */
	public void rollback(){
		this.session.rollback();
		this.session.close();
		lazyMap.clear();
	}

	/**
	 * 批量插入
	 * @param string
	 * @param addPackList
	 * @author wangxf
	 * @date 2012-8-27
	 */
	public boolean insertBatch(String sqlKey, List<Object> list) {
		int result;
		try {
			result = session.insert(sqlKey, list);
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
		
		if(result >= 1)
			return true;
		return false;
	}

	/**
	 * 
	 * @param string
	 * @param oldPackList
	 * @author wangxf
	 * @date 2012-8-27
	 */
	public boolean deleteBatch(String sqlKey, List<Object> list) {
		int result;
		try {
			result = session.delete(sqlKey, list);
		} finally {
			if(autoCommit){
				session.commit();
				session.close();
			}
		}
		
		if(result >= 1)
			return true;
		return false;
	}
}
