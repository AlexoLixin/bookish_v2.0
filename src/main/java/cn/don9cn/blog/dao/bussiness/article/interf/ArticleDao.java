package cn.don9cn.blog.dao.bussiness.article.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.article.Article;

import java.util.Map;
import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 文章dao接口
 * @Create: 2017/10/17 9:13
 * @Modify:
 */
public interface ArticleDao extends BaseDao<Article> {

    /**
     * 开放给普通用户的删除文章功能,使其只能删除自己发布的文章
     * @param map
     * @return
     */
    Optional<Integer> doRemoveByUser(Map<String, String> map);

    /**
     * 开放给普通用户的更新文章功能,使其只能更新自己发布的文章
     * @param entity
     * @return
     */
    Optional<Integer> doUpdateByUser(Article entity);

}
