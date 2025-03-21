package org.hotelbooking.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class RedisTestService {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    public void testRedisConnection() {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            System.out.println("✅ Redis Connected: " + connection.ping());
        } catch (Exception e) {
            System.err.println("❌ Redis Connection Failed: " + e.getMessage());
        }

    }
}
