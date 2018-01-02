package cn.don9cn.blog.model.system

import cn.don9cn.blog.model.BaseModel
import java.util.ArrayList


/**
 * @Author: liuxindong
 * @Description:  用户实体
 * @Created: 2017/12/22 15:45
 * @Modified:
 */
class SysUser() : BaseModel() {
    var username: String? = null        //用户名
    var password: String? = null        //密码
    var email: String? = null           //邮箱
    var phone: String? = null           //手机号码
    var realName: String? = null        //真实姓名
    var age: Int? = null                //年龄
    var gender: String? = null          //性别
    var byWay: String? = null           //创建方式
    var roleCodes: List<String>? = null     //角色主键
    var roleList: List<SysRole>? = null     //角色实体集合
    var roleNames: String? = null           //角色名称,用于前端显示

    constructor(code: String):this() {
        this.code = code
    }

    constructor(code: String, roleCodes: String):this(code) {
        this.roleCodes = roleCodes.split(",")
    }
}

/**
 * @Author: liuxindong
 * @Description:  角色实体
 * @Created: 2017/12/22 15:49
 * @Modified:
 */
class SysRole() : BaseModel() {
    var name: String? = null            //角色名称
    var encoding: String? = null        //角色编码
    var icon: String? = null            //图标
    var menuCodesList: List<String>? = null     //权限菜单主键集合
    var menuList: List<SysPermission> = ArrayList()     //权限菜单集合

    constructor(roleCode: String, permissionCodes: String):this() {
        this.code = roleCode
        this.menuCodesList = permissionCodes.split(",")
    }
}


/**
 * @Author: liuxindong
 * @Description:  权限实体
 * @Created: 2017/12/22 15:51
 * @Modified:
 */
class SysPermission : BaseModel() {
    var name: String? = null            //权限名称
    var url: String? = null             //权限路径
    var httpMethod: String? = null      //请求方法
    var parent: String? = null          //父节点
    var leaf: String? = null            //是否叶子节点
    var childrenCodes: List<String> = ArrayList()       //子菜单主键集合
    var children: MutableList<SysPermission> = ArrayList()  //子菜单集合
    var isChecked: Boolean = false      //角色进行菜单授权时,回显是否勾选当前菜单

    fun addChild(sysPermission: SysPermission) {
        this.children.add(sysPermission)
    }
}

/**
 * 注册结果实体
 */
data class RegisterResult(val success:Boolean,val message:String)
