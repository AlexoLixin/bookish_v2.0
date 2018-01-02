package cn.don9cn.blog.autoconfigure.shiro.util

import cn.don9cn.blog.model.system.SysUser
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo


object ShiroSessionUtil {

    /**
     * 从session获取用户数据
     */
    fun getUser(): SysUser? {
        return SecurityUtils.getSubject()?.session?.getAttribute("currentUser") as? SysUser
    }

    /**
     * 从session获取用户主键
     */
    fun getUserCode(): String {
        return getUser()?.code ?: ""
    }

    /**
     * 从session获取用户名称
     */
    fun getUserName(): String {
        return getUser()?.username ?: ""
    }

    /**
     * 从session获取token
     */
    fun getToken(): String? {
        return SecurityUtils.getSubject()?.session?.getAttribute("token")?.toString()
    }

    /**
     * 获取用户登录认证信息
     */
    fun getAuthenticationInfo(): SimpleAuthenticationInfo? {
        return SecurityUtils.getSubject()?.session?.getAttribute("AuthenticationInfo") as? SimpleAuthenticationInfo
    }

    /**
     * 获取用户权限信息
     */
    fun getAuthorizationInfo(): SimpleAuthorizationInfo? {
        return SecurityUtils.getSubject()?.session?.getAttribute("AuthorizationInfo") as? SimpleAuthorizationInfo
    }
}