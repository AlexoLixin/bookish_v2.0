package cn.don9cn.blog.autoconfigs.websocket.msg;

import cn.don9cn.blog.util.UuidUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: liuxindong
 * @Description: websocket拦截器
 * @Create: 2017/10/23 15:02
 * @Modify:
 */
public class MsgWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    /**
     * 前端连接webSocket之前,获取并设置当前用户
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(true);
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
                String username = (String) session.getAttribute("CURRENT_USER");
                //当前session中没有用户信息,说明是匿名用户,随机生成一个uuid作为用户名
                if (username==null) {
                    username = UuidUtil.getUuid();
                }
                //因为用户名是唯一的,所以可以作为webSocket连接用户的唯一标识
                attributes.put("system_msg_webSocket_user",username);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
