package com.zjc.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : zoujc
 * @date : 2021/7/10
 * @description :
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    //定义一个channel组,管理所有的channel
    //GlobalEventExecutor.INSTANCE 全局事件执行器,是单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //handlerAdded 当连接建立, 一旦建立, 第一个被执行
    //将当前channel加入到channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线的客户,
        // 该方法会将channelGroup中所有的channel遍历,并发送消息,不需要自己遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天 " + dtf.format(LocalDateTime.now()) + "\n");
        channelGroup.add(channel);
    }

    //断开连接,将xx客户离开信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress() + " 离开了 " + dtf.format(LocalDateTime.now()) + "\n");
        System.out.println("channelGroupSize: " + channelGroup.size());
    }

    //表示channel处于活动状态, 提示 xx 上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线~ " + dtf.format(LocalDateTime.now()) + "\n");
    }

    //表示channel处于不活动状态, 提示 xx 离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线~ " + dtf.format(LocalDateTime.now()) + "\n");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        //获取到当前channel
        Channel channel = ctx.channel();
        //遍历channelGroup,根据不同的情况回送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) { //不是当前的channel,就转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了消息:" + s + " " + dtf.format(LocalDateTime.now()) + "\n");
            }else {
                ch.writeAndFlush("[自己]发送了消息:" + s + " " + dtf.format(LocalDateTime.now()) + "\n");
            }
        });
    }

    //异常 关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
