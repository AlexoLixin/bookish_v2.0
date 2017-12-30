package cn.don9cn.blog.action.system.msg;

import cn.don9cn.blog.autoconfigure.activemq.model.CommonMqMessage;
import cn.don9cn.blog.service.system.msg.interf.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuxindong
 * @Description: 消息action
 * @Create: 2017/10/23 10:58
 * @Modify:
 */
@RestController
@RequestMapping("/system/msg")
public class SysMessageAction {

    @Autowired
    private SysMessageService sysMessageService;

    @PostMapping("/push")
    public Object push(CommonMqMessage commonMqMessage){
        return sysMessageService.push(commonMqMessage);
    }

}
