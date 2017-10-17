package cn.don9cn.blog.autoconfigs.shiro.core;


import cn.don9cn.blog.autoconfigs.shiro.util.SessionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liuxindong
 * @Description: 自定义shiro拦截器
 * @Create: 2017/10/17 14:15
 * @Modify:
 */
public class MyShiroFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;



        //请求路径
        String requestURI = request.getRequestURI();
        //请求方法
        String requestMethod = request.getMethod();

        //测试阶段,直接放行前端的跨域option请求
        if(requestMethod.equalsIgnoreCase("OPTIONS")){
            filterChain.doFilter(request,response);
            return;
        }

        Subject subject = SecurityUtils.getSubject();

        if(subject.isAuthenticated()){

            //已登录,检查用户token与当前用户是否一致
            String token = request.getHeader("authorization");
            // token不一致,提示重新登录
            if(!SessionUtil.getTokenFromSession().equals(token)){
                response.sendRedirect("/login/reLogin");
                return;
            }
            // 验证权限,无权限的话提示并禁止操作
            if(!subject.isPermitted(new MyPermission(requestURI,requestMethod))){
                response.sendRedirect("/login/noPermission");
                return;
            }

        }else{
            // 未登录,提示登录
            response.sendRedirect("/login/needLogin");
            return;
        }


        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
