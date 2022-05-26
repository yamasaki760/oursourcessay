package com.timcast.flurbovurbo.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcast.flurbovurbo.service.CacheService;
import com.timcast.flurbovurbo.service.QueueService;

@Component
public class FlurboTextWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	CacheService cacheService;

	@Autowired
	QueueService queueService;
	
	private static Logger logger = LoggerFactory.getLogger(FlurboTextWebSocketHandler.class);

	private List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

	
	@PostConstruct
	public void init() {
		logger.info("im alive");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		super.afterConnectionEstablished(session);
		webSocketSessions.add(session);

		TextMessage tm = null;
		
		int queueNumber = queueService.addUserToQueue(session, 1);

		String doc = cacheService.getCurrentVurboDocument(1);
		tm = new TextMessage("{\"name\":\"CurrentDocument\",\"message\":\"" + doc + "\",\"queueNumber\":\"" + queueNumber + "\",\"sessionId\":\"" + session.getId() + "\"}");
		session.sendMessage(tm);

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		webSocketSessions.remove(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);
		
		try {
			logger.info("GOT A MESSAGE!!!!!");
			
			ObjectMapper om = new ObjectMapper();
			JsonNode json = om.readTree(message.getPayload().toString());
			
			String[] words = json.get("message").asText().split(" ");
			
			String appendVurbo = words[0];
			
			logger.info("vurbo to append: [" + appendVurbo + "]");

			/**
			 * 
			 * dont broadcast message here.  this only works PER JVM 
			 * 
			 * we want EVERYONE on EVERY JVM to get the message (use an amqp broker)
			 * 
			 */
			
			cacheService.appendVurboToFlurbo(session.toString(), 1, appendVurbo);

			broadcastMessage(appendVurbo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

	public void broadcastMessage(String msg) throws Exception {
		
		TextMessage tm = new TextMessage("{\"name\":\"AppendToDocument\",\"message\":\"" + msg + "\"}");
		
		for (WebSocketSession webSocketSession : webSocketSessions) {
			webSocketSession.sendMessage(tm);
		}

	}
	
	
}
