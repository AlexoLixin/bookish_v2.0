package cn.don9cn.blog.autoconfigs.shiro.core;

import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.model.system.rbac.SysPermission;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.rbac.interf.SysRoleService;
import cn.don9cn.blog.service.system.rbac.interf.SysUserService;
import cn.don9cn.blog.util.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @Author: liuxindong
 * @Description: shiro验证及授权实现类
 * @Create: 2017/10/17 14:04
 * @Modify:
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 用户授权(根据验证通过后的用户信息,在数据库中查找其对应的角色和权限,进行授权)
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) super.getAvailablePrincipal(principalCollection);

        // 1.从session中获取用户的权限信息
        SimpleAuthorizationInfo info = (SimpleAuthorizationInfo) MyShiroSessionUtil.getAuthorizationInfo();
        if(info == null){
            // 2.session中未缓存用户权限信息，从数据库查询，然后将结果放入session中作为缓存
            info = new SimpleAuthorizationInfo();
            SysUser user = MyShiroSessionUtil.getUserFromSession();  //权限验证发生在登陆之后,所以此时session中是一定会有登录用户信息的
            List<String> userRoleCodes = user.getRoleCodes();
            if(userRoleCodes !=null){
                for(String roleCode:userRoleCodes){
                    OperaResult operaResult = (OperaResult) sysRoleService.findRoleWithPermissions(roleCode);
                    if(operaResult.isSuccess()){
                        SysRole sysRole = (SysRole) operaResult.getObj();
                        info.addRole(sysRole.getEncoding());
                        List<SysPermission> permissionList = sysRole.getMenuList();
                        if(permissionList !=null){
                            for(SysPermission sysPermission:permissionList){
                                info.addObjectPermission(new MyPermission(sysPermission.getUrl(),sysPermission.getHttpMethod()));
                            }
                        }
                    }
                }
            }
            setAuthorizationInfoToSession(info);
        }
        return info;
    }

    /**
     * 身份验证(根据controller层中subject.login(token)中的token,查找数据库中是否有此用户,有则验证通过,没有则验证失败)
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());

        // 1.从session中获取用户认证信息
        SimpleAuthenticationInfo userInfo = (SimpleAuthenticationInfo) MyShiroSessionUtil.getAuthenticationInfo();
        if(userInfo == null){
            // 2.session中没有用户认证信息，查询数据库，重新验证用户
            OperaResult operaResult = (OperaResult) sysUserService.findByUserName(username);
            if(operaResult.isSuccess()){
                SysUser user = (SysUser) operaResult.getObj();
                if(user.getPassword().equals(password)){
                    userInfo = new SimpleAuthenticationInfo(username,password,this.getName());
                    setUserToSession(user);
                    setAuthenticationInfoToSession(userInfo);
                }else{
                    throw new UnknownAccountException("用户名或密码验证失败");
                }
            }else{
                throw new UnknownAccountException("用户名或密码验证失败");
            }
        }

        return userInfo;
    }


    /**
     * 将登陆用户数据放入session,同时自动生成token并放入到session
     * @param user
     */
    private void setUserToSession(SysUser user){

        setSession("currentUser",user);

        // 根据用户名和密码生成token,存入session
        setSession("token", Md5Util.getMD5(user.getUsername()+user.getPassword()));
    }

    /**
     * 将登陆用户认证信息存入session,作为缓存
     * @param authenticationInfo
     */
    private void setAuthenticationInfoToSession(SimpleAuthenticationInfo authenticationInfo){
        setSession("AuthenticationInfo",authenticationInfo);
    }

    /**
     * 将用户权限信息存入session,作为缓存
     * @param authorizationInfo
     */
    private void setAuthorizationInfoToSession(SimpleAuthorizationInfo authorizationInfo){
        setSession("AuthorizationInfo",authorizationInfo);
    }

    private void setSession(Object key,Object value){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            Session session = subject.getSession();
            if(session!=null){
                session.setAttribute(key,value);
            }
        }
    }

}
