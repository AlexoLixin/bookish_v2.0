package cn.don9cn.blog.autoconfigure.webregistration.filter;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liuxindong
 * @Description: 开发环境下的跨域过滤器
 * @Create: 2017/10/12 10:23
 * @Modify:
 */
@Component
@Profile("production")
public class ProCorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-origin","http://47.94.206.26");
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers","Accept, Origin, X-Requested-With, Content-Type, Last-Modified, authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
