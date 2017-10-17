package cn.don9cn.blog.dao.bussiness.article.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.model.bussiness.article.ArticleAndFile;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.model.system.file.UploadFile;
import cn.don9cn.blog.util.MyStringUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * @Author: liuxindong
 * @Description: ArticleAndFile dao接口
 * @Create: 2017/10/17 9:16
 * @Modify:
 */
public interface ArticleAndFileDao extends BaseDao<ArticleAndFile> {

    /**
     * 根据articleCode删除记录
     * @param articleCode
     * @return
     */
    OptionalInt deleteByArticleCode(String articleCode);

    /**
     * 根据多个articleCode删除记录
     * @param list
     * @return
     */
    OptionalInt deleteByArticleCodes(List<String> list);

    /**
     * 批量添加记录
     * @param article
     * @return
     */
    OptionalInt insertBatch(Article article);

    /**
     * 填充关联信息
     * @param uploadFile
     */
    void fillLink(UploadFile uploadFile);

}
