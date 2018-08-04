package com.krpc.client.pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * KRPC客户端用于通信的Socket
 * 
 * @author yangzhenkun
 *
 */
public class KRPCSocket {

	private Socket socket;
	private SocketChannel channel;
	private ByteBuffer sendBuffer;
	private ByteBuffer receiverBuffer;

	private DataReceiverHandler dataReceiverHandler;
	
	private Object sendLockHelper = new Object();
	private Object receiverLockHelper = new Object();

	private AtomicInteger sessionId = new AtomicInteger(0);

	private ConcurrentHashMap<Integer,ReceiverData> receiverDataWindow = new ConcurrentHashMap<Integer, ReceiverData>();

	public KRPCSocket(String host, int port) throws IOException {
		channel = SocketChannel.open(new InetSocketAddress(host, port));
		socket = channel.socket();
		socket.setSendBufferSize(1024);
		socket.setReceiveBufferSize(1024);
		
		socket.setTcpNoDelay(true);
		
		sendBuffer = ByteBuffer.allocate(1024);
		receiverBuffer = ByteBuffer.allocate(1024);
		
		dataReceiverHandler = new DataReceiverHandler(this);
	}

	public int send(byte[] data) throws IOException {

		synchronized (sendLockHelper) {
			int count = 0;
			sendBuffer.clear();
			sendBuffer.put(data);
			sendBuffer.flip();
			while (sendBuffer.hasRemaining()) {
				count += channel.write(sendBuffer);
			}

			return count;
		}

	}

	protected void receiver() throws IOException {

		synchronized (receiverLockHelper) {

			receiverBuffer.clear();
			int count = channel.read(receiverBuffer);
			byte[] responseBytes = new byte[count];

			receiverBuffer.get(responseBytes, 0, count);
			
			System.out.println("客户端接受:"+responseBytes.length);
		}

	}

	private int createSessionID(){

		if(sessionId.get()==1073741824){//1024^3
			sessionId.compareAndSet(1073741824, 0);
		}

		return sessionId.getAndIncrement();
	}

}
