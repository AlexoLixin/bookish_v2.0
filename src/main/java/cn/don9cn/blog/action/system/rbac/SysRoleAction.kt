package cn.don9cn.blog.action.system.rbac

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.rbac.SysRole
import cn.don9cn.blog.service.system.rbac.SysRoleService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/system/rbac/role"])
open class SysRoleAction : BaseAction<SysRole>() {

    @Autowired
    private var sysRoleService: SysRoleService? = null


    @PostMapping
    override fun baseInsert(t: SysRole): ActionMsg {
        return ActionMsgHandler.insert(sysRoleService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<SysRole>): ActionMsg {
        return ActionMsgHandler.insert(sysRoleService!!.baseInsertBatch(list))
    }

    @PutMapping
    override fun baseUpdate(t: SysRole): ActionMsg {
        return ActionMsgHandler.update(sysRoleService!!.baseUpdate(t))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return ActionMsgHandler.delete(sysRoleService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return ActionMsgHandler.delete(sysRoleService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return ActionMsgHandler.find(sysRoleService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return ActionMsgHandler.find(sysRoleService!!.baseFindAll())
    }

    @GetMapping("/list")
    override fun baseFindListByParams(t: SysRole): ActionMsg {
        return ActionMsgHandler.find(sysRoleService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SysRole): ActionMsg {
        return ActionMsgHandler.find(sysRoleService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @PutMapping("/authorize")
    open fun authorizeRole(roleCode: String, permissionCodes: String): ActionMsg {
        return ActionMsgHandler.update(sysRoleService!!.authorizeRole(roleCode, permissionCodes))
    }
}