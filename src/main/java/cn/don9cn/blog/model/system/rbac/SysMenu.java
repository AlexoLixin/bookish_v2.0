package cn.don9cn.blog.model.system.rbac;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.model.BaseModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单实体
 * @author Don9
 * @create 2017-05-03-22:58
 **/
@DbCollection
public class SysMenu extends BaseModel {



    @DbColumn(content = "菜单名称")
    private String name;

    @DbColumn(content = "菜单路径")
    private String url;

    @DbColumn(content = "父级菜单")
    private String parent;

    @DbColumn(content = "菜单等级")
    private String level;

    @DbColumn(content = "是否是叶子节点")
    private String leaf;
    /**
     * 子菜单集合
     */
    private Set<SysMenu> children = new HashSet<SysMenu>();
    /**
     * 角色进行菜单授权时,回显是否勾选当前菜单
     */
    private boolean checked;

    public SysMenu() {
    }

    public SysMenu(String code, String leaf) {
        this.setCode(code);
        this.leaf = leaf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Set<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(Set<SysMenu> children) {
        this.children = children;
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


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

