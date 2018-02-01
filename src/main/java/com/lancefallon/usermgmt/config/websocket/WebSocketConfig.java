package com.lancefallon.usermgmt.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        //anyone can subscribe, only authenticated users may send messages
        messages
                // message types other than MESSAGE and SUBSCRIBE
                .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.HEARTBEAT, SimpMessageType.UNSUBSCRIBE, SimpMessageType.DISCONNECT).permitAll()
                //SUBSCRIBE and MESSAGE require auth
                .simpTypeMatchers(SUBSCRIBE, MESSAGE).authenticated()
                // catch all
                .anyMessage().denyAll();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/shared", "/direct");
        registry.addEndpoint("/shared", "/direct").withSockJS();
    }

}
