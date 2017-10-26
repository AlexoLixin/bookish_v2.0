package cn.don9cn.blog.autoconfigs.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
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

    @Value("${spring.activemq.default-queue}")
    private String defaultQueue;

    /**
     * 默认队列
     * @return
     */
    @Bean
    public ActiveMQQueue defaultQueue(){
        return new ActiveMQQueue(defaultQueue);
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
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
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory,MessageConverter messageConverter,ActiveMQQueue defaultQueue){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);
        jmsTemplate.setDefaultDestination(defaultQueue);
        return jmsTemplate;
    }



}
