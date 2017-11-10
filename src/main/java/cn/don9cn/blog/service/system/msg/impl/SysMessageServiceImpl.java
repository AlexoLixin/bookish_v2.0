package cn.don9cn.blog.service.system.msg.impl;

import cn.don9cn.blog.autoconfigs.activemq.core.MqProducer;
import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.autoconfigs.activemq.model.SysMessage;
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
    private MqProducer mqProducer;

    @Override
    public OperaResult push(SysMessage message) {
        message.setProducer(MyShiroSessionUtil.getUserNameFromSession());
        try{
            mqProducer.pushToDefaultTopic(message);
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
