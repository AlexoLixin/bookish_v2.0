package cn.don9cn.blog.dao.bussiness

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo
import cn.don9cn.blog.support.mongo.ext.and
import cn.don9cn.blog.support.mongo.ext.eq
import cn.don9cn.blog.support.mongo.ext.query
import org.springframework.stereotype.Repository


/**
 * 订阅模块dao接口
 */
interface SubscribeInfoDao:BaseDao<SubscribeInfo>{

    fun deleteByEmail(entity: SubscribeInfo): Int

    fun deleteByEmailAndAuthor(entity: SubscribeInfo): Int

    fun findEmailSetByAuthor(author: String): Set<String>

    fun findUserNameSetByAuthor(author: String): Set<String>

    fun checkEmailExists(email: String): Boolean

    fun checkInfoExists(entity: SubscribeInfo): Boolean
}

/**
 * 订阅模块dao实现类
 */
@Repository
open class SubscribeInfoDaoImpl:SubscribeInfoDao{

    override fun deleteByEmail(entity: SubscribeInfo): Int {
        return dslOperator{
            remove<SubscribeInfo>(query("user" eq entity.user!!).and("email" eq entity.email!!))
        }
    }

    override fun deleteByEmailAndAuthor(entity: SubscribeInfo): Int {
        return dslOperator{
            remove<SubscribeInfo>(createQueryByEntity(entity))
        }
    }

    override fun findEmailSetByAuthor(author: String): Set<String> {
        val list = dslOperator.findList<SubscribeInfo>(query("author" eq author))
        return list.map { it.email!! }.toSet()
    }

    override fun findUserNameSetByAuthor(author: String): Set<String> {
        val list = dslOperator.findList<SubscribeInfo>(query("author" eq author))
        return list.map { it.user!! }.filter { it != "this is a visitor" }.toSet()
    }

    override fun checkEmailExists(email: String): Boolean {
        return dslOperator.findOne<SubscribeInfo>(query("email" eq email)) != null
    }

    override fun checkInfoExists(entity: SubscribeInfo): Boolean {
        return dslOperator{
            findOne<SubscribeInfo>(query("user" eq entity.user!!)
                                        .and("email" eq entity.email!!)
                                        .and("author" eq entity.author!!)
            )
        } != null
    }

}

