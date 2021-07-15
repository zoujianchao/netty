package com.zjc.netty.dubborpc.customer;

import com.zjc.netty.dubborpc.netty.NettyClient;
import com.zjc.netty.dubborpc.publicinterface.HelloService;

/**
 * @author : zoujc
 * @date : 2021/7/15
 * @description :
 */
public class ClientBootstrap {
    //定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        //创建消费者
        NettyClient customer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        //通过代理对象调用服务提供者的方法(服务)
        String res = service.hello("你好 dubbo~");
        System.out.println("调用的结果res=" + res);
    }
}
