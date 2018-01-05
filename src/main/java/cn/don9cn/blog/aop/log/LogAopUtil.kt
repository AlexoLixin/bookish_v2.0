package cn.don9cn.blog.aop.log

import cn.don9cn.blog.annotation.SkipOperaLog
import cn.don9cn.blog.autoconfigure.shiro.core.MyShiroCacheManager
import cn.don9cn.blog.autoconfigure.shiro.util.ShiroUtil
import cn.don9cn.blog.model.system.log.SysExceptionLog
import cn.don9cn.blog.model.system.log.SysOperaLog
import org.aspectj.lang.JoinPoint
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.ArrayList

object LogAopUtil {

    /**
     * 解析成操作日志
     */
    fun parseToSysOperaLog(joinPoint: JoinPoint): SysOperaLog {
        val sysOperaLog = SysOperaLog()

        with(sysOperaLog){
            val exceClass = joinPoint.target.javaClass
            ignoreSave = if (exceClass.getAnnotation(SkipOperaLog::class.java) != null) {   //是否不保存该action日志
                "Y"
            } else {
                "N"
            }
            actionName = exceClass.typeName                                             //action全类名
            module = exceClass.simpleName.replace("Action", "")     //模块名称
            methodName = joinPoint.signature.name                                       //执行方法名称

            userCode = MyShiroCacheManager.getUserCode()                   // 执行用户

            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            requestUrl = request.requestURI         //请求url
            requestMethod = request.method          //请求method

            when (request.method) {
                "POST" -> type = "添加"
                "DELETE" -> type = "删除"
                "PUT" -> type = "更新"
                "GET" -> {
                    type = "查询"
                    ignoreSave = "Y"
                }
                else -> {}
            }

            val requestParams = ArrayList<String>()
            val parameterNames = request.parameterNames   //请求参数
            while (parameterNames.hasMoreElements()) {
                val paramName = parameterNames.nextElement()
                requestParams.add(paramName + " : " + request.getParameter(paramName))
            }
            params = requestParams
        }

        return sysOperaLog
    }

    /**
     * 解析异常信息并填充到异常日志
     */
    fun fillSysExceptionLog(sysExceptionLog: SysExceptionLog, e: Throwable): SysExceptionLog {
        return sysExceptionLog.apply {
            exceptionType = e.javaClass.name
            message = e.message
            stackTrace = e.stackTrace?.map { it.toString() }?.toList()

        }
    }
}