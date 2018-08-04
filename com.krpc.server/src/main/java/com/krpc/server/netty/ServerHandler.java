package com.krpc.server.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.common.serializer.HessianUtil;
import com.krpc.common.util.CompressUtil;
import com.krpc.common.util.ContextUtil;
import com.krpc.server.core.RequestHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty服务端收发数据
 * 
 * @author yangzhenkun
 *
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	private Logger log = LoggerFactory.getLogger(ServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		byte[] bytes = (byte[])msg;
		log.debug("接受大小:" + bytes.length);
		
		Integer sessionID = ContextUtil.getSessionID(bytes);
		log.debug("接受sessionID:{}",sessionID);
		byte[] bytesrc = CompressUtil.uncompress(ContextUtil.getBody(bytes));

		byte[] responseBytes = ContextUtil.mergeSessionID(sessionID, CompressUtil.compress(RequestHandler.handler(bytesrc)));
		
		log.debug("服务端返回大小:" + responseBytes.length);

		ctx.writeAndFlush(responseBytes);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
