package com.zjc.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : zoujc
 * @date : 2021/7/4
 * @description : NIOServer 服务端代码
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();
        //绑定一个端口6666,在服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到selector 关心事件为 OP_ACCEPT (连接事件)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true) {
            //等待1秒,如果没有事件发生, 就继续
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒, 无连接");
                continue;
            }
            //如果返回的>0 ,就获取到相关的selectionKey集合
            //1.如果返回的>0 ,表示已经获取到关注的事件
            //2.selector.selectedKeys() 返回关注事件的集合
            //通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历 Set<SelectionKey>
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                //获取SelectionKey
                SelectionKey key = keyIterator.next();
                //根据key 对应的通道发生的事件做相应的处理
                //如果是OP_ACCEPT 事件,有新的客户端连接
                if (key.isAcceptable()) {
                   //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功,生成了一个socketChannel: " + socketChannel.hashCode());
                    //将socketChannel设置非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册到selector, 关注事件为OP_READ,
                    //同时给socketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                //如果是OP_READ事件
                if (key.isReadable()) {
                    //通过key反向获取对应的channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端 " + new String(buffer.array()));
                }
                //手动从集合中移除当前的selectionKey, 防止重复操作
                keyIterator.remove();

            }


        }

    }
}
