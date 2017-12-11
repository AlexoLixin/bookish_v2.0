package cn.don9cn.blog.action.system.rbac;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.rbac.interf.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: liuxindong
 * @Description: 角色 Action
 * @Create: 2017/10/9 15:24
 * @Modify:
 */
@RestController
@RequestMapping(value = "/system/rbac/role")
public class SysRoleAction extends BaseAction<SysRole> {

    @Autowired
    private SysRoleService sysRoleService;


    @Override
    @PostMapping
    protected Object baseInsert(SysRole sysRole) {
        return sysRoleService.baseInsert(sysRole);
    }

    @Override
    protected Object baseInsertBatch(List<SysRole> list) {
        return null;
    }

    @Override
    @PutMapping
    protected Object baseUpdate(SysRole sysRole) {
        return sysRoleService.baseUpdate(sysRole);
    }

    @Override
    @DeleteMapping
    protected Object baseRemove(String code) {
        return sysRoleService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected Object baseRemoveBatch(String codes) {
        return sysRoleService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected Object baseFindById(String code) {
        return sysRoleService.baseFindById(code);
    }

    @Override
    @GetMapping("/all")
    protected Object baseFindAll() {
        return sysRoleService.baseFindAll();
    }

    @Override
    @GetMapping("/list")
    protected Object baseFindListByParams(SysRole sysRole) {
        return sysRoleService.baseFindListByParams(sysRole);
    }

    @Override
    @GetMapping("/page")
    protected Object baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysRole sysRole) {
        return sysRoleService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysRole));
    }

    @PutMapping("/authorize")
    protected Object authorizeRole(String roleCode,String permissionCodes) {
        return sysRoleService.authorizeRole(roleCode,permissionCodes);
    }
}
