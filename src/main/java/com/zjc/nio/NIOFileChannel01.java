package com.zjc.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description : 把数据写入到本地文件
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "Hello,内蒙吴彦祖";
        //创建一个文件输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\data_\\Desktop\\file01.txt");
        //通过 fileOutputStream获取对应的channel, 这个fileChannel真实的类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将 str 放入到byteBuffer
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));
        //对byteBuffer进行flip反转
        byteBuffer.flip();
        //将byteBuffer的数据写入到fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
