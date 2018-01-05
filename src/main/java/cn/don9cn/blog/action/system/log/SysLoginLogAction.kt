package cn.don9cn.blog.action.system.log

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.log.SysLoginLog
import cn.don9cn.blog.service.system.log.SysLoginLogService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/system/log/loginLog"])
open class SysLoginLogAction : BaseAction<SysLoginLog>() {

    @Autowired
    private var sysLoginLogService: SysLoginLogService? = null

    override fun baseInsert(t: SysLoginLog): ActionMsg {
        return super.insert(sysLoginLogService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<SysLoginLog>): ActionMsg {
        return super.insert(sysLoginLogService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(t: SysLoginLog): ActionMsg {
        return super.update(sysLoginLogService!!.baseUpdate(t))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return super.delete(sysLoginLogService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(sysLoginLogService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return super.find(sysLoginLogService!!.baseFindById(code))
    }

    override fun baseFindAll(): ActionMsg {
        return super.find(sysLoginLogService!!.baseFindAll())
    }

    override fun baseFindListByParams(t: SysLoginLog): ActionMsg {
        return super.find(sysLoginLogService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SysLoginLog): ActionMsg {
        return super.find(sysLoginLogService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @DeleteMapping("/batch/pre30days")
    open fun remove30Early(): ActionMsg {
        return super.delete(sysLoginLogService!!.doRemoveEarlyDays(30))
    }

}