package com.project.location_tracking_system.infrastructure.redis;

import com.project.location_tracking_system.domain.model.Location;
import com.project.location_tracking_system.domain.ports.LocationRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisLocationRepository implements LocationRepository {

    private static final String KEY_PREFIX = "location:";

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisLocationRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(Location location) {
        String key = KEY_PREFIX + location.userId();

        redisTemplate.opsForValue().set(key, location);

        Object value = redisTemplate.opsForValue().get(key);
    }

    @Override
    public Location findByUserId(String userId) {
        Object value = redisTemplate.opsForValue().get(KEY_PREFIX + userId);

        return (Location) value;
    }
}
