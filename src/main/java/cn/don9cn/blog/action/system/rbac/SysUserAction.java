package cn.don9cn.blog.action.system.rbac;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.service.system.rbac.interf.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: liuxindong
 * @Description: 用户 Action
 * @Create: 2017/10/9 15:24
 * @Modify:
 */
@RestController
@RequestMapping(value = "/system/rbac/user")
public class SysUserAction extends BaseAction<SysUser> {

    @Autowired
    private SysUserService sysUserService;

    @Override
    @PostMapping
    protected Object baseInsert(SysUser sysUser) {
        return sysUserService.baseInsert(sysUser);
    }

    @Override
    @PostMapping("/batch")
    protected Object baseInsertBatch(List<SysUser> list) {
        return sysUserService.baseInsertBatch(list);
    }

    @Override
    @PutMapping
    protected Object baseUpdate(SysUser sysUser) {
        return sysUserService.baseUpdate(sysUser);
    }

    @Override
    @DeleteMapping
    protected Object baseRemove(String code) {
        return sysUserService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected Object baseRemoveBatch(String codes) {
        return sysUserService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected Object baseFindById(String code) {
        return sysUserService.baseFindById(code);
    }

    @Override
    @GetMapping("/all")
    protected Object baseFindAll() {
        return sysUserService.baseFindAll();
    }

    @Override
    @GetMapping("/list")
    protected Object baseFindListByParams(SysUser sysUser) {
        return sysUserService.baseFindListByParams(sysUser);
    }

    @Override
    @GetMapping("/page")
    protected Object baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysUser sysUser) {
        return sysUserService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysUser));
    }

    @GetMapping("/checkName")
    protected Object checkUserName(String username) {
        return sysUserService.checkUserName(username);
    }

    @PutMapping("/authorize")
    protected Object authorizeUser(String userCode,String roleCodes) {
        return sysUserService.authorizeUser(userCode,roleCodes);
    }
}