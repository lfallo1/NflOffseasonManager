package com.lancefallon.usermgmt.config.actuator;

import java.util.Date;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AlternateCustomHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		if(new Date().getTime() % 2 == 0){
			return Health.down().withDetail("ERR-002", "Alternate dummy failure").build();
		}
		return Health.up().build();
	}

}
