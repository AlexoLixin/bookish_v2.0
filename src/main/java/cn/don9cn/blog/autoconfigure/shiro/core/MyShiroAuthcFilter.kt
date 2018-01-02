package cn.don9cn.blog.autoconfigure.shiro.core

import cn.don9cn.blog.autoconfigure.shiro.util.ShiroSessionUtil
import org.apache.shiro.SecurityUtils
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * 自定义shiro拦截器
 */
class MyShiroAuthcFilter : Filter {

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {

    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse
        //项目名称
        val contextPath = request.contextPath
        //请求全路径
        val requestURI = request.requestURI
        //去掉项目名称后的请求地址
        val targetURL = requestURI.split(contextPath.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        //请求方法
        val requestMethod = request.method

        //测试阶段,直接放行前端的跨域option请求
        if (requestMethod.equals("OPTIONS", ignoreCase = true)) {
            filterChain.doFilter(request, response)
            return
        }

        val subject = SecurityUtils.getSubject()

        if (subject.isAuthenticated) {

            //已登录,检查用户token与当前用户是否一致
            val token = request.getHeader("authorization")
            // token不一致,提示重新登录
            if (ShiroSessionUtil.getToken() != token) {
                response.sendRedirect(/*contextPath+*/"/login/reLogin")
                return
            }
            // 验证权限,无权限的话提示并禁止操作
            if (!subject.isPermitted(MyPermission(requestURI, requestMethod))) {
                response.sendRedirect(/*contextPath+*/"/login/noPermission")
                return
            }

        } else {
            // 未登录,提示登录
            response.sendRedirect(/*contextPath+*/"/login/needLogin")
            return
        }


        filterChain.doFilter(request, response)
    }

    override fun destroy() {

    }
}