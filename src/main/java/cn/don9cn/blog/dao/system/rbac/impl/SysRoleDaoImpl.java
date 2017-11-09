package cn.don9cn.blog.dao.system.rbac.impl;

import cn.don9cn.blog.dao.system.rbac.interf.SysRoleDao;
import cn.don9cn.blog.model.system.rbac.SysRole;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *@Author: liuxindong
 *@Description: 角色dao实现类
 *@Create: 2017/10/15 19:21
 *@Modify:
 **/
@Repository
public class SysRoleDaoImpl implements SysRoleDao {

    @Override
    public Optional<SysRole> findByRoleEncoding(String encoding) {
        Query query = Query.query(Criteria.where("encoding").is(encoding));
        return getMyMongoOperator().freeFindOne(query,SysRole.class);
    }

}
