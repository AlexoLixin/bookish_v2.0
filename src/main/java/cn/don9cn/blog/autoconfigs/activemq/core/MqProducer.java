package cn.don9cn.blog.autoconfigs.activemq.core;

import cn.don9cn.blog.model.system.msg.SysMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.activemq.default-topic}")
    private String defaultTopic;

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 点对点发送
     * @param destination
     * @param message
     */
    public void sendToQueue(String destination,SysMessage message){
        ActiveMQQueue queue = new ActiveMQQueue(destination);
        jmsTemplate.convertAndSend(queue,message);
    }

    /**
     * 点对点发送
     * @param message
     */
    public void sendToDefaultQueue(SysMessage message){
        jmsTemplate.convertAndSend(message);
    }

    /**
     * 发布到指定topic
     * @param destination
     * @param message
     */
    public void pushToTopic(String destination,SysMessage message){
        ActiveMQTopic topic = new ActiveMQTopic(destination);
        jmsTemplate.convertAndSend(topic,message);
    }

    /**
     * 发布到默认topic
     * @param message
     */
    public void pushToDefaultTopic(SysMessage message){
        ActiveMQTopic topic = new ActiveMQTopic(defaultTopic);
        jmsTemplate.convertAndSend(topic,message);
    }


}
