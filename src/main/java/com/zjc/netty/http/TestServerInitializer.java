package com.zjc.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author : zoujc
 * @date : 2021/7/7
 * @description :
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个netty提供的httpServerCodec codec => [code - decoder]
        //作用:1.httpServerCodec是netty提供的处理http的编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //增加自定义处理器handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
