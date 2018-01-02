package cn.don9cn.blog.action.bussiness.article

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.Article
import cn.don9cn.blog.service.bussiness.ArticleService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = "/bussiness/article")
open class ArticleAction : BaseAction<Article>() {

    @Autowired
    private val articleService: ArticleService? = null


    @PostMapping
    override fun baseInsert(entity: Article): ActionMsg {
        return super.insert(articleService!!.baseInsert(entity))
    }

    override fun baseInsertBatch(list: List<Article>): ActionMsg {
        return super.insert(articleService!!.baseInsertBatch(list))
    }

    @PutMapping
    override fun baseUpdate(entity: Article): ActionMsg {
        return super.update(articleService!!.baseUpdate(entity))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return super.delete(articleService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(articleService!!.baseDeleteBatch(codes))
    }

    @GetMapping(path = ["", "/public"])
    override fun baseFindById(code: String): ActionMsg {
        return super.find(articleService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return super.find(articleService!!.baseFindAll())
    }

    @GetMapping(path = ["/list", "/list/public"])
    override fun baseFindListByParams(entity: Article): ActionMsg {
        return super.find(articleService!!.baseFindListByParams(entity))
    }

    @GetMapping("/page", "/publish/new/public")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String, endTime: String, orderBy: String, article: Article): ActionMsg {
        return super.find(articleService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, article)))
    }

    /**
     * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
     */
    @PutMapping("/byUser")
    fun doUpdateByUser(article: Article): ActionMsg {
        return super.update(articleService!!.doUpdateByUser(article))
    }

    /**
     * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
     */
    @DeleteMapping("/byUser")
    fun doRemoveByUser(code: String): ActionMsg {
        return super.delete(articleService!!.doRemoveByUser(code))
    }

    /**
     * 个人中心-普通获取文章列表(只能获取自己发布的文章列表,防止其他用户数据被恶意篡改)
     */
    @GetMapping("/page/byUser")
    fun doFindByPageByUser(page: Int, limit: Int, startTime: String, endTime: String, orderBy: String, article: Article): ActionMsg {
        return super.find(articleService!!.doFindByPageByUser(PageResult(page, limit, startTime, endTime, orderBy, article)))
    }

}