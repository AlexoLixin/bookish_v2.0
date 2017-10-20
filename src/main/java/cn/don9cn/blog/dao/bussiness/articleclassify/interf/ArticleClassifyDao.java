package cn.don9cn.blog.dao.bussiness.articleclassify.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 文章分类dao接口
 * @Create: 2017/10/17 9:50
 * @Modify:
 */
public interface ArticleClassifyDao extends BaseDao<ArticleClassify> {

    /**
     * 删除节点
     * @param ids
     * @return
     */
    Optional<List<ArticleClassify>> removeNodes(List<String> ids);

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
}
