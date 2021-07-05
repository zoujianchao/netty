package com.zjc.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description :  MappedByteBuffer 可以让文件直接在内存中修改, 操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("file\\1.txt", "rw"); //打开本地文件会改变
        //获取对应的文件通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1: FileChannel.MapMode.READ_WRITE 使用的是读写模式
         * 参数2: 0 可以直接修改的起始位置
         * 参数3: 5 是映射到内存的大小(最多可以映射5个字节) 修改的字节是[0-5)
         * 实际的类型是 DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        mappedByteBuffer.put(5, (byte) 'M');
        randomAccessFile.close();
        System.out.println("修改成功~~");
    }
}
