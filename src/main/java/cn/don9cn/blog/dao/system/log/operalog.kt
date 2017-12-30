package cn.don9cn.blog.dao.system.log

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.system.SysOperaLog
import cn.don9cn.blog.support.mongo.ext.lt
import cn.don9cn.blog.support.mongo.ext.query
import org.springframework.stereotype.Repository

/**
 * 操作日志dao接口
 */
interface SysOperaLogDao : BaseDao<SysOperaLog> {
    /**
     * 删除30天前的日志记录
     * @return
     */
    fun doRemoveEarlyDays(days: String): Int
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
    override fun doRemoveEarlyDays(days: String): Int {
        return dslOperator{
            remove<SysOperaLog>(query("createTime" lt days))
        }
    }
}
