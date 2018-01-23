package cn.don9cn.blog.action.system.log

import cn.don9cn.blog.support.mongo.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.log.SysExceptionLog
import cn.don9cn.blog.service.system.log.SysExceptionLogService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/system/log/exceptionLog"])
open class SysExceptionLogAction : BaseAction<SysExceptionLog>() {

    @Autowired
    private var sysExceptionLogService: SysExceptionLogService? = null


    override fun baseInsert(t: SysExceptionLog): ActionMsg {
        return ActionMsgHandler.insert(sysExceptionLogService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<SysExceptionLog>): ActionMsg {
        return ActionMsgHandler.insert(sysExceptionLogService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(t: SysExceptionLog): ActionMsg {
        return ActionMsgHandler.update(sysExceptionLogService!!.baseUpdate(t))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return ActionMsgHandler.delete(sysExceptionLogService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return ActionMsgHandler.delete(sysExceptionLogService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return ActionMsgHandler.find(sysExceptionLogService!!.baseFindById(code))
    }

    override fun baseFindAll(): ActionMsg {
        return ActionMsgHandler.find(sysExceptionLogService!!.baseFindAll())
    }

    override fun baseFindListByParams(t: SysExceptionLog): ActionMsg {
        return ActionMsgHandler.find(sysExceptionLogService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SysExceptionLog): ActionMsg {
        return ActionMsgHandler.find(sysExceptionLogService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @DeleteMapping("/batch/pre30days")
    open fun remove30Early(): ActionMsg {
        return ActionMsgHandler.delete(sysExceptionLogService!!.doRemoveEarlyDays(30))
    }

}