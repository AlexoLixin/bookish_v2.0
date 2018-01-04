package cn.don9cn.blog.autoconfigure.shiro.core

import cn.don9cn.blog.autoconfigure.shiro.util.ShiroSessionUtil
import cn.don9cn.blog.model.system.rbac.SysRole
import cn.don9cn.blog.model.system.rbac.SysUser
import cn.don9cn.blog.service.system.rbac.SysRoleService
import cn.don9cn.blog.service.system.rbac.SysUserService
import cn.don9cn.blog.util.Md5Util
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired

/**
 * shiro验证及授权实现类
 */
open class MyShiroRealm : AuthorizingRealm() {

    @Autowired
    private val sysUserService: SysUserService? = null

    @Autowired
    private val sysRoleService: SysRoleService? = null

    /**
     * 用户授权(根据验证通过后的用户信息,在数据库中查找其对应的角色和权限,进行授权)
     * @param principalCollection
     * @return
     */
    override fun doGetAuthorizationInfo(principalCollection: PrincipalCollection): AuthorizationInfo {

        val username = super.getAvailablePrincipal(principalCollection) as String

        // 1.从session中获取用户的权限信息
        var info = ShiroSessionUtil.getAuthorizationInfo()
        if (info == null) {
            // 2.session中未缓存用户权限信息，从数据库查询，然后将结果放入session中作为缓存
            info = SimpleAuthorizationInfo()
            val user = ShiroSessionUtil.getUser()  //权限验证发生在登陆之后,所以此时session中是一定会有登录用户信息的
            user!!.roleCodes?.let { roleCodes_ ->
                roleCodes_.forEach { roleCode ->
                    sysRoleService!!.findRoleWithPermissions(roleCode)?.let { role ->
                        info.addRole(role.encoding)
                        role.menuList.forEach {
                            info.addObjectPermission(MyPermission(it.url!!, it.httpMethod!!))
                        }
                    }
                }
            }
            setAuthorizationInfoToSession(info)
        }
        return info
    }

    /**
     * 身份验证(根据controller层中subject.login(token)中的token,查找数据库中是否有此用户,有则验证通过,没有则验证失败)
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(authenticationToken: AuthenticationToken): AuthenticationInfo {

        val token = authenticationToken as UsernamePasswordToken
        val username = token.username
        val password = String(token.password)

        // 1.从session中获取用户认证信息
        var userInfo = ShiroSessionUtil.getAuthenticationInfo()
        if (userInfo == null) {
            // 2.session中没有用户认证信息，查询数据库，重新验证用户
            sysUserService!!.findByUserName(username)?.let {
                if (it.password == password) {
                    userInfo = SimpleAuthenticationInfo(username, password, this.name)
                    setUserToSession(it)
                    setAuthenticationInfoToSession(userInfo!!)
                } else {
                    throw UnknownAccountException("用户名或密码验证失败")
                }
            }?:throw UnknownAccountException("用户名或密码验证失败")
        }

        return userInfo!!
    }


    /**
     * 将登陆用户数据放入session,同时自动生成token并放入到session
     */
    private fun setUserToSession(user: SysUser) {
        setSession("currentUser", user)
        // 根据用户名和密码生成token,存入session
        setSession("token", Md5Util.getMD5(user.username + user.password))
    }

    /**
     * 将登陆用户认证信息存入session,作为缓存
     */
    private fun setAuthenticationInfoToSession(authenticationInfo: SimpleAuthenticationInfo) {
        setSession("AuthenticationInfo", authenticationInfo)
    }

    /**
     * 将用户权限信息存入session,作为缓存
     */
    private fun setAuthorizationInfoToSession(authorizationInfo: SimpleAuthorizationInfo) {
        setSession("AuthorizationInfo", authorizationInfo)
    }

    private fun setSession(key: Any, value: Any) {
        SecurityUtils.getSubject()?.session?.setAttribute(key, value)
    }

}