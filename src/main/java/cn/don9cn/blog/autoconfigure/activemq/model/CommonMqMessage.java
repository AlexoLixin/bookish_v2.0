package cn.don9cn.blog.autoconfigure.activemq.model;


import cn.don9cn.blog.util.DateExtKt;

/**
 * @Author: liuxindong
 * @Description: 通用消息实体
 * @Create: 2017/10/23 10:16
 * @Modify:
 */
public class CommonMqMessage implements MqMessage {

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发布者
     */
    private String producer;

    /**
     * 发布时间
     */
    private String createTime = DateExtKt.getNowDate();

    /**
     * 消息链接
     */
    private String link;

    public CommonMqMessage(){

    }

    public CommonMqMessage(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
