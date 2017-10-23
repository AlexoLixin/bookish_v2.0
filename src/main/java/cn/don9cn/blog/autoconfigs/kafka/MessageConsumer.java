package cn.don9cn.blog.autoconfigs.kafka;

import cn.don9cn.blog.model.system.msg.SysMessage;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuxindong
 * @Description: 消息消费者
 * @Create: 2017/10/23 15:14
 * @Modify:
 */
@Configuration
public class MessageConsumer {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value("${spring.kafka.system-msg.topic}")
    private List<String> systemMsgTopic;

    @Value("${spring.kafka.system-msg.timeout}")
    private Long consumerPollTime;

    public KafkaConsumer<String,String> build(String groupId){
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        properties.put("group.id",groupId);
        properties.put("auto.commit.interval.ms","1000");
        properties.put("session.timeout.ms","30000");
        return new KafkaConsumer<>(properties);
    }

    /**
     * 消费消息，并返回消息集合
     * @return
     */
    public List<SysMessage> consumeSystemMsg(Consumer<String,String> consumer){
        // 1.新建返回值
        LinkedList<SysMessage> messageList = new LinkedList<>();
        // 2.订阅topic
        consumer.subscribe(systemMsgTopic);
        // 3.获取未消费的消息
        ConsumerRecords<String, String> records = consumer.poll(consumerPollTime);
        // 4.关闭连接
        consumer.close();
        for (ConsumerRecord<String, String> record : records){
            messageList.addFirst(JSON.parseObject(record.value(),SysMessage.class));
        }
        return messageList;
    }

    /**
     * 消费消息，并返回消息集合
     * @param consumer
     * @param timeout
     * @return
     */
    public List<SysMessage> consume(Consumer<String,String> consumer, long timeout, String... topics){
        // 1.新建返回值
        LinkedList<SysMessage> messageList = new LinkedList<>();
        // 2.订阅topic
        consumer.subscribe(Arrays.asList(topics));
        // 3.获取未消费的消息
        ConsumerRecords<String, String> records = consumer.poll(timeout);
        // 4.关闭连接
        consumer.close();
        for (ConsumerRecord<String, String> record : records){
            messageList.addFirst(JSON.parseObject(record.value(),SysMessage.class));
        }
        return messageList;
    }

}
