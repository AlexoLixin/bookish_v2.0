package cn.don9cn.blog.dao.bussiness.article.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 文章dao接口
 * @Create: 2017/10/17 9:13
 * @Modify:
 */
public interface ArticleDao extends BaseDao<Article> {

    /**
     * 开放给普通用户的删除文章功能,使其只能删除自己发布的文章
     * @param code
     * @return
     */
    OptionalInt removeByUser(String code, String userCode);

    /**
     * 开放给普通用户的更新文章功能,使其只能更新自己发布的文章
     * @param entity
     * @return
     */
    OptionalInt updateByUser(Article entity);

}
