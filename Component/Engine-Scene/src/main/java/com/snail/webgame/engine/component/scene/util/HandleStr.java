package com.snail.webgame.engine.component.scene.util;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.common.ByteBuffer;
import org.epilot.ccf.core.util.AbstractStringHandle;

public class HandleStr extends AbstractStringHandle{


	public String decodeStringB(ByteBuffer buffer) {
	
		Charset charset = Charset.forName("GB2312"); 
		CharsetDecoder decoder =  charset.newDecoder();
		try {
			String str = buffer.getString(decoder);
			return str;
		} catch (CharacterCodingException e) {
			e.printStackTrace();
			return null;	
		}
	}

	
	public String decodeStringL(ByteBuffer buffer) {
	
		Charset charset = Charset.forName("GB2312");//little endian  
		CharsetDecoder decoder =  charset.newDecoder();
		try {
			String str = buffer.getString(decoder);
			return str;
		} catch (CharacterCodingException e) {
			e.printStackTrace();
			return null;	
		}
	}


	public byte[] encodeStringB(String str) {
	

		byte[] c = null;
		if(str==null||str.length()==0)
		{
			c =new byte [1];
			return c;
		}
		try {
			
			byte b[] = str.getBytes("GB2312");
			c =new byte [b.length+1];
			System.arraycopy(b, 0, c, 0, b.length);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;	
		}
	}

	
	public byte[] encodeStringL(String str) {

		byte[] c = null;
		if(str==null||str.length()==0)
		{
			c =new byte [1];
			return c;
		}
		try {
			byte b[] = str.getBytes("GB2312");
			c =new byte [b.length+1];
			System.arraycopy(b, 0, c, 0, b.length);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;	
		}

	}

}
