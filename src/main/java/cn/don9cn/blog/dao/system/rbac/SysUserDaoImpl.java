package cn.don9cn.blog.dao.system.rbac;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.system.rbac.SysUser;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 *@Author: liuxindong
 *@Description: 用户dao实现
 *@Create: 2017/10/15 10:59
 *@Modify:
 **/
@Repository
public class SysUserDaoImpl implements BaseDao<SysUser> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public Optional<SysUser> findByUserName(String username) {
        return getMyMongoOperator().freeFindOne(Query.query(Criteria.where("username").is(username)),SysUser.class);
    }

    /**
     * 检查用户名是否已经存在
     * @param username
     * @return
     */
    public boolean checkUserName(String username) {
        boolean result = true;
        if(findByUserName(username).isPresent()){
            result = false;
        }
        return result;
    }

}
