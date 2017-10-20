package cn.don9cn.blog.dao.bussiness.article.impl;

import cn.don9cn.blog.dao.bussiness.article.interf.ArticleDao;
import cn.don9cn.blog.model.bussiness.article.Article;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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
}
