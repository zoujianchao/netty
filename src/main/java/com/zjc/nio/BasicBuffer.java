package com.zjc.nio;

import java.nio.IntBuffer;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description : 缓冲区 Buffer是个可读写的内存块  , NIO面向缓冲区或者说块的编程
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个Buffer
        //创建一个Buffer,大小为5,即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //向buffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        //从buffer读取数据
        //将buffer转换,读写切换
        intBuffer.flip();
        //设置从第二个位置开始读
        intBuffer.position(1);
        //设置limit最大为3,不能超过3位置, 2,4
        intBuffer.limit(3);
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }


}
