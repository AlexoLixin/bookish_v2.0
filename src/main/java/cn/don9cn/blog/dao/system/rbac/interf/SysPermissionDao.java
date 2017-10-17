package cn.don9cn.blog.dao.system.rbac.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 权限dao接口
 * @Create: 2017/10/17 9:05
 * @Modify:
 */
public interface SysPermissionDao extends BaseDao<SysPermission> {

    /**
     * 级联删除节点
     * @param level
     * @return
     */
    OptionalInt deleteCascade(String level);

    /**
     * 级联删除后更新叶子节点
     * @param allCodes
     * @return
     */
    OptionalInt updateLeaf(List<String> allCodes);
}
