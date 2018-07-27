package com.krpc.client.net;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.client.core.RequestHandler;
import com.krpc.common.util.ContextUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

public class TCPClient {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private AtomicInteger sessionId = new AtomicInteger(0);

	private Map<Integer, ReceiverData> receiverDataWindow = new ConcurrentHashMap<Integer, ReceiverData>();
	
	private  Bootstrap bootstrap;

	private Channel channel;
	
	private Integer timeout;

	/**
	 * 初始化Bootstrap
	 * 
	 * @return
	 */
	public  Bootstrap getBootstrap() {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		TcpClientHandler tcpClientHandler = new TcpClientHandler(TCPClient.this);
		b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipeline.addLast("decoder", new ByteArrayDecoder());
				pipeline.addLast("encoder", new ByteArrayEncoder());
				pipeline.addLast("handler", tcpClientHandler);
			}
		});
		return b;
	}
	
	
	public TCPClient(String host,Integer port,Integer timeout){
		this.channel = getChannel(host, port);
		this.timeout=timeout;
		
	}

	private Channel getChannel(String host, int port) {
		try {
			bootstrap = getBootstrap();
			channel = bootstrap.connect(host, port).sync().channel();
		} catch (Exception e) {
			log.error("连接Server(IP{},PORT{})失败", host, port);
			return null;
		}
		return channel;
	}

	public Integer sendMsg(byte[] msg) throws Exception {
		if (channel != null) {
			Integer sessionID = createSessionID();
			byte[] sendData = ContextUtil.mergeSessionID(sessionID, msg);
			ReceiverData receiverData = new ReceiverData();
			receiverDataWindow.put(sessionID, receiverData);
			channel.writeAndFlush(sendData).sync();
			return sessionID;
		} else {
			log.error("消息发送失败,连接尚未建立!");
			return null;
		}
	}

	/**
	 * 获取返回数据接口
	 * 
	 * @return
	 */
	public byte[] getData(int sessionId) throws Exception {

		ReceiverData receiverData = receiverDataWindow.get(sessionId);
		if (Objects.isNull(receiverData)) {
			throw new Exception("get data waitwindow no revice data!id:"+sessionId)  ;
		}
		byte[] respData = receiverData.getData(this.timeout);
		if (Objects.isNull(respData)) {
			throw new Exception("");
		}
		receiverDataWindow.remove(sessionId);

		return respData;
	}
	
	protected void receiver(byte[] data) {
		
		try {
			int currentSessionID = ContextUtil.getSessionID(data);
			ReceiverData receiverData = receiverDataWindow.get(currentSessionID);
			if(Objects.isNull(receiverData)) {
				log.error("revice data waitwindow no reciever data!id:{}",currentSessionID);
			}
			receiverData.setData(ContextUtil.getBody(data));
			
		} catch (Exception e) {
			log.error("receiver data error!",e);
		}
		
	}
	
	private Integer createSessionID() {

		if (sessionId.get() == 1073741824) {// 1024^3
			sessionId.compareAndSet(1073741824, 0);
		}

		return sessionId.getAndIncrement();
	}

}
