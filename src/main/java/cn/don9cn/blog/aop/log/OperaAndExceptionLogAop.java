package cn.don9cn.blog.aop.log;

import cn.don9cn.blog.model.system.log.SysExceptionLog;
import cn.don9cn.blog.model.system.log.SysOperaLog;
import cn.don9cn.blog.support.operaresult.core.OperaResult;
import cn.don9cn.blog.service.system.log.interf.SysExceptionLogService;
import cn.don9cn.blog.service.system.log.interf.SysOperaLogService;
import cn.don9cn.blog.support.vue.VueImageUploadMsg;
import cn.don9cn.blog.util.AopUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: liuxindong
 * @Description: 用于拦截用户操作并生成操作日志
 * @Create: 2017/10/18 14:00
 * @Modify:
 */
@Aspect
@Component
public class OperaAndExceptionLogAop {

    @Autowired
    private SysOperaLogService sysOperaLogService;

    @Autowired
    private SysExceptionLogService sysExceptionLogService;

    private ThreadLocal<Long> costTimeThreadLocal = new ThreadLocal<>();

    private ThreadLocal<SysOperaLog> sysOperaLogThreadLocal = new ThreadLocal<>();

    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.DeleteMapping)"
    )
    public void logPoint(){}

    @Before("logPoint()")
    public void beforeExce(JoinPoint joinPoint) throws NoSuchMethodException {
        // 生成操作日志实体并存入本地线程变量中
        sysOperaLogThreadLocal.set(AopUtil.parseToSysOperaLog(joinPoint));
        // 方法执行前,开始计时
        costTimeThreadLocal.set(System.currentTimeMillis());
    }

    /*@After("logPoint()")
    public void afterExce(JoinPoint joinPoint){
        // @After注解: 切入点执行后调用自定义的动作,注意并不是在切入点返回值之后调用,而是在pjp.proceed()执行完毕后,return之前调用
    }*/

    @AfterThrowing(value = "logPoint()",throwing = "e")
    public void throwException(JoinPoint joinPoint,Throwable e){
        SysOperaLog sysOperaLog = sysOperaLogThreadLocal.get();
        Long startTime = costTimeThreadLocal.get();
        if(sysOperaLog!=null){
            sysOperaLog.setState("失败");
            sysOperaLog.setCostTime(System.currentTimeMillis()-startTime + "");
            // 保存异常日志记录
            SysExceptionLog sysExceptionLog =  new SysExceptionLog(sysOperaLog);
            sysExceptionLogService.baseInsert(AopUtil.fillSysExceptionLog(sysExceptionLog,e));
        }

    }

    @AfterReturning("logPoint()")
    public void afterReturning(JoinPoint joinPoint){
        // 为了不影响方法的执行,日志保存到数据库的动作在方法成功返回后执行
        SysOperaLog sysOperaLog = sysOperaLogThreadLocal.get();
        Long startTime = costTimeThreadLocal.get();
        if(sysOperaLog!=null && sysOperaLog.getIgnoreSave().equals("N")){
            sysOperaLog.setCostTime(System.currentTimeMillis()-startTime + "");
            sysOperaLogService.baseInsert(sysOperaLog);
        }
    }

    @Around("logPoint()")
    public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable{

        Object obj = pjp.proceed();

        // 根据操作的结果判断是否操作成功
        checkProceedResult(obj);

        return obj;
    }

    /**
     * 根据操作的结果判断是否操作成功
     * @param obj
     */
    private void checkProceedResult(Object obj){
        SysOperaLog sysOperaLog = sysOperaLogThreadLocal.get();
        if(sysOperaLog!=null){
            if(sysOperaLog.getIgnoreSave().equals("N")){
                if(obj instanceof OperaResult){
                    OperaResult operaResult = (OperaResult) obj;
                    if(operaResult.isSuccess()){
                        sysOperaLog.setState("成功");
                    }else{
                        sysOperaLog.setState("失败");
                    }
                }else if(obj instanceof VueImageUploadMsg){
                    VueImageUploadMsg uploadMsg = (VueImageUploadMsg) obj;
                    if(uploadMsg.getErrno()==0){
                        sysOperaLog.setState("成功");
                    }else{
                        sysOperaLog.setState("失败");
                    }
                }else{
                    sysOperaLog.setState("成功");
                }
            }
        }
    }



}
