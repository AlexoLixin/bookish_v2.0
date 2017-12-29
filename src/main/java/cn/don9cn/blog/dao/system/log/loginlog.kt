package cn.don9cn.blog.dao.system.log

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.system.SysLoginLog
import cn.don9cn.blog.support.mongo.ext.lt
import cn.don9cn.blog.support.mongo.ext.query
import org.springframework.stereotype.Repository

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
