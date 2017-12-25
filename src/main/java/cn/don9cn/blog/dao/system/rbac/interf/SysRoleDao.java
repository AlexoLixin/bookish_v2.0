package cn.don9cn.blog.dao.system.rbac.interf;

import cn.don9cn.blog.model.system.rbac.SysRole;

import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 角色dao接口
 * @Create: 2017/10/17 9:02
 * @Modify:
 */
public interface SysRoleDao extends BaseDao<SysRole> {

    /**
     * 通过角色编码查找角色
     * @param encoding
     * @return
     */
    Optional<SysRole> findByRoleEncoding(String encoding);

}
