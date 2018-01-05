package cn.don9cn.blog.dao.system.rbac

import cn.don9cn.blog.dao.BaseDao
import cn.don9cn.blog.model.system.rbac.SysUser
import cn.don9cn.blog.support.mongo.ext.eq
import cn.don9cn.blog.support.mongo.ext.query
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
 * 用户dao接口
 */
interface SysUserDao : BaseDao<SysUser> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    fun findByUserName(username: String): SysUser?

    /**
     * 检查用户名是否已经存在
     * @param username
     * @return
     */
    fun checkUserName(username: String): Boolean
}

/**
 * 用户dao实现
 */
@Repository
open class SysUserDaoImpl : SysUserDao {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    override fun findByUserName(username: String): SysUser? {
        return dslOperator{
            findOne(query("username" eq username))
        }
    }

    /**
     * 检查用户名是否已经存在
     * @param username
     * @return
     */
    override fun checkUserName(username: String): Boolean {
        dslOperator.mongoTemplate.count(query("username" eq username),SysUser::class.java).let {
            if(it > 0){
                return false
            }
            return true
        }
    }

}
