package cn.don9cn.blog.dao.system.log.impl;

import cn.don9cn.blog.dao.system.log.interf.SysExceptionLogDao;
import cn.don9cn.blog.model.system.log.SysExceptionLog;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 异常日志dao接口实现
 * @Create: 2017/10/18 10:04
 * @Modify:
 */
@Repository
public class SysExceptionDaoImpl implements SysExceptionLogDao {

    /**
     * 删除30天前的日志记录
     * @return
     */
    @Override
    public OptionalInt doRemoveEarly30(String early30Date) {
        return getMyMongoOperator().freeRemove(Query.query(Criteria.where("createTime").lt(early30Date)), SysExceptionLog.class);
    }

}
