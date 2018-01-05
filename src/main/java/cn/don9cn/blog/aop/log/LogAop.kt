package cn.don9cn.blog.aop.log

import cn.don9cn.blog.model.system.log.SysExceptionLog
import cn.don9cn.blog.model.system.log.SysOperaLog
import cn.don9cn.blog.service.system.log.SysExceptionLogService
import cn.don9cn.blog.service.system.log.SysOperaLogService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.vue.VueImageUploadMsg
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
open class OperaAndExceptionLogAop {

    @Autowired
    private val sysOperaLogService: SysOperaLogService? = null

    @Autowired
    private val sysExceptionLogService: SysExceptionLogService? = null

    private val costTimeThreadLocal = ThreadLocal<Long>()

    private val sysOperaLogThreadLocal = ThreadLocal<SysOperaLog>()

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    fun logPoint() {
    }

    @Before("logPoint()")
    @Throws(NoSuchMethodException::class)
    fun beforeExce(joinPoint: JoinPoint) {
        // 生成操作日志实体并存入本地线程变量中
        sysOperaLogThreadLocal.set(LogAopUtil.parseToSysOperaLog(joinPoint))
        // 方法执行前,开始计时
        costTimeThreadLocal.set(System.currentTimeMillis())
    }

    /*@After("logPoint()")
    public void afterExce(JoinPoint joinPoint){
        // @After注解: 切入点执行后调用自定义的动作,注意并不是在切入点返回值之后调用,而是在pjp.proceed()执行完毕后,return之前调用
    }*/

    @AfterThrowing(value = "logPoint()", throwing = "e")
    fun throwException(joinPoint: JoinPoint, e: Throwable) {
        val sysOperaLog = sysOperaLogThreadLocal.get()
        val startTime = costTimeThreadLocal.get()
        sysOperaLog?.let {
            it.state = "失败"
            it.costTime = (System.currentTimeMillis() - startTime!!).toString()

            // 保存异常日志记录
            val sysExceptionLog = SysExceptionLog(sysOperaLog)
            sysExceptionLogService!!.baseInsert(LogAopUtil.fillSysExceptionLog(sysExceptionLog, e))
        }
    }

    @AfterReturning("logPoint()")
    fun afterReturning(joinPoint: JoinPoint) {
        // 为了不影响方法的执行,日志保存到数据库的动作在方法成功返回后执行
        val sysOperaLog = sysOperaLogThreadLocal.get()
        val startTime = costTimeThreadLocal.get()
        sysOperaLog?.let {
            if(it.ignoreSave == "N"){
                it.costTime = (System.currentTimeMillis() - startTime!!).toString()
                sysOperaLogService!!.baseInsert(it)
            }
        }
    }

    @Around("logPoint()")
    @Throws(Throwable::class)
    fun aroundExec(pjp: ProceedingJoinPoint): Any? {

        val obj:Any? = pjp.proceed()

        // 根据操作的结果判断是否操作成功
        checkProceedResult(obj)

        return obj
    }

    /**
     * 根据操作的结果判断是否操作成功
     * @param obj
     */
    private fun checkProceedResult(obj: Any?) {
        val sysOperaLog = sysOperaLogThreadLocal.get()
        sysOperaLog?.let { log ->
            if(log.ignoreSave == "N"){
                log.state = when(obj){
                    is ActionMsg -> {
                        if(obj.isSuccess) "成功" else "失败"
                    }
                    is VueImageUploadMsg -> {
                        if(obj.errno ==0) "成功" else "失败"
                    }
                    else -> {
                        "成功"
                    }
                }
            }
        }
    }


}