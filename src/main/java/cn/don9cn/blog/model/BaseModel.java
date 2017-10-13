package cn.don9cn.blog.model;


import cn.don9cn.blog.annotation.DbColumn;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Map;

/**
 *@Author: liuxindong
 *@Description: 基础model
 *@Create: 2017/10/8 19:42
 *@Modify:
 **/
public class BaseModel implements Serializable {

    @DbColumn(content = "主键")
    @Id
    private String code;

    @DbColumn(content = "删除标记")
    private String flagDel = "N";

    @DbColumn(content = "创建时间")
    private String createTime;

    @DbColumn(content = "修改时间")
    private String modifyTime;

    @DbColumn(content = "创建人")
    private String createBy;

    @DbColumn(content = "修改人")
    private String modifyBy;

    @DbColumn(content = "备用字段1")
    private String zMore01;

    @DbColumn(content = "备用字段2")
    private String zMore02;

    @DbColumn(content = "备用字段3")
    private String zMore03;

    @DbColumn(content = "备用字段4")
    private String zMore04;

    @DbColumn(content = "备用字段5")
    private String zMore05;

    @DbColumn(content = "说明")
    private String remark;

    @DbColumn(content = "排序")
    private String num;

    private String orderBy;

    /**
     * 字典缓存
     */
    private Map<String,Object> dictMap;


    public String getFlagDel() {
        return flagDel;
    }

    public void setFlagDel(String flagDel) {
        this.flagDel = flagDel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getzMore01() {
        return zMore01;
    }

    public void setzMore01(String zMore01) {
        this.zMore01 = zMore01;
    }

    public String getzMore02() {
        return zMore02;
    }

    public void setzMore02(String zMore02) {
        this.zMore02 = zMore02;
    }

    public String getzMore03() {
        return zMore03;
    }

    public void setzMore03(String zMore03) {
        this.zMore03 = zMore03;
    }

    public String getzMore04() {
        return zMore04;
    }

    public void setzMore04(String zMore04) {
        this.zMore04 = zMore04;
    }

    public String getzMore05() {
        return zMore05;
    }

    public void setzMore05(String zMore05) {
        this.zMore05 = zMore05;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, Object> getDictMap() {
        return dictMap;
    }

    public void setDictMap(Map<String, Object> dictMap) {
        this.dictMap = dictMap;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
