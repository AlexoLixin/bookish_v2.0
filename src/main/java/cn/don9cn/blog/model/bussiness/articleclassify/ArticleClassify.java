package cn.don9cn.blog.model.bussiness.articleclassify;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbTable;
import cn.don9cn.blog.annotation.MapperNameSpace;
import cn.don9cn.blog.model.BaseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 文章分类实体
 * @Create: 2017/10/10 10:14
 * @Modify:
 */
@DbTable
@MapperNameSpace(namespace = "cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify.mapper")
public class ArticleClassify extends BaseModel implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    @DbColumn(allowNull = false,content = "分类名称")
    private String name;
    /**
     * 分类父节点
     */
    @DbColumn(allowNull = false,content = "分类父节点")
    private String parent;
    /**
     * 分类等级
     */
    @DbColumn(allowNull = false,content = "分类等级")
    private String level;

    /**
     * 是否是叶子节点
     */
    @DbColumn(allowNull = false,length = "10",content = "是否是叶子节点")
    private String leaf;

    /**
     * 子节点结合
     */
    private List<ArticleClassify> children = new ArrayList<>();

    public ArticleClassify() {}

    public ArticleClassify(String code, String leaf) {
        this.setCode(code);
        this.leaf = leaf;
    }

    /**
     * 构造根节点
     * @return
     */
    public ArticleClassify root(){
        ArticleClassify articleClassify = new ArticleClassify();
        articleClassify.setCode("ROOT");
        articleClassify.setName("ROOT");
        articleClassify.setParent("NONE");
        articleClassify.setLeaf("N");
        articleClassify.setLevel("0");
        return articleClassify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public List<ArticleClassify> getChildren() {
        return children;
    }

    public void setChildren(List<ArticleClassify> children) {
        this.children = children;
    }
}
