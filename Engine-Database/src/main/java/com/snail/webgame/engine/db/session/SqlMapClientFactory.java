package com.snail.webgame.engine.db.session;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.db.session.client.SqlMapClient;

/**
 * 获取数据库操作对象工厂
 * @author zenggy
 *
 */
public class SqlMapClientFactory {

	private static final Logger log =LoggerFactory.getLogger("logs");
	private static String mybatisConfigPath = "/config/mybatis-config.xml";
	private static Map<String, SqlSessionFactory> sessionFactoryMap = new HashMap<String, SqlSessionFactory>();
	
	/**
	 * 获取一个新的数据库操作对象
	 * @param dbName 数据库名称
	 * @return 数据库操作对象
	 */
	public static SqlMapClient getSqlMapClient(String dbName){
		if(sessionFactoryMap.containsKey(dbName)){
			SqlSession session = sessionFactoryMap.get(dbName).openSession();
			return new SqlMapClient(session, dbName);
		}
		else{
			SqlSessionFactory factory = initSessionFactory(dbName);
			if(factory != null){
				SqlSession session = factory.openSession();
				return new SqlMapClient(session, dbName);
			}
			return null;
		}
	}
	
	/**
	 * 获取一个新的数据库操作对象
	 * @param dbName 数据库名称
	 * @param autoCommit 事务是否自动提交
	 * @return 数据库操作对象
	 */
	public static SqlMapClient getSqlMapClient(String dbName, boolean autoCommit){
		if(sessionFactoryMap.containsKey(dbName)){
			SqlSession session = sessionFactoryMap.get(dbName).openSession(autoCommit);
			return new SqlMapClient(session, dbName, autoCommit);
		}
		else{
			SqlSessionFactory factory = initSessionFactory(dbName);
			if(factory != null){
				SqlSession session = factory.openSession(autoCommit);
				return new SqlMapClient(session, dbName, autoCommit);
			}
			return null;
		}
	}
	
	/**
	 * 获取一个新的数据库操作对象
	 * @param dbName 数据库名称
	 * @param executorType 处理类型: ExecutorType.BATCH、ExecutorType.SIMPLE、ExecutorType.REUSE
	 * @param autoCommit 事务是否自动提交
	 * @return 数据库操作对象
	 */
	public static SqlMapClient getSqlMapClient(String dbName, ExecutorType executorType, boolean autoCommit){
		if(sessionFactoryMap.containsKey(dbName)){
			SqlSession session = sessionFactoryMap.get(dbName).openSession(executorType, autoCommit);
			return new SqlMapClient(session, dbName, autoCommit);
		}
		else{
			SqlSessionFactory factory = initSessionFactory(dbName);
			if(factory != null){
				SqlSession session = factory.openSession(executorType, autoCommit);
				return new SqlMapClient(session, dbName, autoCommit);
			}
			return null;
		}
	}
	
	/**
	 * 初始化SqlSession工厂类
	 * @param dbName 数据库名称
	 * @return SqlSessionFactory
	 */
	private static synchronized SqlSessionFactory initSessionFactory(String dbName){
		InputStream inputStream = SqlMapClientFactory.class.getClass().getResourceAsStream(mybatisConfigPath);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, dbName);
		if(sqlSessionFactory == null){
			log.error("create sqlSessionFactory failure! environment = " + dbName);
			return null;
		}
		else{
			sessionFactoryMap.put(dbName, sqlSessionFactory);
			return sqlSessionFactory;
		}
	}
	
	/**
	 * 获取数据库会话工厂类
	 * @param dbName 数据库名称
	 * @return
	 */
	public static SqlSessionFactory getSessionFactory(String dbName){
		if(sessionFactoryMap.containsKey(dbName)){
			return sessionFactoryMap.get(dbName);
		}
		else{
			return initSessionFactory(dbName);
		}
	}
}
