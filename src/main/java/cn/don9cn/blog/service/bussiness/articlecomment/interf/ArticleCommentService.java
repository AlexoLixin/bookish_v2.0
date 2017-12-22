package cn.don9cn.blog.service.bussiness.articlecomment.interf;


import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment;
import cn.don9cn.blog.service.BaseService;


/**
 * @Author: liuxindong
 * @Description: 文章留言service接口
 * @Create: 2017/10/10 10:19
 * @Modify:
 */
public interface ArticleCommentService extends BaseService<ArticleComment> {


    /**
     * 获取留言树
     * @return
     */
    Object getTree(String articleCode);

}



