package cn.don9cn.blog.autoconfigs.mongodb;

import cn.booklish.mongodsl.core.DslOperator;
import cn.booklish.mongodsl.core.MongoClientConfig;
import cn.booklish.mongodsl.core.MongoClientFactory;
import cn.don9cn.blog.support.mongo.MyMongoOperator;
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

    private final MongoClientProperties mongoClientProperties;

    @Autowired
    public MongoAutoConfig(MongoClientProperties mongoClientProperties) {
        this.mongoClientProperties = mongoClientProperties;
    }

    /**
     * 创建并注入dsl操作模版
     * @return
     */
    @Bean
    public DslOperator dslOperator(){
        MongoClientConfig clientConfig = new MongoClientConfig();
        clientConfig.setHost(mongoClientProperties.getHost());
        clientConfig.setPort(mongoClientProperties.getPort());
        clientConfig.setUsername(mongoClientProperties.getUsername());
        clientConfig.setPassword(mongoClientProperties.getPassword());
        clientConfig.setDatabase(mongoClientProperties.getDatabase());
        return new DslOperator(MongoClientFactory.INSTANCE.getClient(clientConfig),clientConfig.getDatabase());
    }

}
