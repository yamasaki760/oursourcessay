package com.timcast.flurbovurbo.service;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class QueueServiceImpl implements QueueService {

	private static Logger logger = LoggerFactory.getLogger(QueueServiceImpl.class);
	
	private final String USERQUEUE = "userqueue";
	
	@Autowired
	AmqpAdmin rabbitAdmin;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Override
	public int addUserToQueue(WebSocketSession session, int furboId) throws Exception {

		String queueName = generateQueueName(furboId);
		
		//Queue queue = new Queue(queueName, true);
		
        //rabbitAdmin.declareQueue(queue);
        
		int queueNumber = (getQueuedUsersListSize(furboId) + 1);
		
		logger.info("adding user session: [" + session.getId() + "] to queued users for furboId: [" + furboId + "]");

		String json = new String("{\"queueNumber\":\"" + queueNumber + "\",\"sessionId\":\"" + session.getId() + "\"}");

		rabbitTemplate.convertAndSend(queueName, json);
		
        return queueNumber;
        
	}

	@Override
	public int getQueuedUsersListSize(int furboId) {
		
		String queueName = generateQueueName(furboId);
		
		Properties props = rabbitAdmin.getQueueProperties(queueName);
		
		logger.debug(props.toString());
		
		Integer usersQueued = Integer.parseInt(props.get("QUEUE_MESSAGE_COUNT").toString());
		
		logger.debug("[" + usersQueued + "] users queued in system for queue: [" + queueName + "]");

		return usersQueued;
		
	}
	
	public void flushQueue(int flurboId) {
	
		rabbitAdmin.purgeQueue(generateQueueName(flurboId));
		
	}
	
	private String generateQueueName(int furboId) {
		
		String queueName = USERQUEUE + "-" + furboId;
		
		return queueName;
	}
}
