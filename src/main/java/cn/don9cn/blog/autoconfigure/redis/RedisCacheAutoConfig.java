package cn.don9cn.blog.autoconfigure.redis;

import cn.don9cn.blog.util.Md5Util;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

/**
 * @Author: liuxindong
 * @Description: redis缓存自动注入配置
 * @Create: 2017/10/20 10:32
 * @Modify:
 */
@Configuration
@EnableCaching
public class RedisCacheAutoConfig extends CachingConfigurerSupport {


    /**
     * 定义主键生成策略(格式: 类名_方法名_参数md5值)
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (targetObj, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(targetObj.getClass().getName()).append("_");
            sb.append(method.getName()).append("_");
            String reduce = Arrays.stream(params).map(Object::toString).reduce("", (x, y) -> x + y);
            sb.append(Md5Util.getMD5(reduce));
            return sb.toString();
        };
    }

    /**
     * 注入缓存管理器
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }

    /**
     * 注入 RedisTemplate 操作模版
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
