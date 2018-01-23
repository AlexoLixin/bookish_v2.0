package cn.don9cn.blog.action.bussiness.article

import cn.don9cn.blog.support.mongo.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment
import cn.don9cn.blog.service.bussiness.ArticleCommentService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/bussiness/articleComment"])
open class ArticleCommentAction : BaseAction<ArticleComment>() {

    @Autowired
    private var articleCommentService: ArticleCommentService? = null


    @PostMapping("", "/insert/public")
    override fun baseInsert(t: ArticleComment): ActionMsg {
        return ActionMsgHandler.insert(articleCommentService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<ArticleComment>): ActionMsg {
        return ActionMsgHandler.insert(articleCommentService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(t: ArticleComment): ActionMsg {
        return ActionMsgHandler.update(articleCommentService!!.baseUpdate(t))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return ActionMsgHandler.delete(articleCommentService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return ActionMsgHandler.delete(articleCommentService!!.baseDeleteBatch(codes))
    }

    override fun baseFindById(code: String): ActionMsg {
        return ActionMsgHandler.find(articleCommentService!!.baseFindById(code))
    }

    override fun baseFindAll(): ActionMsg {
        return ActionMsgHandler.find(articleCommentService!!.baseFindAll())
    }

    override fun baseFindListByParams(t: ArticleComment): ActionMsg {
        return ActionMsgHandler.find(articleCommentService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: ArticleComment): ActionMsg {
        return ActionMsgHandler.find(articleCommentService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    /**
     * 获取留言树
     * @return
     */
    @GetMapping("/tree/public")
    open fun getTree(articleCode: String): ActionMsg {
        return ActionMsgHandler.find(articleCommentService!!.getTree(articleCode))
    }
}