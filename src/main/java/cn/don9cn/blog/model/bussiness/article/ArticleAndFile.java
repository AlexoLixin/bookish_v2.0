package cn.don9cn.blog.model.bussiness.article;


import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.model.BaseModel;
import cn.don9cn.blog.model.system.file.UploadFile;

import java.io.Serializable;
import java.util.List;

/**
 *@Author: liuxindong
 *@Description: 文章实体和上传文件的中间表实体
 *@Create: 2017/10/8 19:50
 *@Modify:
 **/
@DbCollection
public class ArticleAndFile extends BaseModel {

    @DbColumn(content = "文章主键")
    private String articleCode;

    @DbColumn(content = "上传文件主键")
    private String fileCode;

    public ArticleAndFile(){}

    public ArticleAndFile(String articleCode,String fileCode){
        this.articleCode = articleCode;
        this.fileCode = fileCode;
    }

    public ArticleAndFile(String fileCode){
        this.fileCode = fileCode;
    }

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
