package cn.don9cn.blog.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Don9
 * @create 2017-10-09-16:02
 **/
@Component
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("--- 执行跨域过滤器 ---");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-origin","http://localhost:8888");
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers","Accept, Origin, X-Requested-With, Content-Type, Last-Modified, authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
