package cn.don9cn.blog.dao.system.rbac.interf;

import cn.don9cn.blog.dao.BaseDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 权限dao接口
 * @Create: 2017/10/17 9:05
 * @Modify:
 */
public interface SysPermissionDao extends BaseDao<SysPermission> {

    /**
     * 删除节点
     * @param ids
     * @return
     */
    Optional<List<SysPermission>> removeNodes(List<String> ids);

    /**
     * 更新父节点(为其添加子节点)
     * @param code
     * @param child
     */
    OptionalInt updateParentForPush(String code, String child);

    /**
     * 更新父节点(为其删除子节点)
     * @param code
     * @param child
     */
    OptionalInt updateParentForPull(String code, String child);

    /**
     * 更新节点(跳过childrenCodes字段)
     * @param entity
     * @return
     */
    OptionalInt update(SysPermission entity);
}
