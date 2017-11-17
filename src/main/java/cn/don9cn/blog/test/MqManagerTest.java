package cn.don9cn.blog.test;

import cn.don9cn.blog.autoconfigs.activemq.constant.MqDestinationType;
import cn.don9cn.blog.autoconfigs.activemq.model.MqRegisterMessage;
import cn.don9cn.blog.autoconfigs.activemq.model.CommonMqMessage;

import java.util.concurrent.*;

/**
 * @author Don9
 * @create 2017-11-17-8:29
 **/
public class MqManagerTest {

    static BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();

    static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {


        MqRegisterMessage mqRegisterMessage = new MqRegisterMessage(MqDestinationType.QUEUE,"",new CommonMqMessage());

        System.out.println(mqRegisterMessage.getDestinationType().equals(MqDestinationType.QUEUE));


    }

}
