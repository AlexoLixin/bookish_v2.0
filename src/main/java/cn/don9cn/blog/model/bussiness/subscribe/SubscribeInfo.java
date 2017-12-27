package cn.don9cn.blog.model.bussiness.subscribe;

import cn.don9cn.blog.model.BaseModel;

/**
 * @Author: liuxindong
 * @Description:  订阅信息实体
 * @Created: 2017/12/27 11:13
 * @Modified:
 */
public class SubscribeInfo extends BaseModel {

    private String email;

    private String author;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
