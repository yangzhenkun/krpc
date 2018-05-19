package com.krpc.server.netty;

import com.krpc.server.core.RequestHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		ByteBuf buf = (ByteBuf)msg;  
        byte[] bytes = new byte[buf.readableBytes()];  
        buf.readBytes(bytes);  
        
        byte[] responseBytes = RequestHandler.handler(bytes);
        ByteBuf resbuf = ctx.alloc().buffer(responseBytes.length);
        resbuf.writeBytes(responseBytes);
		ctx.writeAndFlush(resbuf);
        
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	
	
	
	
}
