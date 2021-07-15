package com.zjc.netty.dubborpc.provider;

import com.zjc.netty.dubborpc.netty.NettyServer;

/**
 * @author : zoujc
 * @date : 2021/7/15
 * @description : ServerBootstrap会启动一个服务提供者,就是NettyServer
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 6000);
    }
}
