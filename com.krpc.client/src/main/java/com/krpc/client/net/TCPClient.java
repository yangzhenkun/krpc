package com.krpc.client.net;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.client.core.RequestHandler;

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
	
	private static Bootstrap bootstrap;

	static {
		bootstrap = getBootstrap();
	}
	
	private Channel channel;

	/**
	 * 初始化Bootstrap
	 * 
	 * @return
	 */
	public static Bootstrap getBootstrap() {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipeline.addLast("decoder", new ByteArrayDecoder());
				pipeline.addLast("encoder", new ByteArrayEncoder());
				pipeline.addLast("handler", new TcpClientHandler());
			}
		});
		return b;
	}
	
	
	public TCPClient(String host,Integer port){
		this.channel = getChannel(host, port);
	}

	private Channel getChannel(String host, int port) {
		try {
			channel = bootstrap.connect(host, port).sync().channel();
		} catch (Exception e) {
			log.error("连接Server(IP{},PORT{})失败", host, port);
			return null;
		}
		return channel;
	}

	public void sendMsg(byte[] msg) throws Exception {
		if (channel != null) {
			channel.writeAndFlush(msg).sync();
		} else {
			log.error("消息发送失败,连接尚未建立!");
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
	
	
	private Integer createSessionID() {

		if (sessionId.get() == 1073741824) {// 1024^3
			sessionId.compareAndSet(1073741824, 0);
		}

		return sessionId.getAndIncrement();
	}

}
