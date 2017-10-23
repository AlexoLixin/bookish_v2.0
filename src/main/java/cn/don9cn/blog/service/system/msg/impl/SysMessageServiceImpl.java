package cn.don9cn.blog.service.system.msg.impl;

import cn.don9cn.blog.autoconfigs.kafka.MessageProducer;
import cn.don9cn.blog.model.system.msg.SysMessage;
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
    private MessageProducer messageProducer;

    @Override
    public OperaResult push(SysMessage message) {
        return null;
    }

    @Override
    public OperaResult pull() {
        return null;
    }
}
