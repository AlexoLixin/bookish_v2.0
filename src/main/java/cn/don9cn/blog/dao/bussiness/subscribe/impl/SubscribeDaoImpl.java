package cn.don9cn.blog.dao.bussiness.subscribe.impl;

import cn.don9cn.blog.dao.bussiness.subscribe.interf.SubscribeDao;
import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.OptionalInt;

/**
 * @author Don9
 * @create 2017-12-27-11:17
 **/
@Repository
public class SubscribeDaoImpl implements SubscribeDao {
    @Override
    public OptionalInt deleteByEmail(String email) {
        return getMyMongoOperator().freeRemove(Query.query(Criteria.where("email").is(email)), SubscribeInfo.class);
    }

    @Override
    public OptionalInt deleteByEmailAndAuthor(String email, String author) {
        return getMyMongoOperator().freeRemove(Query.query(Criteria.where("email").is(email).and("author").is(author)), SubscribeInfo.class);
    }
}
