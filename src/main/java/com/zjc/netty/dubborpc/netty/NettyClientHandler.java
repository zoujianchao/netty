package com.zjc.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author : zoujc
 * @date : 2021/7/15
 * @description :
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    //上下文
    private ChannelHandlerContext context;
    //返回的结果
    private String result;
    //客户端调用方法时,传入的参数
    private String para;

    /**
     * 与服务器的连接创建后,就会被调用  这个方法第一个被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //因为我在其他方法会使用到ctx
        context = ctx;
    }

    /**
     * 收到服务器数据后就会被调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); //唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 被代理对象调用,发送数据给服务器, -> wait -> 等待被唤醒(channelRead) -> 返回结果
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        //进行wait
        wait(); //等待channelRead方法获取到服务器的结果后,唤醒
        //服务方返回的结果
        return result;
    }

    void setPara(String para) {
        this.para = para;
    }
}
