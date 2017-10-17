package cn.don9cn.blog.dao.system.rbac.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.system.rbac.SysUser;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 用户dao接口
 * @Create: 2017/10/17 8:59
 * @Modify:
 */
public interface SysUserDao extends BaseDao<SysUser> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    Optional<SysUser> findByUserName(String username);

    /**
     * 检查用户名是否已经存在
     * @param username
     * @return
     */
    boolean checkUserName(String username);
}
