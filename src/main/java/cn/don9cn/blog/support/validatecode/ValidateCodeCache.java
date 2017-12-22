package cn.don9cn.blog.support.validatecode;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author: liuxindong
 * @Description: 验证码缓存,用于验证验证码是否正确
 * @Create: 2017/11/7 9:13
 * @Modify:
 */
public class ValidateCodeCache {

    private static final ConcurrentSkipListSet<String> cache = new ConcurrentSkipListSet<>();

    private ValidateCodeCache(){}

    /**
     * 校验验证码是否匹配
     * @param textCode
     * @return
     */
    public static boolean validate(String textCode){
        boolean result = cache.contains(textCode);
        if(result){
            cache.remove(textCode);
        }
        return result;
    }

    /**
     * 缓存验证码
     * @param textCode
     */
    public static void cache(String textCode){
        cache.add(textCode);
    }

}
