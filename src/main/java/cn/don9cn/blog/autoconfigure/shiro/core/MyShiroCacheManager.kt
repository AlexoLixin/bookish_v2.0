package cn.don9cn.blog.autoconfigure.shiro.core

import cn.don9cn.blog.model.system.rbac.SysUser
import org.apache.shiro.SecurityUtils
import org.apache.shiro.cache.AbstractCacheManager
import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.MapCache
import org.apache.shiro.subject.SimplePrincipalCollection
import java.util.concurrent.ConcurrentHashMap

/**
 * shiro缓存管理器
 */
object MyShiroCacheManager: AbstractCacheManager() {

    override fun createCache(username: String): Cache<*, *> {
        return MapCache(username, ConcurrentHashMap<String, Any>())
    }

    fun getInstance():MyShiroCacheManager{
        return this
    }

    fun getUser(): SysUser? {
        val cache = getCurrentUserCache()
        return cache?.get("UserBean") as? SysUser
    }

    fun getUserCode(): String {
        return getUser()?.code?:""
    }

    fun getUserName(): String {
        return getUser()?.username?:""
    }

    fun getUserRoleName(): String {
        return getUser()?.roleNames?:""
    }

    fun checkAdmin(): Boolean {
        var admin = false
        getUser()?.roleList?.forEach {
            if(it.encoding!!.contains("ADMIN")){
                admin = true
            }
        }
        return admin
    }

    fun getToken(): String {
        return getCurrentUserCache()?.get("Token")?.toString()?:""
    }

    fun getCurrentUserCache(): Cache<String, Any>? {
        val principals = SecurityUtils.getSubject().principals as? SimplePrincipalCollection
        return if(principals != null){
            this.getCache(principals.toString())
        }else{
            null
        }
    }

}