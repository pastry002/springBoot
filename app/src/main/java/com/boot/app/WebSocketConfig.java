package com.boot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// STOMP
	// websocket에서 동작하는 메시징 프로토콜
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// SockJS 활성화
		registry.addEndpoint("/ws-stomp")
		.setAllowedOriginPatterns("*")
		.withSockJS();
	}
	
	// WebSocket 접속
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/chat");
		registry.enableSimpleBroker("/topic", "queue");
	}
}
