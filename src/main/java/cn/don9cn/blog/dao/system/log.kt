package cn.don9cn.blog.dao.system

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.system.SysExceptionLog
import cn.don9cn.blog.model.system.SysLoginLog
import cn.don9cn.blog.model.system.SysOperaLog
import cn.don9cn.blog.support.mongo.ext.lt
import cn.don9cn.blog.support.mongo.ext.query
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
 * 登录日志dao接口
 */
interface SysLoginLogDao : BaseDao<SysLoginLog> {
    /**
     * 删除30天前的日志记录
     * @return
     */
    fun doRemoveEarly30(early30Date: String): Int
}

/**
 * 登录日志dao接口实现
 */
@Repository
open class SysLoginLogDaoImpl : SysLoginLogDao {

    /**
     * 删除30天前的日志记录
     * @return
     */
    override fun doRemoveEarly30(early30Date: String): Int {
        return dslOperator{
            remove<SysLoginLog>(query("createTime" lt early30Date))
        }
    }
}



/**
 * 操作日志dao接口
 */
interface SysOperaLogDao : BaseDao<SysOperaLog> {
    /**
     * 删除30天前的日志记录
     * @return
     */
    fun doRemoveEarly30(early30Date: String): Int
}

/**
 * 操作日志dao接口实现
 */
@Repository
open class SysOperaLogDaoImpl : SysOperaLogDao {

    /**
     * 删除30天前的日志记录
     * @return
     */
    override fun doRemoveEarly30(early30Date: String): Int {
        return dslOperator{
            remove<SysOperaLog>(query("createTime" lt early30Date))
        }
    }
}


/**
 * 异常日志dao接口
 */
interface SysExceptionLogDao : BaseDao<SysExceptionLog> {
    /**
     * 删除30天前的日志记录
     * @return
     */
    fun doRemoveEarly30(early30Date: String): Int
}

/**
 * 操作日志dao接口实现
 */
@Repository
open class SysExceptionLogDaoImpl : SysExceptionLogDao {

    /**
     * 删除30天前的日志记录
     * @return
     */
    override fun doRemoveEarly30(early30Date: String): Int {
        return dslOperator{
            remove<SysExceptionLog>(query("createTime" lt early30Date))
        }
    }
}
