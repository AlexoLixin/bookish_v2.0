package cn.don9cn.blog.action.system.rbac;

import cn.don9cn.blog.action.BaseAction;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.rbac.interf.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.SupportedSourceVersion;
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
    protected OperaResult baseInsert(SysUser sysUser) {
        return sysUserService.baseInsert(sysUser);
    }

    @Override
    protected OperaResult baseInsertBatch(List<SysUser> list) {
        return null;
    }

    @Override
    @PutMapping
    protected OperaResult baseUpdate(SysUser sysUser) {
        return sysUserService.baseUpdate(sysUser);
    }

    @Override
    @DeleteMapping
    protected OperaResult baseRemove(String code) {
        return sysUserService.baseDeleteById(code);
    }

    @Override
    @DeleteMapping("/batch")
    protected OperaResult baseRemoveBatch(String codes) {
        return sysUserService.baseDeleteBatch(codes);
    }

    @Override
    @GetMapping
    protected OperaResult baseFindById(String code) {
        return sysUserService.baseFindById(code);
    }

    @Override
    @GetMapping("/all")
    protected OperaResult baseFindAll() {
        return sysUserService.baseFindAll();
    }

    @Override
    @GetMapping("/list")
    protected OperaResult baseFindListByParams(SysUser sysUser) {
        return sysUserService.baseFindListByParams(sysUser);
    }

    @Override
    @GetMapping("/page")
    protected OperaResult baseFindByPage(int page, int limit, String startTime, String endTime, String orderBy, SysUser sysUser) {
        return sysUserService.baseFindByPage(new PageResult<>(page,limit,startTime,endTime,orderBy,sysUser));
    }

    @GetMapping("/checkName")
    public OperaResult checkUserName(String username) {
        return sysUserService.checkUserName(username);
    }

    @PutMapping("/authorize")
    public OperaResult authorizeUser(String userCode,String roleCodes) {
        return sysUserService.authorizeUser(userCode,roleCodes);
    }

    @GetMapping("/byUser")
    public OperaResult getUserInfo() {
        return sysUserService.getUserInfo();
    }

    @PutMapping("/byUser")
    public OperaResult updateUserInfo(SysUser sysUser) {
        return sysUserService.updateUserInfo(sysUser);
    }
}
