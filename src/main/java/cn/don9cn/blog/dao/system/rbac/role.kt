package cn.don9cn.blog.dao.system.rbac

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.system.rbac.SysRole
import cn.don9cn.blog.support.mongo.ext.eq
import cn.don9cn.blog.support.mongo.ext.query
import com.sun.org.apache.xpath.internal.operations.Bool
import org.springframework.stereotype.Repository

/**
 * 角色dao接口
 */
interface SysRoleDao : BaseDao<SysRole> {

    /**
     * 通过角色编码查找角色
     * @param encoding
     * @return
     */
    fun findByRoleEncoding(encoding: String): SysRole?

}

/**
 * 角色dao实现类
 */
@Repository
open class SysRoleDaoImpl : SysRoleDao {


    override fun findByRoleEncoding(encoding: String): SysRole? {
        return dslOperator{
            findOne(query("encoding" eq encoding))
        }
    }

}
