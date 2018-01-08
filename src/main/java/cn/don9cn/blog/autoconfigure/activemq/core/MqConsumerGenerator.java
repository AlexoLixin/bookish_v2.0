package cn.don9cn.blog.autoconfigure.activemq.core;

import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant;
import cn.don9cn.blog.autoconfigure.activemq.listener.UserMqListener;
import cn.don9cn.blog.autoconfigure.websocket.msg.MsgWebSocketHandler;
import cn.don9cn.blog.exception.ExceptionWrapper;
import cn.don9cn.blog.service.bussiness.SubscribeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liuxindong
 * @Description: 消费者生成器,用于用户登录后动态开启监听
 * @Create: 2017/10/27 9:46
 * @Modify:
 */
@Component
public class MqConsumerGenerator {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private MqConstant mqConstant;

    @Autowired
    private SubscribeService subscribeService;

    private final ConcurrentHashMap<String,Connection> cache = new ConcurrentHashMap<>();

    private static Logger logger = Logger.getLogger(MqConsumerGenerator.class);

    public void startListen(String username, MsgWebSocketHandler msgWebSocketHandler){

        try {
            Connection connection = cache.get(username);
            if(connection==null){
                synchronized (cache){
                    connection = cache.get(username);
                    if(connection==null){
                        connection = connectionFactory.createConnection();
                        connection.setClientID(username);
                        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                        //监听系统通知
                        Topic topic = session.createTopic(mqConstant.TOPIC_MSG_SYSTEM);
                        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, username);
                        topicSubscriber.setMessageListener(new UserMqListener(username,msgWebSocketHandler));

                        //监听用户自身的queue队列
                        Queue queue = session.createQueue(mqConstant.QUEUE_USER_PREFIX + username);
                        MessageConsumer queueConsumer = session.createConsumer(queue);
                        queueConsumer.setMessageListener(new UserMqListener(username,msgWebSocketHandler));

                        //监听用户订阅的作者topic
                        Set<String> set = subscribeService.findSubscribeAuthorSetByUserName(username);
                        for(String author:set){
                            Topic authorTopic = session.createTopic(mqConstant.TOPIC_AUTHOR_SUBSCRIBE_PREFIX + author);
                            TopicSubscriber authorTopicSubscriber = session.createDurableSubscriber(authorTopic, username);
                            authorTopicSubscriber.setMessageListener(new UserMqListener(username,msgWebSocketHandler));
                        }

                        cache.put(username,connection);
                    }
                }
            }
            connection.start();
            logger.info("ActiveMQ : 成功启动用户 ["+username+"] 消息监听 >>>");
        } catch (JMSException e) {
            throw new ExceptionWrapper(e,"MqConsumerGenerator.startListen 启动ActiveMQ订阅者监听失败");
        }

    }

    public void closeListen(String username){

        Connection connection = cache.get(username);
        if(connection!=null){
            synchronized (cache){
                connection = cache.get(username);
                if(connection!=null){
                    try {
                        connection.close();
                        logger.info("ActiveMQ : 成功关闭用户 ["+username+"] 消息监听 >>>");
                    } catch (JMSException e) {
                        throw new ExceptionWrapper(e,"MqConsumerGenerator.closeListen 关闭ActiveMQ订阅者监听失败");
                    }
                }
            }
        }

    }

}
