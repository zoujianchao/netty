package com.zjc.nio;

import java.nio.ByteBuffer;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description : 只读Buffer
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        //创建一个Buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        //读取
        buffer.flip();
        //得到一个只读的Buffer ,不能put
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
        //读取
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        //不能放数据
//        readOnlyBuffer.put((byte) 100);

    }
}
