package cn.don9cn.blog.dao.bussiness.articlecomment.impl;

import cn.don9cn.blog.dao.bussiness.articlecomment.interf.ArticleCommentDao;
import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * @Author: liuxindong
 * @Description: 文章评论dao实现类
 * @Create: 2017/10/10 10:14
 * @Modify:
 */
@SuppressWarnings("Duplicates")
@Repository
public class ArticleCommentDaoImpl implements ArticleCommentDao {


    /**
     * 删除节点
     * @param ids
     * @return
     */
    @Override
    public Optional<List<ArticleComment>> removeNodes(List<String> ids) {
        Query query = Query.query(Criteria.where("_id").in(ids));
        return getMyMongoOperator().freeFindAllAndRemove(query,ArticleComment.class);
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
        return getMyMongoOperator().freeUpdateMulti(query,update,ArticleComment.class);
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
        return getMyMongoOperator().freeUpdateMulti(query,update,ArticleComment.class);
    }

    @Override
    public Optional<List<ArticleComment>> findListByArticleCode(String articleCode) {
        Query query = Query.query(Criteria.where("articleCode").is(articleCode));
        return getMyMongoOperator().freeFindList(query,ArticleComment.class);
    }


}
