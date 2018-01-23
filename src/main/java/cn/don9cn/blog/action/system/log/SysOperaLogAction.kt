package cn.don9cn.blog.action.system.log

import cn.don9cn.blog.support.mongo.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.log.SysOperaLog
import cn.don9cn.blog.service.system.log.SysOperaLogService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/system/log/operaLog"])
open class SysOperaLogAction : BaseAction<SysOperaLog>() {

    @Autowired
    private var sysOperaLogService: SysOperaLogService? = null

    override fun baseInsert(t: SysOperaLog): ActionMsg {
        return ActionMsgHandler.insert(sysOperaLogService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<SysOperaLog>): ActionMsg {
        return ActionMsgHandler.insert(sysOperaLogService!!.baseInsertBatch(list))
    }

    override fun baseUpdate(t: SysOperaLog): ActionMsg {
        return ActionMsgHandler.update(sysOperaLogService!!.baseUpdate(t))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return ActionMsgHandler.delete(sysOperaLogService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return ActionMsgHandler.delete(sysOperaLogService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return ActionMsgHandler.find(sysOperaLogService!!.baseFindById(code))
    }

    override fun baseFindAll(): ActionMsg {
        return ActionMsgHandler.find(sysOperaLogService!!.baseFindAll())
    }

    override fun baseFindListByParams(t: SysOperaLog): ActionMsg {
        return ActionMsgHandler.find(sysOperaLogService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SysOperaLog): ActionMsg {
        return ActionMsgHandler.find(sysOperaLogService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @DeleteMapping("/batch/pre30days")
    open fun remove30Early(): ActionMsg {
        return ActionMsgHandler.delete(sysOperaLogService!!.doRemoveEarlyDays(30))
    }
}