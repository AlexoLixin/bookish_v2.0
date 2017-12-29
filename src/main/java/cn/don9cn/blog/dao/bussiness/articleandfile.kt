package cn.don9cn.blog.dao.bussiness

import cn.don9cn.blog.support.mongo.ext.eq
import cn.don9cn.blog.support.mongo.ext.inThe
import cn.don9cn.blog.support.mongo.ext.query
import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.bussiness.Article
import cn.don9cn.blog.model.bussiness.ArticleAndFile
import cn.don9cn.blog.model.bussiness.ArticleClassify
import cn.don9cn.blog.model.system.UploadFile
import org.springframework.stereotype.Repository

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
open class ArticleAndFileDaoImpl : ArticleAndFileDao {

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
            remove<ArticleAndFile>(query("articleCode" inThe list))
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
        val articleAndFile = dslOperator{
            findOne<ArticleAndFile>(query("fileCode" eq uploadFile.code!!))
        }
        val article = dslOperator{
            findById<Article>(articleAndFile.articleCode)
        }
        val articleClassify = dslOperator{
            findById<ArticleClassify>(article.classify!!)
        }
        uploadFile.link = articleClassify.name + "-" + article.title
    }
}