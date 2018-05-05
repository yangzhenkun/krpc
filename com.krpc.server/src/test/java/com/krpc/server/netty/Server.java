package com.krpc.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	public static void main(String[] args) throws Exception {

		EventLoopGroup bossGroup = new NioEventLoopGroup();// 1
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap();// 2

		bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)// 3
				.childHandler(new ChannelInitializer<SocketChannel>() {// 4

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new TimeServerHandler());

					}
				}).option(ChannelOption.SO_BACKLOG, 128)// 5
				.childOption(ChannelOption.SO_KEEPALIVE, true);// 6

		ChannelFuture f = bootstrap.bind(17999).sync(); // (7)

		f.channel().closeFuture().sync();
	}

}
