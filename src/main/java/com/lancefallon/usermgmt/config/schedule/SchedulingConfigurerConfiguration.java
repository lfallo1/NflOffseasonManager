package com.lancefallon.usermgmt.config.schedule;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * configure scheduling of cron jobs
 * @author lancefallon
 *
 */
public class SchedulingConfigurerConfiguration implements SchedulingConfigurer {
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(100);
		taskScheduler.initialize();
		taskRegistrar.setTaskScheduler(taskScheduler);
	}
}
