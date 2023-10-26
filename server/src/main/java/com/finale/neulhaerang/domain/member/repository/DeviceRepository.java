package com.finale.neulhaerang.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.member.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
	Optional<Device> findDeviceByDeviceToken(String deviceToken);
}
