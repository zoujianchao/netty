package com.zjc.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/10
 * @description :
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建一个ByteBuf
        /**
         * 1.创建一个对象,该对象包含一个数组arr.是一个byte[10]
         * 2.在netty的buffer中,不需要使用flip进行反转,底层维护了 readerIndex和writerIndex
         * 3.通过 readerIndex和writerIndex和capacity ,将buffer分为三个区域
         * 0-readerIndex:已经读取的区域
         * readerIndex-writerIndex:可读区域
         * writerIndex-capacity:可写区域
         */
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
//        for (int i = 0; i < buffer.capacity(); i++) {
//            System.out.println(buffer.getByte(i));
//        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }

    }
}
