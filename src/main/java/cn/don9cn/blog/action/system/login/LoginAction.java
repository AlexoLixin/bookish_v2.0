package cn.don9cn.blog.action.system.login;

import cn.booklish.sharp.client.SharpClient;
import cn.booklish.sharp.config.SharpAutoConfigureCenter;
import cn.don9cn.blog.annotation.SkipOperaLog;
import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.autoconfigs.websocket.msg.MsgWebSocketHandler;
import cn.don9cn.blog.model.system.LoginResult;
import cn.don9cn.blog.model.system.log.SysLoginLog;
import cn.don9cn.blog.model.system.rbac.SysRole;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.validatecode.ValidateCode;
import cn.don9cn.blog.plugins.validatecode.ValidateCodeCache;
import cn.don9cn.blog.service.bussiness.articleclassify.impl.ArticleClassifyServiceImpl;
import cn.don9cn.blog.service.bussiness.articleclassify.interf.ArticleClassifyService;
import cn.don9cn.blog.service.system.log.interf.SysLoginLogService;
import cn.don9cn.blog.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    @Autowired
    private MsgWebSocketHandler msgWebSocketHandler;

    /**
     * 提示登录
     * @return
     */
    @RequestMapping("/needLogin")
    public OperaResult needLogin(){
        return new OperaResult(false,"请先登录!");
    }

    /**
     * 提示重新登录
     * @return
     */
    @RequestMapping("/reLogin")
    public OperaResult reLogin(){
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
    public OperaResult noPermission(){
        return new OperaResult(false,"对不起,您没有相应的操作权限!");
    }

    /**
     * 注销登陆
     * @return
     */
    @RequestMapping("/logout")
    public OperaResult logout(){
        Subject subject = SecurityUtils.getSubject();
        String userNameFromSession = MyShiroSessionUtil.getUserNameFromSession();
        //关闭登录用户的webSocket连接
        msgWebSocketHandler.closeSession(userNameFromSession);
        //注销用户
        subject.logout();
        return new OperaResult(true,"注销成功!");
    }

    /**
     * 生成验证码
     * @return
     */
    @GetMapping("/generateValidateCode")
    public void generateValidateCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //生成文字验证码
        String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_LOWER, 4, null);
        //将验证码放入缓存
        //ValidateCodeCache.cache(verifyCode);
        request.getSession().setAttribute("verifyCode",verifyCode);
        //生成图片验证码
        BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 100, 30, 6,
                true, Color.WHITE, Color.BLACK, null);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param request
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/doLogin")
    public LoginResult doLogin(String username, String password, String validateCode, HttpServletRequest request) throws IOException, ServletException {

        //先校验验证码
        if(StringUtils.isBlank(validateCode)){
            return new LoginResult(false,"验证码校验失败");
        }else{
            Object verifyCode = request.getSession().getAttribute("verifyCode");
            if(verifyCode==null || !verifyCode.toString().equals(validateCode)){
                return new LoginResult(false,"验证码校验失败");
            }
        }

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

        return new LoginResult(true,"登陆成功!")
                                .setAdmin(false)
                                .setToken(MyShiroSessionUtil.getTokenFromSession())
                                .setUser(MyShiroSessionUtil.getUserFromSession());

    }

    /**
     * 检查当前用户是否是管理员
     * @return
     */
    @GetMapping("/authcUserRole")
    public OperaResult checkAdmin(){
        SysUser user = MyShiroSessionUtil.getUserFromSession();
        List<SysRole> roleList = user.getRoleList();
        if(roleList!=null && roleList.size()>0){
            for(SysRole role:roleList){
                if(role.getEncoding().contains("ADMIN")){
                    return new OperaResult(true,"验证成功!");
                }
            }
        }
        return new OperaResult(false,"对不起,验证失败,您不是系统管理员");
    }

    /*@Autowired
    private SharpAutoConfigureCenter sharpAutoConfigureCenter;

    @GetMapping("/test")
    public Object test(){
        ArticleClassifyService service = (ArticleClassifyService) sharpAutoConfigureCenter.getSharpClient()
                .getService("/rpc/cn/booklish/blogApplication/ArticleClassifyServiceImpl", ArticleClassifyService.class);

        return service.getTree();
    }*/



}
