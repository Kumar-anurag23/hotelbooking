package org.hotelbooking;

import org.hotelbooking.Config.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
public class HotelbookingApplication implements CommandLineRunner {

	@Autowired
	private RedisTestService redisTestService;

	public static void main(String[] args) {
		SpringApplication.run(HotelbookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Test Redis connection after the application context is initialized
		redisTestService.testRedisConnection();
	}
}