package cn.don9cn.blog.model.system.rbac;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.model.BaseModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: liuxindong
 * @Description: 权限实体
 * @Create: 2017/10/16 13:58
 * @Modify:
 */
@DbCollection
public class SysPermission extends BaseModel {

    @DbColumn(content = "权限名称")
    private String name;

    @DbColumn(content = "权限路径")
    private String url;

    @DbColumn(content = "请求方法")
    private String httpMethod;

    @DbColumn(content = "父节点")
    private String parent;

    @DbColumn(content = "是否叶子节点")
    private String leaf;

    private List<String> childrenCodes = new ArrayList<>();

    /**
     * 子菜单集合
     */
    private List<SysPermission> children = new ArrayList<>();

    /**
     * 角色进行菜单授权时,回显是否勾选当前菜单
     */
    private boolean checked;

    public SysPermission() {
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public List<SysPermission> getChildren() {
        return children;
    }

    public void setChildren(List<SysPermission> children) {
        this.children = children;
    }

    public List<String> getChildrenCodes() {
        return childrenCodes;
    }

    public void setChildrenCodes(List<String> childrenCodes) {
        this.childrenCodes = childrenCodes;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public void addChild(SysPermission sysPermission) {
        this.children.add(sysPermission);
    }
}

