package cn.don9cn.blog.dao.bussiness.article;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.article.Article;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;


/**
 * @Author: liuxindong
 * @Description: 文章Article dao实现类
 * @Create: 2017/10/9 14:06
 * @Modify:
 */
@Repository
public class ArticleDaoImpl implements BaseDao<Article> {

    /**
     * 开放给普通用户的删除文章功能,使其只能删除自己发布的文章
     * @param map
     * @return
     */
    public Optional<Integer> doRemoveByUser(Map<String, String> map) {
        return Optional.of(this.getSessionTemplate().update(getSqlName("doRemoveByUser"),map));
    }

    /**
     * 开放给普通用户的更新文章功能,使其只能更新自己发布的文章
     * @param entity
     * @return
     */
    public Optional<Integer> doUpdateByUser(Article entity) {
        return Optional.of(this.getSessionTemplate().update(getSqlName("doUpdateByUser"),entity));
    }
}
