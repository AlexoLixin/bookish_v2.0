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
    private var roleName: String? = null
    private var username: String? = null

    fun getToken(): String? = token
    fun isAdmin(): Boolean = admin
    fun getUsername(): String? = username
    fun getRoleName(): String? = roleName

    fun setToken(token: String?): LoginResult {
        this.token = token
        return this
    }

    fun setAdmin(admin: Boolean): LoginResult {
        this.admin = admin
        return this
    }

    fun setUsername(username: String?): LoginResult {
        this.username = username
        return this
    }

    fun setRoleName(roleName: String?): LoginResult {
        this.roleName = roleName
        return this
    }

}
