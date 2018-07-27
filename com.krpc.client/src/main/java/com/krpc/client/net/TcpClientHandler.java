package com.krpc.client.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;

public class TcpClientHandler extends ChannelInboundHandlerAdapter {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	
	public TcpClientHandler() {
	}

	// private byte[] req;
	/**
	 * 链路链接成功
	 */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.debug("连接成功!");
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		byte[] data = (byte[]) msg;

	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
