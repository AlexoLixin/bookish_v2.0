package cn.don9cn.blog.model.bussiness.article;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.model.BaseModel;
import cn.don9cn.blog.model.system.file.UploadFile;

import java.io.Serializable;
import java.util.List;

/**
 *@Author: liuxindong
 *@Description: 文章实体
 *@Create: 2017/10/8 19:50
 *@Modify:
 **/
@DbCollection
public class Article extends BaseModel implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    @DbColumn(content = "文章标题")
    private String title;

    @DbColumn(content = "文章作者")
    private String author;

    @DbColumn(content = "文章分类")
    private String classify;

    @DbColumn(content = "文章内容")
    private String content;

    @DbColumn(content = "附件code")
    private String files;

    /**
     * 附件实体
     */
    private List<UploadFile> filesList;

    /**
     * 分类名称
     */
    private String classifyName;

    @Override
    public String toString() {
        return "Article [code=" + getCode() + ", title=" + title + ", author=" + author + ", classify=" + classify + ", content=" + content + ", files=" + files + "]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public List<UploadFile> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<UploadFile> filesList) {
        this.filesList = filesList;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }


}
