package cn.don9cn.blog.annotation;

import java.lang.annotation.*;

/**
 *@Author: liuxindong
 *@Description: 字段说明
 *@Create: 2017/10/8 19:43
 *@Modify:
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbColumn {

    //列说明
    String content() default "";
}
