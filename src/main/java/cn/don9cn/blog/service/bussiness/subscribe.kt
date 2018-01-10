package cn.don9cn.blog.service.bussiness

import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant
import cn.don9cn.blog.autoconfigure.activemq.constant.MqDestinationType
import cn.don9cn.blog.autoconfigure.activemq.core.MqManager
import cn.don9cn.blog.autoconfigure.activemq.core.MqTask
import cn.don9cn.blog.autoconfigure.activemq.model.CommonMqMessage
import cn.don9cn.blog.autoconfigure.activemq.model.SubscribeMailMessage
import cn.don9cn.blog.autoconfigure.shiro.core.MyShiroCacheManager
import cn.don9cn.blog.dao.bussiness.SubscribeInfoDao
import cn.don9cn.blog.dao.system.rbac.SysUserDao
import cn.don9cn.blog.model.bussiness.subscribe.SubscribeInfo
import cn.don9cn.blog.service.BaseService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.action.ActionMsgHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 订阅模块service接口
 */
interface SubscribeService:BaseService<SubscribeInfo>{

    fun insert(entity: SubscribeInfo): ActionMsg

    fun delete(email: String, author: String): ActionMsg

    /**
     * 主要用于提供给邮件服务器的rpc service
     */
    fun findEmailSetByAuthor(author: String): Set<String>

    fun findByPageByUser(pageResult: PageResult<SubscribeInfo>): PageResult<SubscribeInfo>

    fun findSubscribesByPageByUser(pageResult: PageResult<SubscribeInfo>): PageResult<SubscribeInfo>

}


/**
 * 订阅模块service实现类
 */
@Service
@Transactional
open class SubscribeServiceImpl:SubscribeService{


    @Autowired
    private var subscribeInfoDao: SubscribeInfoDao? = null

    @Autowired
    private var sysUserDao: SysUserDao? = null

    @Autowired
    private var mqConstant: MqConstant? = null

    override fun baseInsert(entity: SubscribeInfo): Int {
        return subscribeInfoDao!!.baseInsert(entity)
    }

    override fun baseInsertBatch(list: List<SubscribeInfo>): Int {
        return subscribeInfoDao!!.baseInsertBatch(list)
    }

    override fun baseUpdate(entity: SubscribeInfo): Int {
        return subscribeInfoDao!!.baseUpdate(entity)
    }

    override fun baseDeleteById(id: String): Int {
        return subscribeInfoDao!!.baseDeleteById(id)
    }

    override fun baseDeleteBatch(codes: String): Int {
        return subscribeInfoDao!!.baseDeleteBatch(codes.split(",".toRegex()))
    }

    override fun baseFindById(id: String): SubscribeInfo? {
        return subscribeInfoDao!!.baseFindById(id)
    }

    override fun baseFindAll(): List<SubscribeInfo> {
        return subscribeInfoDao!!.baseFindAll()
    }

    override fun baseFindListByParams(entity: SubscribeInfo): List<SubscribeInfo> {
        return subscribeInfoDao!!.baseFindListByParams(entity)
    }

    override fun baseFindByPage(pageResult: PageResult<SubscribeInfo>): PageResult<SubscribeInfo> {
        return subscribeInfoDao!!.baseFindByPage(pageResult)
    }

    override fun findByPageByUser(pageResult: PageResult<SubscribeInfo>): PageResult<SubscribeInfo> {
        pageResult.entity!!.user = MyShiroCacheManager.getUser()?.username
        return subscribeInfoDao!!.baseFindByPage(pageResult)
    }

    override fun findSubscribesByPageByUser(pageResult: PageResult<SubscribeInfo>): PageResult<SubscribeInfo> {
        pageResult.entity!!.author = MyShiroCacheManager.getUser()?.username
        return subscribeInfoDao!!.baseFindByPage(pageResult)
    }


    /**
     * 添加订阅信息
     */
    override fun insert(entity: SubscribeInfo): ActionMsg {

        if(sysUserDao!!.checkUserName(entity.author!!)){
            return ActionMsg(false,"订阅失败,不存在作者 ${entity.author} !")
        }

        //判断是否是登录用户
        entity.user = MyShiroCacheManager.getUser()?.username?:"this is a visitor"

        if(subscribeInfoDao!!.checkInfoExists(entity)){
            return ActionMsg(false,"您已订阅作者 ${entity.author} ,无需重复订阅 !")
        }

        val x = subscribeInfoDao!!.baseInsert(entity)
        if(x>0){
            val message = CommonMqMessage()
            message.title = "您有新的订阅者!"
            message.content = "用户 ${entity.user} 订阅了您的文章 "
            message.producer = "system-subscribe"
            MqManager.submit(MqTask(MqDestinationType.QUEUE, mqConstant!!.QUEUE_USER_PREFIX + entity.author, message))
        }

        return ActionMsgHandler.insert(x,"订阅成功","订阅失败")

    }

    /**
     * 删除订阅信息
     */
    override fun delete(email: String, author: String): ActionMsg {

        if(!subscribeInfoDao!!.checkEmailExists(email)){
            return ActionMsg(false,"该邮箱 $email 没有订阅任何作者,无需退订 !")
        }

        val entity = SubscribeInfo().apply {
            this.email = email
            this.author = author
        }

        //判断是否是登录用户
        entity.user = MyShiroCacheManager.getUser()?.username?:"this is a visitor"

        val x = when (author) {
            "*" -> subscribeInfoDao!!.deleteByEmail(entity)
            else -> subscribeInfoDao!!.deleteByEmailAndAuthor(entity)
        }
        return ActionMsgHandler.delete(x,"退订成功","退订失败")
    }

    override fun findEmailSetByAuthor(author: String): Set<String> {
        return subscribeInfoDao!!.findEmailSetByAuthor(author)
    }

}