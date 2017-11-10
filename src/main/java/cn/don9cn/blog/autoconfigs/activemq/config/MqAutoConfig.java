package cn.don9cn.blog.autoconfigs.activemq.config;

import cn.don9cn.blog.autoconfigs.activemq.core.MqConstant;
import cn.don9cn.blog.autoconfigs.activemq.core.SysMqListener;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

/**
 * @Author: liuxindong
 * @Description: ActiveMQ自动配置 javaonfig
 * @Create: 2017/10/26 15:19
 * @Modify:
 */
@Configuration
public class MqAutoConfig {

    @Autowired
    private MqConstant mqConstant;

    /**
     * 消息监听容器(适用于自定义javaConfig方式配置监听器)
     * @param connectionFactory
     * @return
     */
    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(ConnectionFactory connectionFactory, SysMqListener sysMqListener) {

        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        // 设置连接工厂
        container.setConnectionFactory(connectionFactory);
        // 设置客户端id,ActiveMQ通过clientId来实现持久订阅
        container.setClientId(mqConstant.SYS_LISTENER_CLIENT_ID);
        // 设置持久订阅
        container.setSubscriptionDurable(true);
        // 订阅系统消息topic
        container.setDestination(new ActiveMQTopic(mqConstant.SYS_MSG_TOPIC));
        // 设置消息转换器
        container.setMessageConverter(messageConverter());
        // 设置消息监听器,用来处理监听到新消息后的动作
        container.setMessageListener(sysMqListener);

        return container;
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
