package cn.don9cn.blog.service.system.rbac

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.dao.system.rbac.SysPermissionDao
import cn.don9cn.blog.dao.system.rbac.SysRoleDao
import cn.don9cn.blog.model.system.rbac.SysRole
import cn.don9cn.blog.service.BaseService
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 角色service接口
 */
interface SysRoleService : BaseService<SysRole> {

    /**
     * 为角色进行权限授权
     * @param roleCode
     * @param permissionCodes
     * @return
     */
    fun authorizeRole(roleCode: String, permissionCodes: String): Int

    /**
     * 根据主键查询角色,同时填充角色的权限集合
     * @param code
     * @return
     */
    fun findRoleWithPermissions(code: String): SysRole?
}

/**
 * 角色service实现类
 */
@Service
@Transactional
open class SysRoleServiceImpl : SysRoleService {

    @Autowired
    private var sysRoleDao: SysRoleDao? = null

    @Autowired
    private var sysPermissionDao: SysPermissionDao? = null


    override fun baseInsert(entity: SysRole): Int {
        return sysRoleDao!!.baseInsert(entity)
    }

    override fun baseInsertBatch(list: List<SysRole>): Int {
        return sysRoleDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: SysRole): Int {
        return sysRoleDao!!.baseUpdate(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return sysRoleDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        return if (codes.isNotBlank()){
            sysRoleDao!!.baseDeleteBatch(codes.split(","))
        }else{
            0
        }
    }

    override fun baseFindById(id: String): SysRole? {
        return sysRoleDao!!.baseFindById(id)
    }

    override fun baseFindAll(): List<SysRole> {
        return sysRoleDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: SysRole): List<SysRole> {
        return sysRoleDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<SysRole>): PageResult<SysRole> {
        return sysRoleDao!!.baseFindByPage(pageResult)
    }

    override fun authorizeRole(roleCode: String, permissionCodes: String): Int {
        val role: SysRole = if (StringUtils.isNotBlank(permissionCodes)) {
            SysRole(roleCode, permissionCodes)
        } else {
            SysRole(roleCode, "")
        }
        return sysRoleDao!!.baseUpdate(role)
    }

    override fun findRoleWithPermissions(code: String): SysRole? {
        val role = sysRoleDao!!.baseFindById(code)
        role?.let { _role ->
            _role.menuCodesList?.let {
                _role.menuList = sysPermissionDao!!.baseFindListInIds(it)
            }
        }
        return role
    }
}
