package cn.don9cn.blog.plugins.daohelper.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: liuxindong
 * @Description: 实体字段解析工具
 * @Create: 2017/10/9 10:47
 * @Modify:
 */
public class FieldParserUtil {

    /**
     * 匹配特殊字段名称,如 zMore01,因为zMore01的get方法名称为 getzMore01,而不是 getMore01Z所以要特别处理
     */
    private static Pattern pattern = Pattern.compile("[a-z][A-Z].*");

    /**
     * @Author: liuxindong
     * @Description: 根据field字段名称获取其对应的get方法名称
     * @Create: 2017/10/9 10:28
     * @Modify:
     */
    public static String parse2Getter(String fieldName){
        boolean matches = pattern.matcher(fieldName).matches();
        if(matches){
            return "get"+fieldName;
        }
        return "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
    }

    /**
     * @Author: liuxindong
     * @Description: 根据field字段名称获取对应数据库中的列名称
     *               根据entity实体类名称获取数据库中对应的表名称
     * @Create: 2017/10/9 10:31
     * @Modify:
     */
    public static String parse2DbName(String originStr){
        String resultStr;
        resultStr = originStr.substring(0,1).toLowerCase() + originStr.substring(1);
        String regex = "[A-Z]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(resultStr);
        while (matcher.find()){
            resultStr = resultStr.replaceAll(matcher.group(),"_"+matcher.group());
        }
        return resultStr.toUpperCase();
    }

}
