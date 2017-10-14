package cn.don9cn.blog.model.system.file;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.model.BaseModel;

import java.io.Serializable;

/**
 *@Author: liuxindong
 *@Description: 上传文件实体
 *@Create: 2017/10/8 19:50
 *@Modify:
 **/
@DbCollection
public class UploadFile extends BaseModel{



    @DbColumn(content = "文件名称")
    private String name;

    @DbColumn(content = "真实名称")
    private String realName;

    @DbColumn(content = "文件路径")
    private String path;

    @DbColumn(content = "关联来源")
    private String link;

    public UploadFile(){}

    public UploadFile(String realName,String name,String path){
        this.realName = realName;
        this.name = name;
        this.path = path;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}
