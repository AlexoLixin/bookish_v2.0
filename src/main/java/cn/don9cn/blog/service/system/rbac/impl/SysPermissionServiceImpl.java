package cn.don9cn.blog.service.system.rbac.impl;

import cn.don9cn.blog.dao.system.rbac.interf.SysPermissionDao;
import cn.don9cn.blog.model.bussiness.articleclassify.ArticleClassify;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import cn.don9cn.blog.support.daohelper.core.PageResult;
import cn.don9cn.blog.support.operaresult.core.OperaResult;
import cn.don9cn.blog.support.operaresult.util.OperaResultUtil;
import cn.don9cn.blog.service.system.rbac.interf.SysPermissionService;
import cn.don9cn.blog.util.MyStringUtil;
import cn.don9cn.blog.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liuxindong
 * @Description: 权限service接口实现
 * @Create: 2017/10/16 14:16
 * @Modify:
 */
@SuppressWarnings("Duplicates")
@Service
@Transactional
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Override
    public OperaResult baseInsert(SysPermission entity) {
        String code = UuidUtil.getUuid();
        entity.setCode(code);
        //保存当前节点
        OptionalInt optional = sysPermissionDao.baseInsert(entity);
        optional.ifPresent(x -> {
            //更新父节点
            if(!entity.getParent().equals("ROOT")){
                sysPermissionDao.updateParentForPush(entity.getParent(),code);
            }
        });
        return OperaResultUtil.insert(optional);
    }

    @Override
    public OperaResult baseInsertBatch(List<SysPermission> list) {
        return OperaResultUtil.insertBatch(sysPermissionDao.baseInsertBatch(list));
    }

    @Override
    public OperaResult baseUpdate(SysPermission entity) {
        return OperaResultUtil.update(sysPermissionDao.update(entity));
    }

    @Override
    public OperaResult baseDeleteById(String id) {
        return OperaResultUtil.deleteOne(sysPermissionDao.baseDeleteById(id));
    }

    @Override
    public OperaResult baseDeleteBatch(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            // 先删除选中的节点
            Optional<List<SysPermission>> removeNodes = sysPermissionDao.removeNodes(codesList);
            if(removeNodes.isPresent()){
                List<SysPermission> list = removeNodes.get();
                list.forEach(sysPermission -> {
                    // 级联删除其子节点
                    sysPermission.getChildrenCodes().forEach(code -> sysPermissionDao.baseDeleteById(code));
                    // 更新父节点
                    sysPermissionDao.updateParentForPull(sysPermission.getParent(),sysPermission.getCode());
                });
                return OperaResultUtil.deleteBatch(OptionalInt.of(list.size()));
            }else{
                return OperaResultUtil.deleteBatch(OptionalInt.empty());
            }
        }else{
            return new OperaResult(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    public OperaResult baseFindById(String id) {
        return OperaResultUtil.findOne(sysPermissionDao.baseFindById(id));
    }

    @Override
    public OperaResult baseFindAll() {
        return OperaResultUtil.findAll(sysPermissionDao.baseFindAll());
    }

    @Override
    public OperaResult baseFindListByParams(SysPermission entity) {
        return OperaResultUtil.findListByParams(sysPermissionDao.baseFindListByParams(entity));
    }

    @Override
    public OperaResult baseFindByPage(PageResult<SysPermission> pageResult) {
        return OperaResultUtil.findPage(sysPermissionDao.baseFindByPage(pageResult));
    }

    @Override
    public OperaResult getTree() {
        Optional<List<SysPermission>> listOptional = sysPermissionDao.baseFindAll();
        if(listOptional.isPresent()){
            List<SysPermission> sysPermissions = listOptional.get();
            Map<String,SysPermission> tempMap = new HashMap<>();
            sysPermissions.forEach(sysPermission -> tempMap.put(sysPermission.getCode(),sysPermission));
            sysPermissions.forEach(sysPermission -> sysPermission.getChildrenCodes().forEach(childCode -> {
                sysPermission.addChild(tempMap.get(childCode));
            }));
            List<SysPermission> permissionList = sysPermissions.stream()
                    .filter(sysPermission -> sysPermission.getParent().equals("ROOT"))
                    .collect(Collectors.toList());
            return OperaResultUtil.findAll(Optional.ofNullable(permissionList));
        }
        return OperaResultUtil.findAll(Optional.empty());
    }

}
