package cn.don9cn.blog.service.system.log.interf;


import cn.don9cn.blog.model.system.log.SysOperaLog;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.BaseService;


/**
 * @Author: liuxindong
 * @Description: 操作日志service接口
 * @Create: 2017/10/18 10:08
 * @Modify:
 */
public interface SysOperaLogService extends BaseService<SysOperaLog> {

    /**
     * 删除30天前的日志
     * @return
     */
    Object doRemoveEarly30();

}

