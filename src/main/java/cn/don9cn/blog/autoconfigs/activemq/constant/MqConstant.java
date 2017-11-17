package cn.don9cn.blog.autoconfigs.activemq.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuxindong
 * @Description: activeMq常量管理
 * @Create: 2017/11/10 8:39
 * @Modify:
 */
@Configuration
public class MqConstant {

    /**
     * 默认queue
     */
    @Value("${spring.activemq.default-queue}")
    public  String DEFAULT_QUEUE;

    /**
     * 默认topic
     */
    @Value("${spring.activemq.default-topic}")
    public String DEFAULT_TOPIC;

    /**
     * 系统消息topic
     */
    @Value("${spring.activemq.system-msg-topic}")
    public String SYS_MSG_TOPIC;

    /**
     * 应用监听的clientId
     */
    @Value("${spring.activemq.system-listener-clientId}")
    public String SYS_LISTENER_CLIENT_ID;

    /**
     * 邮件消息topic
     */
    @Value("${spring.activemq.mail-msg-topic}")
    public String MAIL_MSG_TOPIC;


}
