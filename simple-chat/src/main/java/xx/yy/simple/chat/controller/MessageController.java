package xx.yy.simple.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import xx.yy.simple.chat.constant.Constants;
import xx.yy.simple.chat.entity.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@Controller
public class MessageController {

    @Autowired
    private SimpUserRegistry userRegistry;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 群聊
     */
    @MessageMapping("/messaging")
    @SendTo("/from:server/messaging")
    public Message messaging(SocketPrincipal principal, Message message) {
        message.setFrom(principal.getToken());
        message.setTo("ALL");
        message.setTime(new Date().getTime());
        // int i = 1;
        // for (SimpUser user : userRegistry.getUsers()) {
        //     log.info("用户" + i++ + "---" + user);
        // }
        // return new MessageOut(currentUser.getName() + " : " + message.getMessage());
        return message;
    }
}
