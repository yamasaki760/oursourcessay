package com.timcast.flurbovurbo.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TimeLeftTextWebSocketHandler extends TextWebSocketHandler {
	
	private static Logger logger = LoggerFactory.getLogger(TimeLeftTextWebSocketHandler.class);

	private List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

	@PostConstruct
	public void init() {
		logger.info("im alive");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		webSocketSessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		webSocketSessions.remove(session);
	}

	public void broadcastMessage(String msg) throws Exception {
		TextMessage tm = new TextMessage(msg);
		for (WebSocketSession webSocketSession : webSocketSessions) {
			webSocketSession.sendMessage(tm);
		}
	}
}
