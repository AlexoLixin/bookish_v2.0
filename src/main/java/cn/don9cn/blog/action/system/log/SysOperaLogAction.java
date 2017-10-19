package cn.don9cn.blog.action.system.log;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.log.SysOperaLog;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.log.interf.SysOperaLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 操作日志action
 * @Create: 2017/10/18 10:26
 * @Modify:
 */
@RestController
@RequestMapping(value = "/system/log/operaLog")
public class SysOperaLogAction extends BaseAction<SysOperaLog> {

    @Autowired
    private SysOperaLogService sysOperaLogService;

    @Override
    protected OperaResult baseInsert(SysOperaLog sysOperaLog) {
        return null;
    }

    @Override
    protected OperaResult baseInsertBatch(List<SysOperaLog> list) {
        return null;
    }

    @Override
    protected OperaResult baseUpdate(SysOperaLog sysOperaLog) {
        return null;
    }

    @Override
    @DeleteMapping
    protected OperaResult baseRemove(String code) {
        return sysOperaLogService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected OperaResult baseRemoveBatch(String codes) {
        return sysOperaLogService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected OperaResult baseFindById(String code) {
        return sysOperaLogService.baseFindById(code);
    }

    @Override
    protected OperaResult baseFindAll() {
        return null;
    }

    @Override
    protected OperaResult baseFindListByParams(SysOperaLog sysOperaLog) {
        return null;
    }

    @Override
    @GetMapping("/page")
    protected OperaResult baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysOperaLog sysOperaLog) {
        return sysOperaLogService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysOperaLog));
    }

    @DeleteMapping("/batch/pre30days")
    public OperaResult remove30Early(){
        return sysOperaLogService.doRemoveEarly30();
    }
}
