package cn.don9cn.blog.autoconfigure.activemq.core;

import cn.don9cn.blog.autoconfigure.activemq.model.MqRegisterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: liuxindong
 * @Description: 消息管理器,负责将要推送到ActiveMQ的新消息的注册和推送任务的分发
 * @Create: 2017/11/17 8:40
 * @Modify:
 */
@Configuration
@AutoConfigureAfter(MqExTemplate.class)
public class MqManager {

    private MqExTemplate mqExTemplate;

    /**
     * 管理待push到ActiveMQ的队列
     */
    private static final BlockingQueue<MqRegisterMessage> queue = new LinkedBlockingDeque<>();

    /**
     * 线程池
     */
    private static final ExecutorService exec = Executors.newFixedThreadPool(2);

    /**
     * 管理器初始化后开启消费者线程
     */
    @Autowired
    public MqManager(MqExTemplate mqExTemplate){
        this.mqExTemplate = mqExTemplate;
        start();
    }

    /**
     * 启动消费者,等待获取要push的新消息
     */
    private void start(){
        exec.execute(() -> {
            while (true) {
                try {
                    MqRegisterMessage take = queue.take();
                    mqExTemplate.parseAndPush(take);
                } catch(InterruptedException e){
                    break;
                }
            }
            Thread.currentThread().interrupt();
        });
    }

    /**
     * 注册新消息到管理器
     * @param message
     */
    public static void submit(MqRegisterMessage message){

        //生产者线程将新消息放入队列
        exec.execute(() -> {
            try {
                queue.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

    }

}
