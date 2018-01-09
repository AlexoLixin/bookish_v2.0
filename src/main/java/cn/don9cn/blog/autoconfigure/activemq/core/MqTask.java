package cn.don9cn.blog.autoconfigure.activemq.core;

import cn.don9cn.blog.autoconfigure.activemq.constant.MqDestinationType;
import cn.don9cn.blog.autoconfigure.activemq.model.MqMessage;

/**
 * @Author: liuxindong
 * @Description: 注册到消息管理器的消息实体
 * @Create: 2017/11/17 8:58
 * @Modify:
 */
public class MqTask {

    private final MqDestinationType destinationType;

    private final String destination;

    private final MqMessage message;


    public MqTask(MqDestinationType destinationType, String destination, MqMessage message) {
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
