package cn.don9cn.blog.autoconfigure.activemq.core;

import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant;
import cn.don9cn.blog.autoconfigure.activemq.listener.UserMqListener;
import cn.don9cn.blog.autoconfigure.websocket.msg.MsgWebSocketHandler;
import cn.don9cn.blog.exception.ExceptionWrapper;
import cn.don9cn.blog.service.bussiness.SubscribeService;
import org.apache.activemq.ActiveMQConnection;
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

    private final ConcurrentHashMap<String,ActiveMQConnection> cache = new ConcurrentHashMap<>();

    private static Logger logger = Logger.getLogger(MqConsumerGenerator.class);

    public void startListen(String username, MsgWebSocketHandler msgWebSocketHandler){

        try {
            ActiveMQConnection connection = cache.get(username);
            //关闭后的activeMq connection不可再次start使用,所以如果由于activeMq服务端可能
            //      导致的连接关闭,也要校验
            if(connection==null || connection.isClosed()){
                synchronized (cache){
                    connection = cache.get(username);
                    if(connection==null || connection.isClosed()){
                        //考虑到此时connection可能不为空,但是出于closed状态,那么进行删除,消除引用,方便gc回收
                        cache.remove(username);

                        ActiveMQConnection newConnection = (ActiveMQConnection) connectionFactory.createConnection();
                        newConnection.setClientID(username);
                        Session session = newConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                        //监听系统通知
                        Topic topic = session.createTopic(mqConstant.TOPIC_MSG_SYSTEM);
                        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, username);
                        topicSubscriber.setMessageListener(new UserMqListener(username,msgWebSocketHandler));
                        logger.info("ActiveMQ : 创建用户 ["+username+"] 监听: "+mqConstant.TOPIC_MSG_SYSTEM);

                        //监听用户自身的queue队列
                        Queue queue = session.createQueue(mqConstant.QUEUE_USER_PREFIX + username);
                        MessageConsumer queueConsumer = session.createConsumer(queue);
                        queueConsumer.setMessageListener(new UserMqListener(username,msgWebSocketHandler));
                        logger.info("ActiveMQ : 创建用户 ["+username+"] 监听: "+mqConstant.QUEUE_USER_PREFIX + username);

                        newConnection.start();

                        cache.put(username,newConnection);
                    }
                }
            }
            logger.info("ActiveMQ : 成功启动用户 ["+username+"] 消息监听 >>>");
        } catch (JMSException e) {
            throw new ExceptionWrapper(e,"MqConsumerGenerator.startListen 启动ActiveMQ订阅者监听失败");
        }

    }

    public void closeListen(String username){

        ActiveMQConnection connection = cache.get(username);
        if(connection!=null){
            synchronized (cache){
                connection = cache.get(username);
                if(connection!=null){
                    try {
                        connection.close();
                        cache.remove(username);
                        logger.info("ActiveMQ : 成功关闭用户 ["+username+"] 消息监听 >>>");
                    } catch (JMSException e) {
                        throw new ExceptionWrapper(e,"MqConsumerGenerator.closeListen 关闭ActiveMQ订阅者监听失败");
                    }
                }
            }
        }

    }

}
