package cn.don9cn.blog.service.bussiness

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.dao.bussiness.SubscribeInfoDao
import cn.don9cn.blog.model.bussiness.SubscribeInfo
import cn.don9cn.blog.service.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 订阅模块service接口
 */
interface SubscribeService:BaseService<SubscribeInfo>{
    fun delete(email: String, author: String): Int

    fun findByAuthor(author: String): Set<SubscribeInfo>
}


/**
 * 订阅模块service实现类
 */
@Service
@Transactional
open class SubscribeServiceImpl:SubscribeService{

    @Autowired
    private var subscribeInfoDao: SubscribeInfoDao? = null

    override fun baseInsert(entity: SubscribeInfo): Int {
        return subscribeInfoDao!!.baseInsert(entity)
    }

    override fun baseInsertBatch(list: List<SubscribeInfo>): Int {
        return subscribeInfoDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: SubscribeInfo): Int {
        return subscribeInfoDao!!.baseUpdate(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return subscribeInfoDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        return subscribeInfoDao!!.baseDeleteBatch(codes.split(",".toRegex()))
    }

    override fun baseFindById(id: String): SubscribeInfo? {
        return subscribeInfoDao!!.baseFindById(id)
    }

    override fun baseFindAll(): List<SubscribeInfo> {
        return subscribeInfoDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: SubscribeInfo): List<SubscribeInfo> {
        return subscribeInfoDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<SubscribeInfo>): PageResult<SubscribeInfo> {
        return subscribeInfoDao!!.baseFindByPage(pageResult)
    }

    override fun delete(email: String, author: String): Int {
        return subscribeInfoDao!!.deleteByEmail(email)
    }

    override fun findByAuthor(author: String): Set<SubscribeInfo> {
        return subscribeInfoDao!!.findByAuthor(author)
    }

}