package cn.don9cn.blog.action.system.rbac

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.SysRole
import cn.don9cn.blog.service.system.rbac.SysRoleService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = "/system/rbac/role")
open class SysRoleAction : BaseAction<SysRole>() {

    @Autowired
    private val sysRoleService: SysRoleService? = null


    @PostMapping
    override fun baseInsert(sysRole: SysRole): ActionMsg {
        return super.insert(sysRoleService!!.baseInsert(sysRole))
    }

    override fun baseInsertBatch(list: List<SysRole>): ActionMsg {
        return super.insert(sysRoleService!!.baseInsertBatch(list))
    }

    @PutMapping
    override fun baseUpdate(sysRole: SysRole): ActionMsg {
        return super.update(sysRoleService!!.baseUpdate(sysRole))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return super.delete(sysRoleService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(sysRoleService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return super.find(sysRoleService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return super.find(sysRoleService!!.baseFindAll())
    }

    @GetMapping("/list")
    override fun baseFindListByParams(sysRole: SysRole): ActionMsg {
        return super.find(sysRoleService!!.baseFindListByParams(sysRole))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String, endTime: String, orderBy: String, sysRole: SysRole): ActionMsg {
        return super.find(sysRoleService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, sysRole)))
    }

    @PutMapping("/authorize")
    protected fun authorizeRole(roleCode: String, permissionCodes: String): ActionMsg {
        return super.update(sysRoleService!!.authorizeRole(roleCode, permissionCodes))
    }
}