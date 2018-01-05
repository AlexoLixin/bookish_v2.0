package cn.don9cn.blog.autoconfigure.shiro.core;

import cn.don9cn.blog.model.system.rbac.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.subject.SimplePrincipalCollection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Don9
 * @create 2018-01-04-16:06
 **/
public class MyShiroCacheManager extends AbstractCacheManager {

    private MyShiroCacheManager(){}

    private static final MyShiroCacheManager INSTANCE = new MyShiroCacheManager();

    public static MyShiroCacheManager getInstance() {
        return INSTANCE;
    }

    @Override
    protected Cache createCache(String username) throws CacheException {
        return new MapCache<String,Object>(username,new ConcurrentHashMap<>());
    }

    public static SysUser getUser(){
        Cache<String, Object> cache = getUserCache();
        Object userBean = cache.get("UserBean");
        if(userBean!=null){
            return (SysUser)userBean;
        }
        return null;
    }

    public static String getUserCode(){
        SysUser user = getUser();
        if(user != null){
            return user.getCode();
        }
        return "";
    }

    public static String getUserName(){
        SysUser user = getUser();
        if(user != null){
            return user.getUsername();
        }
        return "";
    }

    public static String getToken(){
        Cache<String, Object> cache = getUserCache();
        Object token = cache.get("Token");
        if(token!=null){
            return token.toString();
        }
        return "";
    }

    private static Cache<String,Object> getUserCache(){
        SimplePrincipalCollection principals = (SimplePrincipalCollection)SecurityUtils.getSubject().getPrincipals();
        String username = principals.toString();
        Cache<String, Object> cache = INSTANCE.getCache(username);
        return cache;
    }


}
