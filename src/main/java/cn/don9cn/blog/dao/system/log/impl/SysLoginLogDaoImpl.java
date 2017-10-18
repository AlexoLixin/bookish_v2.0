package cn.don9cn.blog.dao.system.log.impl;

import cn.don9cn.blog.dao.system.log.interf.SysLoginLogDao;
import cn.don9cn.blog.model.system.log.SysLoginLog;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 登录日志dao接口实现
 * @Create: 2017/10/18 10:04
 * @Modify:
 */
@Repository
public class SysLoginLogDaoImpl implements SysLoginLogDao {

    /**
     * 删除30天前的日志记录
     * @return
     */
    @Override
    public OptionalInt doRemoveEarly30(String early30Date) {
        return getMyMongoOperator().freeDelete(Query.query(Criteria.where("createTime").lt(early30Date)), SysLoginLog.class);
    }

}
