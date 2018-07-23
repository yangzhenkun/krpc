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

		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		log.debug("接受大小:" + bytes.length + ":::::" + bytes[8]);
		
		Integer sessionID = ContextUtil.getSessionID(bytes);
		log.debug("接受sessionID:{}",sessionID);
		byte[] bytesrc = CompressUtil.uncompress(ContextUtil.getBody(bytes));

		byte[] responseBytes = ContextUtil.mergeSessionID(sessionID, CompressUtil.compress(RequestHandler.handler(bytesrc)));
		
		log.debug("服务端返回大小:" + responseBytes.length);

		ByteBuf resbuf = ctx.alloc().buffer(responseBytes.length);
		resbuf.writeBytes(responseBytes);
		ctx.writeAndFlush(resbuf);
		buf.release();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
