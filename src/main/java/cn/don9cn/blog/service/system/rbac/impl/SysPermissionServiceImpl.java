package cn.don9cn.blog.service.system.rbac.impl;

import cn.don9cn.blog.dao.system.rbac.SysPermissionDaoImpl;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operation.core.OperaResult;
import cn.don9cn.blog.plugins.operation.util.OperaResultUtil;
import cn.don9cn.blog.service.system.rbac.interf.SysPermissionService;
import cn.don9cn.blog.util.MyStringUtil;
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
@Service
@Transactional
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDaoImpl sysPermissionDaoImpl;

    @Override
    public OperaResult baseInsert(SysPermission entity) {
        entity.setLeaf("Y");
        return OperaResultUtil.baseInsert(sysPermissionDaoImpl.baseInsert(entity));
    }

    @Override
    public OperaResult baseInsertBatch(List<SysPermission> list) {
        return OperaResultUtil.baseInsertBatch(sysPermissionDaoImpl.baseInsertBatch(list));
    }

    @Override
    public OperaResult baseUpdate(SysPermission entity) {
        return OperaResultUtil.baseUpdate(sysPermissionDaoImpl.baseUpdate(entity));
    }

    @Override
    public OperaResult baseDeleteById(String id) {
        return OperaResultUtil.baseRemove(sysPermissionDaoImpl.baseDeleteById(id));
    }

    @Override
    public OperaResult baseDeleteBatch(String codes) {
        if(StringUtils.isNotBlank(codes)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            return OperaResultUtil.baseRemoveBatch(sysPermissionDaoImpl.baseDeleteBatch(codesList));
        }else{
            return new OperaResult(false,"删除失败,传入codes为空!");
        }
    }

    @Override
    public OperaResult baseFindById(String id) {
        return OperaResultUtil.baseFindOne(sysPermissionDaoImpl.baseFindById(id));
    }

    @Override
    public OperaResult baseFindAll() {
        return OperaResultUtil.baseFindAll(sysPermissionDaoImpl.baseFindAll());
    }

    @Override
    public OperaResult baseFindListByParams(SysPermission entity) {
        return OperaResultUtil.baseFindListByParams(sysPermissionDaoImpl.baseFindListByParams(entity));
    }

    @Override
    public OperaResult baseFindByPage(PageResult<SysPermission> pageResult) {
        return OperaResultUtil.baseFindByPage(sysPermissionDaoImpl.baseFindByPage(pageResult));
    }


    @Override
    public OperaResult doSave(SysPermission sysPermission) {
        //将当前节点设置为叶子节点
        sysPermission.setLeaf("Y");
        //保存当前节点
        OptionalInt optional = sysPermissionDaoImpl.baseInsert(sysPermission);
        //更新父节点为非叶子节点
        if(!sysPermission.getParent().equals("ROOT")){
            sysPermissionDaoImpl.baseUpdate(new SysPermission(sysPermission.getParent(),"N"));
        }
        return OperaResultUtil.baseInsert(optional);
    }

    @Override
    public OperaResult getTree() {
        Optional<List<SysPermission>> listOptional = sysPermissionDaoImpl.baseFindAll();
        Optional<List<SysPermission>> resultOptional;
        if(listOptional.isPresent()){
            List<SysPermission> all = listOptional.get();
            List<SysPermission> temp = new ArrayList<>();
            // 对所有节点按照父子关系进行重新组装
            List<SysPermission> result = all.stream().filter(t -> t.getLeaf().equals("N")).map(t -> {
                List<SysPermission> children = all.stream()
                        .filter(sub -> sub.getParent().equals(t.getCode()))
                        .sorted(Comparator.comparing(sub -> Integer.parseInt(sub.getLevel())))
                        .collect(Collectors.toList());
                if(children!=null){
                    t.setChildren(children);
                    temp.addAll(children);
                }
                temp.add(t);
                return t;
            }).filter(t -> t.getParent().equals("ROOT"))
                    .sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel()))).collect(Collectors.toList());
            all.removeAll(temp);
            result.addAll(all);
            resultOptional = Optional.ofNullable(result.stream()
                    .sorted(Comparator.comparing(t -> Integer.parseInt(t.getLevel())))
                    .collect(Collectors.toList()));
        }else{
            resultOptional =  Optional.of(new ArrayList<>());
        }
        return OperaResultUtil.baseFindAll(resultOptional);
    }

    @Override
    public OperaResult doRemove(String codes, String levels) {
        if(StringUtils.isNotBlank(codes) && StringUtils.isNotBlank(levels)){
            List<String> codesList = MyStringUtil.codesStr2List(codes);
            List<String> levelsList = MyStringUtil.codesStr2List(levels);
            //删除当前分类
            OptionalInt optional_1 = sysPermissionDaoImpl.baseDeleteBatch(codesList);
            //级联删除分类
            OptionalInt optional_2 = deleteCascade(levelsList);
            //更新节点状态
            updateLeaf();
            return OperaResultUtil.baseRemove(OptionalInt.of(optional_1.orElse(0)+optional_2.orElse(0)));
        }else{
            return new OperaResult(false,"传入codes或者levels为空!");
        }
    }

    /**
     * 级联删除分类
     * @param levelsList
     */
    private OptionalInt deleteCascade(List<String> levelsList) {
        int x = 0;
        for(String level:levelsList){
            int y = sysPermissionDaoImpl.deleteCascade(level).getAsInt();
            x += y;
        }
        return OptionalInt.of(x);
    }

    /**
     * 更新节点状态
     * @return
     */
    private OptionalInt updateLeaf() {
        List<SysPermission> allPermissions = sysPermissionDaoImpl.baseFindAll().get();
        List<String> allCodes = allPermissions.stream().map(SysPermission::getCode).collect(Collectors.toList());
        allCodes.add("ROOT");
        List<String> temp = new ArrayList<>();
        allPermissions.forEach(classify -> {
            if(!allCodes.contains(classify.getParent())){
                temp.add(classify.getParent());
            }
        });
        return sysPermissionDaoImpl.updateLeaf(temp);
    }

}
