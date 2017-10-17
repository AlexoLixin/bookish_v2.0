package cn.don9cn.blog.dao.system.rbac.impl;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.dao.system.rbac.interf.SysPermissionDao;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 权限dao实现
 * @Create: 2017/10/16 14:14
 * @Modify:
 */
@Repository
public class SysPermissionDaoImpl implements SysPermissionDao {

    /**
     * 级联删除节点
     * @param level
     * @return
     */
    @Override
    public OptionalInt deleteCascade(String level) {
        Query query = Query.query(Criteria.where("level").regex("^" + level + "\\d*"));
        return getMyMongoOperator().freeDelete(query,SysPermission.class);
    }

    /**
     * 级联删除后更新叶子节点
     * @param allCodes
     * @return
     */
    @Override
    public OptionalInt updateLeaf(List<String> allCodes) {
        Query query = Query.query(Criteria.where("_id").in(allCodes).and("leaf").is("N"));
        Update update = new Update().set("leaf","Y");
        return getMyMongoOperator().freeUpdateMulti(query,update,SysPermission.class);
    }
}
