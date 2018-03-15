package com.snail.webgame.engine.gate.receive.code;



import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.gate.config.WebGameConfig;
import com.snail.webgame.engine.gate.util.MessageServiceManage;




public class RevRequestEncoder implements ProtocolEncoder {

	private static final Logger log =LoggerFactory.getLogger("logs");
	public void dispose(IoSession session) throws Exception {
		
	
	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		
		
		if(message instanceof String)
		{
			String msg = (String) message;
			byte b [] = msg.getBytes("UTF-8");
			ByteBuffer buffer = ByteBuffer.allocate(16,false);
			buffer.setAutoExpand(true);
			buffer.put(b);
			buffer.put((byte) 0x00);
			buffer.flip();
			out.write(buffer);
		}
		else if(message instanceof byte[])
		{
			 
		 
				ByteBuffer buffer = ByteBuffer.allocate(16,false);
				 
				buffer.setAutoExpand( true );
				
				int encryptType =  WebGameConfig.getInstance().getEncryptType();
				
				if(encryptType>0)
				{
					String name = "";
					
					if(encryptType==1)
					{
						name ="DES";
					}
					else if(encryptType==2)
					{
						name ="BlowFish";
					}
					else if(encryptType==3)
					{
						name ="DESede";
					}
					else if(encryptType==4)
					{
						name ="aes";
					}
					byte b[] = MessageServiceManage.getEncryptMessage(name,  (byte[]) message);
					
					if(b==null)
					{
						if(log.isWarnEnabled())
						{
							 log.warn("System  occure encrypt error!");
						}
						return;
					}
					byte [] lenghtMss = MessageServiceManage.int2bytes(b.length);
					
					
					byte newMessage [] = new byte[b.length+4];
					
					System.arraycopy(b, 0, newMessage, 4, b.length);
					System.arraycopy(lenghtMss, 0, newMessage, 0, lenghtMss.length);
					
					b = null;
					lenghtMss = null;
					message = null;
					
					buffer.put(newMessage);
					buffer.flip();
					out.write(buffer);
				}
				else
				{
					buffer.put((byte[]) message);
					buffer.flip();
					out.write(buffer);
				}
			 
		}
	}

}
