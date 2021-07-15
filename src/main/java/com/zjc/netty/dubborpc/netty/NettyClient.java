package com.zjc.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : zoujc
 * @date : 2021/7/15
 * @description :
 */
public class NettyClient {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler client;

    //使用代理模式获取代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {serviceClass}, ((proxy, method, args) -> {
                    //客户端每调用一次hello就会进入到该代码块
                    if (client == null){
                        initClient();
                    }
                    //设置要发给服务端的信息
                    //providerName 协议头 args[0] 就是客户端调用api hello(???) 参数
                    client.setPara(providerName + args[0]);
                    return executor.submit(client).get();
                }));
    }

    //初始化客户端
    private static void initClient() {
        client = new NettyClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);
                    }
                });
        try {
            bootstrap.connect("127.0.0.1", 6000).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
