package xx.yy.simple.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import xx.yy.simple.chat.constant.Constants;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 从session里取
 */
@Slf4j
public class SessionAuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpSession session = getSession(request);
        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            log.error("用户未登录,websocket权限拒绝!");
            throw new RuntimeException("用户未登录,websocket权限拒绝!");
        }
        log.info("将登录的用户[{}]放进websocket里!", session.getAttribute(Constants.SESSION_USER));
        attributes.put(Constants.SESSION_ID, session.getId());
        attributes.put(Constants.WEBSOCKET_SESSION_USER, session.getAttribute(Constants.SESSION_USER));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}
