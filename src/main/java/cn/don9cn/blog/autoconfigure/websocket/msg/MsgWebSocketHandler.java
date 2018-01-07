package cn.don9cn.blog.autoconfigure.websocket.msg;

import cn.don9cn.blog.autoconfigure.activemq.core.MqConsumerGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: liuxindong
 * @Description: 系统消息webSocket处理器
 * @Create: 2017/10/23 15:31
 * @Modify:
 */
public class MsgWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private MqConsumerGenerator mqConsumerGenerator;

    private static final ConcurrentMap<String,WebSocketSession> userMap;

    private static final ConcurrentMap<String,CompletableFuture<Void>> futureMap;

    private static Logger logger = Logger.getLogger(MsgWebSocketHandler.class);

    static {
        userMap = new ConcurrentHashMap<>();
        futureMap = new ConcurrentHashMap<>();
    }

    public MsgWebSocketHandler() {
    }

    /**
     * 连接成功时候，会触发前端onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String username = (String) session.getAttributes().get("system_msg_webSocket_user");
        userMap.put(username,session);
        logger.info("MsgWebSocket: 用户 ["+username+"] 连接成功. 当前用户数量: " + userMap.values().size());
        // 开始监听并推送消息
        mqConsumerGenerator.startListen(username,this);

    }

    /**
     * 关闭连接时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        String username= (String) session.getAttributes().get("system_msg_webSocket_user");
        userMap.remove(username);
        logger.info("MsgWebSocket: 用户 [" + username + "] 退出连接... 当前用户数量: " + userMap.values().size());
        mqConsumerGenerator.closeListen(username);

    }

    /**
     * js调用websocket.send时候，会调用该方法
     * 由于系统消息的发布走的是kafka客户端,所以在系统消息推送模块,用不到webSocket的send发送
     * 此方法可以暂时不去实现
     * 在聊天室的handler中,此方法是重点
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()) session.close();
        String username= (String) session.getAttributes().get("system_msg_webSocket_user");
        userMap.remove(username);
        mqConsumerGenerator.closeListen(username);
        logger.info("MsgWebSocket: 发生异常,用户 [" + username + "] 退出连接... 当前用户数量: " + userMap.values().size());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 推送消息
     */
    public void sendToUser(String username,String message){

        try {
            WebSocketSession session = userMap.get(username);
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            logger.error("MsgWebSocket: 推送给用户 [" + username + "] 消息时发生异常,推送失败...");
            e.printStackTrace();
        }
    }

    /**
     * 关闭用户的webSocket推送
     */
    public void closeSession(String username){
        try{
            WebSocketSession session = userMap.get(username);
            if(session!=null && session.isOpen()){
                session.close();    //触发afterConnectionClosed方法
            }
        } catch (IOException e) {
            logger.error("MsgWebSocket: 用户 [" + username + "] 的会话session关闭失败...");
            mqConsumerGenerator.closeListen(username);
        }
    }


    /**
     * 内部类,用于封装 用户-未读信息 的对应关系
     */
    private class TempMap{

        private final String username;

        private final String message;

        private TempMap(String username, String message) {
            this.username = username;
            this.message = message;
        }

        public String getUsername() {
            return username;
        }

        public String getMessage() {
            return message;
        }
    }

}
