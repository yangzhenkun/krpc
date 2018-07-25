package com.krpc.server.netty;

import java.io.IOException;

import com.a123.service.user.entity.User;
import com.krpc.common.serializer.HessianUtil;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		
		byte[] resp =  (byte[])msg;
		try {
//			byte[] resp = obj.getBytes(CharsetUtil.UTF_8);
			User user = (User) HessianUtil.deserialize(resp, null);
//			System.out.println("服务端接受数据长度"+resp.length);
			System.out.println("服务端接受数据" + user);
//			System.out.println(obj.length);''
			ctx.write(resp); // (1)
			ctx.flush(); // (2)
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)

		// Close theconnection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	public void channelActive(final ChannelHandlerContext ctx) {
//		ctx.writeAndFlush("有客户端连接"); // (3)
	}
}
