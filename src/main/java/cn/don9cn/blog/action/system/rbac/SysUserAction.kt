package cn.don9cn.blog.action.system.rbac

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.RegisterResult
import cn.don9cn.blog.model.system.SysUser
import cn.don9cn.blog.service.system.rbac.SysUserService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = "/system/rbac/user")
open class SysUserAction : BaseAction<SysUser>() {

    @Autowired
    private val sysUserService: SysUserService? = null

    @PostMapping
    override fun baseInsert(sysUser: SysUser): ActionMsg {
        return super.insert(sysUserService!!.baseInsert(sysUser))
    }

    override fun baseInsertBatch(list: List<SysUser>): ActionMsg {
        return super.insert(sysUserService!!.baseInsertBatch(list))
    }

    @PutMapping
    override fun baseUpdate(sysUser: SysUser): ActionMsg {
        return super.update(sysUserService!!.baseUpdate(sysUser))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return super.delete(sysUserService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return super.delete(sysUserService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return super.find(sysUserService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return super.find(sysUserService!!.baseFindAll())
    }

    @GetMapping("/list")
    override fun baseFindListByParams(sysUser: SysUser): ActionMsg {
        return super.find(sysUserService!!.baseFindListByParams(sysUser))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String, endTime: String, orderBy: String, sysUser: SysUser): ActionMsg {
        return super.find(sysUserService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, sysUser)))
    }

    @GetMapping("/checkName")
    fun checkUserName(username: String): ActionMsg {
        return when(sysUserService!!.checkUserName(username)){
            true -> ActionMsg(true,"用户名可用")
            false -> ActionMsg(false,"用户名不可用")
        }
    }

    @PostMapping("/register")
    fun register(validateCode: String, sysUser: SysUser, request: HttpServletRequest): RegisterResult {
        return sysUserService!!.register(validateCode, sysUser, request)
    }

    @PutMapping("/authorize")
    fun authorizeUser(userCode: String, roleCodes: String): ActionMsg {
        return super.update(sysUserService!!.authorizeUser(userCode, roleCodes))
    }

    @PutMapping("/byUser")
    fun updateUserInfo(sysUser: SysUser): ActionMsg {
        return super.update(sysUserService!!.updateUserInfo(sysUser))
    }
}