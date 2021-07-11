package com.zjc.netty.inandoutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author : zoujc
 * @date : 2021/7/11
 * @description :
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //入站的handler进行解码 MyByteToLongDecoder
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        //服务器端编码 出站
        pipeline.addLast(new MyLongToByteEncoder());
        //自定义handler处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
