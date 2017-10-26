package cn.don9cn.blog.autoconfigs.websocket.msg;

import cn.don9cn.blog.model.system.msg.SysMessage;
import cn.don9cn.blog.util.ExecutorUtil;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * @Author: liuxindong
 * @Description: 系统消息webSocket处理器
 * @Create: 2017/10/23 15:31
 * @Modify:
 */
public class MsgWebSocketHandler extends TextWebSocketHandler {

    /*@Autowired
    private MessageConsumer messageConsumer;*/

    private static final ConcurrentMap<String,WebSocketSession> userMap;

    private static Logger logger = Logger.getLogger(MsgWebSocketHandler.class);

    static {
        userMap = new ConcurrentHashMap<>();
    }

    public MsgWebSocketHandler() {
    }

    /**
     * 连接成功时候，会触发前端onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String username = (String) session.getAttributes().get("system_msg_webSocket_user");
        userMap.put(username,session);
        System.out.println("MsgWebSocket: 新用户 ["+username+"] 连接成功... 当前用户数量: " + userMap.values().size());
        // 登录用户从kafka读取未读消息,并由webSocket推送到前端
        /*Consumer<String, String> consumer = messageConsumer.build(username);
        List<SysMessage> messageList = messageConsumer.consumeSystemMsg(consumer);
        session.sendMessage(new TextMessage(JSON.toJSONString(messageList)));*/
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        String username= (String) session.getAttributes().get("system_msg_webSocket_user");
        userMap.remove(username);
        System.out.println("MsgWebSocket: 用户 [" + username + "] 退出连接... 当前用户数量: " + userMap.values().size());

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

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()) session.close();
        String username= (String) session.getAttributes().get("system_msg_webSocket_user");
        userMap.remove(username);
        System.out.println("MsgWebSocket: 发生异常,用户 [" + username + "] 退出连接... 当前用户数量: " + userMap.values().size());
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 管理员发布新的系统消息,推送给所有在线用户
     */
    public void sendMessageToAll() {
        int poolSize = userMap.keySet().size();
        /*userMap.keySet().forEach(username ->{
            Consumer<String, String> consumer = messageConsumer.build(username);
            List<SysMessage> messageList = messageConsumer.consumeSystemMsg(consumer);
            String msgStr = JSON.toJSONString(messageList);
            WebSocketSession session = userMap.get(username);
            try {
                if (session != null && session.isOpen()) {
                    session.sendMessage(new TextMessage(msgStr));
                }
            } catch (IOException e) {
                System.out.println("MsgWebSocket: 推送给用户 [" + username + "] 消息时发生异常...");
                e.printStackTrace();
            }
        });*/
        /*Stream<CompletableFuture<Void>> futureStream = userMap.keySet().stream()
                // 第一步,对当前所有的登录用户进行异步消费消息
                .map(username -> CompletableFuture.supplyAsync(() -> {
                    System.out.println("1.创建消费者:"+username);
                    Consumer<String, String> consumer = messageConsumer.build(username);
                    List<SysMessage> messageList = messageConsumer.consumeSystemMsg(consumer);
                    String msgStr = JSON.toJSONString(messageList);
                    System.out.println("2.消费到消息:"+msgStr);
                    return new TempMap(username,msgStr);
                }, ExecutorUtil.build(poolSize)))
                // 第二步,将每个用户消费到的消息异步通过websocket推送到前端(查询出一个,推送出一个)
                .map(future -> future.thenAcceptAsync(tempMap -> {
                    String username = tempMap.getUsername();
                    WebSocketSession session = userMap.get(username);
                    System.out.println("3.推送消息到:"+username);
                    try {
                        if (session != null && session.isOpen()) {
                            session.sendMessage(new TextMessage(tempMap.getMessage()));
                        }
                    } catch (IOException e) {
                        System.out.println("MsgWebSocket: 推送给用户 [" + username + "] 消息时发生异常...");
                        e.printStackTrace();
                    }
                },ExecutorUtil.build(poolSize)));
        CompletableFuture[] futures = futureStream.toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();*/
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
