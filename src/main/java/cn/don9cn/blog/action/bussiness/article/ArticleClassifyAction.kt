package cn.don9cn.blog.action.bussiness.article

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify
import cn.don9cn.blog.service.bussiness.ArticleClassifyService
import cn.don9cn.blog.service.bussiness.ArticleService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = "/bussiness/articleClassify")
open class ArticleClassifyAction : BaseAction<ArticleClassify>() {

    @Autowired
    private var articleClassifyService: ArticleClassifyService? = null



    @PostMapping
    override fun baseInsert(articleClassify: ArticleClassify): ActionMsg {
        return ActionMsgHandler.insert(articleClassifyService!!.baseInsert(articleClassify))
    }

    override fun baseInsertBatch(list: List<ArticleClassify>): ActionMsg {
        return ActionMsgHandler.insert(articleClassifyService!!.baseInsertBatch(list))
    }

    @PutMapping
    override fun baseUpdate(articleClassify: ArticleClassify): ActionMsg {
        return ActionMsgHandler.update(articleClassifyService!!.baseUpdate(articleClassify))
    }

    override fun baseRemove(code: String): ActionMsg {
        return ActionMsgHandler.delete(articleClassifyService!!.baseDeleteById(code))
    }

    @DeleteMapping
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return ActionMsgHandler.delete(articleClassifyService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return ActionMsgHandler.find(articleClassifyService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return ActionMsgHandler.find(articleClassifyService!!.baseFindAll())
    }

    @GetMapping("/list")
    override fun baseFindListByParams(articleClassify: ArticleClassify): ActionMsg {
        return ActionMsgHandler.find(articleClassifyService!!.baseFindListByParams(articleClassify))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, articleClassify: ArticleClassify): ActionMsg {
        return ActionMsgHandler.find(articleClassifyService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, articleClassify)))
    }

    /**
     * 获取分类树
     */
    @GetMapping(path = ["/tree", "/tree/public"])
    open fun getTree():ActionMsg{
        return ActionMsgHandler.find(articleClassifyService!!.getTree())
    }

    /**
     * 获取分类下拉列表
     */
    @GetMapping("/selectOptions")
    open fun doGetSelectOptions(): ActionMsg {
        return ActionMsgHandler.find(articleClassifyService!!.doGetSelectOptions())
    }

}