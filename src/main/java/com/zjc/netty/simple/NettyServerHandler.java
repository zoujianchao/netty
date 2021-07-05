package com.zjc.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author : zoujc
 * @date : 2021/7/5
 * @description : 自定义一个handler 需要继承netty规定好的某个handlerAdapter,
 *                  这时我们自定义的handler,才能成为handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取实际数据(读取客户端发送的消息)
    //1.ChannelHandlerContext 上下文对象, 含有管道pipeline,通道channel,地址
    //2.Object 客户端发送的数据 默认是Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        //Netty提供的Buffer 性能更高
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址: " + ctx.channel().remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush : write + flush 将数据写入缓存,并刷新
        //一般,我们对发送数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~", CharsetUtil.UTF_8));
    }

    //处理异常, 一般需要关闭通道

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
