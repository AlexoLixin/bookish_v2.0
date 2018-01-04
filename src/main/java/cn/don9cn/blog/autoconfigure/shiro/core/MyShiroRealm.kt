package cn.don9cn.blog.autoconfigure.shiro.core

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

    init {
        this.cacheManager = MyShiroCacheManager.getInstance()
    }

    /**
     * 用户授权(根据验证通过后的用户信息,在数据库中查找其对应的角色和权限,进行授权)
     * @param principalCollection
     * @return
     */
    override fun doGetAuthorizationInfo(principalCollection: PrincipalCollection): AuthorizationInfo {

        val username = super.getAvailablePrincipal(principalCollection) as String

        val userCache = this.cacheManager.getCache<String,Any>(username)    //从cacheManager缓存管理器中获取用户缓存
        var info = userCache.get("AuthorizationInfo")                       //获取用户授权信息缓存
        if (info == null) {                                                 //缓存为null,重新查询
            info = SimpleAuthorizationInfo()
            val user = userCache.get("UserBean") as? SysUser          //从缓存中获取用户实体
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
            userCache.put("AuthorizationInfo",info)         //将从数据库新查询的用户授权信息放入缓存
        }
        return (info as SimpleAuthorizationInfo)
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
        // 1.从cacheManager缓存管理器中获取用户缓存
        val userCache = this.cacheManager.getCache<String,Any>(username)
        var info = userCache.get("AuthenticationInfo")
        if (info == null) {
            // 2.缓存中没有用户认证信息，查询数据库，重新验证用户
            sysUserService!!.findByUserName(username)?.let {
                if (it.password == password) {
                    info = SimpleAuthenticationInfo(username, password, this.name)
                    setUserToSession(it)
                    userCache.put("UserBean",it)                //将用户实体存入缓存
                    userCache.put("AuthenticationInfo",info)    //将用户认证信息存入缓存
                } else {
                    throw UnknownAccountException("用户名或密码验证失败")
                }
            }?:throw UnknownAccountException("用户名或密码验证失败")
        }

        return (info as SimpleAuthenticationInfo)
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