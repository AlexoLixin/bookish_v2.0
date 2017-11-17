package cn.don9cn.blog.autoconfigs.activemq.runnable;

import cn.don9cn.blog.autoconfigs.activemq.model.MqMessage;
import cn.don9cn.blog.autoconfigs.activemq.model.MqRegisterMessage;

import java.util.concurrent.BlockingQueue;

/**
 * @Author: liuxindong
 * @Description: 消息管理器的生产者线程,负责将用户注册的消息放入queue中
 * @Create: 2017/11/17 8:50
 * @Modify:
 */
public class MqProducerTask implements Runnable{

    private final BlockingQueue<MqRegisterMessage> queue;

    private final MqRegisterMessage message;

    public MqProducerTask(BlockingQueue<MqRegisterMessage> queue, MqRegisterMessage message) {
        this.queue = queue;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
