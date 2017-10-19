package cn.don9cn.blog.annotation;

import java.lang.annotation.*;

/**
 * @Author: liuxindong
 * @Description: 该注解所在的类或者方法不会生成操作日志
 * @Create: 2017/10/18 14:49
 * @Modify:
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipOperaLog {
    boolean value() default true;
}
