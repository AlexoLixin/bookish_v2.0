package cn.don9cn.blog.service.system.log.impl;

import cn.don9cn.blog.dao.system.log.interf.SysOperaLogDao;
import cn.don9cn.blog.model.system.log.SysOperaLog;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.operaresult.util.OperaResultUtil;
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
 * @Description: 操作日志service接口实现
 * @Create: 2017/10/18 13:44
 * @Modify:
 */
@Service
@Transactional
public class SysOperaLogServiceImpl implements SysOperaLogService {

    @Autowired
    private SysOperaLogDao sysOperaLogDao;

    @Override
    public OperaResult baseInsert(SysOperaLog entity) {
        return OperaResultUtil.insert(sysOperaLogDao.baseInsert(entity));
    }

    @Override
    public OperaResult baseInsertBatch(List<SysOperaLog> list) {
        return OperaResultUtil.insertBatch(sysOperaLogDao.baseInsertBatch(list));
    }

    @Override
    public OperaResult baseUpdate(SysOperaLog entity) {
        return OperaResultUtil.update(sysOperaLogDao.baseUpdate(entity));
    }

    @Override
    public OperaResult baseDeleteById(String id) {
        return OperaResultUtil.deleteOne(sysOperaLogDao.baseDeleteById(id));
    }

    @Override
    public OperaResult baseDeleteBatch(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            return OperaResultUtil.deleteBatch(sysOperaLogDao.baseDeleteBatch(codesList));
        }else{
            return new OperaResult(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    public OperaResult baseFindById(String id) {
        return OperaResultUtil.findOne(sysOperaLogDao.baseFindById(id));
    }

    @Override
    public OperaResult baseFindAll() {
        return OperaResultUtil.findAll(sysOperaLogDao.baseFindAll());
    }

    @Override
    public OperaResult baseFindListByParams(SysOperaLog entity) {
        return OperaResultUtil.findListByParams(sysOperaLogDao.baseFindListByParams(entity));
    }

    @Override
    public OperaResult baseFindByPage(PageResult<SysOperaLog> pageResult) {
        return OperaResultUtil.findPage(sysOperaLogDao.baseFindByPage(pageResult));
    }

    @Override
    public OperaResult doRemoveEarly30() {
        return OperaResultUtil.deleteBatch(sysOperaLogDao.doRemoveEarly30(DateUtil.getEarly30Date()));
    }
}
