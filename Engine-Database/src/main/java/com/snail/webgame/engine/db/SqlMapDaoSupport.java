package com.snail.webgame.engine.db;


import org.apache.ibatis.session.ExecutorType;

import com.snail.webgame.engine.db.session.SqlMapClientFactory;
import com.snail.webgame.engine.db.session.client.SqlMapClient;

/**
 * DAO父类
 * 默认提供获得SqlMapClient数据库操作对象方法
 * @author zenggy
 *
 */
public abstract class SqlMapDaoSupport {
	/**
	 * 获取数据库操作对象
	 * @param dbName 数据库名称
	 * @param autoCommit 事务是否自动提交
	 * @return 数据库操作对象
	 */
	public SqlMapClient getSqlMapClient(String dbName, boolean autoCommit){
		return SqlMapClientFactory.getSqlMapClient(dbName, autoCommit);
	}
	/**
	 * 获取数据库操作对象
	 * @param dbName 数据库名称
	 * @return 数据库操作对象
	 */
	public SqlMapClient getSqlMapClient(String dbName){
		return SqlMapClientFactory.getSqlMapClient(dbName);
	}
	/**
	 * 获取数据库操作对象
	 * @param dbName 数据库名称
	 * @param executorType 处理类型： ExecutorType.BATCH、ExecutorType.SIMPLE、ExecutorType.REUSE
	 * @param autoCommit 事务是否自动提交
	 * @return 数据库操作对象
	 */
	public SqlMapClient getSqlMapClient(String dbName, ExecutorType executorType, boolean autoCommit){
		return SqlMapClientFactory.getSqlMapClient(dbName, executorType, autoCommit);
	}
}
