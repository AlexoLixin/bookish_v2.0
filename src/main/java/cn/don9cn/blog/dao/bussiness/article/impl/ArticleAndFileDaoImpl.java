package cn.don9cn.blog.dao.bussiness.article.impl;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.dao.bussiness.article.interf.ArticleAndFileDao;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.model.bussiness.article.ArticleAndFile;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.util.MyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;


/**
 * @Author: liuxindong
 * @Description: 文章Article dao实现类
 * @Create: 2017/10/9 14:06
 * @Modify:
 */
@Repository
public class ArticleAndFileDaoImpl implements ArticleAndFileDao {

    /**
     * 根据articleCode删除记录
     * @param articleCode
     * @return
     */
    public OptionalInt deleteByArticleCode(String articleCode){
        return getMyMongoOperator().freeDelete(Query.query(Criteria.where("articleCode").is(articleCode)),ArticleAndFile.class);
    }

    /**
     * 根据多个articleCode删除记录
     * @param list
     * @return
     */
    public OptionalInt deleteByArticleCodes(List<String> list) {
        return getMyMongoOperator().freeDelete(Query.query(Criteria.where("articleCode").in(list)),ArticleAndFile.class);
    }

    /**
     * 批量添加记录
     * @param article
     * @return
     */
    public OptionalInt insertBatch(Article article){
        List<ArticleAndFile> articleAndFiles = MyStringUtil.codesStr2List(article.getFiles())
                .stream()
                .map(fileCode -> new ArticleAndFile(article.getCode(), fileCode))
                .collect(Collectors.toList());
        return getMyMongoOperator().baseInsertBatch(articleAndFiles);
    }

    /**
     * 填充关联信息
     * @param uploadFile
     */
    public void fillLink(UploadFile uploadFile) {
        Optional<ArticleAndFile> articleAndFile = getMyMongoOperator().freeFindOne(
                Query.query(Criteria.where("fileCode").is(uploadFile.getCode())), ArticleAndFile.class);
        articleAndFile.ifPresent(articleAndFile1 -> {
            Optional<Article> article = getMyMongoOperator().baseFindById(articleAndFile1.getArticleCode(), Article.class);
            article.ifPresent(article1 -> {
                Optional<ArticleClassify> articleClassify = getMyMongoOperator().baseFindById(article1.getClassify(), ArticleClassify.class);
                articleClassify.ifPresent(articleClassify1 ->
                        uploadFile.setLink(articleClassify1.getName() + " - " + article1.getTitle()));
            });
        });
    }
}
