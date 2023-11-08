package com.finale.neulhaerang.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.member.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
	Optional<Device> findDeviceByDeviceToken(String deviceToken);

	List<Device> findDevicesByMember_Id(long memberId);

	int deleteDeviceByDeviceToken(String deviceToken);
}
