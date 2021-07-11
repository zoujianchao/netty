package com.zjc.netty.inandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author : zoujc
 * @date : 2021/7/11
 * @description :
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    //编码方法
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder encoder 被调用");
        System.out.println("msg=" + msg);
        out.writeLong(msg);
    }
}
