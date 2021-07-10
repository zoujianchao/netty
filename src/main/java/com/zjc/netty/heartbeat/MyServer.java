package com.zjc.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author : zoujc
 * @date : 2021/7/11
 * @description : netty心跳检测
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        //创建两个线程组
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();//默认8个NioEventLoop
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) //增加bossGroup日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //加入netty提供的IdleStateHandler
                            /**
                             * 1.IdleStateHandler 是netty提高平的处理空闲状态的处理器
                             * 2.readerIdleTime : 表示多久没有读,server没有读client,就会发送一个心跳检测包,检测是否连接
                             * 3.writerIdleTime : 表示有多久没有写操作,server没有写client,就会发送一个心跳检测包,检测是否连接
                             * 4.allIdleTime : 表示有多久没有读写操作,server没有读写client,就会发送一个心跳检测包,检测是否连接
                             * 5.当 IdleStateHandler 触发后,就会传递给管道的下一个handler处理,
                             * 通过回调触发下一个handler的userEventTriggered方法中处理IdleStateEvent(读空闲,写空闲,读写空闲)
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 8, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的handler(自定义处理器)
                            pipeline.addLast(new MyServerHandler());

                        }
                    });
            System.out.println("netty 服务器启动");
            ChannelFuture channelFuture = b.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
