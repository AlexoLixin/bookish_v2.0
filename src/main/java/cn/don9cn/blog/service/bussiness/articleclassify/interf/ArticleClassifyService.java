package cn.don9cn.blog.service.bussiness.articleclassify.interf;


import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.service.BaseService;
import cn.don9cn.blog.support.vue.VueSelectOption;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * @Author: liuxindong
 * @Description: 文章分类service接口
 * @Create: 2017/10/10 10:19
 * @Modify:
 */
public interface ArticleClassifyService extends BaseService<ArticleClassify> {


    /**
     * 添加分类
     * @param articleClassify
     * @return
     */
    OptionalInt doSave(ArticleClassify articleClassify);

    /**
     * 获取分类树
     * @return
     */
    Optional<List<ArticleClassify>> getTree();

    /**
     * 删除分类
     * @param codesList
     * @param levelsList
     * @return
     */
    OptionalInt doRemove(List<String> codesList,List<String> levelsList);

    /**
     * 获取分类下拉列表
     * @return
     */
    Optional<List<VueSelectOption>> doGetSelectOptions();

}



