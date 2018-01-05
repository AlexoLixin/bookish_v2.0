package cn.don9cn.blog.service.system.rbac

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant
import cn.don9cn.blog.autoconfigure.activemq.constant.MqDestinationType
import cn.don9cn.blog.autoconfigure.activemq.core.MqManager
import cn.don9cn.blog.autoconfigure.activemq.model.MailMessage
import cn.don9cn.blog.autoconfigure.activemq.model.MqRegisterMessage
import cn.don9cn.blog.autoconfigure.shiro.core.MyShiroCacheManager
import cn.don9cn.blog.autoconfigure.shiro.util.ShiroUtil
import cn.don9cn.blog.dao.system.rbac.SysRoleDao
import cn.don9cn.blog.dao.system.rbac.SysUserDao
import cn.don9cn.blog.model.system.rbac.RegisterResult
import cn.don9cn.blog.model.system.rbac.SysUser
import cn.don9cn.blog.service.BaseService
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest

/**
 * 用户service接口
 */
interface SysUserService : BaseService<SysUser> {


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

    /**
     * 为用户进行角色授权
     * @param userCode
     * @param roleCodes
     * @return
     */
    fun authorizeUser(userCode: String, roleCodes: String): Int

    /**
     * 获取用户个人信息(开放给普通用户)
     * @return
     */
    fun getUserInfo(): SysUser?

    /**
     * 更新用户个人信息(开放给普通用户)
     * @param sysUser
     * @return
     */
    fun updateUserInfo(sysUser: SysUser): Int

    /**
     * 注册用户
     * @param validateCode
     * @param sysUser
     * @return
     */
    fun register(validateCode: String, sysUser: SysUser, request: HttpServletRequest): RegisterResult
}

/**
 * 用户service接口实现类
 */
@Service
@Transactional
open class SysUserServiceImpl : SysUserService {

    @Autowired
    private var sysUserDao: SysUserDao? = null

    @Autowired
    private var sysRoleDao: SysRoleDao? = null

    @Autowired
    private var mqConstant: MqConstant? = null

    override fun baseInsert(entity: SysUser): Int {
        entity.byWay = "管理员添加"
        sysRoleDao!!.findByRoleEncoding("ROLE_USER")?.let {
            entity.roleCodes = arrayListOf(it.code!!)
        }
        return sysUserDao!!.baseInsert(entity)
    }

    override fun baseInsertBatch(list: List<SysUser>): Int {
        return sysUserDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: SysUser): Int {
        return sysUserDao!!.baseUpdate(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return sysUserDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        return if (codes.isNotBlank()){
            sysUserDao!!.baseDeleteBatch(codes.split(","))
        }else{
            0
        }
    }

    override fun baseFindById(id: String): SysUser? {
        val sysUser = sysUserDao!!.baseFindById(id)
        sysUser?.let { user ->
            user.roleCodes?.let {
                if (it.isNotEmpty()){
                    val list = sysRoleDao!!.baseFindListInIds(it)
                    user.roleList = list
                    user.roleNames = list.map { it.name!! }.reduce{ x:String,y:String -> x+" "+y }
                }
            }
        }
        return sysUser
    }

    override fun baseFindAll(): List<SysUser> {
        return sysUserDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: SysUser): List<SysUser> {
        return sysUserDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<SysUser>): PageResult<SysUser> {
        val page = sysUserDao!!.baseFindByPage(pageResult)
        page.rows.forEach { user ->
            user.roleCodes?.let {
                if (it.isNotEmpty()){
                    val list = sysRoleDao!!.baseFindListInIds(it)
                    user.roleList = list
                    user.roleNames = list.map { it.name!! }.reduce{ x:String,y:String -> x+" "+y }
                }
            }
        }
        return page
    }

    override fun findByUserName(username: String): SysUser? {
        val sysUser = sysUserDao!!.findByUserName(username)
        sysUser?.let { user ->
            user.roleCodes?.let {
                if (it.isNotEmpty()){
                    val list = sysRoleDao!!.baseFindListInIds(it)
                    user.roleList = list
                    user.roleNames = list.map { it.name!! }.reduce{ x:String,y:String -> x+" "+y }
                }
            }
        }
        return sysUser
    }

    override fun checkUserName(username: String): Boolean {
        return sysUserDao!!.checkUserName(username)
    }

    override fun authorizeUser(userCode: String, roleCodes: String): Int {
        val user = if (StringUtils.isNotBlank(roleCodes)) {
            SysUser(userCode, roleCodes)
        } else {
            SysUser(userCode, "")
        }
        return sysUserDao!!.baseUpdate(user)
    }

    override fun updateUserInfo(sysUser: SysUser): Int {
        return if (sysUser.code == MyShiroCacheManager.getUserCode()) {
            sysUserDao!!.baseUpdate(sysUser)
        } else {
            0
        }
    }

    override fun getUserInfo(): SysUser? {
        return sysUserDao!!.baseFindById(MyShiroCacheManager.getUserCode())
    }

    override fun register(validateCode: String, sysUser: SysUser, request: HttpServletRequest): RegisterResult {
        //先校验验证码
        if (validateCode.isNotBlank()) {
            return RegisterResult(false, "验证码校验失败")
        } else {
            val verifyCode = request.session.getAttribute("verifyCode")
            if (verifyCode == null || verifyCode.toString() != validateCode) {
                return RegisterResult(false, "验证码校验失败")
            }
        }
        //校验邮箱
        val regex = "\\w+@\\w+(.\\w+)+"
        if(sysUser.email==null || !sysUser.email!!.matches(regex.toRegex())){
            return RegisterResult(false, "邮箱校验失败")
        }
        //校验用户名
        if(sysUser.username==null || !checkUserName(sysUser.username!!)){
            return RegisterResult(false, "用户名不可用")
        }
        //保存用户信息到数据库
        sysUser.byWay = "用户注册"
        sysRoleDao!!.findByRoleEncoding("ROLE_USER")?.let {
            sysUser.roleCodes = arrayListOf(it.code!!)
        }

        val x = sysUserDao!!.baseInsert(sysUser)

        //注册成功后,推送信息到activeMq
        return if (x > 0) {
            try {
                MqManager.submit(MqRegisterMessage(MqDestinationType.TOPIC, mqConstant!!.TOPIC_MAIL_REGISTER,
                        MailMessage(sysUser.username, sysUser.email)))
            } catch (e: Exception) {
                // TODO
                //将用户的注册信息推送到activeMq失败,暂时先不处理,等完善好了异常处理系统再修改
                println("新用户的注册消息推送到ActiveMQ失败!")
            }

            RegisterResult(false, "注册成功")
        }else{
            RegisterResult(false, "注册失败")
        }

    }
}

