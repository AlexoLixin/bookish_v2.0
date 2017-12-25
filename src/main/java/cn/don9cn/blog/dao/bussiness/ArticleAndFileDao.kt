package cn.don9cn.blog.dao.bussiness

import cn.booklish.mongodsl.base.eq
import cn.booklish.mongodsl.base.inThe
import cn.booklish.mongodsl.base.query
import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.bussiness.Article
import cn.don9cn.blog.model.bussiness.ArticleAndFile
import cn.don9cn.blog.model.system.UploadFile
import cn.don9cn.blog.util.MyStringUtil
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.stream.Collectors

interface ArticleAndFileDao : BaseDao<ArticleAndFile> {

    /**
     * 根据articleCode删除记录
     * @param articleCode
     * @return
     */
    fun deleteByArticleCode(articleCode: String): Int

    /**
     * 根据多个articleCode删除记录
     * @param list
     * @return
     */
    fun deleteByArticleCodes(list: List<String>): Int

    /**
     * 批量添加记录
     * @param article
     * @return
     */
    fun insertBatch(article: Article): Int

    /**
     * 填充关联信息
     * @param uploadFile
     */
    fun fillLink(uploadFile: UploadFile)

}

@Repository
class ArticleAndFileDaoImpl : ArticleAndFileDao {

    /**
     * 根据articleCode删除记录
     * @param articleCode
     * @return
     */
    override fun deleteByArticleCode(articleCode: String): Int {
        return dslOperator{
            remove<ArticleAndFile>(query("articleCode" eq articleCode))
        }
    }

    /**
     * 根据多个articleCode删除记录
     * @param list
     * @return
     */
    override fun deleteByArticleCodes(list: List<String>): Int {
        return dslOperator{
            remove<ArticleAndFile>(query("articleCode" inThe  list))
        }
    }

    /**
     * 批量添加记录
     * @param article
     * @return
     */
    override fun insertBatch(article: Article): Int {
        val list = article.files!!.split(",".toRegex()).map { ArticleAndFile(article.code!!,it) }.toList()
        return dslOperator{
            insertBatch(list)
        }
    }

    /**
     * 填充关联信息
     * @param uploadFile
     */
    override fun fillLink(uploadFile: UploadFile) {
        val articleAndFile = getMyMongoOperator().freeFindOne(
                Query.query(Criteria.where("fileCode").`is`(uploadFile.getCode())), ArticleAndFile::class.java)
        articleAndFile.ifPresent({ articleAndFile1 ->
            val article = getMyMongoOperator().findById(articleAndFile1.getArticleCode(), Article::class.java)
            article.ifPresent({ article1 ->
                val articleClassify = getMyMongoOperator().findById(article1.getClassify(), ArticleClassify::class.java)
                articleClassify.ifPresent({ articleClassify1 -> uploadFile.setLink(articleClassify1.getName() + " - " + article1.getTitle()) })
            })
        })
    }
}