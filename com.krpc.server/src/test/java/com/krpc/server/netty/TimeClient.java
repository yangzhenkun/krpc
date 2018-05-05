package com.krpc.server.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	
	
	public static void main(String[] args) throws InterruptedException{
		
		
		String host = "127.0.0.1";
		int port = 17999;
		
		EventLoopGroup workerGoup = new NioEventLoopGroup();
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(workerGoup)
		.channel(NioSocketChannel.class);
		
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new TimeClientHandler());
				
			}
		});
		
		ChannelFuture f = bootstrap.connect(host,port).sync();
		f.channel().closeFuture().sync();		
		
	}

}
