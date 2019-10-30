package xx.yy.simple.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;
import xx.yy.simple.chat.entity.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
public class GreetingController {

    public static Map<String, Greeting> userMap = new ConcurrentHashMap<>();

    @Autowired
    private SimpUserRegistry userRegistry;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/login")
    @SendTo("/from:server/greetings")
    public Greeting login(SocketPrincipal principal, Greeting greeting) {
        // 单点登录
        if (userMap.containsKey(principal.getName())) {
            Greeting oldGreeting = userMap.get(principal.getName());
            if (!oldGreeting.getLoginId().equals(greeting.getLoginId())) {
                oldGreeting.setContent("你已在别处登录了!");
                messagingTemplate.convertAndSend("/from:server/single/" + oldGreeting.getToken() + oldGreeting.getLoginId(), oldGreeting);
            }
        }
        //greeting.setLoginId(String.valueOf(new Date().getTime()));
        greeting.setToken(principal.getToken());
        greeting.setContent(principal.getName() + " 加入聊天!");
        greeting.setUserCount(userRegistry.getUserCount());
        userMap.put(principal.getName(), greeting);

        // 所有用户列表
        // int i = 1;
        // for (SimpUser su : userRegistry.getUsers()) {
        //     log.info("用户" + i++ + "---" + principal);
        // }
        //
        // userRegistry.getUser(principal.getName());

        return greeting;
    }

    @MessageMapping("/logout")
    @SendTo("/from:server/greetings")
    public Greeting logout(SocketPrincipal principal) {
        Greeting greeting = userMap.remove(principal.getName());
        greeting.setContent(principal.getName() + " 离开聊天!");
        greeting.setUserCount(userRegistry.getUserCount());
        return greeting;
    }
}
