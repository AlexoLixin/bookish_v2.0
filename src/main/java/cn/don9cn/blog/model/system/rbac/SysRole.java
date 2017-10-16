package cn.don9cn.blog.model.system.rbac;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 角色实体
 * @Create: 2017/10/12 9:03
 * @Modify:
 */
@DbCollection
public class SysRole extends BaseModel {


    @DbColumn(content = "角色名称")
    private String name;

    @DbColumn(content = "角色编码")
    private String encoding;

    @DbColumn(content = "图标")
    private String icon;

    /**
     * 权限菜单主键集合
     */
    private List<SysPermission> menuCodesList = new ArrayList<>();

    /**
     * 权限菜单集合
     */
    private List<SysPermission> menuList = new ArrayList<>();

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public List<SysPermission> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysPermission> menuList) {
        this.menuList = menuList;
    }

    public List<SysPermission> getMenuCodesList() {
        return menuCodesList;
    }

    public void setMenuCodesList(List<SysPermission> menuCodesList) {
        this.menuCodesList = menuCodesList;
    }
}
