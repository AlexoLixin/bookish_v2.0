package cn.don9cn.blog.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 字符串工具类
 * @Create: 2017/10/9 15:38
 * @Modify:
 */
public class MyStringUtil {

    /**
     * @Author: liuxindong
     * @Description: 将code字符串转成list集合
     * @Create: 2017/10/9 15:39
     * @Modify:
     */
    public static List<String> codesStr2List(String codes){
        String[] strings = codes.split(",");
        List<String> list = new ArrayList<String>();
        Arrays.stream(strings).forEach(code -> list.add(code));
        return list;
    }
}
