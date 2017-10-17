package cn.don9cn.blog.model.system.log;


import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.model.BaseModel;


/**
 * @Author: liuxindong
 * @Description: 登录日志实体
 * @Create: 2017/10/17 8:55
 * @Modify:
 */
@DbCollection
public class SysLoginlog extends BaseModel {


    /**
     * 访问主机名
     */
    @DbColumn(content = "访问主机名")
    private String hostName;
    /**
     * 系统版本
     */
    @DbColumn(content = "系统版本")
    private String systemName;
    /**
     * 浏览器
     */
    @DbColumn(content = "浏览器")
    private String browser;
    /**
     * 访问ip
     */
    @DbColumn(content = "登录ip")
    private String ip;
    /**
     * 登录名
     */
    @DbColumn(content = "登录名")
    private String username;
    /**
     * 密码
     */
    @DbColumn(content = "密码")
    private String password;
    /**
     * 登录状态
     */
    @DbColumn(content = "登录状态")
    private String state;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

}
