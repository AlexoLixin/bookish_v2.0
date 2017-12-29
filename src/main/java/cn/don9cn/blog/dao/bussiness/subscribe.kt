package cn.don9cn.blog.dao.bussiness

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.bussiness.SubscribeInfo
import cn.don9cn.blog.support.mongo.ext.and
import cn.don9cn.blog.support.mongo.ext.eq
import cn.don9cn.blog.support.mongo.ext.query
import org.springframework.stereotype.Repository


/**
 * 订阅模块dao接口
 */
interface SubscribeInfoDao:BaseDao<SubscribeInfo>{
    fun deleteByEmail(email: String): Int

    fun deleteByEmailAndAuthor(email: String, author: String): Int

    fun findByAuthor(author: String): Set<SubscribeInfo>
}

/**
 * 订阅模块dao实现类
 */
@Repository
open class SubscribeInfoDaoImpl:SubscribeInfoDao{

    override fun deleteByEmail(email: String): Int {
        return dslOperator{
            remove<SubscribeInfo>(query("email" eq email))
        }
    }

    override fun deleteByEmailAndAuthor(email: String, author: String): Int {
        return dslOperator{
            remove<SubscribeInfo>(query("email" eq email).and("author" eq author))
        }
    }

    override fun findByAuthor(author: String): Set<SubscribeInfo> {
        val list = dslOperator.findList<SubscribeInfo>(query("author" eq author))
        return list.toSet()
    }
}