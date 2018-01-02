package cn.don9cn.blog.action.system.log

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.SysOperaLog
import cn.don9cn.blog.service.system.log.SysOperaLogService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = "/system/log/operaLog")
open class SysOperaLogAction : BaseAction<SysOperaLog>() {

    @Autowired
    private val sysOperaLogService: SysOperaLogService? = null

    override fun baseInsert(sysOperaLog: SysOperaLog): ActionMsg {
        return super.insert(sysOperaLogService!!.baseInsert(sysOperaLog))
    }

    override fun baseInsertBatch(list: List<SysOperaLog>): ActionMsg {
        return super.insert(sysOperaLogService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(sysOperaLog: SysOperaLog): ActionMsg {
        return super.update(sysOperaLogService!!.baseUpdate(sysOperaLog))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return super.delete(sysOperaLogService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(sysOperaLogService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return super.find(sysOperaLogService!!.baseFindById(code))
    }

    override fun baseFindAll(): ActionMsg {
        return super.find(sysOperaLogService!!.baseFindAll())
    }

    override fun baseFindListByParams(sysOperaLog: SysOperaLog): ActionMsg {
        return super.find(sysOperaLogService!!.baseFindListByParams(sysOperaLog))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String, endTime: String, orderBy: String, sysOperaLog: SysOperaLog): ActionMsg {
        return super.find(sysOperaLogService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, sysOperaLog)))
    }

    @DeleteMapping("/batch/pre30days")
    fun remove30Early(): ActionMsg {
        return super.delete(sysOperaLogService!!.doRemoveEarlyDays(30))
    }
}