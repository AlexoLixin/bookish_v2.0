package cn.don9cn.blog.service.system.rbac.interf;

import cn.don9cn.blog.model.system.rbac.SysPermission;
import cn.don9cn.blog.service.BaseService;

/**
 * @Author: liuxindong
 * @Description: 权限service接口
 * @Create: 2017/10/16 14:16
 * @Modify:
 */
public interface SysPermissionService extends BaseService<SysPermission> {


    /**
     * 获取分类树
     * @return
     */
    Object getTree();

}
