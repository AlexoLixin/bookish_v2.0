package cn.don9cn.blog.action.system.rbac

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.SysPermission
import cn.don9cn.blog.service.system.rbac.SysPermissionService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = "/system/rbac/permission")
open class SysPermissionAction : BaseAction<SysPermission>() {

    @Autowired
    private val sysPermissionService: SysPermissionService? = null


    @PostMapping
    override fun baseInsert(sysPermission: SysPermission): ActionMsg {
        return super.insert(sysPermissionService!!.baseInsert(sysPermission))
    }

    override fun baseInsertBatch(list: List<SysPermission>): ActionMsg {
        return super.insert(sysPermissionService!!.baseInsertBatch(list))
    }

    @PutMapping
    override fun baseUpdate(sysPermission: SysPermission): ActionMsg {
        return super.update(sysPermissionService!!.baseUpdate(sysPermission))
    }

    override fun baseRemove(code: String): ActionMsg {
        return super.delete(sysPermissionService!!.baseDeleteById(code))
    }

    @DeleteMapping
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(sysPermissionService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return super.find(sysPermissionService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return super.find(sysPermissionService!!.baseFindAll())
    }

    @GetMapping("/list")
    override fun baseFindListByParams(sysPermission: SysPermission): ActionMsg {
        return super.find(sysPermissionService!!.baseFindListByParams(sysPermission))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String, endTime: String, orderBy: String, sysPermission: SysPermission): ActionMsg {
        return super.find(sysPermissionService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, sysPermission)))
    }

    @GetMapping("/tree")
    fun getTree():ActionMsg{
        return super.find(sysPermissionService!!.getTree())
    }
}
