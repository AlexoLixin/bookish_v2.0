package cn.don9cn.blog.service.system.rbac

import cn.don9cn.blog.support.mongo.core.PageResult
import cn.don9cn.blog.dao.system.rbac.SysPermissionDao
import cn.don9cn.blog.model.system.rbac.SysPermission
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.util.UuidUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 权限service接口
 */
interface SysPermissionService : BaseService<SysPermission> {

    /**
     * 获取分类树
     * @return
     */
    fun getTree(): List<SysPermission>

}

/**
 * 权限service接口实现
 */
@Service
@Transactional
open class SysPermissionServiceImpl : SysPermissionService {


    @Autowired
    private var sysPermissionDao: SysPermissionDao? = null

    override fun baseInsert(entity: SysPermission): Int {
        val code = UuidUtil.getUuid()
        entity.code = code
        //保存当前节点
        val x = sysPermissionDao!!.baseInsert(entity)
        if(x > 0){
            entity.parent?.let {
                if(it != "ROOT")
                    sysPermissionDao!!.updateParentForPush(it, code)
            }
        }
        return x
    }

    override fun baseInsertBatch(list: List<SysPermission>): Int {
        return sysPermissionDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: SysPermission): Int {
        return sysPermissionDao!!.update(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return sysPermissionDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        if (codes.isNotBlank()) {
            val codesList = codes.split(",".toRegex())
            // 先删除选中的节点
            val removeNodes = sysPermissionDao!!.removeNodes(codesList)
            removeNodes.forEach { node ->
                // 级联删除其子节点
                node.childrenCodes.forEach {
                    sysPermissionDao!!.baseDeleteById(it)
                }
                // 更新父节点
                sysPermissionDao!!.updateParentForPull(node.parent!!, node.code!!)
            }
            return removeNodes.size
        } else {
            return 0
        }
    }

    override fun baseFindById(id: String): SysPermission? {
        return sysPermissionDao!!.baseFindById(id)
    }

    override fun baseFindAll(): List<SysPermission> {
        return sysPermissionDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: SysPermission): List<SysPermission> {
        return sysPermissionDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<SysPermission>): PageResult<SysPermission> {
        return sysPermissionDao!!.baseFindByPage(pageResult)
    }

    override fun getTree(): List<SysPermission> {
        val all = sysPermissionDao!!.baseFindAll()
        val map = HashMap<String, SysPermission>()
        all.forEach { map[it.code!!] = it }
        all.forEach { node ->
            node.childrenCodes.forEach {
                map[it]?.let {
                    node.addChild(it)
                }
            }
        }
        return all.filter { it.parent == "ROOT" }.toList()
    }

}
