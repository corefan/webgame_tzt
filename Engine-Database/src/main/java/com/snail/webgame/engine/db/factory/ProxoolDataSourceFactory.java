package com.snail.webgame.engine.db.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.logicalcobwebs.proxool.ProxoolDataSource;

/**
 * 数据源工厂类
 * @author zenggy
 *
 */
public class ProxoolDataSourceFactory implements DataSourceFactory {

	private ProxoolDataSource dataSource;
	public DataSource getDataSource() {
		
		return dataSource;
	}

	/**
	 * 创建数据源对象
	 */
	public void setProperties(Properties p) {
		dataSource = new ProxoolDataSource();
		
		Method[] methods = dataSource.getClass().getMethods();
		
		for(Method method : methods){
			String methodName = method.getName();
			if(methodName.startsWith("set")){
				String propertyName = methodName.substring(3, methodName.length());
				char firstChar = propertyName.charAt(0);
				propertyName = propertyName.replaceFirst(String.valueOf(firstChar), String.valueOf((char)(firstChar + 32)));
				String value = p.getProperty(propertyName);
				
				if(value != null && !"".equals(value)){
					Class parameterType = method.getParameterTypes()[0];
					Object parameter = value;
					if(parameterType.getName().equals("int")){
						parameter = Integer.parseInt(value);
					}
					else if(parameterType.getName().equals("long")){
						parameter = Long.parseLong(value);
					}
					else if(parameterType.getName().equals("boolean")){
						parameter = Boolean.parseBoolean(value);
					}
					else if(parameterType.getName().equals("double")){
						parameter = Double.parseDouble(value);
					}
					else if(parameterType.getName().equals("float")){
						parameter = Float.parseFloat(value);
					}
					else if(parameterType.getName().equals("short")){
						parameter = Short.parseShort(value);
					}
					else if(parameterType.getName().equals("byte")){
						parameter = Byte.parseByte(value);
					}
					
					try {
						method.invoke(dataSource, new Object[]{parameter});
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
