package cn.don9cn.blog.action.system.log;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.log.SysOperaLog;
import cn.don9cn.blog.support.daohelper.core.PageResult;
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
    protected Object baseInsert(SysOperaLog sysOperaLog) {
        return null;
    }

    @Override
    protected Object baseInsertBatch(List<SysOperaLog> list) {
        return null;
    }

    @Override
    protected Object baseUpdate(SysOperaLog sysOperaLog) {
        return null;
    }

    @Override
    @DeleteMapping
    protected Object baseRemove(String code) {
        return sysOperaLogService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected Object baseRemoveBatch(String codes) {
        return sysOperaLogService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected Object baseFindById(String code) {
        return sysOperaLogService.baseFindById(code);
    }

    @Override
    protected Object baseFindAll() {
        return null;
    }

    @Override
    protected Object baseFindListByParams(SysOperaLog sysOperaLog) {
        return null;
    }

    @Override
    @GetMapping("/page")
    protected Object baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysOperaLog sysOperaLog) {
        return sysOperaLogService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysOperaLog));
    }

    @DeleteMapping("/batch/pre30days")
    public Object remove30Early(){
        return sysOperaLogService.doRemoveEarly30();
    }
}
