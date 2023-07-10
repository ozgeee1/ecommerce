package com.ecommerce.usermanagement.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key,Object value,long expirationInMinutes){
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,expirationInMinutes, TimeUnit.MINUTES);
        get(key);

    }

    public Object get (String key){
        return redisTemplate.opsForValue().get(key);
    }
}
