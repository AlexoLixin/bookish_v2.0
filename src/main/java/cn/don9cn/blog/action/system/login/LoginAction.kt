package cn.don9cn.blog.action.system.login

import cn.don9cn.blog.annotation.SkipOperaLog
import cn.don9cn.blog.autoconfigure.shiro.core.MyShiroCacheManager
import cn.don9cn.blog.autoconfigure.shiro.util.ShiroUtil
import cn.don9cn.blog.autoconfigure.websocket.msg.MsgWebSocketHandler
import cn.don9cn.blog.model.system.login.LoginResult
import cn.don9cn.blog.service.system.log.SysLoginLogService
import cn.don9cn.blog.support.action.ActionMsg
import cn.don9cn.blog.support.validatecode.ValidateCode
import cn.don9cn.blog.util.LoginRequestUtil
import org.apache.commons.lang3.StringUtils
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.awt.Color
import java.io.IOException
import javax.imageio.ImageIO
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SkipOperaLog
@RestController
@RequestMapping("/login")
open class LoginAction {

    @Autowired
    private var sysLoginLogService: SysLoginLogService? = null

    @Autowired
    private var msgWebSocketHandler: MsgWebSocketHandler? = null

    /**
     * 提示登录
     */
    @RequestMapping("/needLogin")
    open fun needLogin(): ActionMsg {
        return ActionMsg(false, "请先登录!")
    }

    /**
     * 提示重新登录
     */
    @RequestMapping("/reLogin")
    open fun reLogin(): ActionMsg {
        SecurityUtils.getSubject()?.logout()
        return ActionMsg(false, "用户身份过期,请重新登录!")
    }

    /**
     * 提示权限不足
     */
    @RequestMapping("/noPermission")
    open fun noPermission(): ActionMsg {
        return ActionMsg(false, "对不起,您没有相应的操作权限!")
    }

    /**
     * 注销登陆
     */
    @RequestMapping("/logout")
    open fun logout(): ActionMsg {
        //关闭登录用户的webSocket连接
        msgWebSocketHandler!!.closeSession(MyShiroCacheManager.getUserName())
        //注销用户
        MyShiroCacheManager.getCurrentUserCache()?.clear()      //清空用户的shiro缓存(包括身份认证和权限认证等信息)
        SecurityUtils.getSubject()?.logout()                    //调用shiro提供的注销方法
        return ActionMsg(true, "注销成功!")
    }

    /**
     * 生成验证码
     */
    @GetMapping("/generateValidateCode")
    @Throws(IOException::class)
    open fun generateValidateCode(request: HttpServletRequest, response: HttpServletResponse) {
        //生成文字验证码
        val verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_LOWER, 4, null)
        //将验证码放入缓存
        //ValidateCodeCache.cache(verifyCode);
        request.session.setAttribute("verifyCode", verifyCode)
        //生成图片验证码
        val bim = ValidateCode.generateImageCode(verifyCode, 100, 30, 6,
                true, Color.WHITE, Color.BLACK, null)
        ImageIO.write(bim, "JPEG", response.outputStream)
    }

    /**
     * 登录
     */
    @RequestMapping("/doLogin")
    @Throws(IOException::class, ServletException::class)
    open fun doLogin(username: String, password: String, validateCode: String, rememberMe: Boolean, request: HttpServletRequest): LoginResult {

        //先校验验证码
        if (StringUtils.isBlank(validateCode)) {
            return LoginResult(false, "验证码校验失败")
        } else {
            val verifyCode = request.session.getAttribute("verifyCode")
            if (verifyCode == null || verifyCode.toString() != validateCode) {
                return LoginResult(false, "验证码校验失败")
            }
        }

        // 生成登录日志,无论登陆是否成功,都会保存该日志信息
        val loginLog = LoginRequestUtil.getLoginLog(request, username, password)

        val subject = SecurityUtils.getSubject()

        if (!subject.isAuthenticated) {
            val token = UsernamePasswordToken(username, password,rememberMe)
            try {
                subject.login(token)
            } catch (e: Exception) {
                sysLoginLogService!!.baseInsert(loginLog.withState("失败"))
                return LoginResult(false, "用户名或者密码错误")
            }
        }

        sysLoginLogService!!.baseInsert(loginLog.withState("成功"))
        //登陆成功,设置用户名到session,消息推送中用得到
        request.getSession(false).setAttribute("CURRENT_USER", username)

        return LoginResult(true, "登陆成功!")
                .setAdmin(false)
                .setToken(MyShiroCacheManager.getToken())
                .setUser(MyShiroCacheManager.getUser())

    }

    /**
     * 检查当前用户是否是管理员
     */
    @GetMapping("/authcUserRole")
    open fun checkAdmin(): ActionMsg {
        val user = MyShiroCacheManager.getUser()
        user?.roleList?.forEach{
            if (it.encoding!!.contains("ADMIN")) {
                return ActionMsg(true, "验证成功!")
            }
        }
        return ActionMsg(false, "对不起,验证失败,您不是系统管理员")
    }


}