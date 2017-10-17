package cn.don9cn.blog.autoconfigs.shiro.core;

import org.apache.shiro.authz.Permission;

/**
 * @Author: liuxindong
 * @Description: 自定义shiro Permission的实现
 * @Create: 2017/10/17 15:27
 * @Modify:
 */
public class MyPermission implements Permission{

    private final String requestUrl;

    private final String requestMethod;

    public MyPermission(String requestUrl, String requestMethod) {
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
    }

    /**
     * 权限验证,返回值为true时说明当前的请求与用户的权限相匹配
     * @param permission 用户请求的资源路径
     * @return
     */
    @Override
    public boolean implies(Permission permission) {
        MyPermission request = (MyPermission) permission;
        if(this.requestMethod.equals("ALL")){
            if(request.getRequestUrl().startsWith(this.getRequestUrl()))
                return true;
        }else{
            if(request.getRequestUrl().equals(this.requestUrl) && request.getRequestMethod().equals(this.requestMethod))
                return true;
        }
        return false;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }
}
