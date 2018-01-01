package cn.don9cn.blog.action.system.log;

import cn.don9cn.blog.model.system.log.SysExceptionLog;
import cn.don9cn.blog.service.system.log.interf.SysExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: liuxindong
 * @Description: 异常日志action
 * @Create: 2017/10/18 10:26
 * @Modify:
 */
@RestController
@RequestMapping(value = "/system/log/exceptionLog")
public class SysExceptionLogAction extends BaseAction<SysExceptionLog> {

    @Autowired
    private SysExceptionLogService sysExceptionLogService;


    @Override
    protected Object baseInsert(SysExceptionLog sysExceptionLog) {
        return null;
    }

    @Override
    protected Object baseInsertBatch(List<SysExceptionLog> list) {
        return null;
    }

    @Override
    protected Object baseUpdate(SysExceptionLog sysExceptionLog) {
        return null;
    }

    @Override
    @DeleteMapping
    protected Object baseRemove(String code) {
        return sysExceptionLogService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected Object baseRemoveBatch(String codes) {
        return sysExceptionLogService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected Object baseFindById(String code) {
        return sysExceptionLogService.baseFindById(code);
    }

    @Override
    protected Object baseFindAll() {
        return null;
    }

    @Override
    protected Object baseFindListByParams(SysExceptionLog sysExceptionLog) {
        return null;
    }

    @Override
    @GetMapping("/page")
    protected Object baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysExceptionLog sysExceptionLog) {
        return sysExceptionLogService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysExceptionLog));
    }

    @DeleteMapping("/batch/pre30days")
    public Object remove30Early(){
        return sysExceptionLogService.doRemoveEarly30();
    }

}
