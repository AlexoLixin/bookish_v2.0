package cn.don9cn.blog.service.bussiness.article.interf;


import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operation.core.OperaResult;
import cn.don9cn.blog.service.BaseService;

/**
 * @Author: liuxindong
 * @Description: Article Service接口
 * @Create: 2017/10/9 14:24
 * @Modify:
 */
public interface ArticleService extends BaseService<Article> {

    OperaResult doRemoveByUser(String code);

    OperaResult doUpdateByUser(Article article);

    OperaResult doFindByPageByUser(PageResult<Article> pageResult);
}

