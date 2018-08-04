package com.krpc.client.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.client.core.RequestHandler;
import com.krpc.common.util.ContextUtil;

import java.util.Map;
import java.util.Objects;

/**
 * KRPC客户端用于通信的Socket
 * NIO方式
 * 已过时改由netty实现
 * 
 * @author yangzhenkun
 *
 */
@Deprecated
public class KRPCSocket {

	private Logger log = LoggerFactory.getLogger(KRPCSocket.class);

	private Socket socket;
	private SocketChannel channel;
	private ByteBuffer sendBuffer;
	private ByteBuffer receiverBuffer;

	private DataReceiverHandler dataReceiverHandler;

	private Object sendLockHelper = new Object();
	private Object receiverLockHelper = new Object();

	private AtomicInteger sessionId = new AtomicInteger(0);

	private Map<Integer, ReceiverData> receiverDataWindow = new ConcurrentHashMap<Integer, ReceiverData>();
	private ByteArrayOutputStream receiveData = new ByteArrayOutputStream();

	public KRPCSocket(String host, int port) throws IOException {
		channel = SocketChannel.open(new InetSocketAddress(host, port));
		socket = channel.socket();
		socket.setSendBufferSize(1024);
		socket.setReceiveBufferSize(1024);

		// socket.setTcpNoDelay(true);

		sendBuffer = ByteBuffer.allocate(1024);
		receiverBuffer = ByteBuffer.allocate(1024);

		dataReceiverHandler = new DataReceiverHandler(this);
	}

	/**
	 * tcp数据发送接口
	 * 
	 * @param data
	 * @return sessionID
	 * @throws IOException
	 */
	public int send(byte[] data) throws Exception {
		Integer sessionID = createSessionID();
		byte[] sendData = ContextUtil.mergeSessionID(sessionID, data);

		synchronized (sendLockHelper) {
			try {
				ReceiverData receiverData = new ReceiverData();
				receiverDataWindow.put(sessionID, receiverData);
				sendBuffer.clear();
				sendBuffer.put(sendData);
				sendBuffer.flip();
				while (sendBuffer.hasRemaining()) {
					channel.write(sendBuffer);
				}
			} catch (Exception e) {
				log.error("send error!",e);
				receiverDataWindow.remove(sessionID);
			}

			return sessionID;
		}

	}

	/**
	 * 获取返回数据接口
	 * 
	 * @return
	 */
	public byte[] getData(int sessionId, long timeout) throws Exception {

		ReceiverData receiverData = receiverDataWindow.get(sessionId);
		if (Objects.isNull(receiverData)) {
			throw new Exception("未从等待窗口中取到数据");
		}
		byte[] respData = receiverData.getData(timeout);
		if (Objects.isNull(respData)) {
			throw new Exception("获取数据超时...");
		}
		receiverDataWindow.remove(sessionId);

		return respData;
	}

	/**
	 * 接口数据方法
	 * 
	 * @throws Exception
	 */
	protected void receiver() throws Exception {

		synchronized (receiverLockHelper) {

			receiverBuffer.clear();
			int count = channel.read(receiverBuffer);
			receiverBuffer.flip();
			byte[] responseBytes = new byte[count];
			receiverBuffer.get(responseBytes, 0, count);
			log.debug("客户端接受:{}", responseBytes.length);

			Integer sessionID = ContextUtil.getSessionID(responseBytes);
			log.debug("客户端收到的sessionID:{}", sessionID);

			ReceiverData receiverData = receiverDataWindow.get(sessionID);
			if (Objects.isNull(receiverData)) {
				throw new Exception("wait window no data");
			}
			receiverData.setData(ContextUtil.getBody(responseBytes));
		}

	}

	private Integer createSessionID() {

		if (sessionId.get() == 1073741824) {// 1024^3
			sessionId.compareAndSet(1073741824, 0);
		}

		return sessionId.getAndIncrement();
	}

}
