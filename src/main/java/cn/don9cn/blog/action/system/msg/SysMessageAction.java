package cn.don9cn.blog.action.system.msg;

import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.msg.interf.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public OperaResult push(){

        return null;
    }

}
