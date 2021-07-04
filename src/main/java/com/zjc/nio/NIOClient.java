package com.zjc.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author : zoujc
 * @date : 2021/7/4
 * @description : 客户端代码
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器端
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间,客户端不会阻塞,可以做其他工作");
            }
        }
        //如果连接成功可以发送数据
        String str = "hello,内蒙吴彦祖";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //发送数据,将buffer的数据写入到channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
