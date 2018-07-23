package com.krpc.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.io.IOUtils;

@Deprecated
public class TCPClient {
	
	
	
	public static byte[] send(byte[] sendData,String host,int port,int timeout) throws UnknownHostException, IOException {
		/*
		SocketChannel channel = SocketChannel.open();
		channel.connect(new InetSocketAddress(host, 17666));
		
		ByteBuffer bf = ByteBuffer.wrap(sendData);
		channel.write(bf);
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		byte[] res = null;
		
		while(buffer.hasRemaining()){
			
			channel.read(buffer);
			res = new byte[buffer.remaining()];
			buffer.get(res,0,buffer.remaining());
		}
		
		System.out.println("客户端接受到:"+res.length);
		return res;
		*/
		Socket socket = new Socket(host,port);
		socket.setSoTimeout(timeout);
		socket.setTcpNoDelay(true);
		
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();
		byte resultArray[] = null;
		try {
			os.write(sendData);
			os.flush();
			socket.shutdownOutput();
			resultArray = IOUtils.toByteArray(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
			is.close();
			socket.close();
		}
		return resultArray;
	}

}
