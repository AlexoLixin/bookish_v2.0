package cn.don9cn.blog.dao.bussiness.articleclassify.impl;

import cn.don9cn.blog.action.bussiness.articleclassify.ArticleClassifyAction;
import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.dao.bussiness.articleclassify.interf.ArticleClassifyDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.OptionalInt;


/**
 * @Author: liuxindong
 * @Description: 文章分类dao实现类
 * @Create: 2017/10/10 10:14
 * @Modify:
 */
@Repository
public class ArticleClassifyDaoImpl implements ArticleClassifyDao {

    /**
     * 级联删除节点
     * @param level
     * @return
     */
    @Override
    public OptionalInt deleteCascade(String level) {
        Query query = Query.query(Criteria.where("level").regex("^" + level + "\\d*"));
        return getMyMongoOperator().freeDelete(query,ArticleClassify.class);
    }

    /**
     * 级联删除后更新叶子节点
     * @param allCodes
     * @return
     */
    @Override
    public OptionalInt updateLeaf(List<String> allCodes) {
        Query query = Query.query(Criteria.where("_id").in(allCodes).and("leaf").is("N"));
        Update update = new Update().set("leaf","Y");
        return getMyMongoOperator().freeUpdateMulti(query,update,ArticleClassify.class);
    }
}
