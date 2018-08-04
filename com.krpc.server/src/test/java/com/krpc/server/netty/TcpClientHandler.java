package com.krpc.server.netty;

import com.krpc.common.serializer.HessianUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class TcpClientHandler extends ChannelInboundHandlerAdapter {
	public TcpClientHandler() {
	}

	// private byte[] req;
	/**
	 * 链路链接成功
	 */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		/*
		 * for (int i = 0; i < 1000; i++) { ctx.writeAndFlush("1ac"); }
		 */
		// 链接成功后发送
		System.out.println("连接成功!!");
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
//		String msgStr = (String) msg;
		byte[] data = (byte[]) msg;
//		byte[] data = msgStr.getBytes(CharsetUtil.UTF_8);
//		System.out.println("client收到byte长度"+data.length);
		// ctx.write("收到数据!");
		// ctx.write(msg);
		// ctx.write("w2d");

	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
