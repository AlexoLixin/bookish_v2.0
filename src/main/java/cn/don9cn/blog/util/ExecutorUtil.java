package cn.don9cn.blog.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 自定义线程执行器
 */
public class ExecutorUtil {

    public static Executor build(int poolSize){
        return Executors.newFixedThreadPool(poolSize,
                r -> {
                    Thread t = new Thread(r);
                    //每个线程设置为守护线程
                    t.setDaemon(true);
                    return t;
                });
    }
}
