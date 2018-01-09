package cn.don9cn.blog.test;

import cn.don9cn.blog.autoconfigure.activemq.constant.MqDestinationType;
import cn.don9cn.blog.autoconfigure.activemq.core.MqTask;
import cn.don9cn.blog.autoconfigure.activemq.model.CommonMqMessage;

import java.util.concurrent.*;

/**
 * @author Don9
 * @create 2017-11-17-8:29
 **/
public class MqManagerTest {

    static BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();

    static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {


        MqTask mqTask = new MqTask(MqDestinationType.QUEUE,"",new CommonMqMessage());

        System.out.println(mqTask.getDestinationType().equals(MqDestinationType.QUEUE));


    }

}
