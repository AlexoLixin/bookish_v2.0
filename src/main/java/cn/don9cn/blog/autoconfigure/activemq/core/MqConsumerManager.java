package cn.don9cn.blog.autoconfigure.activemq.core;

import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant;
import cn.don9cn.blog.autoconfigure.activemq.listener.UserMqListener;
import cn.don9cn.blog.autoconfigure.websocket.msg.MsgWebSocketHandler;
import cn.don9cn.blog.exception.ExceptionWrapper;
import org.apache.activemq.ActiveMQConnection;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * @Author: liuxindong
 * @Description: 消费者生成器,用于用户登录后动态开启监听
 * @Create: 2017/10/27 9:46
 * @Modify:
 */
@Component
public class MqConsumerManager {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private MqConstant mqConstant;

    private final ConcurrentHashMap<String,FutureTask<ActiveMQConnection>> cache = new ConcurrentHashMap<>();

    private static Logger logger = Logger.getLogger(MqConsumerManager.class);

    public void startListen(String username, MsgWebSocketHandler msgWebSocketHandler){

        FutureTask<ActiveMQConnection> task = cache.get(username);
        if(task==null){
            FutureTask<ActiveMQConnection> newTask = new FutureTask<>(() -> connect(username, msgWebSocketHandler));
            if(cache.putIfAbsent(username, newTask) == null){
                newTask.run();
                task = newTask;
            }
        }

        try {
            if(task.get().isClosed()){
                FutureTask<ActiveMQConnection> newTask = new FutureTask<>(() -> connect(username, msgWebSocketHandler));
                if(cache.putIfAbsent(username, newTask) == null){
                    newTask.run();
                    newTask.get();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("ActiveMQ : 成功启动用户 ["+username+"] 消息监听 >>>");
    }

    public void closeListen(String username){

        FutureTask<ActiveMQConnection> task = cache.remove(username);
        if(task!=null){
            try {
                task.get().close();
            }catch (Exception e){
                throw new ExceptionWrapper(e,"MqConsumerGenerator.closeListen 关闭ActiveMQ订阅者监听失败");
            }
        }

    }

    private ActiveMQConnection connect(String username,MsgWebSocketHandler msgWebSocketHandler){

        try {
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

            return newConnection;

        } catch (JMSException e) {
            throw new ExceptionWrapper(e,"MqConsumerGenerator.startListen 启动ActiveMQ订阅者监听失败");
        }

    }

}
