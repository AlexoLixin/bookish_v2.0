package cn.don9cn.blog.dao.system.rbac;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.system.rbac.SysRole;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *@Author: liuxindong
 *@Description: 角色dao实现类
 *@Create: 2017/10/15 19:21
 *@Modify:
 **/
@Repository
public class SysRoleDaoImpl implements BaseDao<SysRole> {

    /**
     * 通过用户id 查找角色集合
     * @param userCode
     * @return
     */
    public Optional<List<SysRole>> findListByUser(String userCode) {
        return getMyMongoOperator().freeFindList(Query.query(Criteria.where("userCode").is(userCode)),SysRole.class);
    }
}
