package cn.don9cn.blog.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: liuxindong
 * @Description: 消息key值生成器
 * @Create: 2017/10/24 13:28
 * @Modify:
 */
@Component
public class MsgKeyGenerator {

    private final AtomicLong atomicLong;

    public MsgKeyGenerator() {
        atomicLong = new AtomicLong(1L);
    }

    public Long get(){
        return atomicLong.getAndIncrement();
    }

}


