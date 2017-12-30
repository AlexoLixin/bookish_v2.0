package cn.don9cn.blog.autoconfigure.websocket.chatroom;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: liuxindong
 * @Description: 聊天室webSocket处理器,处理消息的推送和接收
 * @Create: 2017/10/23 15:09
 * @Modify:
 */
@SuppressWarnings("Duplicates")
public class ChatRoomWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentMap<String,WebSocketSession> userMap;

    private static Logger logger = Logger.getLogger(ChatRoomWebSocketHandler.class);

    static {
        userMap = new ConcurrentHashMap<>();
    }

    public ChatRoomWebSocketHandler() {
    }

    /**
     * 连接成功时候，会触发前端onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String username = (String) session.getAttributes().get("chatRoom_msg_webSocket_user");
        userMap.put(username,session);
        System.out.println("MsgWebSocket: ["+username+"] 加入了聊天室... 当前在线人数: " + userMap.values().size());
        // 将新用户加入的消息推送给聊天室在线用户
        // 聊天室系统通知使用统一的消息格式"[$system$]username,num"
        String msgStr = "[$system$]"+username+" 加入了聊天室,"+userMap.values().size();
        sendMessageToAll(new TextMessage(msgStr));
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        String username= (String) session.getAttributes().get("chatRoom_msg_webSocket_user");
        userMap.remove(username);
        System.out.println("MsgWebSocket: 用户 [" + username + "] 离开了聊天室... 当前在线人数: " + userMap.values().size());
        // 将新用户离开的消息推送给聊天室在线用户
        // 聊天室系统通知使用统一的消息格式"[$system$]username,num"
        String msgStr = "[$system$]"+username+" 离开了聊天室,"+userMap.values().size();
        sendMessageToAll(new TextMessage(msgStr));
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username= (String) session.getAttributes().get("chatRoom_msg_webSocket_user");
        // 用户发送消息后,将消息推送给聊天室所有成员
        sendMessageToOthers(username,message);
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()) session.close();
        String username= (String) session.getAttributes().get("chatRoom_msg_webSocket_user");
        userMap.remove(username);
        System.out.println("MsgWebSocket: 发生异常,用户 [" + username + "] 停止连接聊天室... 当前在线人数: " + userMap.values().size());
    }

    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给聊天室所有在线用户发送消息
     * @param message
     */
    public void sendMessageToAll(TextMessage message) {
        userMap.values().forEach(session -> {
            if(session.isOpen()) try {
                session.sendMessage(message);
            } catch (IOException e) {
                System.out.println("MsgWebSocket: 推送给用户 ["+session.getAttributes().get("chatRoom_msg_webSocket_user")+"] 消息时发生异常...");
                e.printStackTrace();
            }
        });
    }

    /**
     * 给聊天室所有其他在线用户发送消息
     */
    public void sendMessageToOthers(String username,TextMessage message) {
        userMap.keySet()
                        .forEach(key -> {
                            WebSocketSession session = userMap.get(key);
                            if(session.isOpen()) try {
                                if(key.equals(username)){
                                    session.sendMessage(new TextMessage("[$self$]" + username +"{{,}}"+message.getPayload()));
                                }else{
                                    session.sendMessage(new TextMessage("[$other$]" + username +"{{,}}"+message.getPayload()));
                                }
                            } catch (IOException e) {
                                System.out.println("MsgWebSocket: 推送给用户 ["+key+"] 消息时发生异常...");
                                e.printStackTrace();
                            }
                        });
    }

}
