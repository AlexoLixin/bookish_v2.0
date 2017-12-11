package cn.don9cn.blog.action.system.rbac;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.rbac.interf.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 权限action
 * @Create: 2017/10/16 14:27
 * @Modify:
 */
@RestController
@RequestMapping(value = "/system/rbac/permission")
public class SysPermissionAction extends BaseAction<SysPermission> {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    @PostMapping
    protected Object baseInsert(SysPermission sysPermission) {
        return sysPermissionService.baseInsert(sysPermission);
    }

    @Override
    protected Object baseInsertBatch(List<SysPermission> list) {
        return null;
    }

    @Override
    @PutMapping
    protected Object baseUpdate(SysPermission sysPermission) {
        return sysPermissionService.baseUpdate(sysPermission);
    }

    @Override
    protected Object baseRemove(String code) {
        return null;
    }

    @Override
    @DeleteMapping
    protected Object baseRemoveBatch(String codes) {
        return sysPermissionService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected Object baseFindById(String code) {
        return sysPermissionService.baseFindById(code);
    }

    @Override
    @GetMapping("/all")
    protected Object baseFindAll() {
        return sysPermissionService.baseFindAll();
    }

    @Override
    @GetMapping("/list")
    protected Object baseFindListByParams(SysPermission sysPermission) {
        return sysPermissionService.baseFindListByParams(sysPermission);
    }

    @Override
    @GetMapping("/page")
    protected Object baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysPermission sysPermission) {
        return sysPermissionService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysPermission));
    }

    /**
     * 获取权限树
     * @return
     */
    @GetMapping("/tree")
    public Object getTree() {
        return sysPermissionService.getTree();
    }
}
