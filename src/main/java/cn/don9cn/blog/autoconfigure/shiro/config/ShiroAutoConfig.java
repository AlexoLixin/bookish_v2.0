package cn.don9cn.blog.autoconfigure.shiro.config;

import cn.don9cn.blog.autoconfigure.shiro.core.MyShiroAuthcFilter;
import cn.don9cn.blog.autoconfigure.shiro.core.MyShiroCacheManager;
import cn.don9cn.blog.autoconfigure.shiro.core.MyShiroRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: liuxindong
 * @Description: 配置shiro
 * @Create: 2017/10/17 14:16
 * @Modify:
 */
@Configuration
public class ShiroAutoConfig {


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 设置拦截路径链,按照添加顺序进行拦截
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        // 设置不拦截的路径
        filterChainMap.put("/login/*","anon");                                  // 登录相关
        filterChainMap.put("/system/rbac/user/register","anon");                // 用户注册
        filterChainMap.put("/system/rbac/user/checkName","anon");                // 用户注册(检查用户名)
        filterChainMap.put("/bussiness/article/public","anon");                 // 加载文章
        filterChainMap.put("/bussiness/article/list/public","anon");            // 加载文章列表
        filterChainMap.put("/bussiness/articleClassify/tree/public","anon");    // 加载文章分类树
        filterChainMap.put("/bussiness/article/publish/new/public","anon");     // 加载最新发布的文章
        filterChainMap.put("/bussiness/articleComment/insert/public","anon");   // 发表文章留言
        filterChainMap.put("/bussiness/articleComment/tree/public","anon");     // 加载文章留言
        filterChainMap.put("/system/upload/image","anon");                      // 图片上传
        filterChainMap.put("/system/upload/fileDownLoad","anon");               // 文件下载
        filterChainMap.put("/msgWebSocket","anon");                             // 消息推送 webSocket
        filterChainMap.put("/sockjs/msgWebSocket","anon");
        filterChainMap.put("/chatRoomWebSocket","anon");                        // 聊天室 webSocket
        filterChainMap.put("/sockjs/chatRoomWebSocket","anon");
        filterChainMap.put("/bussiness/subscribe/public","anon");
        filterChainMap.put("/beans","anon");

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        // filterChainMap.put("/logout", "logout");

        // 拦截器先校验url是否是上述配置的匿名路径,最后,设置其他所有路径进行权限校验
        filterChainMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        // 将权限验证拦截器替换成自定义的MyShiroAuthcFilter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc",new MyShiroAuthcFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(CookieRememberMeManager cookieRememberMeManager,MyShiroRealm myShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm);
        // 设置缓存管理器
        securityManager.setCacheManager(MyShiroCacheManager.getInstance());
        // 设置rememberMe管理器
        securityManager.setRememberMeManager(cookieRememberMeManager);
        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        return new MyShiroRealm();
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager(){
        SimpleCookie rememberMeCookie = new SimpleCookie();
        rememberMeCookie.setName("rememberMe");
        rememberMeCookie.setHttpOnly(true);
        rememberMeCookie.setMaxAge(2592000);       //有效时间30天

        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));    //设置密钥
        manager.setCookie(rememberMeCookie);    //设置cookie
        return manager;
    }

}
