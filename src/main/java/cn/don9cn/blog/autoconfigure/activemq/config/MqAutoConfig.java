package cn.don9cn.blog.autoconfigure.activemq.config;

import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant;
import cn.don9cn.blog.autoconfigure.activemq.listener.SysMqListener;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.*;

/**
 * @Author: liuxindong
 * @Description: ActiveMQ自动配置 javaonfig
 * @Create: 2017/10/26 15:19
 * @Modify:
 */
@Configuration
@AutoConfigureAfter(ConnectionFactory.class)
public class MqAutoConfig {

    private final MqConstant mqConstant;

    private ConnectionFactory connectionFactory;

    @Autowired
    public MqAutoConfig(MqConstant mqConstant, ConnectionFactory connectionFactory){
        this.mqConstant = mqConstant;
        this.connectionFactory = connectionFactory;
        initListener();
    }

    /**
     * 启动系统监听器(支持多topic同时订阅或者queue监听)
     */
    private void initListener() {
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(mqConstant.SYS_LISTENER_CLIENT_ID);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //监听系统通知
            Topic topic = session.createTopic(mqConstant.TOPIC_MSG_SYSTEM);
            //创建topic订阅者
            TopicSubscriber consumer1 = session.createDurableSubscriber(topic, mqConstant.SYS_LISTENER_CLIENT_ID);
            //设置消息处理器
            consumer1.setMessageListener(new SysMqListener());

            connection.start();

            System.out.println("成功启动ActiveMQ系统监听器 >>>");

        }catch (Exception e){
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e1) {
                    throw new RuntimeException("系统ActiveMQ监听器初始化失败!",e);
                }
            }
            throw new RuntimeException("系统ActiveMQ监听器初始化失败!",e);
        }

    }

    /**
     * Jackson2 消息转换器
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    /**
     * JmsTemplate 消息操作模版
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory,MessageConverter messageConverter){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);
        jmsTemplate.setDefaultDestination(new ActiveMQQueue(mqConstant.DEFAULT_QUEUE));
        return jmsTemplate;
    }


}
