package cn.don9cn.blog.autoconfigs.activemq.listener;

import cn.don9cn.blog.autoconfigs.websocket.msg.MsgWebSocketHandler;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @Author: liuxindong
 * @Description: 用户消息监听器
 * @Create: 2017/10/26 15:38
 * @Modify:
 */
public class UserMqListener extends AbstractAdaptableMessageListener {

    private final String username;

    private final MsgWebSocketHandler msgWebSocketHandler;

    public UserMqListener(String username, MsgWebSocketHandler msgWebSocketHandler){
        this.username = username;
        this.msgWebSocketHandler = msgWebSocketHandler;
    }

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        String msg = ((ActiveMQTextMessage)message).getText();
        System.out.println("用户 ["+username+"] 监听到新消息 : "+msg);
        msgWebSocketHandler.sendToUser(username,msg);
    }

}
