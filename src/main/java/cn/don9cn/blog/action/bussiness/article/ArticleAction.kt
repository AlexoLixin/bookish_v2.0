package cn.don9cn.blog.action.bussiness.article

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.article.Article
import cn.don9cn.blog.service.bussiness.ArticleService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/bussiness/article"])
open class ArticleAction : BaseAction<Article>() {

    @Autowired
    private var articleService: ArticleService? = null


    @PostMapping
    override fun baseInsert(t: Article): ActionMsg {
        return ActionMsgHandler.insert(articleService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<Article>): ActionMsg {
        return ActionMsgHandler.insert(articleService!!.baseInsertBatch(list))
    }

    @PutMapping
    override fun baseUpdate(t: Article): ActionMsg {
        return ActionMsgHandler.update(articleService!!.baseUpdate(t))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return ActionMsgHandler.delete(articleService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return ActionMsgHandler.delete(articleService!!.baseDeleteBatch(codes))
    }

    @GetMapping(path = ["", "/public"])
    override fun baseFindById(code: String): ActionMsg {
        return ActionMsgHandler.find(articleService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return ActionMsgHandler.find(articleService!!.baseFindAll())
    }

    @GetMapping(path = ["/list", "/list/public"])
    override fun baseFindListByParams(t: Article): ActionMsg {
        return ActionMsgHandler.find(articleService!!.baseFindListByParams(t))
    }

    @GetMapping("/page", "/publish/new/public")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: Article): ActionMsg {
        return ActionMsgHandler.find(articleService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    /**
     * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
     */
    @PutMapping("/byUser")
    open fun doUpdateByUser(article: Article): ActionMsg {
        return ActionMsgHandler.update(articleService!!.doUpdateByUser(article))
    }

    /**
     * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
     */
    @DeleteMapping("/byUser")
    open fun doRemoveByUser(code: String): ActionMsg {
        return ActionMsgHandler.delete(articleService!!.doRemoveByUser(code))
    }

    /**
     * 个人中心-普通获取文章列表(只能获取自己发布的文章列表,防止其他用户数据被恶意篡改)
     */
    @GetMapping("/page/byUser")
    open fun doFindByPageByUser(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, article: Article): ActionMsg {
        return ActionMsgHandler.find(articleService!!.doFindByPageByUser(PageResult(page, limit, startTime, endTime, orderBy, article)))
    }

}