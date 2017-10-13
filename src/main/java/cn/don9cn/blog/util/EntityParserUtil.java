package cn.don9cn.blog.util;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author: liuxindong
 * @Description: 实体解析工具
 * @Create: 2017/10/9 10:47
 * @Modify:
 */
public class EntityParserUtil {

    /**
     * 匹配特殊字段名称,如 zMore01,因为zMore01的get方法名称为 getzMore01,而不是 getMore01Z所以要特别处理
     */
    private static Pattern pattern = Pattern.compile("[a-z][A-Z].*");

    /**
     * 根据field字段名称获取其对应的get方法名称
     * @param fieldName
     * @return
     */
    public static String parseField2Getter(String fieldName){
        boolean matches = pattern.matcher(fieldName).matches();
        if(matches){
            return "get"+fieldName;
        }
        return "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
    }

    /**
     * 获取实体包括其父类的所有字段
     * @param entity
     * @return
     */
    public static List<Field> getAllFields(Object entity){
        List<Field> list = new ArrayList<>();
        parseAllFields(entity.getClass(),list);
        return list;
    }

    private static void parseAllFields(Class entity, List<Field> fieldList) {
        fieldList.addAll(Arrays.asList(entity.getDeclaredFields()));
        Class superclass = entity.getSuperclass();
        if(superclass.getName() != "java.lang.Object"){
            parseAllFields(superclass,fieldList);
        }
    }

}
