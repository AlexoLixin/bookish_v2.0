package cn.don9cn.blog.autoconfigs.activemq.model;

import cn.don9cn.blog.autoconfigs.activemq.constant.MqDestinationType;

/**
 * @Author: liuxindong
 * @Description: 注册到消息管理器的消息实体
 * @Create: 2017/11/17 8:58
 * @Modify:
 */
public class MqRegisterMessage {

    private final MqDestinationType destinationType;

    private final String destination;

    private final MqMessage message;


    public MqRegisterMessage(MqDestinationType destinationType, String destination, MqMessage message) {
        this.destinationType = destinationType;
        this.destination = destination;
        this.message = message;
    }

    public MqDestinationType getDestinationType() {
        return destinationType;
    }

    public String getDestination() {
        return destination;
    }

    public MqMessage getMessage() {
        return message;
    }
}
