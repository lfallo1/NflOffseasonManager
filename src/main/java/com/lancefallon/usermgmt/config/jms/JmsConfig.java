package com.lancefallon.usermgmt.config.jms;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {

	public static final String textMsgHealthcheckQueue = "text.healthcheck";

	@Bean
	public Queue textMessageHealthCheckQueue() {
		return new ActiveMQQueue(textMsgHealthcheckQueue);
	}
}
