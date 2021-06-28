package com.zjc.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : zoujc
 * @date : 2021/6/28
 * @description :  BIO:同步阻塞,服务器实现模式为一个连接一个线程
 * BIO : 1.每个请求都需要创建独立的线程,与客户端进行数据Read,业务处理,数据Write
 *       2.当并发比较大时,需要创建大量线程来处理连接,系统资源占用较大
 *       3.连接建立后,如果当前线程暂时没有数据可读,则线程就阻塞到Read操作上,造成资源浪费
 */
public class BIOServer {
    public static void main(String[] args) throws Exception{
        //线程池机制

        //1.创建一个线程池
        //2.如果有客户端连接,就创建一个线程,与之通讯
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            //监听,等待客户端连接
            System.out.println("等待连接...");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            //创建一个线程,与之通讯
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //与客户端通讯
                    handler(socket);
                }
            });
        }
    }

    public static void handler(Socket socket) {
        try {
            System.out.println("线程信息 id = " + Thread.currentThread().getId() + "名字 = " +
                    Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的数据
            while (true) {
                System.out.println("线程信息 id = " + Thread.currentThread().getId() + "名字 = " +
                        Thread.currentThread().getName());
                System.out.println("read......");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

