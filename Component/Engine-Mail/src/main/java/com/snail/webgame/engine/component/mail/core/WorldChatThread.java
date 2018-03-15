package com.snail.webgame.engine.component.mail.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
public class WorldChatThread {
	
    private static WorldChatThread thisInstance;
    
    
	private static ThreadPoolExecutor threadPool1;
	private static ThreadPoolExecutor threadPool2;
	private static ThreadPoolExecutor threadPool3;
	private static int queueSize1 = 2000;
	private static int queueSize2 = 2000;
	private static int queueSize3 = 2000;
	private WorldChatThread()
	{
		threadPool1 =new ThreadPoolExecutor(1,
				1,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(queueSize1), 
				new ThreadPoolExecutor.CallerRunsPolicy());
		
		threadPool2 =new ThreadPoolExecutor(1,
				1,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(queueSize2), 
				new ThreadPoolExecutor.CallerRunsPolicy());
		threadPool3 =new ThreadPoolExecutor(1,
				60,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(queueSize3), 
				new ThreadPoolExecutor.CallerRunsPolicy());

	
	
	
	}
	
    public static synchronized WorldChatThread getInstance () {
    	
    	if(thisInstance==null)
    	{
    		thisInstance = new WorldChatThread();
    	}
        return thisInstance;
    }
	
	public  boolean isCanAdd1()
	{
		
		if(queueSize1>threadPool1.getQueue().size())
		{
			return true;
		}
		return false;
	}
	public  boolean isCanAdd2()
	{
		
		if(queueSize2>threadPool2.getQueue().size())
		{
			return true;
		}
		return false;
	}
	
	public  boolean isCanAdd3()
	{
		
		if(queueSize3>threadPool3.getQueue().size())
		{
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 单线程执行世界聊天
	 * @param runnable
	 */
	public  void run1(Runnable runnable)
	{
		threadPool1.execute(runnable);
	}
	/**
	 * 单线程执行喇叭GM
	 * @param runnable
	 */
	public  void run2(Runnable runnable)
	{
		threadPool2.execute(runnable);
	}
	
	public  void run3(Runnable runnable)
	{
		threadPool3.execute(runnable);
	}
}
