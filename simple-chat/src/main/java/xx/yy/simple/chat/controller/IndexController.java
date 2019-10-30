package xx.yy.simple.chat.controller;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xx.yy.simple.chat.constant.Constants;
import xx.yy.simple.chat.entity.SocketPrincipal;
import xx.yy.simple.chat.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(HttpSession httpSession, String name) {
        // SocketPrincipal principal = new SocketPrincipal();
        // principal.setName(name);
        // httpSession.setAttribute("SESSION_USER", principal);
        // log.info("[{}]:登录成功跳转到聊天界面", principal.getName());
        return "index";
    }

    /**
     * 用户名和密码登录生成Token
     *
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Object login(HttpSession httpSession, @RequestBody User user) {
        Assert.notBlank(user.getUsername(), "用户名不允许为空!");
        Assert.notBlank(user.getPassword(), "密码不允许为空!");

        // 验证登录
        String token = String.format("%s%s%s", user.getUsername(), httpSession.getId(), new Date().getTime());
        user.setToken(token);

        // 登录成功后传递唯一标志(Token)给WebSocket
        httpSession.setAttribute(Constants.SESSION_USER, new SocketPrincipal(user.getUsername(), token));

        // 返回成功
        Map<String, Object> resultMap = new HashMap<>();
        user.setPassword("");
        resultMap.put("data", user);
        resultMap.put("resultCode", "00000");
        return resultMap;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
