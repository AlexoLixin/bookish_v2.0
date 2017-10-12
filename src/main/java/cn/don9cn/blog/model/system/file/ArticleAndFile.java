package cn.don9cn.blog.model.system.file;

import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.model.BaseModel;

import java.io.Serializable;

/**
 * @Author: liuxindong
 * @Description: 文章和附件的关联实体
 * @Create: 2017/10/12 9:24
 * @Modify:
 */
@DbCollection
public class ArticleAndFile extends BaseModel implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    @DbColumn(content = "文章主键")
    private String articleCode;

    @DbColumn(content = "附件主键")
    private String fileCode;


    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}
