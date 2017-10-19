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
    protected OperaResult baseInsert(SysPermission sysPermission) {
        return sysPermissionService.doSave(sysPermission);
    }

    @Override
    protected OperaResult baseInsertBatch(List<SysPermission> list) {
        return null;
    }

    @Override
    @PutMapping
    protected OperaResult baseUpdate(SysPermission sysPermission) {
        return sysPermissionService.baseUpdate(sysPermission);
    }

    @Override
    protected OperaResult baseRemove(String code) {
        return null;
    }

    @Override
    protected OperaResult baseRemoveBatch(String codes) {
        return null;
    }

    @Override
    @GetMapping
    protected OperaResult baseFindById(String code) {
        return sysPermissionService.baseFindById(code);
    }

    @Override
    @GetMapping("/all")
    protected OperaResult baseFindAll() {
        return sysPermissionService.baseFindAll();
    }

    @Override
    @GetMapping("/list")
    protected OperaResult baseFindListByParams(SysPermission sysPermission) {
        return sysPermissionService.baseFindListByParams(sysPermission);
    }

    @Override
    @GetMapping("/page")
    protected OperaResult baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysPermission sysPermission) {
        return sysPermissionService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysPermission));
    }

    /**
     * 删除节点
     * @param codes
     * @param levels
     * @return
     */
    @DeleteMapping
    protected OperaResult doRemove(String codes,String levels) {
        return sysPermissionService.doRemove(codes,levels);
    }

    /**
     * 获取权限树
     * @return
     */
    @GetMapping("/tree")
    public OperaResult getTree() {
        return sysPermissionService.getTree();
    }
}
