package com.zjc.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description : 拷贝文件 用transferFrom
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception{
        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\data_\\Desktop\\a1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\data_\\Desktop\\a2.jpg");
        //获取各流对应的fileChannel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();
        //使用transferFrom完成拷贝
        destCh.transferFrom(sourceCh, 0, sourceCh.size());
        //关闭相关的通道和流
        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
