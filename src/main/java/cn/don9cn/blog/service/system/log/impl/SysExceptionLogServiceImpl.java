package cn.don9cn.blog.service.system.log.impl;

import cn.don9cn.blog.dao.system.log.interf.SysExceptionLogDao;
import cn.don9cn.blog.dao.system.log.interf.SysOperaLogDao;
import cn.don9cn.blog.model.system.log.SysExceptionLog;
import cn.don9cn.blog.model.system.log.SysOperaLog;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.system.log.interf.SysExceptionLogService;
import cn.don9cn.blog.service.system.log.interf.SysOperaLogService;
import cn.don9cn.blog.util.DateUtil;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 异常日志service接口实现
 * @Create: 2017/10/18 13:44
 * @Modify:
 */
@Service
@Transactional
public class SysExceptionLogServiceImpl implements SysExceptionLogService {

    @Autowired
    private SysExceptionLogDao sysExceptionLogDao;


    @Override
    public OperaResult baseInsert(SysExceptionLog entity) {
        return OperaResultUtil.insert(sysExceptionLogDao.baseInsert(entity));
    }

    @Override
    public OperaResult baseInsertBatch(List<SysExceptionLog> list) {
        return OperaResultUtil.insertBatch(sysExceptionLogDao.baseInsertBatch(list));
    }

    @Override
    public OperaResult baseUpdate(SysExceptionLog entity) {
        return OperaResultUtil.update(sysExceptionLogDao.baseUpdate(entity));
    }

    @Override
    public OperaResult baseDeleteById(String id) {
        return OperaResultUtil.deleteOne(sysExceptionLogDao.baseDeleteById(id));
    }

    @Override
    public OperaResult baseDeleteBatch(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            return OperaResultUtil.deleteBatch(sysExceptionLogDao.baseDeleteBatch(codesList));
        }else{
            return new OperaResult(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    public OperaResult baseFindById(String id) {
        return OperaResultUtil.findOne(sysExceptionLogDao.baseFindById(id));
    }

    @Override
    public OperaResult baseFindAll() {
        return OperaResultUtil.findAll(sysExceptionLogDao.baseFindAll());
    }

    @Override
    public OperaResult baseFindListByParams(SysExceptionLog entity) {
        return OperaResultUtil.findListByParams(sysExceptionLogDao.baseFindListByParams(entity));
    }

    @Override
    public OperaResult baseFindByPage(PageResult<SysExceptionLog> pageResult) {
        return OperaResultUtil.findPage(sysExceptionLogDao.baseFindByPage(pageResult));
    }

    @Override
    public OperaResult doRemoveEarly30() {
        return OperaResultUtil.deleteBatch(sysExceptionLogDao.doRemoveEarly30(DateUtil.getEarly30Date()));
    }
}
