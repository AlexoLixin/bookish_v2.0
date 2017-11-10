package cn.don9cn.blog.service.system.msg.interf;

import cn.don9cn.blog.autoconfigs.activemq.model.SysMessage;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;

/**
 * @Author: liuxindong
 * @Description: 消息service接口
 * @Create: 2017/10/23 10:22
 * @Modify:
 */
public interface SysMessageService {

    OperaResult push(SysMessage message);

    OperaResult pull();

}
