package cn.don9cn.blog.autoconfigs.mongodb;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: mongoDb自动配置,不使用spring data的自动配置,因为其自动配置未提供详细参数的配置
 * @Create: don9 2017/10/29
 * @Modify:
 */
@Configuration
public class MongoAutoConfig {

    @Autowired
    private MongoClientProperties mongoClientProperties;

    /**
     * 自定义配置mongo客户端的详细连接属性,如过期时间/等待时间等...
     * @return
     */
    @Bean
    public MongoClient mongoClient(){
        ServerAddress serverAddress = new ServerAddress(mongoClientProperties.getHost(),mongoClientProperties.getPort());
        MongoClientOptions.Builder builder =
                new MongoClientOptions.Builder()
                        .connectionsPerHost(10)
                        .minConnectionsPerHost(0)
                        .threadsAllowedToBlockForConnectionMultiplier(5)
                        .connectTimeout(10000)
                        .maxWaitTime(120000)
                        .socketKeepAlive(false)
                        .socketTimeout(0);
        MongoCredential credential = MongoCredential.createCredential(mongoClientProperties.getUsername(),
                mongoClientProperties.getDatabase(), mongoClientProperties.getPassword().toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        return new MongoClient(serverAddress,credentials,builder.build());
    }

    /**
     * 创建并注入自定义的操作模版
     * @param mongoClient
     * @return
     */
    @Bean
    public MyMongoOperator myMongoOperator(MongoClient mongoClient){
        return new MyMongoOperator(mongoClient,mongoClientProperties.getDatabase());
    }


}
