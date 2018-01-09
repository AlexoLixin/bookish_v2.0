package cn.don9cn.blog.autoconfigure.activemq.model;

import cn.don9cn.blog.util.DateExtKt;

/**
 * @Author: liuxindong
 * @Description: 用户注册邮件消息实体
 * @Create: 2017/11/10 8:53
 * @Modify:
 */
public class RegisterMailMessage implements MqMessage {

    private final String username;

    private final String email;

    private String createTime = DateExtKt.getNowDate();

    public RegisterMailMessage(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
