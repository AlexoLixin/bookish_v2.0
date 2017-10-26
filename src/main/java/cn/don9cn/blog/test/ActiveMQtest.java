package cn.don9cn.blog.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author Don9
 * @create 2017-10-26-14:27
 **/
public class ActiveMQtest {

    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory("admin","admin","tcp://45.77.6.109:61616");
        JmsTemplate jmsTemplate = new JmsTemplate(factory);
        jmsTemplate.send("aaaaaaaa", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("lalalalalal");
            }
        });

    }



}
