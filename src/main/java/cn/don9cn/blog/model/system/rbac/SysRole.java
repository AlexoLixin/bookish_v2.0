package cn.don9cn.blog.model.system.rbac;


import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.annotation.DbTable;
import cn.don9cn.blog.annotation.MapperNameSpace;
import cn.don9cn.blog.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 角色实体
 * @Create: 2017/10/12 9:03
 * @Modify:
 */
@DbTable
@MapperNameSpace(namespace = "cn.don9cn.blog.model.system.rbac.SysRole.mapper")
public class SysRole extends BaseModel implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;
    /**
     * 角色名称
     */
    @DbColumn(allowNull = false,content = "角色名称")
    private String name;
    /**
     * 角色编码
     */
    @DbColumn(allowNull = false,content = "角色编码")
    private String encoding;
    /**
     * 图标
     */
    @DbColumn(content = "图标")
    private String icon;
    /**
     * 权限菜单集合
     */
    private List<SysMenu> menuList;

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

    public List<SysMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenu> menuList) {
        this.menuList = menuList;
    }
}
