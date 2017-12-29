package cn.don9cn.blog.service.system.log

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.dao.system.SysLoginLogDao
import cn.don9cn.blog.model.system.SysLoginLog
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 登录日志service接口
 */
interface SysLoginLogService : BaseService<SysLoginLog> {
    /**
     * 删除30天前的日志
     * @return
     */
    fun doRemoveEarly30(): Int
}

/**
 * 登录日志service接口实现
 */
@Service
@Transactional
open class SysLoginLogServiceImpl : SysLoginLogService {

    @Autowired
    private val sysLoginLogDao: SysLoginLogDao? = null


    override fun baseInsert(entity: SysLoginLog): Int {
        return sysLoginLogDao!!.baseInsert(entity)
    }

    override fun baseInsertBatch(list: List<SysLoginLog>): Int {
        return sysLoginLogDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: SysLoginLog): Int {
        return sysLoginLogDao!!.baseUpdate(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return sysLoginLogDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        return if (codes.isNotBlank()){
            sysLoginLogDao!!.baseDeleteBatch(codes.split(","))
        }else{
            0
        }
    }

    override fun baseFindById(id: String): SysLoginLog? {
        return sysLoginLogDao!!.baseFindById(id)
    }

    override fun baseFindAll(): List<SysLoginLog> {
        return sysLoginLogDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: SysLoginLog): List<SysLoginLog> {
        return sysLoginLogDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<SysLoginLog>): PageResult<SysLoginLog> {
        return sysLoginLogDao!!.baseFindByPage(pageResult)
    }

    override fun doRemoveEarly30(): Int {
        return sysLoginLogDao!!.doRemoveEarly30(DateUtil.getEarly30Date())
    }
}