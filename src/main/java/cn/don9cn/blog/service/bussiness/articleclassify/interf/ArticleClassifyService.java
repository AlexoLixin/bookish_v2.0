package cn.don9cn.blog.service.bussiness.articleclassify.interf;


import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.BaseService;


/**
 * @Author: liuxindong
 * @Description: 文章分类service接口
 * @Create: 2017/10/10 10:19
 * @Modify:
 */
public interface ArticleClassifyService extends BaseService<ArticleClassify> {


    /**
     * 获取分类树
     * @return
     */
    Object getTree();

    /**
     * 获取分类下拉列表
     * @return
     */
    Object doGetSelectOptions();

}



