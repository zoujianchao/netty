package com.zjc.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author : zoujc
 * @date : 2021/7/4
 * @description : 零拷贝 客户端
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String fileName = "C:\\Users\\data_\\Desktop\\a.zip";
        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        //准备发送
        long startTime = System.currentTimeMillis();
        //在linux下 : 一个transferTo 方法就可以完成传输
        //在windows下 : 一次调用transferTo 只能发送8M, 需要分段传输文件,要主要传输的位置 (循环fileChannel.size()/8)
        //transferTo: 底层使用零拷贝
        long transferToCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总的字节数: " + transferToCount + " 耗时: " + (System.currentTimeMillis() - startTime));
        //关闭通道
        fileChannel.close();

    }
}
