package cn.don9cn.blog.model.system.msg;

import cn.don9cn.blog.util.DateUtil;


/**
 * @Author: liuxindong
 * @Description: 消息实体
 * @Create: 2017/10/23 10:16
 * @Modify:
 */
public class SysMessage {

    /**
     * 所属topic
     */
    private String topic;

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
    private String createTime = DateUtil.getCreateDateString();

    /**
     * 消息链接
     */
    private String link;

    public SysMessage(){

    }

    public SysMessage(String title,String content){
        this.title = title;
        this.content = content;
    }

    public SysMessage(String topic,String title,String content){
        this.topic = topic;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
