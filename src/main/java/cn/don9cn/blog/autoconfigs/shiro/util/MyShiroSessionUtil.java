package cn.don9cn.blog.autoconfigs.shiro.util;

import cn.don9cn.blog.model.system.rbac.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Author: liuxindong
 * @Description: shiro session工具类,用于从当前用户subject的session中获取用户信息,token,缓存的身份和权限验证信息
 * @Create: 2017/10/17 15:38
 * @Modify:
 */
public class MyShiroSessionUtil {

    /**
     * 从session获取用户数据
     * @return
     */
    public static SysUser getUserFromSession(){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            Session session = subject.getSession();
            if(session!=null){
                return (SysUser) session.getAttribute("currentUser");
            }
        }
        return null;
    }

    /**
     * 从session获取用户主键
     * @return
     */
    public static String getUserCodeFromSession(){
        SysUser sysUser = getUserFromSession();
        if(sysUser!=null){
            return sysUser.getCode();
        }else{
            return "";
        }
    }

    /**
     * 从session获取用户名称
     * @return
     */
    public static String getUserNameFromSession(){
        SysUser sysUser = getUserFromSession();
        if(sysUser!=null){
            return sysUser.getUsername();
        }else{
            return "";
        }
    }

    /**
     * 从session获取token
     * @return
     */
    public static String getTokenFromSession(){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            Session session = subject.getSession();
            if(session!=null){
                return session.getAttribute("token").toString();
            }
        }
        return null;
    }

    /**
     * 获取用户登录认证信息
     * @return
     */
    public static Object getAuthenticationInfo(){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            Session session = subject.getSession();
            if(session!=null){
                return session.getAttribute("AuthenticationInfo");
            }
        }
        return null;
    }

    /**
     * 获取用户权限信息
     * @return
     */
    public static Object getAuthorizationInfo(){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            Session session = subject.getSession();
            if(session!=null){
                return session.getAttribute("AuthorizationInfo");
            }
        }
        return null;
    }
}
