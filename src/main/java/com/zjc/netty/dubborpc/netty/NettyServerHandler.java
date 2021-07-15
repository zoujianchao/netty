package com.zjc.netty.dubborpc.netty;

import com.zjc.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author : zoujc
 * @date : 2021/7/15
 * @description : 服务器端handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息,并调用服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器的api时,我们需要定义一个协议
        //比如我们要求,每次发消息时,都必须以某个字符串开头 "HelloService#hello#你好"
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString()
                    .lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
