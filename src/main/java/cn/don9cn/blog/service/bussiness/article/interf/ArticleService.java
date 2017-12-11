package cn.don9cn.blog.service.bussiness.article.interf;


import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.BaseService;

/**
 * @Author: liuxindong
 * @Description: Article Service接口
 * @Create: 2017/10/9 14:24
 * @Modify:
 */
public interface ArticleService extends BaseService<Article> {

    Object doRemoveByUser(String code);

    Object doUpdateByUser(Article article);

    Object doFindByPageByUser(PageResult<Article> pageResult);
}

