package com.timcast.flurbovurbo.service;

import org.springframework.web.socket.WebSocketSession;

public interface QueueService {

	public int addUserToQueue(WebSocketSession session, int furboId) throws Exception;
	
	public int getQueuedUsersListSize(int furboId);
	
	public void flushQueue(int flurboId);
	
}
