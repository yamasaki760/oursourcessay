package com.timcast.flurbovurbo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcast.flurbovurbo.handlers.CurrentUserTextWebSocketHandler;
import com.timcast.flurbovurbo.handlers.FlurboTextWebSocketHandler;
import com.timcast.flurbovurbo.handlers.TimeLeftTextWebSocketHandler;

@Service
public class ProctorServiceImpl {

	private static final Integer SLEEP_SECONDS = 10;

	private static Logger logger = LoggerFactory.getLogger(ProctorServiceImpl.class);

	@Autowired
	FlurboTextWebSocketHandler flurboWsHandler;

	@Autowired
	TimeLeftTextWebSocketHandler timeLeftWsHandler;

	@Autowired
	CurrentUserTextWebSocketHandler currentUserWsHandler;
	
	@Autowired
	QueueService queueService;

	@RabbitListener(queues = "userqueue-1", exclusive = true, containerFactory = "prefetchOneRabbitListenerContainerFactory")
	public void listen(String in) {

		logger.info("Current User: " + in);

		ObjectMapper om = new ObjectMapper();

		try {

			JsonNode json = om.readTree(in);

			String message = "{\"name\":\"proctorSelection\",\"currentUser\":\"" + json.get("queueNumber").asText()
					+ "\",\"sessionId\":" + json.get("sessionId") + "}";

			logger.info(message);

			currentUserWsHandler.broadcastMessage(message);

			/** TODO: promote users a better way **/
			for (int i = 1; i <= SLEEP_SECONDS; ++i) {
				Thread.sleep(1000);
				String timerMessage = "{\"name\":\"proctorClock\",\"timeLeft\":\"" + (SLEEP_SECONDS - i) + "\"}";
				logger.info(message);
				currentUserWsHandler.broadcastMessage(message);
				timeLeftWsHandler.broadcastMessage(timerMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedRate = 1000)
	public void updateTotalUsersQueued() {
		
		int totalQueuedUsers = queueService.getQueuedUsersListSize(1);
		
		try {
			currentUserWsHandler.broadcastMessage("{\"totalQueued\":\"" + totalQueuedUsers + "\"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
