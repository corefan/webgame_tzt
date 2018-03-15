package com.snail.webgame.engine.gate.receive.manage;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.DefaultIoFilterChainBuilder;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoAcceptorConfig;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.common.ThreadModel;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.gate.config.WebGameConfig;
import com.snail.webgame.engine.gate.receive.code.ReceiveCodeFactory;
import com.snail.webgame.engine.gate.util.ExecutorFilterSend;


public class Listener {

	private static final Logger log=LoggerFactory.getLogger("logs");

	public static boolean listener(IoHandlerAdapter handlerAdapter)
	{
        IoAcceptor acceptor = new SocketAcceptor();
        IoAcceptorConfig config = new SocketAcceptorConfig();
        config.setThreadModel(ThreadModel.MANUAL);
        DefaultIoFilterChainBuilder chain = config.getFilterChain();
        ByteBuffer.setUseDirectBuffers(false);
		ByteBuffer.setAllocator(new SimpleByteBufferAllocator());
		
        chain.addLast( "codec", new ProtocolCodecFilter( 
			       new ReceiveCodeFactory()));
    	chain.addLast("threadPool1", 
				new ExecutorFilter(Executors.newCachedThreadPool()));
    	
    	chain.addLast("threadPool2", 
				new ExecutorFilterSend(Executors.newCachedThreadPool()));
    	
    	String localIP = WebGameConfig.getInstance().getLocalConfig().getLocalIP();
    	String localPortStr = WebGameConfig.getInstance().getLocalConfig().getLocalPort();
  
    	String str [] = localPortStr.split(",");
    	
    	for(int i=0;i<str.length;i++)
    	{
    	 
    		try 
    		{
    			acceptor.bind(new InetSocketAddress(localIP,Integer.valueOf(str[i])), 
    					handlerAdapter, config);
    			if(log.isInfoEnabled())
    			{
    				log.info("GateServer listener start success(IP:"+localIP+",Port:"+str[i]+")!");
    			}
    		}
    		catch (Exception e) {
    			if(log.isErrorEnabled())
    			{
    				log.error("GateServer listener start failure(IP:"+localIP+",Port:"+str[i]+")!", e);
    			}
    		}
    	}
    	return true;

	}
	
}
