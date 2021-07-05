package com.zjc.nio;

import java.nio.ByteBuffer;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description : Buffer中按照对应类型存取, 否则会抛出异常,或者解析不正确
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        //创建一个Buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
        //类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('祖');
        buffer.putShort((short) 4);
        //取出
        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

    }
}
