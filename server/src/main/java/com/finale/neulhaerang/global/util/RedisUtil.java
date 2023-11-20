package com.finale.neulhaerang.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {
	private final RedisTemplate<String, String> redisTemplate;
	private final String geoKey = "members";

	public void setData(String deviceToken, String refreshKey, Long expiredTime){
		redisTemplate.opsForValue().set(deviceToken, refreshKey, expiredTime, TimeUnit.MILLISECONDS);
	}

	public List<String> changeGeo(Point point, String deviceId) {
		redisTemplate.opsForGeo().add(geoKey, point, deviceId);
		return getAroundMember(deviceId);
	}

	public List<String> getAroundMember(String deviceId) {
		Metric metric = RedisGeoCommands.DistanceUnit.METERS;
		GeoResults<RedisGeoCommands.GeoLocation<String>> members = redisTemplate.opsForGeo().radius(geoKey, deviceId, new Distance(1000, metric));
		List<String> deviceIds = new ArrayList<>();
		members.forEach(geoLocationGeoResult -> {
			RedisGeoCommands.GeoLocation<String> content = geoLocationGeoResult.getContent();
			String device = content.getName();
			deviceIds.add(device);
			log.debug("Around Device Id : "+device);
		});
		return deviceIds;
	}

	public String getData(String key){
		return redisTemplate.opsForValue().get(key);
	}

	public void deleteData(String deviceToken){
		redisTemplate.delete(deviceToken);
		deleteGeo(deviceToken);
	}

	public void deleteGeo(String deviceToken) {
		redisTemplate.opsForGeo().remove(geoKey, deviceToken);
	}
}
