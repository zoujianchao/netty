package com.zjc.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/10
 * @description :
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world,北京", StandardCharsets.UTF_8);
        //使用相关的API
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            //将content转成字符串
            System.out.println(new String(content, StandardCharsets.UTF_8));
            System.out.println("byteBuf=" + byteBuf); //byteBuf=UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 18, cap: 64)
            System.out.println(byteBuf.arrayOffset()); // 0
            System.out.println(byteBuf.readerIndex()); // 0
            System.out.println(byteBuf.writerIndex()); // 18
            System.out.println(byteBuf.capacity()); // 64
            System.out.println(byteBuf.readableBytes()); // 可读取字节数(长度) 18
            System.out.println(byteBuf.readByte()); // ASSIC 104
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char) byteBuf.getByte(i));
            }
            System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8)); //左闭右开
        }
    }
}
