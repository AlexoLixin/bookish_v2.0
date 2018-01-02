package cn.don9cn.blog.autoconfigure.shiro.core

import org.apache.shiro.authz.Permission
import java.io.Serializable

/**
 * 自定义shiro Permission的实现
 */
class MyPermission(private val requestUrl: String, private val requestMethod: String) : Permission, Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

    /**
     * 权限验证,返回值为true时说明当前的请求与用户的权限相匹配
     * @param permission 用户请求的资源路径
     * @return
     */
    override fun implies(permission: Permission): Boolean {
        val request = permission as MyPermission
        if (this.requestMethod == "ALL") {
            if (request.requestUrl.startsWith(this.requestUrl))
                return true
        } else {
            if (request.requestUrl == this.requestUrl && request.requestMethod == this.requestMethod)
                return true
        }
        return false
    }

}