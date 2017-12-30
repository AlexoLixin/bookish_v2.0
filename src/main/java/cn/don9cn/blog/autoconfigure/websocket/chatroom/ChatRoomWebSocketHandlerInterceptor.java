package cn.don9cn.blog.autoconfigure.websocket.chatroom;

import cn.don9cn.blog.autoconfigure.websocket.chatroom.util.RandomNameUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: liuxindong
 * @Description: 聊天室webSocket拦截器
 * @Create: 2017/10/23 15:08
 * @Modify:
 */
public class ChatRoomWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(true);
            if (session != null) {
                // 如果是登陆用户,就使用其用户名加入聊天室
                String username = (String) session.getAttribute("CURRENT_USER");
                // 否则随机创建一个用户名
                if (username==null) {
                    username = RandomNameUtil.get();
                }
                attributes.put("chatRoom_msg_webSocket_user",username);
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
