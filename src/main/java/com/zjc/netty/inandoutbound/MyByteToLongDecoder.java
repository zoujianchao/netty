package com.zjc.netty.inandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author : zoujc
 * @date : 2021/7/11
 * @description :
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *
     * @param ctx 上下文
     * @param in 入站的ByteBuf
     * @param out list集合, 将解码后的数据传给下一个handler处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        //Long 8个字节, 需要判断有8个字节才能读取一个long
        if (in.readableBytes() >=8 ) {
            out.add(in.readLong());
        }

    }
}
