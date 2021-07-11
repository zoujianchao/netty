package com.zjc.netty.inandoutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author : zoujc
 * @date : 2021/7/11
 * @description :
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入出站的handler.对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());
        //服务端入站解码
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        //加入自定义handler处理业务逻辑
        pipeline.addLast(new MyClientHandler());

    }
}
