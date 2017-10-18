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
    protected Object baseInsert(SysLoginLog sysLoginLog) {
        return null;
    }

    @Override
    protected Object baseInsertBatch(List<SysLoginLog> list) {
        return null;
    }

    @Override
    protected Object baseUpdate(SysLoginLog sysLoginLog) {
        return null;
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
    protected Object baseFindAll() {
        return null;
    }

    @Override
    protected Object baseFindListByParams(SysLoginLog sysLoginLog) {
        return null;
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
