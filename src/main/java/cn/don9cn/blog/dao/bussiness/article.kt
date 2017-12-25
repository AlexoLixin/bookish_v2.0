package cn.don9cn.blog.dao.bussiness

import cn.booklish.mongodsl.base.and
import cn.booklish.mongodsl.base.eq
import cn.booklish.mongodsl.base.query
import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.bussiness.Article
import cn.don9cn.blog.support.daohelper.core.DaoHelper
import com.alibaba.fastjson.JSON
import com.mongodb.BasicDBObject
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @Author: liuxindong
 * @Description:  文章dao接口
 * @Created: 2017/12/22 16:39
 * @Modified:
 */
interface ArticleDao : BaseDao<Article> {

    /**
     * 开放给普通用户的删除文章功能,使其只能删除自己发布的文章
     */
    fun removeByUser(code: String, userCode: String): Int

    /**
     * 开放给普通用户的更新文章功能,使其只能更新自己发布的文章
     */
    fun updateByUser(entity: Article): Int

    /**
     * 分页查询文章(但是不包含content字段,以加快速度)
     */
    fun findPageWithoutContent(pageResult: PageResult<Article>): PageResult<Article>

    /**
     * 查询文章集合(不包含content字段)
     */
    fun findListWithoutContent(entity: Article): List<Article>

}

/**
 * @Author: liuxindong
 * @Description: 文章dao实现类
 * @Created: 2017/12/22 16:40
 * @Modified:
 */
@Repository
class ArticleDaoImpl:ArticleDao {

    /**
     * 开放给普通用户的删除文章功能,使其只能删除自己发布的文章
     * @param code
     * @return
     */
    override fun removeByUser(code: String, userCode: String): Int {
        return dslOperator.remove(
                query("_id" eq code).and("createBy" eq userCode),
                Article::class.java
        )
    }

    /**
     * 开放给普通用户的更新文章功能,使其只能更新自己发布的文章
     * @param entity
     * @return
     */
    override fun updateByUser(entity: Article): Int {
        return dslOperator.updateOne(
                query("_id" eq entity.code!!).and("createBy" eq entity.createBy!!),
                entity
        )
    }

    /**
     * 分页查询文章(但是不包含content字段,以加快速度)
     * @param pageResult
     * @return
     */
    override fun findPageWithoutContent(pageResult: PageResult<Article>): PageResult<Article> {
        val query = BasicDBObject.parse(JSON.toJSONString(pageResult.entity))
        val fields = BasicDBObject("content", false)
        val resultQuery = BasicQuery(query, fields)
        return dslOperator.findPage(resultQuery,pageResult)
    }


    /**
     * 查询文章集合(不包含content字段)
     * @param entity
     * @return
     */
    override fun findListWithoutContent(entity: Article): List<Article> {
        val query = BasicDBObject.parse(JSON.toJSONString(entity))
        val fields = BasicDBObject("content", false)
        val resultQuery = BasicQuery(query, fields)
        return dslOperator.findList(resultQuery,Article::class.java)
    }


}
