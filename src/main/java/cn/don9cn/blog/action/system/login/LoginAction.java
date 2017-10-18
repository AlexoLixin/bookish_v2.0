package cn.don9cn.blog.action.system.login;

import cn.don9cn.blog.annotation.SkipOperaLog;
import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.model.system.LoginResult;
import cn.don9cn.blog.model.system.log.SysLoginLog;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.log.interf.SysLoginLogService;
import cn.don9cn.blog.util.RequestUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 登录action
 * @Create: 2017/10/17 16:44
 * @Modify:
 */
@SkipOperaLog
@RestController
@RequestMapping("/login")
public class LoginAction {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * 提示登录
     * @return
     */
    @RequestMapping("/needLogin")
    public Object needLogin(){
        return new OperaResult(false,"请先登录!");
    }

    /**
     * 提示重新登录
     * @return
     */
    @RequestMapping("/reLogin")
    public Object reLogin(){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            // 有一种情况是用户已经登陆了,但是前端传过来的token不一致,那就注销当前用户,提示重新登录
            subject.logout();
        }
        return new OperaResult(false,"用户身份过期,请重新登录!");
    }

    /**
     * 提示权限不足
     * @return
     */
    @RequestMapping("/noPermission")
    public Object noPermission(){
        return new OperaResult(false,"对不起,您没有相应的操作权限!");
    }
    
    @RequestMapping("/doLogin")
    public Object doLogin(String username, String password, String role, HttpServletRequest request) throws IOException, ServletException {

        // 生成登录日志,无论登陆是否成功,都会保存该日志信息
        SysLoginLog loginLog = RequestUtil.getLoginLog(request, username, password);

        Subject subject = SecurityUtils.getSubject();

        if(!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            try{
                subject.login(token);
            }catch(Exception e){
                sysLoginLogService.baseInsert(loginLog.withState("失败"));
                return new LoginResult(false,"用户名或者密码错误");
            }
        }

        sysLoginLogService.baseInsert(loginLog.withState("成功"));
        //登陆成功,设置用户名到session,消息推送中用得到
        request.getSession(false).setAttribute("CURRENT_USER",username);
        //登陆成功,检查是否是管理员登录
        if(role.equals("admin")){
            return checkAdmin();
        }
        return new LoginResult(true,"登陆成功!")
                                .setAdmin(false)
                                .setToken(MyShiroSessionUtil.getTokenFromSession())
                                .setUser(MyShiroSessionUtil.getUserFromSession());

    }

    /**
     * 检查当前用户是否是管理员,并设置返回信息
     * @return
     */
    private LoginResult checkAdmin(){
        SysUser user = (SysUser) MyShiroSessionUtil.getUserFromSession();
        List<SysRole> roleList = user.getRoleList();
        if(roleList!=null && roleList.size()>0){
            for(SysRole role:roleList){
                if(role.getEncoding().contains("ADMIN")){
                    return new LoginResult(true,"登陆成功!")
                                        .setAdmin(true)
                                        .setToken(MyShiroSessionUtil.getTokenFromSession())
                                        .setUser(user);
                }
            }
        }
        return new LoginResult(false,"对不起,您不是系统管理员");
    }


}
