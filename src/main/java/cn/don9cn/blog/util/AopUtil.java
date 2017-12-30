package cn.don9cn.blog.util;

import cn.don9cn.blog.annotation.SkipOperaLog;
import cn.don9cn.blog.autoconfigure.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.model.system.log.SysExceptionLog;
import cn.don9cn.blog.model.system.log.SysOperaLog;
import cn.don9cn.blog.model.system.rbac.SysUser;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: aop更具类,主要负责解析JoinPoint
 * @Create: 2017/10/18 14:15
 * @Modify:
 */
public class AopUtil {

    /**
     * 解析成操作日志
     * @param joinPoint
     * @return
     */
    public static SysOperaLog parseToSysOperaLog(JoinPoint joinPoint){
        SysOperaLog sysOperaLog = new SysOperaLog();

        // 执行action类名
        Class<?> exceClass = joinPoint.getTarget().getClass();
        if(exceClass.getAnnotation(SkipOperaLog.class)!=null){
            sysOperaLog.setIgnoreSave("Y");
        }else{
            sysOperaLog.setIgnoreSave("N");
        }
        sysOperaLog.setActionName(exceClass.getTypeName());
        sysOperaLog.setModule(exceClass.getSimpleName().replace("Action",""));

        // 执行方法名
        String exceMethodName = joinPoint.getSignature().getName();
        sysOperaLog.setMethodName(exceMethodName);

        // 执行用户
        SysUser userFromSession = MyShiroSessionUtil.getUserFromSession();
        if(userFromSession!=null) sysOperaLog.setUserCode(userFromSession.getCode());

        // 解析请求request
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        sysOperaLog.setRequestUrl(request.getRequestURI());     //请求url
        String method = request.getMethod();                    //请求method
        sysOperaLog.setRequestMethod(method);
        switch (method){
            case "POST":
                sysOperaLog.setType("添加");
                break;
            case "DELETE":
                sysOperaLog.setType("删除");
                break;
            case "PUT":
                sysOperaLog.setType("更新");
                break;
            case "GET":{
                sysOperaLog.setType("查询");
                sysOperaLog.setIgnoreSave("Y");
                break;
            }
        }
        List<String> params = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();   //请求参数
        while (parameterNames.hasMoreElements()){
            String paramName = parameterNames.nextElement();
            params.add(paramName+" : "+request.getParameter(paramName));
        }
        sysOperaLog.setParams(params);

        return sysOperaLog;
    }

    /**
     * 解析异常信息并填充到异常日志
     * @param sysExceptionLog
     * @param e
     * @return
     */
    public static SysExceptionLog fillSysExceptionLog(SysExceptionLog sysExceptionLog,Throwable e){
        String exceptionType = e.getClass().getName();
        String message = e.getMessage();
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> stackTraceList = new ArrayList<>();
        if(stackTrace!=null && stackTrace.length>0){
            for(StackTraceElement element:stackTrace){
                stackTraceList.add(element.toString());
            }
        }
        sysExceptionLog.setExceptionType(exceptionType);
        sysExceptionLog.setMessage(message);
        sysExceptionLog.setStackTrace(stackTraceList);
        return sysExceptionLog;
    }
}
