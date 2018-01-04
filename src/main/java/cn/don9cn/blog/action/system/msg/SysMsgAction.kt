package cn.don9cn.blog.action.system.msg

import cn.don9cn.blog.autoconfigure.activemq.model.CommonMqMessage
import cn.don9cn.blog.service.system.msg.SysMessageService
import cn.don9cn.blog.support.action.ActionMsg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/system/msg")
open class SysMessageAction {

    @Autowired
    private var sysMessageService: SysMessageService? = null

    @PostMapping("/push")
    open fun push(commonMqMessage: CommonMqMessage): ActionMsg {
        return sysMessageService!!.push(commonMqMessage)
    }

}