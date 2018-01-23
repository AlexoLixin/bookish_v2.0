package cn.don9cn.blog.action.system.rbac

import cn.don9cn.blog.support.mongo.core.PageResult
import cn.don9cn.blog.action.BaseAction
import cn.don9cn.blog.model.system.rbac.RegisterResult
import cn.don9cn.blog.model.system.rbac.SysUser
import cn.don9cn.blog.service.system.rbac.SysUserService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/system/rbac/user"])
open class SysUserAction : BaseAction<SysUser>() {

    @Autowired
    private var sysUserService: SysUserService? = null

    @PostMapping
    override fun baseInsert(t: SysUser): ActionMsg {
        return ActionMsgHandler.insert(sysUserService!!.baseInsert(t))
    }

    override fun baseInsertBatch(list: List<SysUser>): ActionMsg {
        return ActionMsgHandler.insert(sysUserService!!.baseInsertBatch(list))
    }

    @PutMapping
    override fun baseUpdate(t: SysUser): ActionMsg {
        return ActionMsgHandler.update(sysUserService!!.baseUpdate(t))
    }

    @DeleteMapping
    override fun baseRemove(code: String): ActionMsg {
        return ActionMsgHandler.delete(sysUserService!!.baseDeleteById(code))
    }

    @DeleteMapping("/batch")
    override fun baseRemoveBatch(codes: String): ActionMsg {
        return ActionMsgHandler.delete(sysUserService!!.baseDeleteBatch(codes))
    }

    @GetMapping
    override fun baseFindById(code: String): ActionMsg {
        return ActionMsgHandler.find(sysUserService!!.baseFindById(code))
    }

    @GetMapping("/all")
    override fun baseFindAll(): ActionMsg {
        return ActionMsgHandler.find(sysUserService!!.baseFindAll())
    }

    @GetMapping("/list")
    override fun baseFindListByParams(t: SysUser): ActionMsg {
        return ActionMsgHandler.find(sysUserService!!.baseFindListByParams(t))
    }

    @GetMapping("/page")
    override fun baseFindByPage(page: Int, limit: Int, startTime: String?, endTime: String?, orderBy: String?, t: SysUser): ActionMsg {
        return ActionMsgHandler.find(sysUserService!!.baseFindByPage(PageResult(page, limit, startTime, endTime, orderBy, t)))
    }

    @GetMapping("/checkName")
    open fun checkUserName(username: String): ActionMsg {
        return when(sysUserService!!.checkUserName(username)){
            true -> ActionMsg(true,"用户名可用")
            false -> ActionMsg(false,"用户名不可用")
        }
    }

    @PostMapping("/register")
    open fun register(validateCode: String, sysUser: SysUser, request: HttpServletRequest): RegisterResult {
        return sysUserService!!.register(validateCode, sysUser, request)
    }

    @PutMapping("/authorize")
    open fun authorizeUser(userCode: String, roleCodes: String): ActionMsg {
        return ActionMsgHandler.update(sysUserService!!.authorizeUser(userCode, roleCodes))
    }

    @GetMapping("/byUser")
    open fun getUserInfo(): ActionMsg {
        return ActionMsgHandler.find(sysUserService!!.getUserInfo())
    }

    @PutMapping("/byUser")
    open fun updateUserInfo(sysUser: SysUser): ActionMsg {
        return ActionMsgHandler.update(sysUserService!!.updateUserInfo(sysUser))
    }
}