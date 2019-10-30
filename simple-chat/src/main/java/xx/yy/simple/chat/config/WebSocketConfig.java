package xx.yy.simple.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // @Bean
    // public ServerEndpointExporter serverEndpointExporter() {
    //     return new ServerEndpointExporter();
    // }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // /*
        //  * 用户可以订阅来自"/topic"和"/all"的消息，
        //  * 在Controller中，可通过@SendTo注解指明发送目标，这样服务器就可以将消息发送到订阅相关消息的客户端
        //  *
        //  * 在本Demo中，使用topic来达到聊天室效果（单聊+多聊），使用all进行群发效果
        //  *
        //  * 客户端只可以订阅这两个前缀的主题
        //  */
        // config.enableSimpleBroker("/topic","/all");
        //
        // /*
        //  * 客户端发送过来的消息，需要以"/app"为前缀，再经过Broker转发给响应的Controller
        //  */
        // config.setApplicationDestinationPrefixes("/app");

        /**
         * 服务器监听客户端地址前缀，客户端通过此地址前缀向服务器发送消息
         */
        config.setApplicationDestinationPrefixes("/from:app");
        /**
         * 发送给客户端消息的地址前缀，客户端可以绑定监听此地址前缀
         */
        config.enableSimpleBroker("/from:server");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        /**
         * 路径"/chatroom"被注册为STOMP端点，对外暴露，客户端通过该路径接入WebSocket服务
         */
        registry.addEndpoint("/chatroom")
                .setAllowedOrigins("*")
                .addInterceptors(new SessionAuthHandshakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(500 * 1024 * 1024);
        registration.setSendBufferSizeLimit(1024 * 1024 * 1024);
        registration.setSendTimeLimit(200000);
    }

    /**
     * 配置客户端入站通道拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(userInterceptor());
    }


    /*将客户端渠道拦截器加入spring ioc容器*/
    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }
}
