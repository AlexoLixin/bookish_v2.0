package cn.don9cn.blog.autoconfigs.activemq.core;

import cn.don9cn.blog.autoconfigs.activemq.model.MqMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 * @Author: liuxindong
 * @Description: 消息生产者,简单封装了一些常用的发送方法
 * @Create: 2017/10/26 15:10
 * @Modify:
 */
@Configuration
public class MqProducer {

    @Autowired
    private MqConstant mqConstant;

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 点对点发送
     * @param destination
     * @param message
     */
    public void sendToQueue(String destination,MqMessage message){
        ActiveMQQueue queue = new ActiveMQQueue(destination);
        jmsTemplate.convertAndSend(queue,message);
    }

    /**
     * 默认点对点发送
     * @param message
     */
    public void sendToDefaultQueue(MqMessage message){
        ActiveMQQueue queue = new ActiveMQQueue(mqConstant.DEFAULT_QUEUE);
        jmsTemplate.convertAndSend(queue,message);
    }

    /**
     * 发布到指定topic
     * @param destination
     * @param message
     */
    public void pushToTopic(String destination,MqMessage message){
        ActiveMQTopic topic = new ActiveMQTopic(destination);
        jmsTemplate.convertAndSend(topic,message);
    }

    /**
     * 发布到默认topic
     * @param message
     */
    public void pushToDefaultTopic(MqMessage message){
        ActiveMQTopic topic = new ActiveMQTopic(mqConstant.DEFAULT_TOPIC);
        jmsTemplate.convertAndSend(topic,message);
    }


}
