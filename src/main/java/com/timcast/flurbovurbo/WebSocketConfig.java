package com.timcast.flurbovurbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.timcast.flurbovurbo.handlers.CurrentUserTextWebSocketHandler;
import com.timcast.flurbovurbo.handlers.FlurboTextWebSocketHandler;
import com.timcast.flurbovurbo.handlers.TimeLeftTextWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	FlurboTextWebSocketHandler flurboSocketHandler;

	@Autowired
	TimeLeftTextWebSocketHandler timeLeftSocketHandler;

	@Autowired
	CurrentUserTextWebSocketHandler currentUserSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(flurboSocketHandler, "/flurbovurbo");
        webSocketHandlerRegistry.addHandler(timeLeftSocketHandler, "/timeLeft");
        webSocketHandlerRegistry.addHandler(currentUserSocketHandler, "/currentUser");
    }

}