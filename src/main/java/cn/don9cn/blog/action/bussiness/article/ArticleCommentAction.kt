package cn.don9cn.blog.action.bussiness.article

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment
import cn.don9cn.blog.service.bussiness.ArticleCommentService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/bussiness/articleComment"])
open class ArticleCommentAction : BaseAction<ArticleComment>() {

    @Autowired
    private var articleCommentService: ArticleCommentService? = null


    @PostMapping("", "/insert/public")
    override fun baseInsert(t: ArticleComment): ActionMsg {
        return super.insert(articleCommentService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<ArticleComment>): ActionMsg {
        return super.insert(articleCommentService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(t: ArticleComment): ActionMsg {
        return super.update(articleCommentService!!.baseUpdate(t))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return super.delete(articleCommentService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(articleCommentService!!.baseDeleteBatch(codes))
    }

    override fun baseFindById(code: String): ActionMsg {
        return super.find(articleCommentService!!.baseFindById(code))
    }

    override fun baseFindAll(): ActionMsg {
        return super.find(articleCommentService!!.baseFindAll())
    }

    override fun baseFindListByParams(t: ArticleComment): ActionMsg {
        return super.find(articleCommentService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: ArticleComment): ActionMsg {
        return super.find(articleCommentService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    /**
     * 获取留言树
     * @return
     */
    @GetMapping("/tree/public")
    open fun getTree(articleCode: String): ActionMsg {
        return super.find(articleCommentService!!.getTree(articleCode))
    }
}