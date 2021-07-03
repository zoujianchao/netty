package com.zjc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description : 从本地文件读取数据输出到控制台
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\data_\\Desktop\\file01.txt");
        //创建文件的输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //通过fileInputStream 获取对应的fileChannel -> 实际类型 FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //将通道的数据读入到buffer中
        fileChannel.read(byteBuffer);
        //将缓冲区byteBuffer的字节转成字符串
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
