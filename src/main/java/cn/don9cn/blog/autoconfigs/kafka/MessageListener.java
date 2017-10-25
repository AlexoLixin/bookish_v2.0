package cn.don9cn.blog.autoconfigs.kafka;

import cn.don9cn.blog.autoconfigs.websocket.msg.MsgWebSocketHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author: liuxindong
 * @Description: 消息监听者
 * @Create: 2017/10/23 10:34
 * @Modify:
 */
@Component
public class MessageListener {

    @Autowired
    private MsgWebSocketHandler msgWebSocketHandler;

    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<String,String> record) {
        System.out.println("监听到新消息:"+record.value());
        // 监听到新消息后,通过webSocket推送到前端
        msgWebSocketHandler.sendMessageToAll();
    }

}
