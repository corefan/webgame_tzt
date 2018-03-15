package com.snail.webgame.engine.component.login.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.common.ByteBuffer;
import org.epilot.ccf.core.util.AbstractStringHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlashHandleStr extends AbstractStringHandle{

	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	
	public String decodeStringB(ByteBuffer buffer) {
	
		Charset charset = Charset.forName("UTF-8"); 
		CharsetDecoder decoder =  charset.newDecoder();
		try {
			if(buffer.remaining()>2)
			{
				String str = buffer.getPrefixedString(2, decoder);
				return str;
			}
			else
			{
				return  "";
			}
			
		} catch (Exception e) {
			if(logger.isErrorEnabled())
			{
				logger.error("",e);
			}
			return "";	
		}
	}

	
	public String decodeStringL(ByteBuffer buffer) {
		
		Charset charset = Charset.forName("UTF-8"); 
		CharsetDecoder decoder =  charset.newDecoder();
		try {
			if(buffer.remaining()>2)
			{
				String str = buffer.getPrefixedString(2, decoder);
				return str;
			}
			else
			{
				return  "";
			}
		} catch (Exception e) {
			if(logger.isErrorEnabled())
			{
				logger.error("",e);
			}
			return "";	
		}
	}


	public byte[] encodeStringB(String str) {
		 
		if(str!=null&&str.length()>0)
		{
			byte [] m = null ;
			try {
				m = str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				 
				if(logger.isErrorEnabled())
				{
					logger.error("",e);
				}
			}
			
			if(m!=null&&m.length>0)
			{
				byte n []  =new byte[m.length+2];
				
				System.arraycopy(m, 0, n,2, m.length);
				byte k[] = shortToBytes((short) m.length);
				System.arraycopy(k, 0, n,0, 2);
				
				return n;
			}
			
		}
		return shortToBytes((short) 0);
	}

	
	public byte[] encodeStringL(String str) {

		if(str!=null&&str.length()>0)
		{
			byte [] m = null ;
			try {
				m = str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				 
				if(logger.isErrorEnabled())
				{
					logger.error("",e);
				}
			}
			
			if(m!=null&&m.length>0)
			{
				byte n []  =new byte[m.length+2];
				
				System.arraycopy(m, 0, n,2, m.length);
				byte k[] = shortToBytes((short) m.length);
				System.arraycopy(k, 0, n,0, 2);
				
				return n;
			}
			
		}
		return shortToBytes((short) 0);
	}
	
	public  byte[] shortToBytes(short sNum){
	    byte[] bytesRet = new byte[2];
	    bytesRet[0] = (byte) ((sNum >> 8) & 0xFF);
	    bytesRet[1] = (byte) (sNum & 0xFF);
	    return bytesRet;
	}
}
