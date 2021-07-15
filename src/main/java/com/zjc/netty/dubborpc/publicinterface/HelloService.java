package com.zjc.netty.dubborpc.publicinterface;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/15
 * @description : 此接口服务提供方和服务消费方都需要
 */
public interface HelloService {

    String hello(String mes);
}
