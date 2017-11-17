package cn.don9cn.blog.service.system.msg.impl;

import cn.don9cn.blog.autoconfigs.activemq.constant.MqConstant;
import cn.don9cn.blog.autoconfigs.activemq.constant.MqDestinationType;
import cn.don9cn.blog.autoconfigs.activemq.core.MqManager;
import cn.don9cn.blog.autoconfigs.activemq.model.MqRegisterMessage;
import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.autoconfigs.activemq.model.CommonMqMessage;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.msg.interf.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: liuxindong
 * @Description: 消息service接口实现类
 * @Create: 2017/10/23 10:23
 * @Modify:
 */
@Service
@Transactional
public class SysMessageServiceImpl implements SysMessageService{

    @Autowired
    private MqConstant mqConstant;

    @Override
    public OperaResult push(CommonMqMessage message) {
        try{
            message.setProducer(MyShiroSessionUtil.getUserNameFromSession());
            //利用消息管理器,实现异步推送,前端直接响应成功状态,后台线程处理消息推送的任务
            MqManager.submit(new MqRegisterMessage(MqDestinationType.TOPIC,mqConstant.TOPIC_MSG_SYSTEM,message));
        }catch (Exception e){
            return new OperaResult(false,"消息发布失败");
        }
        return new OperaResult(true,"消息发布成功");
    }

    @Override
    public OperaResult pull() {
        return null;
    }
}
