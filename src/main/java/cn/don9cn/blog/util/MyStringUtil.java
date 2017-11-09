package cn.don9cn.blog.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 校验字符串与正则表达式是否匹配
     * @return
     */
    public static boolean match(String regex,String str){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
