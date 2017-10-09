package cn.don9cn.blog.util;

import java.util.UUID;

/**
 * @Author: liuxindong
 * @Description: uuid工具类
 * @Create: 2017/10/9 10:46
 * @Modify:
 */
public class UuidUtil {

    /**
     * @Author: liuxindong
     * @Description: 生成uuid
     * @Create: 2017/10/9 10:46
     * @Modify:
     */
    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
