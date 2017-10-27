package cn.don9cn.blog.autoconfigs.activemq.core;

import cn.don9cn.blog.autoconfigs.websocket.msg.MsgWebSocketHandler;
import cn.don9cn.blog.exception.ExceptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liuxindong
 * @Description: 消费者生成器,用于动态生成topic的持久订阅者
 * @Create: 2017/10/27 9:46
 * @Modify:
 */
@Component
public class MqConsumerGenerator {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${spring.activemq.system-msg-topic}")
    private String sysMsgTopic;

    private final ConcurrentHashMap<String,Connection> cache = new ConcurrentHashMap<>();

    public void startListen(String username, MsgWebSocketHandler msgWebSocketHandler){
        Connection connection = cache.get(username);
        if(connection==null){
            synchronized (this){
                try {
                    connection = connectionFactory.createConnection();
                    connection.setClientID(username);
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    Topic topic = session.createTopic(sysMsgTopic);
                    TopicSubscriber consumer = session.createDurableSubscriber(topic, username);
                    consumer.setMessageListener(new UserMqListener(username,msgWebSocketHandler));
                    cache.put(username,connection);
                } catch (JMSException e) {
                    throw new ExceptionWrapper(e,"MqConsumerGenerator.createConsumer 启动订阅者监听失败");
                }
            }
        }
        try {
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        
    }

    public void closeListen(String username){
        Connection connection = cache.get(username);
        if(connection!=null){
            synchronized (this){
                try {
                    connection.close();
                    cache.remove(username);
                } catch (JMSException e) {
                    throw new ExceptionWrapper(e,"MqConsumerGenerator.closeListen 关闭订阅者监听失败");
                }
            }
        }
    }

}
