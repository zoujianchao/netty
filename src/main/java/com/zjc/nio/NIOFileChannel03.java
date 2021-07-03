package com.zjc.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description : 使用一个Buffer实现文件拷贝
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("file\\1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("file\\2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //循环读取
        while (true) {
            /**
             * public final Buffer clear() {
             *         position = 0;
             *         limit = capacity;
             *         mark = -1;
             *         return this;
             *     }
             */
            //重置标志位(清空buffer)
            byteBuffer.clear();
            //读取数据放入缓冲区
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read = " + read);
            if (read == -1) { //表示读完
                break;
            }
            //将buffer中的数据写入到fileChannel02
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);

        }
        //关闭输入输出流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
