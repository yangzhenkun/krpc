package com.krpc.server;


import java.io.File;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.common.protocal.ProtocalConst;
import com.krpc.server.core.LoadConfigure;
import com.krpc.server.entity.Global;
import com.krpc.server.netty.ServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 启动器
 * 
 * @author yangzhenkun01
 *
 */
public class BootStrap {

	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				// 初始化项目路径
				String serviceName = args[0];
				if(args.length==2) {
					Global.getInstance().setPort(Integer.parseInt(args[1]));
				}
				
				Global.getInstance().setServiceName(serviceName);
				String userDir = System.getProperty("user.dir");
				String rootPath = userDir + File.separator + ".." + File.separator;
				String rootLibPath = userDir + File.separator + "lib";

				String serviceRootPath = rootPath + "service" + File.separator + serviceName + File.separator;

				// 初始化log4j
				DOMConfigurator.configure(serviceRootPath + File.separator + "conf" + File.separator + "log4j.xml");
				Logger log = LoggerFactory.getLogger(BootStrap.class);

				// 加载配置文件，并初始化相关内容
				LoadConfigure.load(serviceRootPath);

				// 启动netty server
				EventLoopGroup bossGroup = new NioEventLoopGroup();
				EventLoopGroup workerGroup = new NioEventLoopGroup();
				ServerBootstrap bootstrap = new ServerBootstrap();

				ByteBuf buf = Unpooled.copiedBuffer(ProtocalConst.P_END_TAG);
				
				
				bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
						.childHandler(new ChannelInitializer<SocketChannel>() {

							@Override
							protected void initChannel(SocketChannel ch) throws Exception {
								//解决粘包问题
//								ch.pipeline().addLast(new LengthFieldPrepender(3));
								ch.pipeline().addLast(new ServerHandler());
							}
						}).option(ChannelOption.SO_BACKLOG, 128)
						.childOption(ChannelOption.SO_KEEPALIVE, true)
						.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64,Global.getInstance().getMaxBuf(),Global.getInstance().getMaxBuf()));
				
				
				ChannelFuture f = bootstrap.bind(Global.getInstance().getPort()).sync(); 
				log.info("启动成功,监听端口:"+Global.getInstance().getPort());
				f.channel().closeFuture().sync();
				
			} else {
				System.out.println("请输入启动的服务名字");
			}
			
		} catch (Exception e) {
			System.out.println("启动失败");
			e.printStackTrace();
		}

	}

}
