package cn.don9cn.blog.autoconfigs.activemq.core;

import cn.don9cn.blog.model.system.msg.SysMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: liuxindong
 * @Description: 消息监听器
 * @Create: 2017/10/26 15:38
 * @Modify:
 */
@Component
public class MqListener {

    @JmsListener(destination = "test",subscription = "test",containerFactory = "myFactory")
    public void receiveQueue(SysMessage text) {
        System.out.println("Consumer1 收到新消息:"+text);
    }

    /*@JmsListener(destination = "test.queue",containerFactory = "myFactory")
    public void receiveQueue2(SysMessage text) {
        System.out.println("Consumer2 收到新消息:"+text);
    }*/

}
