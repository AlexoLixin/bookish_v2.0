package cn.don9cn.blog.service.system.log.interf;


import cn.don9cn.blog.model.system.log.SysLoginLog;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.BaseService;


/**
 * @Author: liuxindong
 * @Description: 登录日志service接口
 * @Create: 2017/10/18 10:08
 * @Modify:
 */
public interface SysLoginLogService extends BaseService<SysLoginLog> {

    /**
     * 删除30天前的日志
     * @return
     */
    Object doRemoveEarly30();

}

