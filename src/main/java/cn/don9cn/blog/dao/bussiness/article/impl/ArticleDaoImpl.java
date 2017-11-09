package cn.don9cn.blog.dao.bussiness.article.impl;

import cn.don9cn.blog.dao.bussiness.article.interf.ArticleDao;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * @Author: liuxindong
 * @Description: 文章Article dao实现类
 * @Create: 2017/10/9 14:06
 * @Modify:
 */
@Repository
public class ArticleDaoImpl implements ArticleDao {

    /**
     * 开放给普通用户的删除文章功能,使其只能删除自己发布的文章
     * @param code
     * @return
     */
    @Override
    public OptionalInt removeByUser(String code,String userCode) {
        Query query = Query.query(Criteria.where("_id").is(code).and("createBy").is(userCode));
        return getMyMongoOperator().freeRemove(query,Article.class);
    }

    /**
     * 开放给普通用户的更新文章功能,使其只能更新自己发布的文章
     * @param entity
     * @return
     */
    @Override
    public OptionalInt updateByUser(Article entity) {
        Query query = Query.query(Criteria.where("_id").is(entity.getCode()).and("createBy").is(entity.getCreateBy()));
        return getMyMongoOperator().freeUpdateOne(query,getMyMongoOperator().createDefaultUpdate(entity),Article.class);
    }

    /**
     * 分页查询文章(但是不包含content字段,以加快速度)
     * @param pageResult
     * @return
     */
    public Optional<PageResult<Article>> findPageWithoutContent(PageResult<Article> pageResult){
        BasicDBObject query = BasicDBObject.parse(JSON.toJSONString(pageResult.getEntity()));
        BasicDBObject fields = new BasicDBObject("content",false);
        BasicQuery resultQuery = new BasicQuery(query,fields);
        return getMyMongoOperator().freeFindPage(pageResult,resultQuery);
    }


    /**
     * 查询文章集合(不包含content字段)
     * @param entity
     * @return
     */
    @Override
    public Optional<List<Article>> findListWithoutContent(Article entity) {
        BasicDBObject query = BasicDBObject.parse(JSON.toJSONString(entity));
        BasicDBObject fields = new BasicDBObject("content",false);
        BasicQuery resultQuery = new BasicQuery(query,fields);
        return getMyMongoOperator().freeFindList(resultQuery,Article.class);
    }


}
