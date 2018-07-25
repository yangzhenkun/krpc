package com.krpc.server.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.a123.service.user.entity.User;
import com.krpc.common.serializer.HessianUtil;
import com.krpc.server.BootStrap;


public class TCPClient {

	public static String HOST = "127.0.0.1";
	public static int PORT = 9999;

	public static Bootstrap bootstrap = getBootstrap();
	public static Channel channel = getChannel(HOST, PORT);

	/**
	 * 初始化Bootstrap
	 * 
	 * @return
	 */
	public static final Bootstrap getBootstrap() {
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

	public static final Channel getChannel(String host, int port) {
		Channel channel = null;
		try {
			channel = bootstrap.connect(host, port).sync().channel();
		} catch (Exception e) {
			System.out.println(String.format("连接Server(IP[%s],PORT[%s])失败", host, port));
			return null;
		}
		return channel;
	}

	public static void sendMsg(byte[] msg) throws Exception {
		if (channel != null) {
			channel.writeAndFlush(msg).sync();
		} else {
			System.out.println("消息发送失败,连接尚未建立!");
		}
	}

	public static void main(String[] args) throws Exception {
		DOMConfigurator.configure("C:/Users/yangzhenkun01/Desktop/krpc/client/log4j.xml");
		Logger log = LoggerFactory.getLogger(TCPClient.class);
		
		Executor pool = Executors.newFixedThreadPool(10);
		final CountDownLatch count = new CountDownLatch(1000);
		for (int i = 0; i < 1000; i++) {

			pool.execute(new Task(i));

		}
		
		count.await();
		System.out.println("run done");

	}

	public static class Task implements Runnable {

		int i;

		Task(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			try {
				
				List<User> data = new ArrayList<>();
				
//				for(int i=0;i<10;i++){
//					User user = new User();
//					user.setId(i);
//					user.setBirthDay(new Date());
//					user.setName("杨震坤"+i);
//					user.setPhone(1881057L);
//					
//					data.add(user);
//				}
				int i= 1;
				User user = new User();
				user.setId(i);
				user.setBirthDay(new Date());
				user.setName("杨震坤"+i);
				user.setPhone(1881057L);
				
				data.add(user);
				
				
				
				Long phone = new Long(18810572463L);
				
				byte[] res = HessianUtil.serialize(user);

//				String sendMsg = new String(res,CharsetUtil.UTF_8);
				
				String sendMsg = String.valueOf(i);
				
				
				System.out.println("原数据长度:"+res.length);
				
				TCPClient.sendMsg(res);
				
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

}
