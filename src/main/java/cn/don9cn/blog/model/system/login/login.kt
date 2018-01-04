package cn.don9cn.blog.model.system.login

/**
 * @Author: liuxindong
 * @Description:  登陆结果返回实体
 * @Created: 2017/12/22 15:54
 * @Modified:
 */
class LoginResult(val isSuccess: Boolean, val message: String) {
    private var token: String? = null
    private var admin: Boolean = false
    private var user: Any? = null

    fun getToken(): String? = token
    fun isAdmin(): Boolean = admin
    fun getUser(): Any? = user

    fun setToken(token: String?): LoginResult {
        this.token = token
        return this
    }

    fun setAdmin(admin: Boolean): LoginResult {
        this.admin = admin
        return this
    }

    fun setUser(user: Any?): LoginResult {
        this.user = user
        return this
    }

}
