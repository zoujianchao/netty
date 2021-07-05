package com.zjc.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/3
 * @description : 分散聚合Buffer
 *
 *      Scattering: 将数据写入到Buffer时, 可以采用Buffer数组, 依次写入 [分散]
 *      Gathering : 从Buffer读取数据时,可以采用Buffer数组,依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception{
        //使用ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口到socket 并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //服务器端创建Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        //等待客服端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        //从客户端最多接受8个字节
        int messageLength = 8;
        //循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long read = socketChannel.read(byteBuffers);
                //累计读取的字节数
                byteRead += read;
                System.out.println("byteRead=" + byteRead);
                //使用流打印,输出当前的Buffer的position和 limit
                Arrays.stream(byteBuffers).map(buffer ->
                        "position=" + buffer.position() + ", limit=" + buffer.limit())
                        .forEach(System.out::println);
            }
            //将所有的buffer反转 flip
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }
            //将所有的Buffer clear
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead=" + byteRead + " byteWrite=" + byteWrite + " messageLength=" + messageLength);
        }



    }
}
