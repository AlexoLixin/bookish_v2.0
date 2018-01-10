package cn.don9cn.blog.autoconfigure.activemq.model;

import cn.don9cn.blog.util.DateExtKt;

/**
 * @Author: liuxindong
 * @Description: 用户文章订阅邮件消息实体
 * @Create: 2017/11/10 8:53
 * @Modify:
 */
public class SubscribeMailMessage implements MqMessage {

    private final String title;

    private final String author;

    private final String articleCode;

    private String createTime = DateExtKt.getNowDate();

    public SubscribeMailMessage(String title, String author, String articleCode) {
        this.title = title;
        this.author = author;
        this.articleCode = articleCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getArticleCode() {
        return articleCode;
    }
}
