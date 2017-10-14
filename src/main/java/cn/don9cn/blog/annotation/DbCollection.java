package cn.don9cn.blog.annotation;

import java.lang.annotation.*;

/**
 *@Author: liuxindong
 *@Description: 在mongo中作为collection
 *@Create: 2017/10/8 19:44
 *@Modify:
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbCollection {

    public boolean value() default true;
}
