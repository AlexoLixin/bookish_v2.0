package cn.don9cn.blog.test;

import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: liuxindong
 * @Description: redis连接测试
 * @Create: 2017/10/20 9:17
 * @Modify:
 */
public class RedisTest {

    public static void main(String[] args) {

        //Jedis jedis = new Jedis("45.77.6.109",7001);

        HostAndPort node1 = new HostAndPort("45.77.6.109",7001);
        /*HostAndPort node2 = new HostAndPort("45.77.6.109",7002);
        HostAndPort node3 = new HostAndPort("45.77.6.109",7003);
        HostAndPort node4 = new HostAndPort("45.77.6.109",7004);
        HostAndPort node5 = new HostAndPort("45.77.6.109",7005);
        HostAndPort node6 = new HostAndPort("45.77.6.109",7006);*/
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(node1);
        /*nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);
        nodes.add(node6);*/

        JedisCluster jedisCluster = new JedisCluster(nodes);

        //jedisCluster.set("aaa","测试啊");

        System.out.println(jedisCluster.get("aaa"));

        jedisCluster.set("bbb","测试啊2");

        System.out.println(jedisCluster.get("bbb"));

        //System.out.println(jedisCluster.ping());

        RedisTemplate redisTemplate = new RedisTemplate();

    }

}
