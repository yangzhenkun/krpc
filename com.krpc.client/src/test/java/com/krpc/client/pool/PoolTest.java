package com.krpc.client.pool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.krpc.common.entity.Request;
import com.krpc.common.serializer.SerializeUtil;
import com.krpc.common.util.CompressUtil;
import com.krpc.common.util.FileUtil;

import org.apache.commons.io.IOUtils;

import com.krpc.common.serializer.HessianUtil;
import org.junit.Test;

public class PoolTest {

	@Test
	public void receiverTest(){

		try {
			byte[] data = FileUtil.read("D:/byte.txt");
			byte[] sumData = new byte[data.length*2];
			int length = data.length;

			for(int i=0;i<data.length;i++){
				sumData[i]=data[i];
				sumData[i+length]=data[i];
			}


			Request request = (Request) HessianUtil.deserialize(CompressUtil.uncompress(sumData),null);

			System.out.println(request.getMethodName());


		} catch (Exception e) {
			e.printStackTrace();
		}


	}



	@Test
	public void channelTest(){
		
		try {
//			byte[] data = FileUtil.read("D:/byte.txt");
			
			Integer i = 10;
			byte[] data = HessianUtil.serialize(i);
			
			KRPCSocket socket = new KRPCSocket("127.0.0.1", 17666);
			
			System.out.println("发送了:"+socket.send(data));
			System.out.println("发送了:"+socket.send(data));
			

			Thread.sleep(1000*60*5);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	@Test
	public void test(){
		
		try {
			byte[] data = FileUtil.read("D:/byte.txt");//压缩后的数据
			
			Socket socket = new Socket("127.0.0.1",17666);
			
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			byte resultArray[] = null;
			try {
				os.write(data);
				os.flush();
				socket.shutdownOutput();
				resultArray = IOUtils.toByteArray(is);
				System.out.println(resultArray.length);
				
				os = socket.getOutputStream();
				os.write(data);
				os.flush();
				socket.shutdownOutput();
				resultArray = IOUtils.toByteArray(is);
				
				System.out.println(resultArray.length);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				os.close();
				is.close();
				socket.close();
			}
			
			
			Thread.sleep(1000*60*10);
			
			
//			TCPClient.send(data, "127.0.0.1",17666, 10_000);//返回的也是压缩后的数据
			
//			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void int2Byte() throws IOException{
		System.out.println(Integer.MAX_VALUE);
		int i = 1;
		long l = 1L;
		byte[] bs = HessianUtil.serialize(i);
		
		for(byte b:bs){
			System.out.println(b);
		}
		
		
	}

}
