package cn.don9cn.blog.service.system.rbac.interf;


import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.BaseService;

/**
 * @Author: liuxindong
 * @Description: 角色service接口
 * @Create: 2017/10/16 10:03
 * @Modify:
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 为角色进行权限授权
     * @param roleCode
     * @param permissionCodes
     * @return
     */
    OperaResult authorizeRole(String roleCode, String permissionCodes);
}
