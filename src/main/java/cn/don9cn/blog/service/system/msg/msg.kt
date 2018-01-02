package cn.don9cn.blog.service.system.msg

import cn.don9cn.blog.autoconfigure.activemq.constant.MqConstant
import cn.don9cn.blog.autoconfigure.activemq.constant.MqDestinationType
import cn.don9cn.blog.autoconfigure.activemq.core.MqManager
import cn.don9cn.blog.autoconfigure.activemq.model.CommonMqMessage
import cn.don9cn.blog.autoconfigure.activemq.model.MqRegisterMessage
import cn.don9cn.blog.autoconfigure.shiro.util.ShiroSessionUtil
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface SysMessageService {

    fun push(message: CommonMqMessage): ActionMsg

}

@Service
@Transactional
open class SysMessageServiceImpl : SysMessageService {

    @Autowired
    private val mqConstant: MqConstant? = null

    override fun push(message: CommonMqMessage): ActionMsg {
        try {
            message.producer = ShiroSessionUtil.getUserName()
            //利用消息管理器,实现异步推送,前端直接响应成功状态,后台线程处理消息推送的任务
            MqManager.submit(MqRegisterMessage(MqDestinationType.TOPIC, mqConstant!!.TOPIC_MSG_SYSTEM, message))
        } catch (e: Exception) {
            return ActionMsg(false, "消息发布失败")
        }
        return ActionMsg(true, "消息发布成功")
    }

}
