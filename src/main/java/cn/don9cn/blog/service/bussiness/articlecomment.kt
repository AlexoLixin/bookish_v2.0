package cn.don9cn.blog.service.bussiness

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant
import cn.don9cn.blog.autoconfigure.activemq.constant.MqDestinationType
import cn.don9cn.blog.autoconfigure.activemq.core.MqManager
import cn.don9cn.blog.autoconfigure.activemq.model.CommonMqMessage
import cn.don9cn.blog.autoconfigure.activemq.model.MqRegisterMessage
import cn.don9cn.blog.dao.bussiness.ArticleCommentDao
import cn.don9cn.blog.dao.bussiness.ArticleDao
import cn.don9cn.blog.model.bussiness.acticlecomment.ArticleComment
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.util.UuidUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.collections.HashMap

/**
 * 文章分类service接口
 */
interface ArticleCommentService : BaseService<ArticleComment> {
    fun getTree(articleCode:String):List<ArticleComment>
}

/**
 * 文章分类模块service接口实现类
 */
@Service
@Transactional
open class ArticleCommentServiceImpl : ArticleCommentService {

    @Autowired
    private var articleCommentDao: ArticleCommentDao? = null

    @Autowired
    private var articleDao: ArticleDao? = null

    @Autowired
    private var mqConstant: MqConstant? = null

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseInsert(entity: ArticleComment): Int {
        val code = UuidUtil.getUuid()
        entity.code = code
        //保存当前节点
        val x = articleCommentDao!!.baseInsert(entity)
        if(x > 0){
            //更新父节点
            entity.parent?.let {
                if(it != "ROOT")
                    articleCommentDao!!.updateParentForPush(it, code)
            }

            //保存成功后推送消息到用户的个人消息队列
            val article = articleDao!!.baseFindById(entity.articleCode!!)
            article?.let {
                val message = CommonMqMessage()
                message.title = "您有新留言!"
                message.content = "您的文章 《" + it.title + "》 收到一条来自 [" + entity.nickname + "] 的新留言!"
                message.link = "/loadArticle?articleCode=" + it.code
                MqManager.submit(MqRegisterMessage(MqDestinationType.QUEUE, mqConstant!!.QUEUE_USER_PREFIX + it.author, message))
            }
        }
        return x
    }

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseInsertBatch(list: List<ArticleComment>): Int {
        return articleCommentDao!!.baseInsertBatch(list)
    }

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseUpdate(entity: ArticleComment): Int {
        return articleCommentDao!!.baseUpdate(entity)
    }

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseDeleteById(id: String): Int {
        val removeNode = articleCommentDao!!.removeNode(id)
        removeNode?.let { node ->
            // 级联删除其子节点
            node.replyCodes.forEach {
                articleCommentDao!!.baseDeleteById(it)
            }
            // 更新父节点
            articleCommentDao!!.updateParentForPull(node.parent!!, node.code!!)
            return 1
        }
        return 0
    }

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseDeleteBatch(codes: String): Int {
        if (codes.isNotBlank()) {
            val codesList = codes.split(",".toRegex())
            // 先删除选中的节点
            val removeNodes = articleCommentDao!!.removeNodes(codesList)
            removeNodes.forEach { node ->
                // 级联删除其子节点
                node.replyCodes.forEach {
                    articleCommentDao!!.baseDeleteById(it)
                }
                // 更新父节点
                articleCommentDao!!.updateParentForPull(node.parent!!, node.code!!)
            }
            return removeNodes.size
        } else {
            return 0
        }
    }

    //@Cacheable(value = "ArticleClassify")
    override fun baseFindById(id: String): ArticleComment? {
        return articleCommentDao!!.baseFindById(id)
    }

    //@Cacheable(value = "ArticleClassify")
    override fun baseFindAll(): List<ArticleComment> {
        return articleCommentDao!!.baseFindAll()
    }

    //@Cacheable(value = "ArticleClassify")
    override fun baseFindListByParams(entity: ArticleComment): List<ArticleComment> {
        return articleCommentDao!!.baseFindListByParams(entity)
    }

    //@Cacheable(value = "ArticleClassify")
    override fun baseFindByPage(pageResult: PageResult<ArticleComment>): PageResult<ArticleComment> {
        return articleCommentDao!!.baseFindByPage(pageResult)
    }

    override fun getTree(articleCode:String): List<ArticleComment> {
        val all = articleCommentDao!!.findListByArticleCode(articleCode)
        val map = HashMap<String, ArticleComment>()
        all.forEach { map[it.code!!] = it }
        all.forEach { node ->
            node.replyCodes.forEach {
                map[it]?.let {
                    node.addReply(it)
                }
            }
        }
        return all.filter { it.parent == "ROOT" }.toList()
    }

}


