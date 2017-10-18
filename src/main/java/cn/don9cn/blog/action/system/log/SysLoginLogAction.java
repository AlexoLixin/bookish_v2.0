package cn.don9cn.blog.action.system.log;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.log.SysLoginLog;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
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
    @PostMapping
    protected Object baseInsert(SysLoginLog sysLoginLog) {
        return sysLoginLogService.baseInsert(sysLoginLog);
    }

    @Override
    @PostMapping("/batch")
    protected Object baseInsertBatch(List<SysLoginLog> list) {
        return sysLoginLogService.baseInsertBatch(list);
    }

    @Override
    @PutMapping
    protected Object baseUpdate(SysLoginLog sysLoginLog) {
        return sysLoginLogService.baseUpdate(sysLoginLog);
    }

    @Override
    @DeleteMapping
    protected Object baseRemove(String code) {
        return sysLoginLogService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected Object baseRemoveBatch(String codes) {
        return sysLoginLogService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected Object baseFindById(String code) {
        return sysLoginLogService.baseFindById(code);
    }

    @Override
    @GetMapping("/all")
    protected Object baseFindAll() {
        return sysLoginLogService.baseFindAll();
    }

    @Override
    @GetMapping("/list")
    protected Object baseFindListByParams(SysLoginLog sysLoginLog) {
        return sysLoginLogService.baseFindListByParams(sysLoginLog);
    }

    @Override
    @GetMapping("/page")
    protected Object baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysLoginLog sysLoginLog) {
        return sysLoginLogService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysLoginLog));
    }

    @DeleteMapping("/batch/pre30days")
    public Object remove30Early(){
        return sysLoginLogService.doRemoveEarly30();
    }

}
