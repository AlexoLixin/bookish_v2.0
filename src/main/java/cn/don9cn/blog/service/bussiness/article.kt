package cn.don9cn.blog.service.bussiness

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant
import cn.don9cn.blog.autoconfigure.activemq.constant.MqDestinationType
import cn.don9cn.blog.autoconfigure.activemq.core.MqManager
import cn.don9cn.blog.autoconfigure.activemq.model.CommonMqMessage
import cn.don9cn.blog.autoconfigure.activemq.model.MqRegisterMessage
import cn.don9cn.blog.autoconfigure.shiro.core.MyShiroCacheManager
import cn.don9cn.blog.autoconfigure.shiro.util.ShiroUtil
import cn.don9cn.blog.dao.bussiness.ArticleAndFileDao
import cn.don9cn.blog.dao.bussiness.ArticleClassifyDao
import cn.don9cn.blog.dao.bussiness.ArticleDao
import cn.don9cn.blog.dao.bussiness.SubscribeInfoDao
import cn.don9cn.blog.dao.system.file.UploadFileDao
import cn.don9cn.blog.model.bussiness.article.Article
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.util.UuidUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * Article Service接口
 */
interface ArticleService : BaseService<Article> {
    fun doRemoveByUser(code: String): Int
    fun doUpdateByUser(article: Article): Int
    fun doFindByPageByUser(pageResult: PageResult<Article>): PageResult<Article>
}

/**
 * Article Service实现类
 */
@Service
@Transactional
open class ArticleServiceImpl : ArticleService {

    @Autowired
    private var articleDao: ArticleDao? = null

    @Autowired
    private var articleClassifyDao: ArticleClassifyDao? = null

    @Autowired
    private var uploadFileDao: UploadFileDao? = null

    @Autowired
    private var articleAndFileDao: ArticleAndFileDao? = null

    @Autowired
    private var subscribeInfoDao: SubscribeInfoDao? = null

    @Autowired
    private var mqConstant: MqConstant? = null

    //@CacheEvict(value = "Article",allEntries = true)
    override fun baseInsert(entity: Article): Int {
        entity.code = UuidUtil.getUuid()
        entity.author = MyShiroCacheManager.getUserName()
        entity.files?.isNotBlank().let {
            articleAndFileDao!!.insertBatch(entity)
        }
        val x = articleDao!!.baseInsert(entity)
        if(x>0){
            val subscribers = subscribeInfoDao!!.findUserNameSetByAuthor(entity.author!!)
            val message = CommonMqMessage()
            message.title = "新文章!"
            message.content = "您订阅的作者 [${entity.author}] 发布了新文章 <<${entity.title}>> "
            message.link = "/loadArticle?articleCode=" + entity.code
            subscribers.forEach{
                MqManager.submit(MqRegisterMessage(MqDestinationType.QUEUE, mqConstant!!.QUEUE_USER_PREFIX + it, message))
            }
        }
        return x
    }

    //@CacheEvict(value = "Article",allEntries = true)
    override fun baseInsertBatch(list: List<Article>): Int {
        list.forEach { it.author = MyShiroCacheManager.getUserName() }
        return articleDao!!.baseInsertBatch(list)
    }

    //@CacheEvict(value = "Article",allEntries = true)
    override fun baseUpdate(entity: Article): Int {
        articleAndFileDao!!.deleteByArticleCode(entity.code!!)
        entity.files?.isNotBlank().let {
            articleAndFileDao!!.insertBatch(entity)
        }
        return articleDao!!.baseUpdate(entity)
    }

    //@CacheEvict(value = "Article",allEntries = true)
    override fun baseDeleteById(id: String): Int {
        articleAndFileDao!!.deleteByArticleCode(id)
        return articleDao!!.baseDeleteById(id)
    }

    //@CacheEvict(value = "Article",allEntries = true)
    override fun baseDeleteBatch(codes: String): Int {
        return with(codes){
            val list = split(",".toRegex())
            articleAndFileDao!!.deleteByArticleCodes(list)
            articleDao!!.baseDeleteBatch(list)
        }
    }

    //@Cacheable(value = "Article")
    override fun baseFindById(id: String): Article? {
        val article = articleDao!!.baseFindById(id)
        article?.let { _article ->
            _article.files?.isNotBlank().let {
                _article.filesList = uploadFileDao!!.findListInCodes(_article.files!!.split(","))
            }
            articleClassifyDao!!.baseFindById(_article.classify!!).let {
                _article.classifyName = it?.name
            }
        }
        return article
    }

    //@Cacheable(value = "Article")
    override fun baseFindAll(): List<Article> {
        return articleDao!!.baseFindAll()
    }

    //@Cacheable(value = "Article")
    override fun baseFindListByParams(entity: Article): List<Article> {
        return articleDao!!.findListWithoutContent(entity)
    }

    //@Cacheable(value = "Article")
    override fun baseFindByPage(pageResult: PageResult<Article>): PageResult<Article> {
        val page = articleDao!!.findPageWithoutContent(pageResult)
        page.rows.forEach{ article ->
            articleClassifyDao!!.baseFindById(article.classify!!)?.let {
                article.classifyName = it.name
            }
        }
        return page
    }

    /**
     * 个人中心-普通用户删除文章(只能删除自己发布的文章,防止其他用户数据被恶意篡改)
     */
    //@CacheEvict(value = "Article",allEntries = true)
    override fun doRemoveByUser(code: String): Int {
        return articleDao!!.removeByUser(code, MyShiroCacheManager.getUserCode())
    }

    /**
     * 个人中心-普通用户更新文章(只能更新自己发布的文章,防止其他用户数据被恶意篡改)
     */
    //@CacheEvict(value = "Article",allEntries = true)
    override fun doUpdateByUser(article: Article): Int {
        article.createBy = MyShiroCacheManager.getUserCode()
        article.modifyBy = MyShiroCacheManager.getUserCode()
        return articleDao!!.updateByUser(article)
    }

    /**
     * 个人中心-普通获取文章列表(只能获取自己发布的文章列表,防止其他用户数据被恶意篡改)
     */
    //@Cacheable(value = "Article")
    override fun doFindByPageByUser(pageResult: PageResult<Article>): PageResult<Article> {
        pageResult.entity!!.createBy = MyShiroCacheManager.getUserCode()
        val page = articleDao!!.findPageWithoutContent(pageResult)
        page.rows.forEach{ article ->
            articleClassifyDao!!.baseFindById(article.classify!!)?.let {
                article.classifyName = it.name
            }
        }
        return page
    }
}

