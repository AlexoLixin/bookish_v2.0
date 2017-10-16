package cn.don9cn.blog.action.system.rbac;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
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
@RequestMapping(value = "/system/rbac")
public class SysRoleAction extends BaseAction<SysRole> {

    @Autowired
    private SysRoleService sysRoleService;


    @Override
    @PostMapping("/role")
    protected Object baseInsert(SysRole sysRole) {
        return sysRoleService.baseInsert(sysRole);
    }

    @Override
    @PostMapping("/role/batch")
    protected Object baseInsertBatch(List<SysRole> list) {
        return sysRoleService.baseInsertBatch(list);
    }

    @Override
    @PutMapping("/role")
    protected Object baseUpdate(SysRole sysRole) {
        return sysRoleService.baseUpdate(sysRole);
    }

    @Override
    @DeleteMapping("/role")
    protected Object baseRemove(String code) {
        return sysRoleService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/role/batch")
    protected Object baseRemoveBatch(String codes) {
        return sysRoleService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping("/role/{code}")
    protected Object baseFindById(@PathVariable String code) {
        return sysRoleService.baseFindById(code);
    }

    @Override
    @GetMapping("/role/all")
    protected Object baseFindAll() {
        return sysRoleService.baseFindAll();
    }

    @Override
    @GetMapping("/role/list")
    protected Object baseFindListByParams(SysRole sysRole) {
        return sysRoleService.baseFindListByParams(sysRole);
    }

    @Override
    @GetMapping("/role/page")
    protected Object baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysRole sysRole) {
        return sysRoleService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysRole));
    }
}
