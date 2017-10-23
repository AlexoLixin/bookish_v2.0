package cn.don9cn.blog.dao.system.log.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.system.log.SysLoginLog;

import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 登录日志dao接口
 * @Create: 2017/10/18 10:03
 * @Modify:
 */
public interface SysLoginLogDao extends BaseDao<SysLoginLog> {

    /**
     * 删除30天前的日志记录
     * @return
     */
    OptionalInt doRemoveEarly30(String early30Date);

}