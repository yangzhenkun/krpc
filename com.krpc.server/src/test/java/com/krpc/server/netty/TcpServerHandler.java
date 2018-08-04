package com.krpc.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) { 
		
		byte[] resp =  (byte[])msg;
		
		

	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)

		cause.printStackTrace();
		ctx.close();
	}

	public void channelActive(final ChannelHandlerContext ctx) {
	}
}
