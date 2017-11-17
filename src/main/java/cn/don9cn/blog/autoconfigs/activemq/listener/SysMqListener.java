package cn.don9cn.blog.autoconfigs.activemq.listener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @Author: liuxindong
 * @Description: 系统消息监听器(处理监听到的消息)
 * @Create: 2017/10/26 15:38
 * @Modify:
 */
@Component
public class SysMqListener implements SessionAwareMessageListener<ActiveMQTextMessage> {

    @Override
    public void onMessage(ActiveMQTextMessage message, Session session) throws JMSException {
        System.out.println("系统监听到新消息 : "+message.getText());
    }
}
