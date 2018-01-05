package cn.don9cn.blog.service.bussiness

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.dao.bussiness.ArticleClassifyDao
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.support.vue.VueSelectOption
import cn.don9cn.blog.util.UuidUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.collections.HashMap

/**
 * 文章分类service接口
 */
interface ArticleClassifyService : BaseService<ArticleClassify> {

    fun getTree():List<ArticleClassify>

    fun doGetSelectOptions(): List<VueSelectOption>

}

/**
 * 文章分类模块service接口实现类
 */
@Service
@Transactional
open class ArticleClassifyServiceImpl : ArticleClassifyService {

    @Autowired
    private var articleClassifyDao: ArticleClassifyDao? = null

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseInsert(entity: ArticleClassify): Int {
        val code = UuidUtil.getUuid()
        entity.code = code
        //保存当前节点
        val x = articleClassifyDao!!.baseInsert(entity)
        if(x > 0){
            entity.parent?.let {
                if(it != "ROOT")
                    articleClassifyDao!!.updateParentForPush(it, code)
            }
        }
        return x
    }

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseInsertBatch(list: List<ArticleClassify>): Int {
        return articleClassifyDao!!.baseInsertBatch(list)
    }

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseUpdate(entity: ArticleClassify): Int {
        return articleClassifyDao!!.update(entity)
    }

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseDeleteById(id: String): Int {
        return articleClassifyDao!!.baseDeleteById(id)
    }

    //@CacheEvict(value = "ArticleClassify",allEntries = true)
    override fun baseDeleteBatch(codes: String): Int {
        if (codes.isNotBlank()) {
            val codesList = codes.split(",".toRegex())
            // 先删除选中的节点
            val removeNodes = articleClassifyDao!!.removeNodes(codesList)
            removeNodes.forEach { node ->
                // 级联删除其子节点
                node.childrenCodes.forEach {
                    articleClassifyDao!!.baseDeleteById(it)
                }
                // 更新父节点
                articleClassifyDao!!.updateParentForPull(node.parent!!, node.code!!)
            }
            return removeNodes.size
        } else {
            return 0
        }
    }

    //@Cacheable(value = "ArticleClassify")
    override fun baseFindById(id: String): ArticleClassify? {
        return articleClassifyDao!!.baseFindById(id)
    }

    //@Cacheable(value = "ArticleClassify")
    override fun baseFindAll(): List<ArticleClassify> {
        return articleClassifyDao!!.baseFindAll()
    }

    //@Cacheable(value = "ArticleClassify")
    override fun baseFindListByParams(entity: ArticleClassify): List<ArticleClassify> {
        return articleClassifyDao!!.baseFindListByParams(entity)
    }

    //@Cacheable(value = "ArticleClassify")
    override fun baseFindByPage(pageResult: PageResult<ArticleClassify>): PageResult<ArticleClassify> {
        return articleClassifyDao!!.baseFindByPage(pageResult)
    }

    override fun getTree(): List<ArticleClassify> {
        val all = articleClassifyDao!!.baseFindAll()
        val map = HashMap<String, ArticleClassify>()
        all.forEach { map[it.code!!] = it }
        all.forEach { node ->
            node.childrenCodes.forEach {
                map[it]?.let {
                    node.addChild(it)
                }
            }
        }
        return all.filter { it.parent == "ROOT" }.toList()
    }

    /**
     * 获取下拉分类
     * @return
     */
    //@Cacheable(value = "ArticleClassify")
    override fun doGetSelectOptions(): List<VueSelectOption> {
        val all = articleClassifyDao!!.baseFindAll()
        val map = HashMap<String, ArticleClassify>()
        val result = arrayListOf<VueSelectOption>()
        all.forEach { map[it.code!!] = it }
        all.filter { it.childrenCodes.isNotEmpty() }
            .forEach { parent ->
                parent.childrenCodes.forEach { childCode ->
                    result.add(VueSelectOption("【 " + parent.name + " 】 - " + map[childCode]!!.name, childCode))
                }
            }
        return result
    }

}


