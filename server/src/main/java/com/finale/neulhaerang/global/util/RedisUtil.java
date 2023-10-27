package com.finale.neulhaerang.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisUtil {
	private final RedisTemplate<String, String> redisTemplate;

	public void setData(String key, String value, Long expiredTime){
		redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
	}

	public String getData(String key){
		return redisTemplate.opsForValue().get(key);
	}

	public void deleteData(String key){
		redisTemplate.delete(key);
	}
}
