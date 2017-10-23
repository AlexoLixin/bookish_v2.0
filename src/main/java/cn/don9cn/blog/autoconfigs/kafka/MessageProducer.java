package cn.don9cn.blog.autoconfigs.kafka;

import cn.don9cn.blog.exception.ExceptionWrapper;
import cn.don9cn.blog.model.system.msg.SysMessage;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: liuxindong
 * @Description: 消息发布者
 * @Create: 2017/10/23 10:33
 * @Modify:
 */
@Component
public class MessageProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public MessageProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 消息发布
     * @param message
     */
    public void push(String key,SysMessage message){
        try {
            String topic = message.getTopic();
            if(StringUtils.isNotBlank(topic)){
                kafkaTemplate.send(topic,key,JSON.toJSONString(message));
            }else{
                kafkaTemplate.sendDefault(key,JSON.toJSONString(message));
            }
            kafkaTemplate.flush();
        }catch (Exception e){
            System.out.println("发布消息到Kafka失败...");
            e.printStackTrace();
            throw new ExceptionWrapper(e,"MessageProducer.push 发布消息失败");
        }
    }


}
