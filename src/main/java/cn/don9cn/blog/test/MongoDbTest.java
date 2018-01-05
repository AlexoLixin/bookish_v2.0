package cn.don9cn.blog.test;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Don9
 * @create 2017-10-12-15:59
 **/
public class MongoDbTest {



    public static void main(String[] args) {
        // 连接到 mongo 服务
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );

        MongoOperations mongoTemplate = new MongoTemplate(mongoClient,"don9blog");


        test(mongoTemplate);


    }

    private static void test(MongoOperations mongoTemplate) {


        mongoTemplate.remove(Query.query(Criteria.where("_id").is("0dcc7a6d67134ec29ba35ec604c223dc")),"SysPermission");

    }




}
