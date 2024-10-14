package se.pj.tbike.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import se.pj.tbike.util.cache.StorageManager;

@Configuration
public class Global {

	@Bean
	public static TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize( 10 );
		return scheduler;
	}

	@Bean
	public static StorageManager storageManager( TaskScheduler scheduler ) {
		return new StorageManager( scheduler );
	}
}
