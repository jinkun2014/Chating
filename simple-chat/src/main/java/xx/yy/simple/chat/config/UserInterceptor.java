package xx.yy.simple.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import xx.yy.simple.chat.constant.Constants;
import xx.yy.simple.chat.controller.GreetingController;
import xx.yy.simple.chat.entity.*;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class UserInterceptor implements ChannelInterceptor {

    @Lazy
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Lazy
    @Autowired
    private SimpUserRegistry userRegistry;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        // if (StompCommand.CONNECT.equals(accessor.getCommand())) {
        //     SocketPrincipal principal = (SocketPrincipal) accessor.getSessionAttributes().get(Constants.WEBSOCKET_SESSION_USER);
        //     log.info("设置当前WebSocket的认证用户:user:{}", principal);
        //     accessor.setUser(principal);
        //     // Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
        //     // if (raw instanceof Map) {
        //     //     Object name = ((Map) raw).get("name");
        //     //     if (name instanceof LinkedList) {
        //     //         // 设置当前访问器的认证用户
        //     //         accessor.setUser(new User(((LinkedList) name).get(0).toString()));
        //     //     }
        //     // }
        // }
        SocketPrincipal principal = (SocketPrincipal) accessor.getSessionAttributes().get(Constants.WEBSOCKET_SESSION_USER);
        String sessionId = accessor.getSessionAttributes().get(Constants.SESSION_ID).toString();
        switch (accessor.getCommand()) {
            case CONNECT:
                connect(sessionId, principal, accessor);
                break;
            case DISCONNECT:
                disconnect(sessionId, principal, accessor);
                break;
            default:
                break;
        }
        return message;
    }

    //连接成功
    private void connect(String sessionId, SocketPrincipal principal, StompHeaderAccessor accessor) {
        log.info("STOMP Connect [name: {},sessionId: {},token: {}]", principal.getName(), sessionId, principal.getToken());

        // 绑定认证到Header
        accessor.setUser(principal);
    }

    //断开连接
    private void disconnect(String sessionId, SocketPrincipal principal, StompHeaderAccessor accessor) {
        log.info("STOMP Disconnect [name: {},sessionId: {},token: {}]", principal.getName(), sessionId, principal.getToken());
        if (GreetingController.userMap.containsKey(principal.getName())) {
            Greeting greeting = GreetingController.userMap.remove(principal.getName());
            greeting.setContent(principal.getName() + " 离开聊天!");
            greeting.setUserCount(userRegistry.getUserCount());
            messagingTemplate.convertAndSend("/from:server/greetings", greeting);
        }
    }

}
