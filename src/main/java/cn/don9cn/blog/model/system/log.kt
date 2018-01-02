package cn.don9cn.blog.model.system

import cn.don9cn.blog.model.BaseModel

/**
 * @Author: liuxindong
 * @Description:  操作日志实体
 * @Created: 2017/12/22 15:32
 * @Modified:
 */
class SysOperaLog : BaseModel() {
    var requestUrl: String? = null      //请求url
    var requestMethod: String? = null   //请求方法
    var type: String? = null            //操作类型
    var module: String? = null          //操作模块
    var actionName: String? = null      //操作action
    var methodName: String? = null      //操作方法
    var params: List<String>? = null    //请求参数
    var costTime: String? = null        //操作用时
    var state: String? = null           //操作状态
    var userCode: String? = null        //操作用户主键
    var userName: String? = null        //操作用户名称
    var userRole: String? = null        //操作用户角色
    var ignoreSave: String? = null      //是否保存到数据库
}

/**
 * @Author: liuxindong
 * @Description:  登录日志实体
 * @Created: 2017/12/22 15:34
 * @Modified:
 */
class SysLoginLog : BaseModel() {
    var hostName: String? = null        //主机名
    var systemName: String? = null      //系统版本
    var browser: String? = null         //浏览器
    var ip: String? = null              //登录ip
    var username: String? = null        //登录名
    var password: String? = null        //密码
    var state: String? = null           //登录状态

    fun withState(state: String): SysLoginLog {
        this.state = state
        return this
    }
}

/**
 * @Author: liuxindong
 * @Description:  异常日志实体
 * @Created: 2017/12/22 15:40
 * @Modified:
 */
class SysExceptionLog() : BaseModel() {
    var requestUrl: String? = null      //请求url
    var requestMethod: String? = null   //请求方法
    var type: String? = null            //操作类型
    var module: String? = null          //操作模块
    var actionName: String? = null      //操作action
    var methodName: String? = null      //操作方法
    var params: List<String>? = null    //请求参数
    var costTime: String? = null        //操作用时
    var state: String? = null           //操作状态
    var userCode: String? = null        //操作用户主键
    var userName: String? = null        //操作用户名称
    var userRole: String? = null        //操作用户角色
    var ignoreSave: String? = null      //是否保存到数据库
    var exceptionType: String? = null   //异常类型
    var message: String? = null         //异常消息
    var stackTrace: List<String>? = null    //异常堆栈

    constructor(sysOperaLog: SysOperaLog):this() {
        with(sysOperaLog){
            requestUrl?.let { this.requestUrl = it }
            requestMethod?.let { this.requestMethod = it }
            type?.let { this.type = it }
            module?.let { this.module = it }
            actionName?.let { this.actionName = it }
            methodName?.let { this.methodName = it }
            params?.let { this.params = it }
            userCode?.let { this.userCode = it }
            costTime?.let { this.costTime = it }
            state?.let { this.state = it }
            userName?.let { this.userName = it }
            userRole?.let { this.userRole = it }
            this.ignoreSave = ignoreSave
        }
    }
}
