package cn.don9cn.blog.action.bussiness.article

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.bussiness.ArticleComment
import cn.don9cn.blog.service.bussiness.ArticleCommentService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = "/bussiness/articleComment")
open class ArticleCommentAction : BaseAction<ArticleComment>() {

    @Autowired
    private val articleCommentService: ArticleCommentService? = null


    @PostMapping("", "/insert/public")
    override fun baseInsert(articleComment: ArticleComment): ActionMsg {
        return super.insert(articleCommentService!!.baseInsert(articleComment))
    }

    override fun baseInsertBatch(list: List<ArticleComment>): ActionMsg {
        return super.insert(articleCommentService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(articleComment: ArticleComment): ActionMsg {
        return super.update(articleCommentService!!.baseUpdate(articleComment))
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

    override fun baseFindListByParams(articleComment: ArticleComment): ActionMsg {
        return super.find(articleCommentService!!.baseFindListByParams(articleComment))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String, endTime: String, orderBy: String, articleComment: ArticleComment): ActionMsg {
        return super.find(articleCommentService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, articleComment)))
    }

    /**
     * 获取留言树
     * @return
     */
    @GetMapping("/tree/public")
    fun getTree(articleCode: String): ActionMsg {
        return super.find(articleCommentService!!.getTree(articleCode))
    }
}