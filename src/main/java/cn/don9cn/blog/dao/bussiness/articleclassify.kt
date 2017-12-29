package cn.don9cn.blog.dao.bussiness

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.bussiness.ArticleClassify
import cn.don9cn.blog.support.mongo.ext.*
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

/**
 * 文章分类dao接口
 */
interface ArticleClassifyDao : BaseDao<ArticleClassify> {

    /**
     * 删除节点
     * @param ids
     * @return
     */
    fun removeNodes(ids: List<String>): List<ArticleClassify>

    /**
     * 更新父节点(为其添加子节点)
     * @param code
     * @param child
     */
    fun updateParentForPush(code: String, child: String): Int

    /**
     * 更新父节点(为其删除子节点)
     * @param code
     * @param child
     */
    fun updateParentForPull(code: String, child: String): Int

    /**
     * 更新节点(跳过childrenCodes字段)
     * @param entity
     * @return
     */
    fun update(entity: ArticleClassify): Int

}

/**
 * 文章分类dao实现类
 */
@Repository
open class ArticleClassifyDaoImpl : ArticleClassifyDao {


    /**
     * 删除节点
     * @param ids
     * @return
     */
    override fun removeNodes(ids: List<String>): List<ArticleClassify> {
        return dslOperator{
            findAndRemove(query("_id" inThe ids))
        }
    }

    /**
     * 更新父节点
     * @param code
     * @param child
     * @return
     */
    override fun updateParentForPush(code: String, child: String): Int {
        return dslOperator{
            updateOne<ArticleClassify>(
                    query("_id" eq code),
                    update("childrenCodes" push child)
            )
        }
    }

    /**
     * 更新父节点
     * @param code
     * @param child
     * @return
     */
    override fun updateParentForPull(code: String, child: String): Int {
        return dslOperator{
            updateOne<ArticleClassify>(
                    query("_id" eq code),
                    update("childrenCodes" pull child)
            )
        }
    }

    /**
     * 更新节点(跳过childrenCodes字段)
     * @param entity
     * @return
     */
    override fun update(entity: ArticleClassify): Int {
        val update = Update()
        dslOperator.parseEntityToMap(entity).forEach {
            if(it.key != "childrenCodes"){
                update.set(it.key,it.value)
            }
        }
        return dslOperator{
            updateOne<ArticleClassify>(
                    createQueryByEntity(entity),
                    update
            )
        }
    }
}
