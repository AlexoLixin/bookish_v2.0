package cn.don9cn.blog.service.bussiness.article.interf;


import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.BaseService;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: Article Service接口
 * @Create: 2017/10/9 14:24
 * @Modify:
 */
public interface ArticleService extends BaseService<Article> {

    OptionalInt doRemoveByUser(String code);

    OptionalInt doUpdateByUser(Article article);

    Optional<PageResult<Article>> doFindByPageByUser(PageParamsBean<Article> pageParamsBean);
}

