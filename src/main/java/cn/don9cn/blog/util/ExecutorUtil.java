package cn.don9cn.blog.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: liuxindong
 * @Description: 线程执行器工具类
 * @Create: 2017/10/12 8:39
 * @Modify:
 */
public class ExecutorUtil {

    public static Executor build(int poolSize){
        return Executors.newFixedThreadPool(poolSize,
                Thread::new);
    }
}
