package com.zjc.netty.inandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author : zoujc
 * @date : 2021/7/11
 * @description :
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用");
        //在ReplayingDecoder中 不需要判断数据是否足够读取,内部会进行处理判断
        out.add(in.readLong());
    }
}
