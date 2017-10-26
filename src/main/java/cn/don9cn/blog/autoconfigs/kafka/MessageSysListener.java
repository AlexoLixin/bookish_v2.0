package cn.don9cn.blog.autoconfigs.kafka;

import cn.don9cn.blog.autoconfigs.websocket.msg.MsgWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.*;

/**
 * @Author: liuxindong
 * @Description: 创建kafka消息监听器,此处不使用springboot的注解进行快速创建,因为实际中需要系统监听这个消费者快速过期,
 *               便于后续用户及时消费最新消息,所以需要自定义过期时间
 * @Create: 2017/10/26 9:22
 * @Modify:
 */
@Configuration
@EnableKafka
public class MessageSysListener {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private MsgWebSocketHandler msgWebSocketHandler;

    @Value("${spring.kafka.system-msg.topic}")
    private List<String> systemMsgTopic;

    @Value("${spring.kafka.system-msg.listener-name}")
    private String listenerName;

    /**
     * 消费者配置
     * @return
     */
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        properties.put("session.timeout.ms","4000");
        return properties;
    }

    @Bean
    KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainer() {
        String[] topics = new String[]{};
        ContainerProperties containerProperties = new ContainerProperties(systemMsgTopic.toArray(topics));
        KafkaMessageListenerContainer<String, String> container =
                new KafkaMessageListenerContainer<>(new DefaultKafkaConsumerFactory<>(consumerConfigs()), containerProperties);
        container.setBeanName(listenerName);
        container.setupMessageListener(
                (MessageListener<String, String>) record -> {
                    System.out.println("系统监听到新消息 : "+record.value());
                    msgWebSocketHandler.sendMessageToAll();
                }
        );
        return container;
    }


}
