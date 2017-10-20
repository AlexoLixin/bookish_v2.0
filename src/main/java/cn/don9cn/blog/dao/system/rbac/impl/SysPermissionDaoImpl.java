package cn.don9cn.blog.dao.system.rbac.impl;

import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.dao.system.rbac.interf.SysPermissionDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import cn.don9cn.blog.util.DateUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
     * 删除节点
     * @param ids
     * @return
     */
    @Override
    public Optional<List<SysPermission>> removeNodes(List<String> ids) {
        Query query = Query.query(Criteria.where("_id").in(ids));
        return getMyMongoOperator().freeFindAllAndRemove(query,SysPermission.class);
    }

    /**
     * 更新父节点
     * @param code
     * @param child
     * @return
     */
    @Override
    public OptionalInt updateParentForPush(String code, String child) {
        Query query = Query.query(Criteria.where("_id").is(code));
        Update update = new Update().push("childrenCodes",child);
        return getMyMongoOperator().freeUpdateMulti(query,update,SysPermission.class);
    }

    /**
     * 更新父节点
     * @param code
     * @param child
     * @return
     */
    @Override
    public OptionalInt updateParentForPull(String code, String child) {
        Query query = Query.query(Criteria.where("_id").is(code));
        Update update = new Update().pull("childrenCodes",child);
        return getMyMongoOperator().freeUpdateMulti(query,update,SysPermission.class);
    }

    /**
     * 更新节点(跳过childrenCodes字段)
     * @param entity
     * @return
     */
    @Override
    public OptionalInt update(SysPermission entity) {
        entity.setModifyBy(MyShiroSessionUtil.getUserCodeFromSession());
        entity.setModifyTime(DateUtil.getModifyDateString());
        Query query = getMyMongoOperator().createDefaultQuery(entity);
        Update update = getMyMongoOperator().createDefaultUpdate(entity).unset("childrenCodes");
        return getMyMongoOperator().freeUpdateOne(query,update,SysPermission.class);
    }
}
