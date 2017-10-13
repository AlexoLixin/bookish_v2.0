package cn.don9cn.blog.dao.bussiness.articleclassify;

import cn.don9cn.blog.action.bussiness.articleclassify.ArticleClassifyAction;
import cn.don9cn.blog.dao.BaseDao;
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
public class ArticleClassifyDaoImpl implements BaseDao<ArticleClassify> {

    private static Logger logger = Logger.getLogger(ArticleClassifyAction.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 级联删除节点
     * @param level
     * @return
     */
    public OptionalInt deleteCascade(String level) {

        int x;
        Query query = Query.query(Criteria.where("level").regex("^" + level + "\\d*"));
        try{
            x = mongoTemplate.findAllAndRemove(query, ArticleClassify.class, "ArticleClassify").size();
        }catch (Exception e){
            logger.error("ArticleClassifyDaoImpl.deleteCascade 级联删除失败,异常信息:"+e.getMessage());
            return OptionalInt.empty();
        }
        return OptionalInt.of(x);

    }

    /**
     * 级联删除后更新叶子节点
     * @param allCodes
     * @return
     */
    public OptionalInt updateLeaf(List<String> allCodes) {

        int x;
        Query query = Query.query(Criteria.where("_id").in(allCodes).and("leaf").is("N"));
        Update update = new Update().set("leaf","Y");
        try{
            x = mongoTemplate.updateMulti(query, update,ArticleClassify.class,"ArticleClassify").getN();
        }catch (Exception e){
            logger.error("ArticleClassifyDaoImpl.updateLeaf 更新叶子节点失败,异常信息:"+e.getMessage());
            return OptionalInt.empty();
        }
        return OptionalInt.of(x);

    }
}
