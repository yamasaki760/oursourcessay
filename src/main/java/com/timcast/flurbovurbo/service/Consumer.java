package com.timcast.flurbovurbo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timcast.flurbovurbo.handlers.FlurboTextWebSocketHandler;

@Service
public class Consumer {
	
	private static Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	@Autowired 
	FlurboTextWebSocketHandler wsHandler;
	
	@RabbitListener(queues = "flurbovurbo", exclusive = true)
	public void listen(String in) {
	    logger.info("Received Message: " + in);
	    try {
			wsHandler.broadcastMessage(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}