package cn.don9cn.blog.annotation;

import java.lang.annotation.*;

/**
 *@Author: liuxindong
 *@Description: 标记该实体对应的mapper.xml映射文件的namespace
 *@Create: 2017/10/8 19:45
 *@Modify:
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperNameSpace {

    String namespace() default "";

}
