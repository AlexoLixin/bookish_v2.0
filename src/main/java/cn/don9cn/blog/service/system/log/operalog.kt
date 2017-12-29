package cn.don9cn.blog.service.system.log

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.dao.system.SysLoginLogDao
import cn.don9cn.blog.dao.system.SysOperaLogDao
import cn.don9cn.blog.model.system.SysLoginLog
import cn.don9cn.blog.model.system.SysOperaLog
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 登录日志service接口
 */
interface SysOperaLogService : BaseService<SysOperaLog> {
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
open class SysOperaLogServiceImpl : SysOperaLogService {

    @Autowired
    private val sysOperaLogDao: SysOperaLogDao? = null

    @Autowired
    private val sysUserService: SysUserService? = null


    override fun baseInsert(entity: SysOperaLog): Int {
        return sysOperaLogDao!!.baseInsert(entity)
    }

    override fun baseInsertBatch(list: List<SysOperaLog>): Int {
        return sysOperaLogDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: SysOperaLog): Int {
        return sysOperaLogDao!!.baseUpdate(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return sysOperaLogDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        return if (codes.isNotBlank()){
            sysOperaLogDao!!.baseDeleteBatch(codes.split(","))
        }else{
            0
        }
    }

    override fun baseFindById(id: String): SysOperaLog? {
        return sysOperaLogDao!!.baseFindById(id)
    }

    override fun baseFindAll(): List<SysOperaLog> {
        return sysOperaLogDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: SysOperaLog): List<SysOperaLog> {
        return sysOperaLogDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<SysOperaLog>): PageResult<SysOperaLog> {
        return sysOperaLogDao!!.baseFindByPage(pageResult)
    }

    override fun doRemoveEarly30(): Int {
        return sysOperaLogDao!!.doRemoveEarly30(DateUtil.getEarly30Date())
    }
}
