package com.zjc.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author : zoujc
 * @date : 2021/7/11
 * @description :
 */
public class MyServer {
    public static void main(String[] args) throws Exception{
        //创建两个线程组
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();//默认8个NioEventLoop
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());
            System.out.println("netty 服务器启动");
            ChannelFuture channelFuture = b.bind(6000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
