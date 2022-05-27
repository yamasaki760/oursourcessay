package com.timcast.flurbovurbo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.timcast.flurbovurbo.service.Consumer;

@Configuration
public class AmqpConfig {

	private static Logger logger = LoggerFactory.getLogger(Consumer.class);

	protected final String activityQueue = "flurbovurbo";

	@Bean
	public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchOneRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
	    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	    factory.setConnectionFactory(rabbitConnectionFactory);
	    factory.setPrefetchCount(1);
	    return factory;
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		connectionFactory.setVirtualHost("flurbovurbo");
		connectionFactory.setUsername("flurbovurbo");
		connectionFactory.setPassword("flurbovurbo");
		logger.info("Connection Factory Instantiated");
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		logger.info("Amqp Admin Instantiated");
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setExchange("flurbovurbo.exchange");
		template.setRoutingKey(this.activityQueue);
		template.setDefaultReceiveQueue(this.activityQueue);
		logger.info("RabbitTemplate: " + template.toString());
		return template;
	}

	@Bean
	public Queue helloWorldQueue() {
		return new Queue(this.activityQueue);
	}

}
