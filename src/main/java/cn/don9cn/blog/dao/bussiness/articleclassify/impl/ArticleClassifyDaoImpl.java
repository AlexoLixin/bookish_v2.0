package cn.don9cn.blog.dao.bussiness.articleclassify.impl;

import cn.don9cn.blog.dao.bussiness.articleclassify.interf.ArticleClassifyDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
     * 删除节点
     * @param ids
     * @return
     */
    @Override
    public Optional<List<ArticleClassify>> removeNodes(List<String> ids) {
        Query query = Query.query(Criteria.where("_id").in(ids));
        return getMyMongoOperator().freeFindAllAndRemove(query,ArticleClassify.class);
    }

    /**
     * 更新父节点
     * @param code
     * @param child
     * @return
     */
    @Override
    public OptionalInt updateParentForPush(String code, String child) {
        Query query = Query.query(Criteria.where("_id").is(code));
        Update update = new Update().push("childrenCodes",child);
        return getMyMongoOperator().freeUpdateMulti(query,update,ArticleClassify.class);
    }

    /**
     * 更新父节点
     * @param code
     * @param child
     * @return
     */
    @Override
    public OptionalInt updateParentForPull(String code, String child) {
        Query query = Query.query(Criteria.where("_id").is(code));
        Update update = new Update().pull("childrenCodes",child);
        return getMyMongoOperator().freeUpdateMulti(query,update,ArticleClassify.class);
    }
}
