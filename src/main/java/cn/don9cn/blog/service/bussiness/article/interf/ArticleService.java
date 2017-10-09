package cn.don9cn.blog.service.bussiness.article.interf;


import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.BaseService;

import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: Article Service接口
 * @Create: 2017/10/9 14:24
 * @Modify:
 */
public interface ArticleService extends BaseService<Article> {

    Optional<Integer> doRemoveByUser(String code);

    Optional<Integer> doUpdateByUser(Article article);

    Optional<PageResult<Article>> doFindByPageByUser(PageParamsBean<Article> pageParamsBean);
}

