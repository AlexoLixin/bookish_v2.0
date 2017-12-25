package cn.don9cn.blog.service.system.log.impl;

import cn.don9cn.blog.dao.system.log.interf.SysExceptionLogDao;
import cn.don9cn.blog.model.system.log.SysExceptionLog;
import cn.don9cn.blog.model.system.log.SysOperaLog;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.support.operaresult.core.OperaResult;
import cn.don9cn.blog.support.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.system.log.interf.SysExceptionLogService;
import cn.don9cn.blog.service.system.rbac.interf.SysUserService;
import cn.don9cn.blog.util.DateUtil;
import cn.don9cn.blog.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 异常日志service接口实现
 * @Create: 2017/10/18 13:44
 * @Modify:
 */
@SuppressWarnings("Duplicates")
@Service
@Transactional
public class SysExceptionLogServiceImpl implements SysExceptionLogService {

    @Autowired
    private SysExceptionLogDao sysExceptionLogDao;

    @Autowired
    private SysUserService sysUserService;


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
        Optional<SysExceptionLog> sysExceptionLogOptional = sysExceptionLogDao.baseFindById(id);
        sysExceptionLogOptional.ifPresent(sysExceptionLog -> {
            String userCode = sysExceptionLog.getUserCode();
            if(StringUtils.isNotBlank(userCode)){
                OperaResult operaResult = (OperaResult) sysUserService.baseFindById(userCode);
                if(operaResult.isSuccess()){
                    SysUser user = (SysUser) operaResult.getObj();
                    sysExceptionLog.setUserName(user.getUsername());
                    sysExceptionLog.setUserRole(user.getRoleNames());
                }
            }
        });
        return OperaResultUtil.findOne(sysExceptionLogOptional);
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
