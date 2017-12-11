package cn.don9cn.blog.service.system.rbac.interf;


import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.BaseService;

import javax.servlet.http.HttpServletRequest;

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
    Object findByUserName(String username);

    /**
     * 检查用户名是否已经存在
     * @param username
     * @return
     */
    Object checkUserName(String username);

    /**
     * 为用户进行角色授权
     * @param userCode
     * @param roleCodes
     * @return
     */
    Object authorizeUser(String userCode,String roleCodes);

    /**
     * 获取用户个人信息(开放给普通用户)
     * @return
     */
    Object getUserInfo();

    /**
     * 更新用户个人信息(开放给普通用户)
     * @param sysUser
     * @return
     */
    Object updateUserInfo(SysUser sysUser);

    /**
     * 注册用户
     * @param validateCode
     * @param sysUser
     * @return
     */
    Object register(String validateCode, SysUser sysUser, HttpServletRequest request);
}

