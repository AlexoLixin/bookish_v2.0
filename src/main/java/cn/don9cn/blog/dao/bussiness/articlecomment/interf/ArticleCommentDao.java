package cn.don9cn.blog.dao.bussiness.articlecomment.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 文章评论dao接口
 * @Create: 2017/10/17 9:50
 * @Modify:
 */
public interface ArticleCommentDao extends BaseDao<ArticleComment> {

    /**
     * 删除节点
     * @param ids
     * @return
     */
    Optional<List<ArticleComment>> removeNodes(List<String> ids);

    /**
     * 更新父节点(为其添加子节点)
     * @param code
     * @param child
     */
    OptionalInt updateParentForPush(String code, String child);

    /**
     * 更新父节点(为其删除子节点)
     * @param code
     * @param child
     */
    OptionalInt updateParentForPull(String code, String child);

    /**
     * 查找指定文章下的所有留言
     * @return
     */
    Optional<List<ArticleComment>> findListByArticleCode(String articleCode);
}
