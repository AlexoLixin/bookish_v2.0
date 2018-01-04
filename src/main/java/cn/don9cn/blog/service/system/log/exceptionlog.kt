package cn.don9cn.blog.service.system.log

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.dao.system.log.SysExceptionLogDao
import cn.don9cn.blog.model.system.log.SysExceptionLog
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.service.system.rbac.SysUserService
import cn.don9cn.blog.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 删除30天前的日志
 */
interface SysExceptionLogService : BaseService<SysExceptionLog> {

    /**
     * 删除30天前的日志
     * @return
     */
    fun doRemoveEarlyDays(num: Int): Int

}

/**
 * 异常日志service接口实现
 */
@Service
@Transactional
open class SysExceptionLogServiceImpl : SysExceptionLogService {

    @Autowired
    private var sysExceptionLogDao: SysExceptionLogDao? = null

    @Autowired
    private var sysUserService: SysUserService? = null


    override fun baseInsert(entity: SysExceptionLog): Int {
        return sysExceptionLogDao!!.baseInsert(entity)
    }

    override fun baseInsertBatch(list: List<SysExceptionLog>): Int {
        return sysExceptionLogDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: SysExceptionLog): Int {
        return sysExceptionLogDao!!.baseUpdate(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return sysExceptionLogDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        return if (codes.isNotBlank()){
            sysExceptionLogDao!!.baseDeleteBatch(codes.split(","))
        }else{
            0
        }
    }

    override fun baseFindById(id: String): SysExceptionLog? {
        val exceptionLog = sysExceptionLogDao!!.baseFindById(id)
        exceptionLog?.let { log ->
            exceptionLog.userCode?.let{
                sysUserService!!.baseFindById(it)?.let {
                    log.userName = it.username
                    log.userRole = it.roleNames
                }
            }
        }

        return exceptionLog
    }

    override fun baseFindAll(): List<SysExceptionLog> {
        return sysExceptionLogDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: SysExceptionLog): List<SysExceptionLog> {
        return sysExceptionLogDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<SysExceptionLog>): PageResult<SysExceptionLog> {
        return sysExceptionLogDao!!.baseFindByPage(pageResult)
    }

    override fun doRemoveEarlyDays(num: Int): Int {
        return sysExceptionLogDao!!.doRemoveEarlyDays(num.days.ago.pattern)
    }
}
