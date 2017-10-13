package cn.don9cn.blog.test;

import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Don9
 * @create 2017-10-12-15:59
 **/
public class MongoDbTest {



    public static void main(String[] args) {
        // 连接到 mongodb 服务
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );

        MongoOperations mongoTemplate = new MongoTemplate(mongoClient,"don9blog");

        //mongoTemplate.createCollection("ArticleClassify");

        //insert(mongoTemplate);

        //find(mongoTemplate);

        remove(mongoTemplate);

        //findAll(mongoTemplate);

        //update(mongoTemplate);

    }

    public static void update(MongoOperations mongoTemplate){
        //mongoTemplate.updateFirst(new Query(where("title").is("333")), Update.update("title","hahahaha"),Article.class);
        Update update = new Update().push("category").each("spring", "data");
        mongoTemplate.updateMulti(Query.query(where("author").is("liuxindong")),update,Article.class);
    }

    public static void findAll(MongoOperations mongoTemplate){

        List<ArticleClassify> all = mongoTemplate.findAll(ArticleClassify.class);
        System.out.println(all);

    }

    public static void remove(MongoOperations mongoTemplate){

        WriteResult remove = mongoTemplate.remove(query(where("leaf").is("Y")), "ArticleClassify");
        System.out.println(remove.getN());

    }

    public static void find(MongoOperations mongoTemplate){
        ArticleClassify one = mongoTemplate.findOne(query(where("code").is("59e04b28bc64ca143066a32f")), ArticleClassify.class,"ArticleClassify");
        System.out.println(one.getCode());

    }

    // 测试添加
    public static void insert(MongoOperations mongoTemplate){

        Article article = new Article();
        article.setCode("123123123123");
        article.setTitle("333");
        article.setAuthor("liuxindong");
        article.setClassify("this is a classify");
        article.setContent("this is contents");

        mongoTemplate.insert(article,"Article");

    }



}