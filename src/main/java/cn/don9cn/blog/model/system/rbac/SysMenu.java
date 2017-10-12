package cn.don9cn.blog.model.system.rbac;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbTable;
import cn.don9cn.blog.annotation.MapperNameSpace;
import cn.don9cn.blog.model.BaseModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单实体
 * @author Don9
 * @create 2017-05-03-22:58
 **/
@DbTable
@MapperNameSpace(namespace = "cn.don9cn.blog.model.system.rbac.SysMenu.mapper")
public class SysMenu extends BaseModel implements Serializable,Comparable<SysMenu> {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @DbColumn(allowNull = false,content = "菜单名称")
    private String name;
    /**
     * 菜单路径
     */
    @DbColumn(length = "200",allowNull = false,content = "菜单路径")
    private String url;
    /**
     * 父级菜单
     */
    @DbColumn(allowNull = false,content = "父级菜单")
    private String parent;
    /**
     * 菜单等级
     */
    @DbColumn(allowNull = false,content = "菜单等级")
    private String level;
    /**
     * 叶子状态
     */
    @DbColumn(allowNull = false,length = "10",content = "菜单等级")
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

    @Override
    public int compareTo(SysMenu o) {
        return Integer.parseInt(this.level) - Integer.parseInt(o.getLevel());
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

