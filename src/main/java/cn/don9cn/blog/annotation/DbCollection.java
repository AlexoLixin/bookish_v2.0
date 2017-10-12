package cn.don9cn.blog.annotation;

import java.lang.annotation.*;

/**
 *@Author: liuxindong
 *@Description: 自动建表工具注解,用于声明model类是否需要在数据库中建表的声明
 *@Create: 2017/10/8 19:44
 *@Modify:
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbCollection {

    public boolean value() default true;
}
