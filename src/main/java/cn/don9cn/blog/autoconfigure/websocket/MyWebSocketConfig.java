package cn.don9cn.blog.autoconfigure.websocket;

import cn.don9cn.blog.autoconfigure.websocket.chatroom.ChatRoomWebSocketHandler;
import cn.don9cn.blog.autoconfigure.websocket.chatroom.ChatRoomWebSocketHandlerInterceptor;
import cn.don9cn.blog.autoconfigure.websocket.msg.MsgWebSocketHandler;
import cn.don9cn.blog.autoconfigure.websocket.msg.MsgWebSocketHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Don9
 * @create 2017-09-27-14:47
 **/
@Configuration
@EnableWebSocket
public class MyWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    //允许连接的域,只能以http或https开头
    String[] allowsOrigins = {"*"};

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        //-------- 注册用于系统消息推送的WebSocket -------
        registry.addHandler(msgWebSocketHandler(),"/msgWebSocket")
                .setAllowedOrigins(allowsOrigins)
                .addInterceptors(msgWebSocketHandlerInterceptor());

        registry.addHandler(msgWebSocketHandler(), "/sockjs/msgWebSocket")
                .setAllowedOrigins(allowsOrigins)
                .addInterceptors(msgWebSocketHandlerInterceptor()).withSockJS();

        //-------- 注册用于聊天室功能的WebSocket -------
        registry.addHandler(chatRoomWebSocketHandler(),"/chatRoomWebSocket")
                .setAllowedOrigins(allowsOrigins)
                .addInterceptors(chatRoomWebSocketHandlerInterceptor());

        registry.addHandler(chatRoomWebSocketHandler(), "/sockjs/chatRoomWebSocket")
                .setAllowedOrigins(allowsOrigins)
                .addInterceptors(chatRoomWebSocketHandlerInterceptor()).withSockJS();
    }

    /**
     * 消息推送WebSocket处理器
     * @return
     */
    @Bean
    public MsgWebSocketHandler msgWebSocketHandler() {
        return new MsgWebSocketHandler();
    }

    /**
     * 消息推送WebSocket拦截器
     * @return
     */
    @Bean
    public MsgWebSocketHandlerInterceptor msgWebSocketHandlerInterceptor(){
        return new MsgWebSocketHandlerInterceptor();
    }

    /**
     * 聊天室WebSocket处理器
     * @return
     */
    @Bean
    public ChatRoomWebSocketHandler chatRoomWebSocketHandler() {
        return new ChatRoomWebSocketHandler();
    }

    /**
     * 聊天室WebSocket拦截器
     * @return
     */
    @Bean
    public ChatRoomWebSocketHandlerInterceptor chatRoomWebSocketHandlerInterceptor(){
        return new ChatRoomWebSocketHandlerInterceptor();
    }

}
