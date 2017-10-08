package cn.don9cn.blog.annotation;

import java.lang.annotation.*;

/**
 *@Author: liuxindong
 *@Description: 自动建表工具注解,用于声明字段在数据库表中是否作为字段存在,并且可设置
 *              字段类型 / 长度 / 是否允许空 / 是否主键 / 列说明
 *@Create: 2017/10/8 19:43
 *@Modify:
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbColumn {

    //字段类型
    String type() default "varchar";

    //是否主键
    boolean primaryKey() default false;

    //是否允许空
    boolean allowNull() default true;

    //是否有长度
    boolean hasLength() default true;

    //列说明
    String content() default "";

    //长度值
    String length() default "50";
}
