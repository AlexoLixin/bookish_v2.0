package cn.don9cn.blog.action.system.log;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.log.SysLoginLog;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.log.interf.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 登录日志action
 * @Create: 2017/10/18 10:26
 * @Modify:
 */
@RestController
@RequestMapping(value = "/system/log/loginLog")
public class SysLoginLogAction extends BaseAction<SysLoginLog> {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Override
    protected OperaResult baseInsert(SysLoginLog sysLoginLog) {
        return null;
    }

    @Override
    protected OperaResult baseInsertBatch(List<SysLoginLog> list) {
        return null;
    }

    @Override
    protected OperaResult baseUpdate(SysLoginLog sysLoginLog) {
        return null;
    }

    @Override
    @DeleteMapping
    protected OperaResult baseRemove(String code) {
        return sysLoginLogService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected OperaResult baseRemoveBatch(String codes) {
        return sysLoginLogService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected OperaResult baseFindById(String code) {
        return sysLoginLogService.baseFindById(code);
    }

    @Override
    protected OperaResult baseFindAll() {
        return null;
    }

    @Override
    protected OperaResult baseFindListByParams(SysLoginLog sysLoginLog) {
        return null;
    }

    @Override
    @GetMapping("/page")
    protected OperaResult baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysLoginLog sysLoginLog) {
        return sysLoginLogService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysLoginLog));
    }

    @DeleteMapping("/batch/pre30days")
    public OperaResult remove30Early(){
        return sysLoginLogService.doRemoveEarly30();
    }

}
