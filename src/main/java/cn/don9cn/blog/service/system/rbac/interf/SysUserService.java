package cn.don9cn.blog.service.system.rbac.interf;


import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.operation.core.OperaResult;
import cn.don9cn.blog.service.BaseService;

/**
 *@Author: liuxindong
 *@Description: 用户service接口
 *@Create: 2017/10/15 11:09
 *@Modify:
 **/
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    OperaResult findByUserName(String username);

    /**
     * 检查用户名是否已经存在
     * @param username
     * @return
     */
    OperaResult checkUserName(String username);

    /**
     * 为用户进行角色授权
     * @param userCode
     * @param roleCodes
     * @return
     */
    OperaResult authorizeUser(String userCode,String roleCodes);

}

