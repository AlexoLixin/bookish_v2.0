package cn.don9cn.blog.model.system.log;

import cn.don9cn.blog.annotation.DbCollection;
import cn.don9cn.blog.annotation.DbColumn;
import cn.don9cn.blog.model.BaseModel;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 异常日志
 * @Create: 2017/10/18 16:02
 * @Modify:
 */
@DbCollection
public class SysExceptionLog extends BaseModel {

    @DbColumn(content = "请求url")
    private String requestUrl;

    @DbColumn(content = "请求方法")
    private String requestMethod;

    @DbColumn(content = "操作类型")
    private String type;

    @DbColumn(content = "操作模块")
    private String module;

    @DbColumn(content = "操作action")
    private String actionName;

    @DbColumn(content = "操作方法")
    private String methodName;

    @DbColumn(content = "请求参数")
    private List<String> params;

    @DbColumn(content = "操作用户")
    private String userCode;

    @DbColumn(content = "操作用时")
    private String costTime;

    @DbColumn(content = "操作状态")
    private String state;

    @DbColumn(content = "操作用户")
    private String userName;

    @DbColumn(content = "操作用户")
    private String userRole;

    @DbColumn(content = "是否保存到数据库")
    private String ignoreSave;

    @DbColumn(content = "异常类型")
    private String exceptionType;

    @DbColumn(content = "异常消息")
    private String message;

    @DbColumn(content = "异常堆栈")
    private List<String> stackTrace;

    public SysExceptionLog(){}

    public SysExceptionLog(SysOperaLog sysOperaLog) {
        if(sysOperaLog.getRequestUrl()!=null) this.requestUrl = sysOperaLog.getRequestUrl();
        if(sysOperaLog.getRequestMethod()!=null) this.requestMethod = sysOperaLog.getRequestMethod();
        if(sysOperaLog.getType()!=null) this.type = sysOperaLog.getType();
        if(sysOperaLog.getModule()!=null) this.module = sysOperaLog.getModule();
        if(sysOperaLog.getActionName()!=null) this.actionName = sysOperaLog.getActionName();
        if(sysOperaLog.getMethodName()!=null) this.methodName = sysOperaLog.getMethodName();
        if(sysOperaLog.getParams()!=null) this.params = sysOperaLog.getParams();
        if(sysOperaLog.getUserCode()!=null) this.userCode = sysOperaLog.getUserCode();
        if(sysOperaLog.getCostTime()!=null) this.costTime = sysOperaLog.getCostTime();
        if(sysOperaLog.getState()!=null) this.state = sysOperaLog.getState();
        if(sysOperaLog.getUserName()!=null) this.userName = sysOperaLog.getUserName();
        if(sysOperaLog.getUserRole()!=null) this.userRole = sysOperaLog.getUserRole();
        this.ignoreSave = sysOperaLog.getIgnoreSave();
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }


    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getIgnoreSave() {
        return ignoreSave;
    }

    public void setIgnoreSave(String ignoreSave) {
        this.ignoreSave = ignoreSave;
    }
}
