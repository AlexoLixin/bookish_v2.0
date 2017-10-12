package cn.don9cn.blog.dao.bussiness.articleclassify;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @Author: liuxindong
 * @Description: 文章分类dao实现类
 * @Create: 2017/10/10 10:14
 * @Modify:
 */
@Repository
public class ArticleClassifyDaoImpl implements BaseDao<ArticleClassify> {

    /**
     * 级联删除节点
     * @param level
     * @return
     */
    public Optional<Integer> deleteCascade(String level) {
        return Optional.empty();
    }

    /**
     * 级联删除后更新叶子节点
     * @param allCodes
     * @return
     */
    public Optional<Integer> updateLeaf(List<String> allCodes) {
        return Optional.empty();
    }
}
