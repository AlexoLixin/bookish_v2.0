package cn.don9cn.blog.service.system.rbac.interf;

import cn.don9cn.blog.model.system.rbac.SysPermission;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.BaseService;

/**
 * @Author: liuxindong
 * @Description: 权限service接口
 * @Create: 2017/10/16 14:16
 * @Modify:
 */
public interface SysPermissionService extends BaseService<SysPermission> {

    /**
     * 添加分类
     * @param sysPermission
     * @return
     */
    OperaResult doSave(SysPermission sysPermission);

    /**
     * 获取分类树
     * @return
     */
    OperaResult getTree();

    /**
     * 删除分类
     * @param codes
     * @param levels
     * @return
     */
    OperaResult doRemove(String codes,String levels);

}
