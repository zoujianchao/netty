package com.zjc.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author : zoujc
 * @date : 2021/7/5
 * @description : NettyServer
 */
public class NettyServer {
    public static void main(String[] args) throws Exception{
        //创建BossGroup 和 WorkerGroup
        //1.创建两个线程组, boosGroup 和 workerGroup
        //2.boosGroup只是处理连接请求, 真正的和客户端业务处理,会交给workerGroup
        //3.两个都是无限循环
        //4.boosGroup 和 workerGroup 含有子线程(NioEventLoop)的个数 默认实际上 cpu核数*2
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端启动的对象, 配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); //给我们的workerGroup的EventLoop对应的管道设置处理器
            System.out.println("......服务器 is ready...");
            //绑定一个端口并且同步, 生成一个ChannelFuture对象
            //启动服务器,绑定端口
            ChannelFuture cf = bootstrap.bind(6668).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
