package cn.don9cn.blog.autoconfigure.activemq.constant;

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
    @Value("${spring.activemq.queue-default}")
    public  String DEFAULT_QUEUE;

    /**
     * 默认topic
     */
    @Value("${spring.activemq.topic-default}")
    public String DEFAULT_TOPIC;

    /**
     * 应用监听的clientId
     */
    @Value("${spring.activemq.system-listener-clientId}")
    public String SYS_LISTENER_CLIENT_ID;

    /**
     * 系统消息topic
     */
    @Value("${spring.activemq.topic-msg-system}")
    public String TOPIC_MSG_SYSTEM;

    /**
     * 注册邮件消息topic
     */
    @Value("${spring.activemq.topic-mail-register}")
    public String TOPIC_MAIL_REGISTER;

    /**
     * 订阅邮件消息topic
     */
    @Value("${spring.activemq.topic-mail-subscribe}")
    public String TOPIC_MAIL_SUBSCRIBE;

    /**
     * 用户queue前缀
     */
    @Value("${spring.activemq.queue-user-prefix}")
    public String QUEUE_USER_PREFIX;

    /**
     * 用户文章订阅topic前缀
     */
    @Value("${spring.activemq.topic-author-subscribe-prefix}")
    public String TOPIC_AUTHOR_SUBSCRIBE_PREFIX;


}
