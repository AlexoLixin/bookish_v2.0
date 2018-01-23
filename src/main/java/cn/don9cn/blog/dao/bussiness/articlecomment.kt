package cn.don9cn.blog.dao.bussiness

import cn.don9cn.blog.support.mongo.core.PageResult
import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment
import cn.don9cn.blog.support.mongo.ext.*
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

/**
 * 文章评论dao
 */
interface ArticleCommentDao : BaseDao<ArticleComment> {

    /**
     * 删除节点
     * @param ids
     * @return
     */
    fun removeNodes(ids: List<String>): List<ArticleComment>

    /**
     * 删除单个节点
     * @param id
     * @return
     */
    fun removeNode(id: String): ArticleComment?

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
     * 查找指定文章下的所有留言
     * @return
     */
    fun findListByArticleCode(articleCode: String): List<ArticleComment>
}

/**
 * 文章评论dao实现类
 */
@Repository
open class ArticleCommentDaoImpl : ArticleCommentDao {


    /**
     * 批量删除节点
     * @param ids
     * @return
     */
    override fun removeNodes(ids: List<String>): List<ArticleComment> {
        return dslOperator{
            findAndRemove(query("_id" inThe ids))
        }
    }

    /**
     * 删除单个节点
     * @param id
     * @return
     */
    override fun removeNode(id: String): ArticleComment? {
        return dslOperator{
            findAndRemoveById(id)
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
            updateOne<ArticleComment>(
                    query("_id" eq code),
                    update("replyCodes" push child)
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
            updateOne<ArticleComment>(
                    query("_id" eq code),
                    update("childrenCodes" pull child)
            )
        }
    }

    /**
     * 根据文章主键查询留言列表
     * @param articleCode
     * @return
     */
    override fun findListByArticleCode(articleCode: String): List<ArticleComment> {
        return dslOperator{
            findList(query("articleCode" eq articleCode).with(Sort(Sort.Direction.DESC, "createTime")))
        }
    }

    override fun baseFindByPage(pageResult: PageResult<ArticleComment>): PageResult<ArticleComment> {
        val query = Query().apply{
            dslOperator.parseEntityToMap(pageResult.entity!!).forEach{
                if(it.key!="childrenCodes" && it.key!="replies"){
                    addCriteria(Criteria.where(it.key).`is`(it.value))
                }
            }
        }
        return dslOperator{
            findPage(query,pageResult)
        }
    }
}
