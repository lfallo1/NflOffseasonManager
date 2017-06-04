package com.lancefallon.usermgmt.config.actuator.health;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.lancefallon.usermgmt.config.jms.JmsTextMessageServiceImpl;

@Component
public class CustomHealthIndicator implements HealthIndicator {

	@Autowired
	private JmsTextMessageServiceImpl jmsTextMessageServiceImpl;
	
	@Override
	public Health health() {
		if(new Date().getTime() % 2 == 0){
			String msg = String.format("%s : %s", "ERR-001", "Dummy failure");
			jmsTextMessageServiceImpl.sendTextMessage(msg);
			return Health.down().withDetail("ERR-001", "Dummy failure").build();
		}
		return Health.up().build();
	}

}
