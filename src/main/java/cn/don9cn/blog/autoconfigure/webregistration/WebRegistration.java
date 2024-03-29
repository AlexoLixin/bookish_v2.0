package cn.don9cn.blog.autoconfigure.webregistration;

import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuxindong
 * @Description: 集中管理自定义 filter / listener / servlet 的注册
 * @Create: 2017/10/12 10:16
 * @Modify:
 */
@Configuration
public class WebRegistration {

    /*@Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new IndexServlet());
        registration.addUrlMappings("/hello");
        return registration;
    }*/

    /**
     * 跨域filter注册
     * @return
     */
    /*@Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new DevCorsFilter());
        registration.addUrlPatterns("*//*");
        return registration;
    }*/

    /**
     * listener注册
     * @return
     */
    /*@Bean
    @ConditionalOnBean(DatabaseBuilderDaoImpl.class)
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new DatabaseBuilderListener());
        return servletListenerRegistrationBean;
    }*/
}
