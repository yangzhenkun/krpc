package com.krpc.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("有连接进来");
		
		byte[] res = "world".getBytes();
		
//		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		ByteBuf time = ctx.alloc().buffer(res.length);
		time.writeBytes(res);
		ctx.writeAndFlush(time);
		
		/*final ChannelFuture f = 
		f.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture cf) throws Exception {

				assert f == cf;
				ctx.close();

			}
		});*/

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
