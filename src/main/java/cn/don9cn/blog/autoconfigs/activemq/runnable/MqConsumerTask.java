package cn.don9cn.blog.autoconfigs.activemq.runnable;

import cn.don9cn.blog.autoconfigs.activemq.core.MqExTemplate;
import cn.don9cn.blog.autoconfigs.activemq.model.MqRegisterMessage;

import java.util.concurrent.BlockingQueue;

/**
 * @Author: liuxindong
 * @Description: 消息管理器的消费者线程,负责从queue中消费消息并推送到ActiveMQ中
 * @Create: 2017/11/17 8:50
 * @Modify:
 */
public class MqConsumerTask implements Runnable{

    private final BlockingQueue<MqRegisterMessage> queue;

    private final MqExTemplate producer;

    public MqConsumerTask(BlockingQueue<MqRegisterMessage> queue, MqExTemplate producer) {
        this.queue = queue;
        this.producer = producer;
    }

    @Override
    public void run() {

        while (true) {
            try {
                MqRegisterMessage take = queue.take();
                producer.parseAndPush(take);
            } catch(InterruptedException e){
                break;
            }
        }
        Thread.currentThread().interrupt();

    }
}
