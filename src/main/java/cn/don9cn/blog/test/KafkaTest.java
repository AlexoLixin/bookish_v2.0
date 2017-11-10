package cn.don9cn.blog.test;


import cn.don9cn.blog.autoconfigs.activemq.model.SysMessage;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.*;

/**
 * @Author: liuxindong
 * @Description: kafka测试
 * @Create: 2017/10/23 9:00
 * @Modify:
 */
public class KafkaTest {

    public static void main(String[] args) {
        KafkaTemplate<String,String> kafkaTemplate = kafkaTemplate();
        System.out.println("---push start----");
        SysMessage sysMessage = new SysMessage("aaa","111111111");
        kafkaTemplate.send("test","1", JSON.toJSONString(sysMessage));
        kafkaTemplate.flush();
        System.out.println("----push Done----");
        System.out.println("----poll start----");
        Consumer<String,String> consumer = consumer();
        List<String> topics = new ArrayList<>();
        topics.add("test");
        consumer.subscribe(topics);
        ConsumerRecords<String, String> records = consumer.poll(1000);
        consumer.close();
        records.forEach(record -> System.out.println(record.value()));
        System.out.println("----poll done----");
    }

    public static Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.94.206.26:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 4096);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /** 获取工厂 */
    public static ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /** 注册实例 */
    public static KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public static Consumer<String,String> consumer(){
        Properties prop = new Properties();
        prop.put("bootstrap.servers","47.94.206.26:9092");
        prop.put("auto.offset.reset","earliest");
        prop.put("enable.auto.commit","true");
        prop.put("auto.commit.interval.ms","1000");
        prop.put("session.timeout.ms","10000");
        prop.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("group.id","liuxindong");
        return new KafkaConsumer<>(prop);
    }

}
