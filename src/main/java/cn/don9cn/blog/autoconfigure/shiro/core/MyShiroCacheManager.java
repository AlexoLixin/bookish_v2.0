package cn.don9cn.blog.autoconfigure.shiro.core;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.MapCache;

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


}
