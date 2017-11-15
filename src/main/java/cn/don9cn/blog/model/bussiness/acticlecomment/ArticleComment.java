package cn.don9cn.blog.model.bussiness.acticlecomment;

import cn.don9cn.blog.model.BaseModel;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 文章评论model
 * @Create: 2017/11/15 16:15
 * @Modify:
 */
public class ArticleComment extends BaseModel{

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 内容
     */
    private String content;

    /**
     * 关联的文章主键
     */
    private String articleCode;

    /**
     * 父节点
     */
    private String parent;

    /**
     * 回复主键集合
     */
    private List<String> replyCodes = new ArrayList<>();

    /**
     * 回复实体集合
     */
    private List<ArticleComment> replies = new ArrayList<>();


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getReplyCodes() {
        return replyCodes;
    }

    public void setReplyCodes(List<String> replyCodes) {
        this.replyCodes = replyCodes;
    }

    public List<ArticleComment> getReplies() {
        return replies;
    }

    public void setReplies(List<ArticleComment> replies) {
        this.replies = replies;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void addReply(ArticleComment reply){
        this.replies.add(reply);
    }
}
